import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Six_HandlingPathAndQueryParameters {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test
    void pathAndQueryParameters()
    {
        given()
                    .header("x-api-key", "reqres-free-v1")
                    .pathParams("path","users") //Path parameter
                    .queryParam("page", 2) //Query parameter
                    .queryParam("id", 4) //Query parameter
                .when()
                    .get("https://reqres.in/api/{path}")
                .then()
                    .statusCode(200)
                    .log().all();
    }

}
