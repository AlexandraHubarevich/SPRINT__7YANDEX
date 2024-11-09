package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("put(\"/api/v1/orders/accept/\" + id)")
    public Response acceptOrder(Integer id, Integer CourierId) {
        Response response = given()
                .header("Content-type", "application/json")
                .queryParam("courierId", CourierId)
                .put("/api/v1/orders/accept/" + id);

        return response;
    }
    @Step("post(\"/api/v1/orders\")")
    public Response createOrder(OrderAccept orderAccept) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderAccept)
                .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step(".get(\"/api/v1/orders/track\")")
    public Response getOrderByTrack(Integer track) {
        Response response = given()
                .header("Content-type", "application/json")
                .queryParam("t", track)
                .get("/api/v1/orders/track");
        return response;

    }
}



