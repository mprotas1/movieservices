package com.movieapp.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.*;

import java.time.Duration;

@Component
@Lazy
class UsersTestClient {
    @Value(value = "${local.server.port}")
    private int port;
    private static final String path = "/users";

    public WebTestClient newWebClient() {
        return WebTestClient
                .bindToServer()
                .baseUrl(baseUri())
                .responseTimeout(Duration.ofSeconds(90))
                .build();
    }

    public ResponseSpec getResponseSpecForRequestBody(Object request) {
        return this.getPostRequestBodySpec()
                .bodyValue(request)
                .exchange();
    }

    public RequestBodySpec getPostRequestBodySpec() {
        return this
                .newWebClient()
                .post()
                .uri(path);
    }

    public ResponseSpec getDeleteWithIdResponseSpec(Long id) {
        return this.newWebClient()
                .delete()
                .uri(path + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
    }

    public ResponseSpec requestSpecForGetById(int id) {
        return this.newWebClient()
                .get()
                .uri(path + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
    }

    public String baseUri() {
        return "http://localhost:" + port;
    }

}
