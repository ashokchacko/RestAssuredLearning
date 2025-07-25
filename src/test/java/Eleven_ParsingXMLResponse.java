import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Eleven_ParsingXMLResponse {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test(priority = 1)
    void getXMLResponseApproach1()
    {
        given()
                .when()
                    .get("https://mocktarget.apigee.net/xml")
                .then()
                    .statusCode(200)
                    .header("Content-Type", "application/xml; charset=utf-8")
                    .body("root.city", equalTo("San Jose"))
                    .body("root.state", equalTo("CA"))
                    .log().all();
    }

    @Test(priority=2)
    void getXMLResponseApproach2()
    {
        Response res = given()
                .when()
                    .get("https://mocktarget.apigee.net/xml");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(res.header("Content-Type"), "application/xml; charset=utf-8");

        String city = res.xmlPath().get("root.city");
        Assert.assertEquals(city, "San Jose");
        System.out.println("City: " +city);

        String state = res.xmlPath().get("root.state");
        Assert.assertEquals(state, "CA");
        System.out.println("State: " +state);
    }

    @Test(priority =3)
    void getXMLResponseApproach3()
    {
        Response res = given()
                .when()
                    .get("https://mocktarget.apigee.net/xml");

        XmlPath xmlobj = new XmlPath(res.asString());

        //Verify total number of Users
        List<String> city_names = xmlobj.getList("root");
        Assert.assertEquals(city_names.size(), 1);

        //Verify user's firstname present in response
        List<String> firstnames = xmlobj.getList("root.firstName");
        boolean status = false;
        for (String firstname : firstnames)
        {
            if (firstname.equals("John"))
            {
                status = true;
                break;
            }
        }
        Assert.assertEquals(status, true);
    }
}
