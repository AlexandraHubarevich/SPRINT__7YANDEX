import client.Courier;
import client.CourierClient;
import client.CourierLog;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoginCourierTest {
    private String login = RandomStringUtils.randomAlphanumeric(10);
    private String password = RandomStringUtils.randomAlphanumeric(15);
    private String firstName = RandomStringUtils.randomAlphanumeric(20);
    private CourierClient courierClient = new CourierClient();
    int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Логин курьера: курьер может авторизоваться; успешный запрос возвращает id")
    //курьер может авторизоваться;
    //успешный запрос возвращает id.
    public void CreateCourierTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response loginResponse = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));

        int id = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword())).jsonPath().getInt("id");
        Assert.assertEquals(200, loginResponse.statusCode());
        Assert.assertEquals("{\"id\":" + id + "}", loginResponse.asString());
    }

    @Test
    @DisplayName("Логин курьера: для авторизации нужно передать все обязательные поля:проверяем, что нельзя залогиниться только с указанием пароля")
    //для авторизации нужно передать все обязательные поля:проверяем, что нельзя залогиниться только с указанием пароля;
    public void LoginWithoutLoginTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseEmptyLogin = courierClient.loginCourier(new CourierLog(null, courier.getPassword()));
        Assert.assertEquals(400, responseEmptyLogin.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для входа\"}", responseEmptyLogin.asString());
    }

    @Test
    @DisplayName("Логин курьера: для авторизации нужно передать все обязательные поля:проверяем, что нельзя залогиниться только с указанием логина")
//для авторизации нужно передать все обязательные поля:проверяем, что нельзя залогиниться только с указанием логина
    public void LoginWithoutPasswordTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseEmptyPassword = courierClient.loginCourier(new CourierLog(courier.getLogin(), null));
        Assert.assertEquals(400, responseEmptyPassword.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для входа\"}", responseEmptyPassword.asString());
    }

    @Test
    @DisplayName("Логин курьера: если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
//если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    public void LoginNotExist() {
        Response responseNotExist = courierClient.loginCourier(new CourierLog(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(15)));
        Assert.assertEquals(404, responseNotExist.statusCode());
        Assert.assertEquals("{\"code\":404,\"message\":\"Учетная запись не найдена\"}", responseNotExist.asString());
    }


    @Test
    @DisplayName("Логин курьера: система вернёт ошибку, если неправильно указать логин")
//система вернёт ошибку, если неправильно указать логин;
    public void LoginIncorrectLoginTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseIncorrectLogin = courierClient.loginCourier(new CourierLog(RandomStringUtils.randomAlphanumeric(10), courier.getPassword()));
        Assert.assertEquals(404, responseIncorrectLogin.statusCode());
        Assert.assertEquals("{\"code\":404,\"message\":\"Учетная запись не найдена\"}", responseIncorrectLogin.asString());
    }

    @Test
    @DisplayName("Логин курьера: система вернёт ошибку, если неправильно указать пароль")
//система вернёт ошибку, если неправильно указать пароль;;
    public void LoginIncorrectPasswordTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseIncorrectPassword = courierClient.loginCourier(new CourierLog(courier.getLogin(), RandomStringUtils.randomAlphanumeric(10)));
        Assert.assertEquals(404, responseIncorrectPassword.statusCode());
        Assert.assertEquals("{\"code\":404,\"message\":\"Учетная запись не найдена\"}", responseIncorrectPassword.asString());
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(id);
    }

}