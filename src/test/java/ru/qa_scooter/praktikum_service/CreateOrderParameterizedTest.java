package ru.qa_scooter.praktikum_service;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qa_scooter.praktikum_service.Models.Order;
import ru.qa_scooter.praktikum_service.Steps.OrdersStep;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;

@Feature("Orders - Создание заказа POST /api/v1/orders")
@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {
    /*
    Создание заказа
        Проверь, что когда создаёшь заказ:
            можно указать один из цветов — BLACK или GREY;
            можно указать оба цвета;
            можно совсем не указывать цвет;
            тело ответа содержит track.
            Чтобы протестировать создание заказа, нужно использовать параметризацию.
     */
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    public CreateOrderParameterizedTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "{0}, {1}, {2}, {3}, {8}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Джон ", "Вик", "Отель континенталь 13", "Тверска", "79261111111", 1, "01-01-2023", "Я служил, и еще послужу", List.of("Black")},
                {"Мистер", "Андерсон", "Улица", "No Subway", "79262111112", 2, "02-02-2023", "Тут должен быть комментарий", List.of("Gray")},
                {"Иван", "Иванов", "Ленина 15", "Библиотека имени Ленина", "79263111113", 3, "03-03-2023", "There should be a comment", List.of("Black", "Gray")},
                {"Олег", "Олегов", "Олегова 15", "Красные ворота", "123456789", 4, "04-04-2023", "ок", null},
//                {"Петя", "Петров", "Тут должен быть адрес", "Киевская","8гудок953", 5, "05-05-2023", "Комментария не будет", List.of("Black")}
        };
    }

    @Test
    @DisplayName("Успешное создание заказа")
    public void SuccessfulOrderCreationTest(){
        Order order = new Order(firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,color);
        ValidatableResponse response = OrdersStep.orderСreation(order);
        response
                .statusCode(201)
                .assertThat()
                .body("track",notNullValue());
    }

}
