import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativeTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {WebDriverManager.chromedriver().setup();}

    @BeforeEach
    public void beforeEach(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDowd() {
        driver.quit();
        driver = null;
    }

    @Test
    void sendingFormTestWithInvalidName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Gavrina Anastasia");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79290236555");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub" )).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void sendingFormTestWithoutName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79290236555");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub" )).getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void sendingFormTestWithOutPhoneNumber() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Гаврина Анастасия");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub" )).getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void sendingFormTestWithInvalidPhoneNumber() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Гаврина Анастасия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Phone number");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub" )).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void sendingFormTestWithOutCheckBox() throws InterruptedException {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Гаврина Анастасия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79290236555");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue (driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid" )).isDisplayed());
    }
}