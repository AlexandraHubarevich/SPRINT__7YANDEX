import client.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class OrderCheckListResponseTest {

    private String login = RandomStringUtils.randomAlphanumeric(10);
    private String password = RandomStringUtils.randomAlphanumeric(15);
    private String firstName = RandomStringUtils.randomAlphanumeric(20);
    private CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Проверь, что в тело ответа возвращается список заказов")
//Проверь, что в тело ответа возвращается список заказов.
    public void orderListTest() {
        Courier courier = new Courier(login, password, firstName);
        courierClient.сreateCourier(courier);
        Response responseLogin = courierClient.loginCourier(new CourierLog(courier.getLogin(), courier.getPassword()));
        int CourierId = responseLogin.jsonPath().getInt("id");

        Order orderTest = new Order(CourierId, 5, "1", 5);

        Response response = given()
                .header("Content-type", "application/json")
                .body(orderTest)
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().body("orders.id", notNullValue())
                .and()
                .statusCode(200);
        List<String> responseOrder = response.then().extract().path("orders.id");
        System.out.println(responseOrder);
    }

}
