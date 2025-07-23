import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ParsingJsonResponse {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test(priority = 1)
    void parseJsonResponseApproach1()
    {
        //Approach 1
        given()
                    .contentType(ContentType.JSON)
                .when()
                    .get("http://localhost:3000/store")
                .then()
                    .statusCode(200)
                    .header("Content-Type", "application/json")
                    .body("book[2].title", equalTo("True Story"))
                    .log().all();
    }

    @Test(priority = 2)
    void parseJsonRequestApproach2()
    {
        Response res = given()
                    .contentType(ContentType.JSON)
                .when()
                    .get("http://localhost:3000/store");
        Assert.assertEquals(res.getStatusCode(), 200);
        String book_title = res.jsonPath().get("book[2].title").toString();
        Assert.assertEquals(book_title, "True Story");
        System.out.print("Book title: " +book_title);
    }

    @Test(priority = 3)
    void validateJSONDataApproach1()
    {
        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:3000/store");

        JSONObject jo = new JSONObject(res.asString());

        for (int i = 0; i < jo.getJSONArray("book").length(); i++)
        {
            String book_title = jo.getJSONArray("book").getJSONObject(i).get("title").toString();
            System.out.println(+i+1+ " Title of the book : " +book_title);
        }
    }
    @Test(priority = 4)
    void validateJSONDataApproach2()
    {
        Response res = given()
                    .contentType(ContentType.JSON)
                .when()
                    .get("http://localhost:3000/store");
        JSONObject jo = new JSONObject(res.asString());

        boolean status = false;
        for (int i = 0; i < jo.getJSONArray("book").length(); i++)
        {
            String book_title = jo.getJSONArray("book").getJSONObject(i).get("title").toString();
            if (book_title.equals("True Story"))
            {
                status = true;
                System.out.println("Required title of the book: " +book_title);
                break;
            }
        }
        Assert.assertEquals(status, true);
    }

    @Test(priority = 5)
    void validateJSOnDataApproach3()
    {
        Response res = given()
                    .contentType(ContentType.JSON)
                .when()
                    .get("http://localhost:3000/store");
        JSONObject jo = new JSONObject(res.asString());

        double total_price = 0;
        for (int i = 0; i < jo.getJSONArray("book").length(); i++)
        {
            String price = jo.getJSONArray("book").getJSONObject(i).get("price").toString();
            total_price = (total_price + Double.parseDouble(price));
        }
        System.out.println("Total price of books: " +total_price);
    }

}
