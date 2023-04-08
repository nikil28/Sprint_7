package ru.qa_scooter.praktikum_service.Models;

public class CourierLogin { // Данные из свагера по логину курьера
    // ключи login, password стали полем типа String
    String login;
    String password;

    // геттеры для login, password
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public CourierLogin(String logon, String password) {
        this.login = logon;
        this.password = password;
    }
    public static CourierLogin getCredentials(Courier courier) {
        return new CourierLogin(courier.getLogin(),courier.getPassword());
    }
}
