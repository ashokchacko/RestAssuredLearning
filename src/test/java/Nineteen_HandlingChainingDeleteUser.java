import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Nineteen_HandlingChainingDeleteUser {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test
    void deleteUser(ITestContext context)
    {
        String bearerToken = "b9d131c2c1742eead3e729af912a405f1cc97ed61af874ec946eacf55e7ee31b";

        //This should come from create user request and is used in the test level in testng1.xml file
        //int id = (int) context.getAttribute("user_id");

       //This should come from createUser request and is used in the suite level in testng1.xml file
        int id = (int) context.getSuite().getAttribute("user_id");

        given()
                    .headers("Authorization", "Bearer " +bearerToken)
                    .pathParam("id", id)
                .when()
                    .delete("https://gorest.co.in/public/v2/users/{id}")
                .then()
                    .statusCode(204)
                    .log().all();

    }
}
