package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class BankTest {

    @Test
    public void shouldTest() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Буруков Генадий");
        form.$("[data-test-id=phone] input").setValue("+79095885504");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=\"order-success\"]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldTestMistakeInSurname() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Smirnoff Сергей");
        form.$("[data-test-id=phone] input").setValue("+77077408506");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldTestMistakeInPhoneNumber() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Галкин Александр");
        form.$("[data-test-id=phone] input").setValue("89310015539");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldTestNotName() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+79043329982");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldTestNotPhoneNumber() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Чернов Степан");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldTestNoAgreement() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Назин Леонид");
        form.$("[data-test-id=phone] input").setValue("+79043329982");
        form.$("button").click();
        form.$("[data-test-id=agreement]").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

}
