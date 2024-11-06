import client.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;


public class OrderAcceptTest {
    private String login = RandomStringUtils.randomAlphanumeric(10);
    private String password = RandomStringUtils.randomAlphanumeric(15);
    private String firstName = RandomStringUtils.randomAlphanumeric(20);
    private CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Принять заказ: курьер может авторизоваться; успешный запрос возвращает id")
//Принять заказ:успешный запрос возвращает ok: true;
    public void AcceptExistOrderTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept("Alex2", "Ivanov2", "Konoha, 143 apt.", "6", "+8 805 500 35 35", 2, "2024-12-12", "Text2", List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.GetOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(id, CourierId);

        Assert.assertEquals(200, responseOrderAccept.statusCode());
        Assert.assertEquals("{\"ok\":true}", responseOrderAccept.asString());


    }

    @Test
    @DisplayName("Принять заказ: если не передать id курьера, запрос вернёт ошибку")
//Принять заказ:если не передать id курьера, запрос вернёт ошибку;
    public void AcceptWithoutCourierId() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept("Alex2", "Ivanov2", "Konoha, 143 apt.", "6", "+8 805 500 35 35", 2, "2024-12-12", "Text2", List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.GetOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(id, null);
        Assert.assertEquals(400, responseOrderAccept.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для поиска\"}", responseOrderAccept.asString());
    }

    @Test
    @DisplayName("Принять заказ: если передать неверный id курьера, запрос вернёт ошибку")
//Принять заказ:если передать неверный id курьера, запрос вернёт ошибку;
    public void AcceptWithIncorrectCourierId() {

        OrderAccept orderAccept = new OrderAccept("Alex2", "Ivanov2", "Konoha, 143 apt.", "6", "+8 805 500 35 35", 2, "2024-12-12", "Text2", List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.GetOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(id, new Random().nextInt());

        Assert.assertEquals(404, responseOrderAccept.statusCode());
        Assert.assertEquals("{\"code\":404,\"message\":\"Курьера с таким id не существует\"}", responseOrderAccept.asString());
    }

    @Test
    @DisplayName("Принять заказ: если не передать номер заказа, запрос вернёт ошибку")
//Принять заказ:если не передать номер заказа, запрос вернёт ошибку;
    public void AcceptWithoutOrderId() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept("Alex2", "Ivanov2", "Konoha, 143 apt.", "6", "+8 805 500 35 35", 2, "2024-12-12", "Text2", List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.GetOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");

        Response responseOrderAccept = orderClient.acceptOrder(null, CourierId);

        Assert.assertEquals(400, responseOrderAccept.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для поиска\"}", responseOrderAccept.asString());
    }

    @Test
    @DisplayName("Принять заказ: если передать неверный номер заказа, запрос вернёт ошибку")
//Принять заказ://если передать неверный номер заказа, запрос вернёт ошибку.
    public void AcceptIncorrectOrderId() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        OrderAccept orderAccept = new OrderAccept("Alex2", "Ivanov2", "Konoha, 143 apt.", "6", "+8 805 500 35 35", 2, "2024-12-12", "Text2", List.of("BLACK"));
        OrderClient orderClient = new OrderClient();
        Response responseTrackFromCreateOrder = orderClient.createOrder(orderAccept);
        int track = responseTrackFromCreateOrder.jsonPath().getInt("track");
        Response responseGetOrderByTrack = orderClient.GetOrderByTrack(track);
        int id = responseGetOrderByTrack.jsonPath().getInt("order.id");
        Response responseOrderAccept = orderClient.acceptOrder(new Random().nextInt(), CourierId);
        Assert.assertEquals(404, responseOrderAccept.statusCode());
        Assert.assertEquals("{\"code\":404,\"message\":\"Заказа с таким id не существует\"}", responseOrderAccept.asString());

    }
}

