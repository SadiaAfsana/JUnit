import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static java.lang.Thread.sleep;

public class MyJunit {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
        /*System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headed");
        driver = new ChromeDriver(chromeOptions);*/

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
        driver.findElement(By.id("submit")).sendKeys(Keys.CONTROL, Keys.END);
        // driver.findElement(By.id("submit")).click();
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

       /* Select cars = new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }*/
    }

    @Test
    public void handleNewTab() throws InterruptedException {
        driver.get("https://demoqa.com/links");
        driver.findElement(By.id("simpleLink")).click();
        sleep(5000);

        ArrayList<String> w = new ArrayList<>(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement imgElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(("//img[@src='/images/Toolsqa.jpg']"))));
        Boolean status = imgElement.isDisplayed();
        //Boolean status = driver.findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']")).isDisplayed();
        Assert.assertEquals(true, status);
        driver.close();
        driver.switchTo().window(w.get(0));
    }

    @Test
    public void handleChildWindow() {
        driver.get("https://demoqa.com/browser-windows");
        // Thread.sleep(5000);
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        // wait.until(ExpectedConditions.elementToBeClickable(By.id("windowButton")));
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text = driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }

        }
    }

    @Test
    public void modalDialog() {

        driver.get("https://demoqa.com/modal-dialogs");
        //WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("showSmallModal")));
        driver.findElement(By.id("showSmallModal")).click();
        //element.click();
        driver.findElement(By.id("closeSmallModal")).click();

    }

    @Test
    public void webTables() {
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-1']//*[@stroke='currentColor']")).click();
        driver.findElement(By.xpath("//input[@id='firstName']")).clear();
        driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("Sadia Afsana");
        driver.findElement(By.id("submit")).click();

    }

    @Test
    public void scrapData() {
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i = 0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num[" + i + "] " + cell.getText());

            }
        }
    }

    @Test
    public void uploadImage() {
        driver.get("https://demoqa.com/upload-download");
        WebElement uploadElement = driver.findElement(By.id("uploadFile"));
        uploadElement.sendKeys("C:\\Users\\bri_6\\OneDrive\\Desktop\\Image\\Rose.jpg");

        String text = driver.findElement(By.id("uploadedFilePath")).getText();
        Assert.assertTrue(text.contains("Rose.jpg"));
    }

    @Test
    public void handleIframe() {
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text = driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(text);
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();

    }

    @Test
    public void mouseHover() throws InterruptedException {
        driver.get("https://green.edu.bd/");
        WebElement mainMenu = driver.findElement(By.xpath("//a[@class='dropdown-toggle'][contains(text(),'About Us')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(mainMenu).perform();
        Thread.sleep(3000);
        WebElement subMenu = driver.findElement(By.xpath("//li[@id='menu-item-325']//a[contains(text(),'History')]"));
        actions.moveToElement(subMenu);
        actions.click().build().perform();

    }

    @Test
    public void keyboardEvents() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver").keyUp(Keys.SHIFT).doubleClick().contextClick().perform();

        Thread.sleep(5000);
    }

    @Test
    public void takeScreenshot() throws IOException {
        driver.get("https://demoqa.com");
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);

    }

    public static void readFromExcel(String filePath, String fileName, String sheetName) throws IOException {
        File file = new File(filePath + "\\" + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if (fileExtensionName.equals(".xls")) {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (int i = 0; i < rowCount + 1; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                System.out.print((row.getCell(j).getStringCellValue()) + "|| ");
            }
            System.out.println();

        }
    }

    @Test
    public void readData() throws IOException {
        readFromExcel("C:\\", "Users\\bri_6\\OneDrive\\Desktop\\Selenium\\Selenium_Test.xls", "Sheet1");
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

}

