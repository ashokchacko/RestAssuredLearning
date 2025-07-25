import org.json.*;
import org.testng.annotations.Test;
import java.io.*;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Two_HandlingJsonRequest {
    //HashMap Method
    //@Test(priority = 1)
    void hashMapMethod()
    {
        HashMap data = new HashMap();
        data.put("name", "Gill");
        data.put("location", "India");
        data.put("phone", "1234567890");

        String coursesArr [] = {"Selenium", "Java"};
        data.put("courses", coursesArr);

        given()
                    .contentType("application/json")
                    .body(data)
                .when()
                    .post("http://localhost:3000/students")
                .then()
                    .statusCode(201)
                    .body("name", equalTo("Gill"))
                    .body("phone", equalTo("1234567890"))
                    .body("courses[0]", equalTo("Selenium"))
                    .body("courses[1]", equalTo("Java"))
                    .header("Content-Type", "aaplication/json; charset=utf-8")
                    .log().all();
    }
    //JSON Library Method
    //@Test(priority = 2)
    void jsonLibraryMethod()
    {
        JSONObject data = new JSONObject();
        data.put("name", "Johnson");
        data.put("location", "Canada");
        data.put("phone", "2345678909");

        String coursesArr[] = {"RestSharp", "RestAssured"};
        data.put("courses", coursesArr);

        given()
                    .contentType("application/json")
                    .body(data.toString())
                .when()
                    .post("http://localhost:3000/students")
                .then()
                    .statusCode(201)
                    .body("name", equalTo("Johnson"))
                    .body("courses[0]", equalTo("RestSharp"))
                    .log().all();
    }
    //POJO class Method
    //@Test(priority = 3)
    void pojoClassMethod()
    {
        Three_POJOBaseClass data = new Three_POJOBaseClass();
        data.setName("Glenn");
        data.setLocation("Australia");
        data.setPhone("9899999999");

        String coursesArr[] = {"Cypress", "Python"};
        data.setCourses(coursesArr);

        given()
                    .contentType("application/json")
                    .body(data)
                .when()
                    .post("http://localhost:3000/students")
                .then()
                    .statusCode(201)
                    .body("name", equalTo("Glenn"))
                    .body("courses[1]", equalTo("Python"))
                    .log().all();
    }

    //JSON FileMethod
    @Test(priority = 4)
    void jsonFileMethod() throws FileNotFoundException
    {
        File file = new File(".\\jsonfile.json");  //Open the file
        FileReader filereader = new FileReader(file);  //Read the data from the file
        JSONTokener jsontokener = new JSONTokener(filereader);  //To get the JSON form of the data
        JSONObject data = new JSONObject(jsontokener);  //Convert the JSON data into JSON Object

        given()
                    .contentType("application/json")
                    .body(data.toString())
                .when()
                    .post("http://localhost:3000/students")
                .then()
                    .statusCode(201)
                    .body("students[0].location", equalTo("Japan"))
                    .body("students[0].courses[1]", equalTo("UFT"))
                    .log().all();
    }
}
