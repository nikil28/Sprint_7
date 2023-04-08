package ru.qa_scooter.praktikum_service;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.qa_scooter.praktikum_service.Steps.OrdersStep;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Orders - Получение списка заказов GET /api/v1/orders")
public class GettingListOrdersTest {
    /*
    Список заказов
        Проверь, что в тело ответа возвращается список заказов.
     */
    @Test
    @DisplayName("Получение списка всех заказов")
    public void getAllOrdersTest(){
        ValidatableResponse response = OrdersStep.getOrders();
        response
                .statusCode(200)
                .assertThat()
                .body("orders", notNullValue());
    }

}
