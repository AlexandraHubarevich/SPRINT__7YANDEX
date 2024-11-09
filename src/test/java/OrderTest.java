import client.OrderAccept;
import client.OrderClient;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String address = faker.address().fullAddress();
    String metrostation = String.valueOf(new Random().nextInt());
    String phone = String.valueOf(faker.phoneNumber());
    int rentTime = new Random().nextInt();
    String deliveryDate = "2024-12-12";
    String comment = "ASAP";
    List<String> color;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    public OrderTest(List<String> color) {
        this.color = color;
    }
    // Проверь, что когда создаёшь заказ:
    //  можно указать один из цветов — BLACK или GREY;
    // можно указать оба цвета;
    //  можно совсем не указывать цвет;
    //  тело ответа содержит track.


    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY", "BLACK")},
                {List.of()}
        };
    }

    //Проверь, что в тело ответа возвращается список заказов.
    @Test
    @DisplayName("Проверь, что в тело ответа возвращается список заказов")
    public void CheckVariety() {
        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, color);
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderAccept)
                .when()
                .post("/api/v1/orders");

        Assert.assertEquals(201, response.statusCode());
        assertTrue(response.asString().contains("track"));

    }
}