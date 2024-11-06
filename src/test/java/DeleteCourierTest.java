import client.Courier;
import client.CourierClient;
import client.CourierLog;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;


public class DeleteCourierTest {
    private String login = RandomStringUtils.randomAlphanumeric(10);
    private String password = RandomStringUtils.randomAlphanumeric(15);
    private String firstName = RandomStringUtils.randomAlphanumeric(20);
    private CourierClient courierClient = new CourierClient();


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Удалить курьера: успешный запрос возвращает ok: true;")
    //успешный запрос возвращает ok: true;
    public void DeleteCourierTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        int id = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword())).jsonPath().getInt("id");
        Response responseDelete = courierClient.deleteCourier(id);
        Assert.assertEquals(200, responseDelete.statusCode());
        Assert.assertEquals("{\"ok\":true}", responseDelete.asString());
    }

    @Test
    @DisplayName("Удалить курьера: неуспешный запрос возвращает соответствующую ошибку;если отправить запрос с несуществующим id, вернётся ошибка")
    //неуспешный запрос возвращает соответствующую ошибку;
    //если отправить запрос с несуществующим id, вернётся ошибка.
    public void DeleteIncorrectTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseDelete = courierClient.deleteCourier(new Random().nextInt());
        Assert.assertEquals(404, responseDelete.statusCode());
        Assert.assertEquals("{\"code\":404,\"message\":\"Курьера с таким id нет.\"}", responseDelete.asString());
    }

    @Test
    @DisplayName("Удалить курьера: если отправить запрос без id, вернётся ошибка;")
    //если отправить запрос без id, вернётся ошибка;
    public void DeleteWithoutTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseDelete = courierClient.deleteCourierWithoutId();
        System.out.println(responseDelete.asString());
        Assert.assertEquals(404, responseDelete.statusCode());
        Assert.assertEquals("{\"message\":  \"Недостаточно данных для удаления курьера\"}", responseDelete.asString());
    }
}
