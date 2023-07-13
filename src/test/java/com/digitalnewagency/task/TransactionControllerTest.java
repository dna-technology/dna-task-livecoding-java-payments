package com.digitalnewagency.task;

import com.digitalnewagency.task.persistence.Transaction;
import com.digitalnewagency.task.persistence.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TransactionControllerTest {

	@Value("${server.port}")
	int port;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	void shouldSaveTransaction() throws URISyntaxException, IOException, InterruptedException {
		// given
		// when
		String transactionPayload = """
			{
				"userId": "0720f0d6-0e82-4be5-98f0-72c2c9f229a6"
			}
			""";

		URI uri = URI.create("http://localhost:%d/transactions".formatted(port));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.POST(HttpRequest.BodyPublishers.ofString(transactionPayload))
				.build();
		HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

		// then
		List<Transaction> transactions = transactionRepository.findAll();
		assertThat(transactions).hasSize(1);
	}

}
