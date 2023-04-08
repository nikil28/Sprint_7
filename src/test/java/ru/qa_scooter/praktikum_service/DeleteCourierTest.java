package ru.qa_scooter.praktikum_service;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.qa_scooter.praktikum_service.Models.Courier;
import ru.qa_scooter.praktikum_service.Models.CourierLogin;
import ru.qa_scooter.praktikum_service.Steps.CourierStep;
import static org.hamcrest.CoreMatchers.equalTo;

@Feature("Courier - Удаление курьера DELETE /api/v1/courier/:id")
public class DeleteCourierTest {
    /*
    Удалить курьера
    С методом DELETE можно работать так же, как с другими методами.
        Проверь:
            неуспешный запрос возвращает соответствующую ошибку;
            успешный запрос возвращает ok: true;
            если отправить запрос без id, вернётся ошибка;
            если отправить запрос с несуществующим id, вернётся ошибка.
     */
    Courier courier;
    String id;

    @Test
    @DisplayName("Успешное удаление курьера")
    public void successfullyDeleteCourierTest(){
        courier = Courier.generateRandomCourier();
        CourierStep.create(courier);
        id = CourierStep.login(CourierLogin.getCredentials(courier))
                .extract().path("id").toString();
        ValidatableResponse response = CourierStep.delete(id);
        response
                .statusCode(200)
                .body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    public void deletingCourierWithoutIdTest(){
        ValidatableResponse response = CourierStep.delete("");
        response
                .assertThat()
                .statusCode(404)
                .and()
                .body("code",equalTo(404))
                .and()
                .body("message",equalTo("Not Found."));
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    public void DeleteCourierInvalidIdTest(){
        ValidatableResponse response = CourierStep.delete("1234567890");
        response
                .assertThat()
                .statusCode(404)
                .and()
                .body("code",equalTo(404))
                .and()
                .body("message",equalTo("Курьера с таким id нет."));
    }

}
