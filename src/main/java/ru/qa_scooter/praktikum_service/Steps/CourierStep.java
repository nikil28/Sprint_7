package ru.qa_scooter.praktikum_service.Steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.qa_scooter.praktikum_service.Models.Courier;
import ru.qa_scooter.praktikum_service.Models.CourierLogin;
import ru.qa_scooter.praktikum_service.Сonfig.Config;
import static io.restassured.RestAssured.given;

public class CourierStep extends Config {

    @Step ("Создание курьера")
    public static ValidatableResponse create (Courier courier) {
        return given()
                .spec(getRqSpec())
                .when()
                .body(courier).log().all()
                .post(Courier_URL).then().log().all();
    }

    @Step("Логин курьера в системе")
    public static ValidatableResponse login (CourierLogin courierLogin){
        return given()
                .spec(getRqSpec())
                .when()
                .body(courierLogin).log().all()
                .post(Courier_URL + "login").then().log().all();
    }

    @Step("Удаление курьера из системы")
    public static ValidatableResponse delete (String courierId){
        return given()
                .spec(getRqSpec())
                .when().log().all()
                .delete(Courier_URL + courierId).then().log().all();
    }
}
