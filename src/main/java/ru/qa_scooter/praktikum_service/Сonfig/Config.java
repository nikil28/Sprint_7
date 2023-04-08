package ru.qa_scooter.praktikum_service.Сonfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {
    public static final String Base_URL = "https://qa-scooter.praktikum-services.ru"; // стартовый сайт
    public static final String Courier_URL = "/api/v1/courier/"; // api курьера
    public static final String Orders_url = "api/v1/orders"; // api получения списка заказов
    public static RequestSpecification getRqSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(Base_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
