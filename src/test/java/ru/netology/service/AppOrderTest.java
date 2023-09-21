package ru.netology.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;



public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void BeforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    public void AfterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    void tesTheCardOrderForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);

    }

    @Test
    void InvalidName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        driver.findElement(By.cssSelector(".input_invalid[data-test-id=name]"));
        String errorMassage = driver.findElement(By.className("input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", errorMassage);
    }

    @Test
    void EmptyFieldName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        driver.findElement(By.cssSelector(".input_invalid[data-test-id=name]"));
        String errorMassage = driver.findElement(By.className("input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", errorMassage);
    }
    // @Test
//    void EmptyPhoneField() {
//        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов иван");
//        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
//        driver.findElement(By.cssSelector("button.button")).click();
//        driver.findElement(By.cssSelector(".input_invalid[data-test-id=phone]"));
//        String errorMassage = driver.findElement(By.className("input__sub")).getText();
//        Assertions.assertEquals("Поле обязательно для заполнения", errorMassage); // актуал: укажите точно как в паспорте
//    }

//    @Test
//    void InvalidPhone() {
//        driver.get("http://localhost:9999");
//        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
//        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("9876543210");
//        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
//        driver.findElement(By.cssSelector("button.button")).click();
//        driver.findElement(By.cssSelector(".input_invalid[data-test-id=phone]"));
//        String errorMassage = driver.findElement(By.className("input__sub")).getText();
//        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", errorMassage);
}


