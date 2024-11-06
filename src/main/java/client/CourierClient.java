package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierClient {
    @Step(".post(\"/api/v1/courier\")")
    public Response сreateCourier(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step(".post(\"/api/v1/courier/Login\")")
    public Response loginCourier(CourierLog courierLog) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierLog)
                .when()
                .post("/api/v1/courier/Login");
        return response;
    }

    @Step(".delete(\"api/v1/courier/\" + id)")
    public Response deleteCourier(Integer id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("api/v1/courier/" + id);
    }

    @Step("delete(\"api/v1/courier/\")")
    public Response deleteCourierWithoutId() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("api/v1/courier/");
    }

}
