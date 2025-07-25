import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class Eight_HandlingLogs {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test
    void getLogs()
    {
        given()
                    .header("x-api-key", "reqres-free-v1")
                .when()
                    .get("https://reqres.in/api/users?page=2")
                .then()
                    //.log().all()
                    .log().body();
                    //.log().headers()
                    //.log().cookies();
    }

}
