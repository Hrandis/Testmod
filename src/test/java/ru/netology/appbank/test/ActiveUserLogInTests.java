package ru.netology.appbank.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.appbank.data.DataGenerator;
import ru.netology.appbank.data.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ActiveUserLogInTests {
    UserInfo correctUser;
    UserInfo wrongUser;

    @BeforeEach
    public void Preps() {
        correctUser = DataGenerator.User.generateCorrectUser("ru", "active");
        wrongUser = DataGenerator.User.generateWrongUser("ru", "active");
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInActiveUserWithCorrectData() {
        $("[data-test-id=\"login\"] input").setValue(correctUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(correctUser.getPassword());
        $(".button").click();
        $("h2").shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(1))
                .shouldBe(visible);
    }

    @Test
    void shouldShowWarningForActiveUserWithWrongLogin() {
        $("[data-test-id=\"login\"] input").setValue(wrongUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(correctUser.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(1))
                .shouldBe(visible);
    }

    @Test
    void shouldShowWarningForActiveUserWithWrongPassword() {
        $("[data-test-id=\"login\"] input").setValue(correctUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(wrongUser.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(1))
                .shouldBe(visible);
    }

    @Test
    void shouldShowWarningForActiveUserWithWrongData() {
        $("[data-test-id=\"login\"] input").setValue(wrongUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(wrongUser.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(1))
                .shouldBe(visible);
    }
}