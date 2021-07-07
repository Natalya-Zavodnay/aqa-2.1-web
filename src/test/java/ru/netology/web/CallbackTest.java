package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CallbackTest {

    @BeforeEach
    void setUp() {open("http://localhost:9999");
    }

    @Test
    void getTrueInputValidForm() {
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[type=text]").setValue("Петров Сергей");
        form.$("[type=tel]").setValue("+79999999999");
        form.$("[class=checkbox__box]").click();
        form.$("[type=button]").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void errorExpectedWhenEmptyFieldName() {
        $("[type='text']").setValue("");
        $("[type='tel']").setValue("+79999999999");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave
                (exactText("Поле обязательно для заполнения"));
    }

    @Test
    void errorExpectedWhenInputIncorrectName() {
        $("[type='text']").setValue("Niki");
        $("[type='tel']").setValue("+79999999999");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave
                (exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void errorExpectedWhenInputIncorrectTelNumber() {
        $("[type='text']").setValue("Петров Сергей");
        $("[type='tel']").setValue("+7 (999)-999-99-99");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave
                (exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void errorExpectedWhenEmptyFieldPhone() {
        $("[type='text']").setValue("Петров Сергей");
        $("[type='tel']").setValue("");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave
                (exactText("Поле обязательно для заполнения"));
    }
    @Test
    void errorExpectedWhenUncheckedCheckbox() {
        $("[type='text']").setValue("Петров Сергей");
        $("[type='tel']").setValue("+79999999999");
        $("[type='button']").click();
        $("[data-test-id=agreement].input_invalid").shouldHave
                (exactText("Я соглашаюсь с условиями обработки и использования моих " +
                        "персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

}

