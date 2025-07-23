import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HandlingCookies {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test(priority = 1)
    void verifySingleCookie()
    {
        given()
                .when()
                    .get("https://www.google.com/")
                .then()
                    .cookie("AEC", "AVh_V2j6Wuc9ZVpxPcGo9bc8EOizlQjGFUrhJg_PhXz9KC1t-gzdRCcJY6g")
                    .log().all();
    }

    @Test(priority = 2)
    void getSingleCookeInfo()
    {
        Response res = given()
                .when()
                    .get("https://www.google.com/");

      //Get single cookie info
      String cookie_value = res.getCookie("AEC");
      System.out.println("Value of AEC cookie is: " +cookie_value);
    }

    @Test(priority = 3)
    void getAllCookiesInfo()
    {
        Response res = given()
                .when()
                    .get("https://www.google.com/");

        //Get all cookies info
       Map<String, String> cookie_name = res.getCookies();
        System.out.println("Cookie name: " +cookie_name.keySet());
        for (String k : cookie_name.keySet())
        {
            String cookie_value = res.getCookie(k);
            System.out.println("Cookie name: " + k + " | Cookie value: " +cookie_value);
        }
    }
}
