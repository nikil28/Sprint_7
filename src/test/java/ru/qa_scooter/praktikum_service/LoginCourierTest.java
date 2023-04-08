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
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static ru.qa_scooter.praktikum_service.Сonfig.Config.Courier_URL;
import static ru.qa_scooter.praktikum_service.Сonfig.Config.getRqSpec;

@Feature("Courier - Логин курьера в системе POST /api/v1/courier/login")
public class LoginCourierTest {
    /*
    Логин курьера
        Проверь:
            курьер может авторизоваться;
            для авторизации нужно передать все обязательные поля;
            если какого-то поля нет, запрос возвращает ошибку;
            если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
            успешный запрос возвращает id.
     */
    Courier courier;
    String id;

    @Before
    public void setUp(){
        courier = Courier.generateRandomCourier();
        CourierStep.create(courier);
        id = given()
                .spec(getRqSpec())
                .when()
                .body(CourierLogin.getCredentials(courier))
                .post(Courier_URL + "login")
                .then().extract().path("id").toString();
    }

    @Test
    @DisplayName("Успешный логин в системе")
    public void successfullyLoginTest(){
        ValidatableResponse response = CourierStep.login(CourierLogin.getCredentials(courier));
       response
               .statusCode(200)
               .assertThat()
               .body("id",is(notNullValue()));
    }

    @Test
    @DisplayName("Авторизация незарегистрированного курьера")
    public void loginWithNonExistentCourierTest(){
        courier = Courier.generateRandomCourier();
        ValidatableResponse response = CourierStep.login(CourierLogin.getCredentials(courier));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message",equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Запрос с неверным логином")
    public void loginWithInvalidLoginTest(){
        courier.setLogin("AaAaAa");
        ValidatableResponse response = CourierStep.login(CourierLogin.getCredentials(courier));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message",equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Запрос с неверным паролем")
    public void loginWithInvalidPasswordTest(){
        courier.setPassword("AaAaQqQq");
        ValidatableResponse response = CourierStep.login(CourierLogin.getCredentials(courier));
        response
                .statusCode(404)
                .assertThat()
                .body("code",equalTo(404))
                .and()
                .body("message",equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Запрос без пароля")
    public void loginWithoutPasswordTest(){
        courier.setPassword("");
        ValidatableResponse response = CourierStep.login(CourierLogin.getCredentials(courier));
        response
                .statusCode(400)
                .assertThat()
                .body("code",equalTo(400))
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Запрос без логина")
    public void loginWithoutLoginTest(){
        courier.setLogin("");
        ValidatableResponse response = CourierStep.login(CourierLogin.getCredentials(courier));
        response
                .statusCode(400)
                .assertThat()
                .body("code",equalTo(400))
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @After
    public void cleanUp(){
        if (id != null){
            CourierStep.delete(id);
        }
    }

}
