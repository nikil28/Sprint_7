package ru.qa_scooter.praktikum_service.Models;
import org.apache.commons.lang3.RandomStringUtils;

public class Courier { // Данные из свагера по созданию курьеру
    // ключ login, password, firstName стал полем типа String
    private String login;
    private String password;
    private String firstName;


    public Courier(String login, String password, String firstName) {  // конструктор со всеми параметрами
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // геттеры и сеттеры для login password firstName
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static Courier generateRandomCourier(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        String firstName = RandomStringUtils.randomAlphabetic(6);
        return new Courier(login,password,firstName);
    }
}
