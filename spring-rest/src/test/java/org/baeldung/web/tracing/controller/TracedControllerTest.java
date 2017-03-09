package org.baeldung.web.tracing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.baeldung.AppRunner;
import org.baeldung.web.dto.BaeldungItem;
import org.baeldung.web.dto.ItemDetails;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@Profile("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppRunner.class)
public class TracedControllerTest {
    @LocalServerPort
    private int serverPort;

    @Rule
    public WireMockRule serviceMock = new WireMockRule(8089);

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void givenExternalMicroservice_whenSendPostRequest_thenRequestHasTraceIdWhenCallOtherMicroservice() throws JsonProcessingException {
        serviceMock.stubFor(get(urlEqualTo("/item/item-id"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(new ItemDetails("description-for-the-item")))));


        given().body(new BaeldungItem("item-id"))
                .when()
                .post(getBaseUrl() + "/item")
                .then()
                .statusCode(200);
    }

    private String getBaseUrl() {
        return String.format("http://localhost:%d", serverPort);
    }

}