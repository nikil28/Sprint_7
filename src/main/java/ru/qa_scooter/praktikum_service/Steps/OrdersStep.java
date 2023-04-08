package ru.qa_scooter.praktikum_service.Steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.qa_scooter.praktikum_service.Models.Order;
import ru.qa_scooter.praktikum_service.Сonfig.Config;
import static io.restassured.RestAssured.given;

public class OrdersStep extends Config {

    @Step("Создание заказа")
    public static ValidatableResponse orderСreation(Order order){
        return given()
                .spec(getRqSpec())
                .body(order)
                .when()
                .post(Orders_url).then().log().all();
    }

    @Step("Получение списка заказов")
    public static ValidatableResponse getOrders(){
        return given()
                .spec(getRqSpec())
                .when()
                .get(Orders_url).then().log().all();
    }

}
