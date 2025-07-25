import io.restassured.matcher.RestAssuredMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class Thriteen_HandlingXmlSchemaValidation {

    @BeforeClass
    public void setup()
    {
        useRelaxedHTTPSValidation(); // Skips SSL cert validation
    }

    @Test(priority = 1)
    void xmlCchemaValidation()
    {
        given()
                .when()
                    .get("https://mocktarget.apigee.net/xml")
                .then()
                    .assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("samplexmlschema.xsd"))
                    .log().body();
    }
}
