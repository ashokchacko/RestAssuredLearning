import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

public class Seven_HandlingSerializationDeserialization {

    @Test(priority = 1)
    void serialization() throws JsonProcessingException //convert pojo to json
    {
        Three_POJOBaseClass stupojo = new Three_POJOBaseClass(); //Created java object using pojo class
        stupojo.setName("James");
        stupojo.setLocation("Australia");
        stupojo.setPhone("9899999999");

        String coursesArr[] = {"Cypress", "Python"};
        stupojo.setCourses(coursesArr);

        //convert java object to json object(serialization)
        ObjectMapper objectMapper = new ObjectMapper();
        String jsondata = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(stupojo);
        System.out.println(jsondata);
    }

    @Test(priority = 2)
    void deserialization() throws JsonProcessingException //converts json to pojo
    {
        String jsondata = "{\n" +
                "  \"name\" : \"James\",\n" +
                "  \"location\" : \"Australia\",\n" +
                "  \"phone\" : \"9899999999\",\n" +
                "  \"courses\" : [ \"Cypress\", \"Python\" ]\n" +
                "}\n";

        //converting json data to pojo object: Deserialization
        ObjectMapper stuobj = new ObjectMapper();

        Three_POJOBaseClass stupojo = stuobj.readValue(jsondata, Three_POJOBaseClass.class); //POJOBaseClass is the java class file

        System.out.println("Name: " +stupojo.getName());
        System.out.println("Location: " +stupojo.getLocation());
        System.out.println("Phone: " +stupojo.getPhone());
        System.out.println("Course1: " +stupojo.getCourses()[0]);
        System.out.println("Course2: " +stupojo.getCourses()[1]);
    }
}
