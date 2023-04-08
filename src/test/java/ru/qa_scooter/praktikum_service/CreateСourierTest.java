package ru.qa_scooter.praktikum_service;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qa_scooter.praktikum_service.Models.Courier;
import ru.qa_scooter.praktikum_service.Models.CourierLogin;
import ru.qa_scooter.praktikum_service.Steps.CourierStep;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@Feature("Courier - Создание курьера POST /api/v1/courier")
public class CreateСourierTest {
    /*
    Создание курьера
        Проверь:
            курьера можно создать;
            нельзя создать двух одинаковых курьеров;
            чтобы создать курьера, нужно передать в ручку все обязательные поля;
            запрос возвращает правильный код ответа;
            успешный запрос возвращает ok: true;
            если одного из полей нет, запрос возвращает ошибку;
            если создать пользователя с логином, который уже есть, возвращается ошибка.
    */
    Courier courier;
    String id;

    @Before // сначала сгенерируем рамдомного курьера
    public void setUp(){
        courier = Courier.generateRandomCourier();
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void successfullyCourierCreationTest(){
        ValidatableResponse regResponse = CourierStep.create(courier);
        ValidatableResponse loginResponse = CourierStep.login(CourierLogin.getCredentials(courier));
        id = loginResponse.extract().path("id").toString();
        regResponse
                .statusCode(201)
                .assertThat().body("ok",is(true));
    }

    @Test
    @DisplayName("Запрос на регистрацию курьеров с повторяющимся логином")
    public void duplicateCourierCreationTest(){
        CourierStep.create(courier);
        id = CourierStep.login(CourierLogin.getCredentials(courier))
                .extract().path("id").toString();
        ValidatableResponse response = CourierStep.create(courier);
        response
                .statusCode(409)
                .assertThat()
                .body("code",equalTo(409))
                .and()
                .body("message",equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Регистрация курьера без логина")
    public void creatingCourierWithoutLoginTest(){
        courier.setLogin(null);
        ValidatableResponse response = CourierStep.create(courier);
        response
                .statusCode(400)
                .assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Регистрация курьера без пароля")
    public void courierCreationWithoutPasswordTest(){
        courier.setPassword(null);
        ValidatableResponse response = CourierStep.create(courier);
        response
                .statusCode(400)
                .assertThat()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Успешная регистрация без firstName")
    public void successfullyCourierCreationWithoutFirstNameTest(){
        courier.setFirstName(null);
        ValidatableResponse regResponse = CourierStep.create(courier);
        ValidatableResponse loginResponse = CourierStep.login(CourierLogin.getCredentials(courier));
        id = loginResponse.extract().path("id").toString();
        regResponse
                .statusCode(201)
                .assertThat().body("ok",is(true));
    }

    @After // в конце удаляем курьера
    public void cleanUp(){
        if(id != null){
            CourierStep.delete(id);
        }
    }

}