import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    List<String> color = List.of("Black", "Grey");


    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.address = address;
        this.comment = comment;
        this.deliveryDate = deliveryDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.color = color;
    }
    // Проверь, что когда создаёшь заказ:
    //  можно указать один из цветов — BLACK или GREY;
    // можно указать оба цвета;
    //  можно совсем не указывать цвет;
    //  тело ответа содержит track.


    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Alex2", "Ivanov2", "Konoha, 143 apt.", "6", "+8 805 500 35 35", 2, "2024-12-12", "Text2", List.of("BLACK")},
                {"Alex3", "Ivanov3", "Konoha, 144 apt.", "5", "+8 806 500 35 35", 4, "2024-12-06", "Text3", List.of("GREY")},
                {"Alex4", "Ivanov4", "Konoha, 145 apt.", "6", "+8 807 500 35 35", 1, "2024-12-07", "Text4", List.of("GREY", "BLACK")},
                {"Alex5", "Ivanov5", "Konoha, 146 apt.", "4", "+7 808 355 35 35", 5, "2020-06-08", "Text5", List.of()}


        };
    }
    //Проверь, что в тело ответа возвращается список заказов.
    @Test
    @DisplayName("Проверь, что в тело ответа возвращается список заказов")
    public void CheckVariety() {
        OrderTest order = new OrderTest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");

        Assert.assertEquals(201, response.statusCode());
        assertTrue(response.asString().contains("track"));

    }
}