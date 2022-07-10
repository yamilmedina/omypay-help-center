package cl.company.omypay.helpcenter.feedback.infrastructure.web;

import cl.company.omypay.helpcenter.utils.FixtureBuilder;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
//@QuarkusTestResource(SesResource.class)
class UserFeedbackResourceIntegrationTest {

    // @Test
    @Disabled("to fix with testcontainers")
    public void shouldRespondOkWhenParamsAreCorrectsAndHaveValues() {
        Response response = given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .body(generateFeedbackUserRequest("omypay lover", "user@happy.cl", "I love omypay"))
                .when()
                .post("/api/v1/feedback").thenReturn();

        assertEquals(200, response.statusCode());
        assertJsonEquals(new FixtureBuilder().useFixture("feedback/api_feedback_sent_response_ok.json").build(), response.body().asString());
    }

    // @Test
    @Disabled("to fix with testcontainers")
    public void shouldRaiseAnErrorToTheClientWhenParamsAreNotOk() {
        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .body(generateFeedbackUserRequest("omypay lover", "user@happy.cl", null))
                .when()
                .post("/api/v1/feedback")
                .then()
                .statusCode(is(400));
    }

    private UserFeedbackRequest generateFeedbackUserRequest(String name, String email, String message) {
        return new UserFeedbackRequest(name, email, message);
    }

}