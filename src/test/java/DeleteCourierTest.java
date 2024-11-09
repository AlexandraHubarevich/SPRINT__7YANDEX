import client.Courier;
import client.CourierClient;
import client.CourierLog;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;


public class DeleteCourierTest {
    Faker faker = new Faker();
    private String login = faker.name().username();
    private String password = faker.internet().password(3, 10);
    private String firstName = faker.name().firstName();
    private CourierClient courierClient = new CourierClient();


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Удалить курьера: успешный запрос возвращает ok: true;")
    //успешный запрос возвращает ok: true;
    public void deleteCourierTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        int id = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword())).jsonPath().getInt("id");
        Response responseDelete = courierClient.deleteCourier(id);
        Assert.assertEquals(200, responseDelete.statusCode());
        Assert.assertEquals("true", responseDelete.jsonPath().getString("ok"));
    }

    @Test
    @DisplayName("Удалить курьера: неуспешный запрос возвращает соответствующую ошибку;если отправить запрос с несуществующим id, вернётся ошибка")
    //неуспешный запрос возвращает соответствующую ошибку;
    //если отправить запрос с несуществующим id, вернётся ошибка.
    public void deleteIncorrectTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseDelete = courierClient.deleteCourier(new Random().nextInt());
        Assert.assertEquals(404, responseDelete.statusCode());
        Assert.assertEquals("Курьера с таким id нет.", responseDelete.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Удалить курьера: если отправить запрос без id, вернётся ошибка;")
    //если отправить запрос без id, вернётся ошибка;
    public void deleteWithoutTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseDelete = courierClient.deleteCourierWithoutId();
        System.out.println(responseDelete.asString());
        Assert.assertEquals(404, responseDelete.statusCode());
        Assert.assertEquals("Недостаточно данных для удаления курьера.", responseDelete.jsonPath().getString("message"));
    }
}
