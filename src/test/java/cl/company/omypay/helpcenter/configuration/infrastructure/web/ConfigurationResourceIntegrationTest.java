package cl.company.omypay.helpcenter.configuration.infrastructure.web;

import cl.company.omypay.helpcenter.utils.FixtureBuilder;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ConfigurationResourceIntegrationTest {

    // @Test
    @Disabled("to fix with testcontainers")
    public void shouldRespondOkAndTogglesWhenFetchedWithSuccess() {
        Response response = given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/configuration")
                .thenReturn();

        assertEquals(200, response.statusCode());
        assertJsonEquals(new FixtureBuilder().useFixture("configuration/api_configuration_toggles_response_ok.json").build(), response.body().asString());
    }

    // @Test
    @Disabled("to fix with testcontainers")
    public void shouldRaiseAnErrorToTheClientWhenParamsAreNotOk() {
        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/v1/configuration")
                .then()
                .statusCode(is(503));
    }

}