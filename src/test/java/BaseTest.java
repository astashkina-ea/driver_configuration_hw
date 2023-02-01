import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    @BeforeEach
    public void setUp(TestInfo info) {
        WebDriverManager.chromedriver().setup();
        if (info.getTags().contains("headless")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            options.addArguments("window-size=3456x2234");
            driver = new ChromeDriver(options);
            logger.info("Открыли Chrome в headless режиме");
        } else if (info.getTags().contains("fullscreen")) {
            driver = new ChromeDriver();
            driver.manage().window().fullscreen();
            logger.info("Открыли Chrome в режиме киоска");
        }  else if (info.getTags().contains("maximize")) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            logger.info("Открыли Chrome в режиме полного экрана");
        } else {
            driver = new ChromeDriver();
            logger.info("Открыли Chrome в обычном режиме");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void close() {
        if (driver != null)
            driver.quit();
    }
}