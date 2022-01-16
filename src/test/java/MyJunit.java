import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class MyJunit {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--headed");
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void getTitle() {

        driver.get("https://demoqa.com");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("ToolsQA"));
    }

    @Test
    public void CheckIfElementExists() {
        driver.get("https://demoqa.com");
        //  boolean status = driver.findElement(By.className("banner-image")).isDisplayed();
        /*WebElement imgElement = driver.findElement(By.className("banner-image"));
        boolean status = imgElement.isDisplayed();
        Assert.assertTrue(status);
        driver.close();*/

       /* wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        boolean status = wait.until(ExpectedConditions.elementToBeClickable(By.className("banner-image"))).isDisplayed();
       Assert.assertTrue(status);*/

        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement imgElement = wait.until(ExpectedConditions.elementToBeClickable(By.className("banner-image")));
        boolean status = imgElement.isDisplayed();
        Assert.assertTrue(status);

    }

    @Test
    public void fillupForm() {
        driver.get("https://demoqa.com/text-box");
        //  driver.findElement(By.id("userName")).sendKeys("Sadia Afsana");
        // driver.findElement(By.cssSelector("#userName")).sendKeys("Sadia Afsana");
        driver.findElement(By.cssSelector("[placeholder = 'Full Name']")).sendKeys("Sadia Afsana");
        driver.findElement(By.id("userEmail")).sendKeys("Sadia.Afsana@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Dhanmondi, Dhaka-1205");
        driver.findElement(By.id("permanentAddress")).sendKeys("Ghatail, Tangail");
        driver.findElement(By.id("submit")).click();
    }

    @Test
    public void clickButton() {
        driver.get("https://demoqa.com/buttons");
        WebElement doubleClickBtnElement = driver.findElement(By.id("doubleClickBtn"));
        WebElement rightClickBtnElement = driver.findElement(By.id("rightClickBtn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickBtnElement).perform();
        actions.contextClick(rightClickBtnElement).perform();

        String text1 = driver.findElement(By.id("rightClickMessage")).getText();
        String text2 = driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertTrue(text1.contains("You have done a right click"));
        Assert.assertTrue(text2.contains("You have done a double click"));

    }

    @Test
    public void clickMultipleButton() {
        driver.get("https://demoqa.com/buttons");
        //List<WebElement> buttonElement =  driver.findElements(By.tagName("button"));
        List<WebElement> buttonElement = driver.findElements(By.cssSelector("[type = button]"));
        Actions actions = new Actions(driver);
        actions.doubleClick(buttonElement.get(1)).perform();
        actions.contextClick(buttonElement.get(2)).perform();
        actions.click(buttonElement.get(3)).perform();
    }

    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        driver.switchTo().alert().accept();
        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().dismiss();
        driver.findElement(By.id(("promtButton"))).click();
        driver.switchTo().alert().sendKeys("Sadia");
        sleep(2000);
        driver.switchTo().alert().accept();
        String text = driver.findElement(By.id("promptResult")).getText();
        Assert.assertTrue(text.contains("Sadia"));

    }

    @Test
    public void selectDate() {
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).clear();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("05/08/1993");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
    }

    @Test
    public void selectDropdown() {
        driver.get("https://demoqa.com/select-menu");
        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByValue("2");

        Select cars = new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

}
