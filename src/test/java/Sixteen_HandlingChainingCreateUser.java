import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class Sixteen_HandlingChainingCreateUser {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test
    void createUser(ITestContext context)
    {
        Faker faker = new Faker();

        JSONObject data = new JSONObject();
        data.put("name", faker.name().fullName());
        data.put("gender", "Male");
        data.put("email", faker.internet().emailAddress());
        data.put("status", "inactive");

        String bearerToken = "b9d131c2c1742eead3e729af912a405f1cc97ed61af874ec946eacf55e7ee31b";

        int id =
               given()
                    .headers("Authorization", "Bearer " +bearerToken)
                    .contentType("application/json")
                    .body(data.toString())
                .when()
                    .post("https://gorest.co.in/public/v2/users")
                    .jsonPath().getInt("id");

        System.out.println("Generated Id: " +id);

        // This will make the id variable available in the test level in testng1.xml file
        //context.setAttribute("user_id", id);

        // This will make the id variable available in the suite level in testng1.xml file
        context.getSuite().setAttribute("user_id", id);
        context.getSuite().setAttribute("token", bearerToken);
    }
}
