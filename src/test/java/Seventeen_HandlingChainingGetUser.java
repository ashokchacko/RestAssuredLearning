import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class Seventeen_HandlingChainingGetUser {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test
    void getUser(ITestContext context)
    {
        //This should come from createUser request and is used in the test level in testng1.xml file
        //int id = (int) context.getAttribute("user_id");

        //This should come from createUser request and is used in the suite level in testng1.xml file
        int id = (int) context.getSuite().getAttribute("user_id");

        String bearerToken = (String) context.getSuite().getAttribute("token");
        //String bearerToken = "b9d131c2c1742eead3e729af912a405f1cc97ed61af874ec946eacf55e7ee31b";

        given()
                    .headers("Authorization", "Bearer " +bearerToken)
                    .pathParam("id", id)
                .when()
                    .get("https://gorest.co.in/public/v2/users/{id}")
                .then()
                    .statusCode(200)
                    .log().all();
    }

}
