package com.digitalnewagency.task;

import com.digitalnewagency.task.persistence.entity.Account;
import com.digitalnewagency.task.persistence.entity.User;
import com.digitalnewagency.task.persistence.repository.AccountRepository;
import com.digitalnewagency.task.persistence.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    @Value("${server.port}")
    int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private final BasicJsonTester jsonTester = new BasicJsonTester(getClass());

    @Test
    void shouldSaveTransaction() throws URISyntaxException, IOException, InterruptedException, JSONException {
        URI uri = URI.create("http://localhost:%d/users".formatted(port));

        // given
        String fullName = "John Doe";
        String email = "john.doe@digitalnewagency.com";

        String userPayload = """
                {
                	"fullName": "%s",
                	"email": "%s"
                }
                """.formatted(fullName, email);
        ;

        // when
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(userPayload))
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(201);
        JSONObject jsonObject = new JSONObject(response.body());
        responseBodyContainsValidData(jsonObject, fullName, email);
        String userId = jsonObject.getString("userId");
        validUserEntityCreated(userId, fullName, email);

        validAccountEntityCreated(userId);
    }

    private static void responseBodyContainsValidData(JSONObject jsonObject, String fullName, String email)
            throws JSONException {
        assertThat(jsonObject.has("userId")).isTrue();
        assertThat(jsonObject.has("fullName")).isTrue();
        assertThat(jsonObject.get("fullName")).isEqualTo(fullName);
        assertThat(jsonObject.has("email")).isTrue();
        assertThat(jsonObject.get("email")).isEqualTo(email);
    }

    private void validUserEntityCreated(String userId, String fullName, String email) {
        Optional<User> userOptional = userRepository.findOneByUserId(UUID.fromString(userId));
        assertThat(userOptional.isPresent()).isTrue();
        assertThat(userOptional.get().getFullName()).isEqualTo(fullName);
        assertThat(userOptional.get().getEmail()).isEqualTo(email);
    }

    private void validAccountEntityCreated(String userId) {
        Optional<Account> accountOptional = accountRepository.findOneByUserId(UUID.fromString(userId));
        assertThat(accountOptional.isPresent()).isTrue();
        assertThat(accountOptional.get().getAccountId()).isNotNull();
        assertThat(accountOptional.get().getBalance()).isZero();
    }
}
