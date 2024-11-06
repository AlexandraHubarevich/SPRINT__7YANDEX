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


public class CreateCourierTest {
    private String login = RandomStringUtils.randomAlphanumeric(10);
    private String password = RandomStringUtils.randomAlphanumeric(15);
    private String firstName = RandomStringUtils.randomAlphanumeric(20);
    private CourierClient courierClient = new CourierClient();
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Создание Курьера: Курьера можно создать и запрос возвращает правильный код ответа и успешный запрос возвращает ok: true")
//Курьера можно создать и запрос возвращает правильный код ответа и успешный запрос возвращает ok: true;
    public void CreateCourierTest() {
        //создание курьера
        Courier courier = new Courier(login, password, firstName);
        Response responseCreate = courierClient.сreateCourier(courier);
        Assert.assertEquals(201, responseCreate.statusCode());
        Assert.assertEquals("{\"ok\":true}", responseCreate.asString());

        //логин курьера для получения айди и последующего удаления
        Response loginResponse = courierClient.loginCourier(new CourierLog(courier.getLogin(),courier.getPassword()));
        int id = courierClient.loginCourier(new CourierLog(courier.getLogin(),courier.getPassword())).jsonPath().getInt("id");
        Assert.assertEquals(200, loginResponse.statusCode());


    }

    @Test
    @DisplayName("Создание Курьера: нельзя создать двух одинаковых курьеров")
//нельзя создать двух одинаковых курьеров;
    public void CreateIdenticalCourierTest() {
        //создание курьера
        Courier courier = new Courier(login, password, firstName);
        Response responseCreate = courierClient.сreateCourier(courier);
        Assert.assertEquals(201, responseCreate.statusCode());
        Assert.assertEquals("{\"ok\":true}", responseCreate.asString());


        Courier courierDuplicated = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        Response responseDuplicated = courierClient.сreateCourier(courierDuplicated);
        Assert.assertEquals(409, responseDuplicated.statusCode());
        Assert.assertEquals("{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}", responseDuplicated.asString());

        // логин курьера для получения айди и последующего удаления
        Response loginResponse = courierClient.loginCourier(new CourierLog(courier.getLogin(),courier.getPassword()));
        int id = courierClient.loginCourier(new CourierLog(courier.getLogin(),courier.getPassword())).jsonPath().getInt("id");
        Assert.assertEquals(200, loginResponse.statusCode());

    }


    //чтобы создать курьера, нужно передать в ручку все обязательные поля: пустой логин;
    @Test
    @DisplayName("Создание Курьера: чтобы создать курьера, нужно передать в ручку все обязательные поля, пустой логин")
    public void CreateCourierEmptyLogin() {
        Courier courierEmptyLogin = new Courier(null, password, firstName);
        Response responseEmptyLogin = courierClient.сreateCourier(courierEmptyLogin);
        Assert.assertEquals(400, responseEmptyLogin.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}", responseEmptyLogin.asString());
    }

    @Test
    @DisplayName("Создание Курьера: чтобы создать курьера, нужно передать в ручку все обязательные поля: пустой пароль")
    public void CreateCourierEmptyPassword() {
        Courier courierEmptyPassword = new Courier(login, null, password);
        Response responseEmptyPassword = courierClient.сreateCourier(courierEmptyPassword);
        Assert.assertEquals(400, responseEmptyPassword.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}", responseEmptyPassword.asString());
    }

    @Test
    @DisplayName("Создание Курьера: чтобы создать курьера, нужно передать в ручку все обязательные поля: пустое имя")
    public void CreateCourierEmptyFirstName() {
        Courier courierEmptyFirstName = new Courier(login, password, null);
        Response responseEmptyFirstName = courierClient.сreateCourier(courierEmptyFirstName);
        Assert.assertEquals(400, responseEmptyFirstName.statusCode());
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}", responseEmptyFirstName.asString());
    }


    @After
    public void tearDown() {
        courierClient.deleteCourier(id);
    }
}
