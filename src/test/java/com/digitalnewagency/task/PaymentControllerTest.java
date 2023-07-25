package com.digitalnewagency.task;

import com.digitalnewagency.task.api.dto.AccountDto;
import com.digitalnewagency.task.persistence.entity.Payment;
import com.digitalnewagency.task.persistence.repository.PaymentRepository;
import com.digitalnewagency.task.service.AccountService;
import com.digitalnewagency.task.service.MerchantService;
import com.digitalnewagency.task.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PaymentControllerTest {

    @Value("${server.port}")
    int port;

    @Autowired
    private UserService userService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentRepository paymentRepository;

    private String thereIsAUser() {
        return userService.addUser("Jan Kowalski", "jan.kowalski@digitalnewagency.com").getUserId().toString();
    }

    private String thereIsAMerchant() {
        return merchantService.addMerchant("DNA").getMerchantId().toString();
    }

    private BigDecimal userHasPositiveAccountBalance(String userId) {
        UUID accountId = accountService.getAccountForUser(UUID.fromString(userId)).getAccountId();
        return accountService.increaseBalance(accountId, BigDecimal.valueOf(100)).getBalance();
    }


    @Test
    void shouldSaveUser() throws URISyntaxException, IOException, InterruptedException, JSONException {
        // given
        String userId = thereIsAUser();
        String merchantId = thereIsAMerchant();
        BigDecimal initialBalance = userHasPositiveAccountBalance(userId);

        // when
        BigDecimal amount = new BigDecimal(10);
        String transactionPayload = """
                {
                	"userId": "%s",
                	"merchantId": "%s",
                	"amount": "%.4f"
                }
                """.formatted(userId, merchantId, amount);

        URI uri = URI.create("http://localhost:%d/transactions".formatted(port));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(transactionPayload))
                .build();
        HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(201);
        JSONObject jsonObject = new JSONObject(response.body());

        responseBodyContainsValidData(jsonObject, userId, merchantId, amount);
        String paymentId = jsonObject.getString("paymentId");

        validPaymentEntityCreated(paymentId, userId, merchantId, amount);
        accountBalanceDecreased(userId, initialBalance, amount);

    }

    private static void responseBodyContainsValidData(
            JSONObject jsonObject, String userId, String merchantId, BigDecimal amount) throws JSONException {
        assertThat(jsonObject.has("paymentId")).isTrue();
        assertThat(jsonObject.has("userId")).isTrue();
        assertThat(jsonObject.get("userId")).isEqualTo(userId);
        assertThat(jsonObject.has("merchantId")).isTrue();
        assertThat(jsonObject.get("merchantId")).isEqualTo(merchantId);
        assertThat(jsonObject.has("amount")).isTrue();
        assertThat(jsonObject.getDouble("amount")).isEqualTo(amount.doubleValue());
    }

    private void validPaymentEntityCreated(String paymentId, String userId, String merchantId, BigDecimal amount) {
        Optional<Payment> paymentOptional = paymentRepository.findOneByPaymentId(UUID.fromString(paymentId));
        assertThat(paymentOptional.isPresent()).isTrue();
        assertThat(paymentOptional.get().getPaymentId().toString()).isEqualTo(paymentId);
        assertThat(paymentOptional.get().getUserId().toString()).isEqualTo(userId);
        assertThat(paymentOptional.get().getMerchantId().toString()).isEqualTo(merchantId);
        assertThat(paymentOptional.get().getAmount()).isEqualByComparingTo(amount);
    }

    private void accountBalanceDecreased(String userId, BigDecimal initialBalance, BigDecimal paymentAmount) {
        AccountDto accountDto = accountService.getAccountForUser(UUID.fromString(userId));
        BigDecimal expectedBalance = initialBalance.subtract(paymentAmount);
        assertThat(accountDto.getBalance()).isEqualByComparingTo(expectedBalance);
    }
}
