import client.*;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;


public class OrderAcceptTest {
    Faker faker = new Faker();
    private String login = faker.name().username();
    private String password = faker.internet().password(3, 10);
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String address = faker.address().fullAddress();
    String metrostation = String.valueOf(new Random().nextInt());
    String phone = String.valueOf(faker.phoneNumber());
    int rentTime = new Random().nextInt();
    String deliveryDate = "2024-12-12";
    String comment = "ASAP";
    private CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Принять заказ: курьер может авторизоваться; успешный запрос возвращает id")
//Принять заказ:успешный запрос возвращает ok: true;
    public void acceptExistOrderTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(id, CourierId);

        Assert.assertEquals(responseOrderAccept.statusCode(), responseOrderAccept.statusCode());
        Assert.assertEquals("true", responseOrderAccept.jsonPath().getString("ok"));


    }

    @Test
    @DisplayName("Принять заказ: если не передать id курьера, запрос вернёт ошибку")
//Принять заказ:если не передать id курьера, запрос вернёт ошибку;
    public void acceptWithoutCourierId() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(id, null);
        Assert.assertEquals(400, responseOrderAccept.statusCode());
        Assert.assertEquals("Недостаточно данных для поиска", responseOrderAccept.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Принять заказ: если передать неверный id курьера, запрос вернёт ошибку")
//Принять заказ:если передать неверный id курьера, запрос вернёт ошибку;
    public void acceptWithIncorrectCourierId() {

        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(id, new Random().nextInt());

        Assert.assertEquals(404, responseOrderAccept.statusCode());
        Assert.assertEquals("Курьера с таким id не существует", responseOrderAccept.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Принять заказ: если не передать номер заказа, запрос вернёт ошибку")
//Принять заказ:если не передать номер заказа, запрос вернёт ошибку;
    public void acceptWithoutOrderId() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(null, CourierId);

        Assert.assertEquals(400, responseOrderAccept.statusCode());
        Assert.assertEquals("Недостаточно данных для поиска", responseOrderAccept.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Принять заказ: если передать неверный номер заказа, запрос вернёт ошибку")
//Принять заказ://если передать неверный номер заказа, запрос вернёт ошибку.
    public void acceptIncorrectOrderId() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept(firstName, lastName, address, metrostation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.getOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");
        Response responseOrderAccept = orderClient.acceptOrder(new Random().nextInt(), CourierId);
        Assert.assertEquals(404, responseOrderAccept.statusCode());
        Assert.assertEquals("Заказа с таким id не существует", responseOrderAccept.jsonPath().getString("message"));

    }
}

