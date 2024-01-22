import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class klimberFrontEnd {
    ChromeOptions options = new ChromeOptions();
    static WebDriver driver = new ChromeDriver();
    By priceSliderLocator  = By.xpath( "//*[@id=\"frmIndex\"]/div[1]/div[4]/div/div[2]/div/div[5]");
    @BeforeClass
    void setup () {
        options.addArguments("--remote-allow-origins*");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://purchase-testing.klimber.com/ar/GroupLife/Index");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    void teststeps() {

        // Clear and input data (Environments Variables)
        // date
        WebElement dateBox = driver.findElement(By.id("BirthdayStep1"));
        dateBox.clear();
        dateBox.sendKeys(System.getenv("date"));
        // Province
        driver.findElement(By.id("select2-province-container")).click();
        WebElement comboProv = driver.findElement(By.className("select2-search__field"));
        comboProv.clear();
        comboProv.sendKeys(System.getenv("boxProv"), Keys.ENTER);
        // Cel Phone
        WebElement phoneCode = driver.findElement(By.id("txtPhoneCode"));
        phoneCode.clear();
        phoneCode.sendKeys("11");
        WebElement phoneNumber = driver.findElement(By.id("txtPhoneNumber"));
        phoneNumber.clear();
        phoneNumber.sendKeys("11991199");

        // Validation slider
        WebElement priceSlider = driver.findElement(priceSliderLocator);
        Dimension sliderSize = priceSlider.getSize();
        int sliderWidth = sliderSize.getWidth();
        int xCoord = priceSlider.getLocation().getX();
        Actions builder = new Actions(driver);
        builder.moveToElement(priceSlider)
                .click()
                .dragAndDropBy(priceSlider,xCoord + sliderWidth, 0)
                .build()
                .perform();

        // Assert between texts prices por month
        WebElement SliderValLabel = driver.findElement(By.id("ex6CurrentSliderValLabel"));
        String SliderValLabelValue = SliderValLabel.getText();
        WebElement SliderContrat = driver.findElement(By.id("btnSaveStep1"));
        String SliderContratValue = SliderContrat.getText();
        Assert.assertEquals(SliderValLabelValue.substring(2),SliderContratValue.substring(15));
    }
}
