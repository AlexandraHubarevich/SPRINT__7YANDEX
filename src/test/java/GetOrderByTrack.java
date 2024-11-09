import client.*;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrderByTrack {
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String address = faker.address().fullAddress();
    String metrostation = String.valueOf(new Random().nextInt());
    String phone = String.valueOf(faker.phoneNumber());
    int rentTime = new Random().nextInt();
    String deliveryDate = "2024-12-12";
    String comment = "ASAP";


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }


    @Test
    @DisplayName("Получить заказ по его номеру: успешный запрос возвращает объект с заказом")
//Получить заказ по его номеру: успешный запрос возвращает объект с заказом;
    public void checkOrderObjectTest() {
        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        OrderUser orderUser = given()
                .get("/api/v1/orders/track?t=" + track)
                .body().as(OrderUser.class);
        MatcherAssert.assertThat(orderUser, notNullValue());

    }


    @Test
    @DisplayName("Получить заказ по его номеру: запрос без номера заказа возвращает ошибку")
//Получить заказ по его номеру: запрос без номера заказа возвращает ошибку;
    public void checkOrderWithoutTrackTest() {
        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        orderClient.createOrder(orderAccept);
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(null);
        Assert.assertEquals(400, responseGetOrderByTrack.statusCode());
        Assert.assertEquals("Недостаточно данных для поиска", responseGetOrderByTrack.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Получить заказ по его номеру: запрос с несуществующим заказом возвращает ошибку")
//Получить заказ по его номеру: запрос с несуществующим заказом возвращает ошибку.
    public void checkOrderWithIncorrectTrackTest() {
        OrderClient orderClient = new OrderClient();
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(new Random().nextInt());
        Assert.assertEquals(404, responseGetOrderByTrack.statusCode());
        Assert.assertEquals("Заказ не найден", responseGetOrderByTrack.jsonPath().getString("message"));
    }
}