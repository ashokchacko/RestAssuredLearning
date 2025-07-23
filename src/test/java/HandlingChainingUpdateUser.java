import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HandlingChainingUpdateUser {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test
    void updateUser(ITestContext context)
    {
        Faker faker = new Faker();

        JSONObject data = new JSONObject();
        data.put("name", faker.name().fullName());
        data.put("gender", "Male");
        data.put("email", faker.internet().emailAddress());
        data.put("status", "active");

        String bearerToken = "b9d131c2c1742eead3e729af912a405f1cc97ed61af874ec946eacf55e7ee31b";

        //This should come from create user request and is used in the test level in testng1.xml file
        //int id = (int) context.getAttribute("user_id");

        //This should come from createUser request and is used in the suite level in testng1.xml file
        int id = (int) context.getSuite().getAttribute("user_id");
        given()
                    .headers("Authorization", "Bearer " +bearerToken)
                    .contentType("application/json")
                    .pathParam("id", id)
                    .body(data.toString())
                .when()
                    .put("https://gorest.co.in/public/v2/users/{id}")
                .then()
                    .statusCode(200)
                    .log().all();
    }
}
