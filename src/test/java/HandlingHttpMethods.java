import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HandlingHttpMethods {
    int id;

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

   @Test(priority = 1)
    void getUsers()
      {
          given()
                    .header("x-api-key", "reqres-free-v1")
                .when()
                    .get("https://reqres.in/api/users?page=2")
                .then()
                    .statusCode(200)
                    .body("page", equalTo(2))
                    .log().all();
    }

    @Test(priority = 2)
    void addUser()
    {
        HashMap data = new HashMap();
        data.put("name", "Gill");
        data.put("job", "Engineer");

        id=given()
                    .header("x-api-key", "reqres-free-v1")
                    .contentType("application/json")
                    .body(data)
                .when()
                    .post("https://reqres.in/api/users")
                    .jsonPath().getInt("id");
//                .then()
//                    .statusCode(201)
//                    .body("createdAt", notNullValue())
//                    .log().all();
    }

   @Test (priority = 3, dependsOnMethods = "addUser")
    void updateUser()
    {
        HashMap data = new HashMap();
        data.put("name", "Jaiswal");
        data.put("job", "Engineer");

        given()
                    .header("x-api-key", "reqres-free-v1")
                    .contentType("application/json")
                    .body(data)
                .when()
                    .put("https://reqres.in/api/users/"+id)
                .then()
                    .statusCode(200)
                    .body("updatedAt", notNullValue())
                    .log().all();
    }

    @Test(priority = 4)
    void deleteUser()
    {
        given()
                    .header("x-api-key", "reqres-free-v1")
                .when()
                    .delete("https://reqres.in/api/users/"+id)
                .then()
                    .statusCode(204)
                    .log().all();
    }
}
