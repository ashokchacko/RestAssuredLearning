import com.github.javafaker.Faker;
import org.testng.annotations.Test;

public class HandlingFakerDataGenerator {

    @Test
    void generateDummyData()
    {
        Faker faker = new Faker();

        String fullname = faker.name().fullName();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();

        String username = faker.name().username();
        String password = faker.internet().password(4, 10);

        String phonenumber = faker.phoneNumber().phoneNumber();

        String emailaddress = faker.internet().safeEmailAddress();

        System.out.println("Full Name: " +fullname);
        System.out.println("First Name: " +firstname);
        System.out.println("Last Name: " +lastname);
        System.out.println("User Name: " +username);
        System.out.println("Password: " +password);
        System.out.println("Phone Number: " +phonenumber);
        System.out.println("Email Address: " +emailaddress);
    }
}
