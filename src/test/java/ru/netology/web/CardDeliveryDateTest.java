package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryDateTest {
    private String generateDate(long addDays, String pattern) {
        /*new() -возвращает текущую дату
         * plusDays()- добавляет к дате нужное количество дней вперед
         *format(DateTimeFormatter.ofPattern(pattern)- форматирует дату в String из типа LocalDate */
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldDateTimeDeliveryTest() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        //этот метод устанавливает - сколько дней вперед надо промотать
        String planningDate = generateDate(4, "dd.MM.yyyy");
        //метод для выделения и очистки поля для возможности нового ввода
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79230000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        /*метод .shouldBe(Condition.visible ,Duration.ofSeconds
        ( -показывает - сколько секунд будет ждать загрузки экрана-15 секунд)
         */
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
