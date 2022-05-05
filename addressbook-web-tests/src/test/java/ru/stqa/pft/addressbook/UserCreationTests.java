package ru.stqa.pft.addressbook;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class UserCreationTests {
  private WebDriver driver;
  private JavascriptExecutor js;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\kirra\\Downloads\\chromedriver_win32_100\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    js = (JavascriptExecutor) driver;
    driver.get("http://localhost/addressbook/");
    login("admin", "secret");
  }

  private void login(String userName, String password) {
    driver.findElement(By.name("user")).click();
    driver.findElement(By.name("user")).clear();
    driver.findElement(By.name("user")).sendKeys(userName);
    driver.findElement(By.name("pass")).clear();
    driver.findElement(By.name("pass")).sendKeys(password);
    driver.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testUserCreation() throws Exception {

    addNewUser();
    fillUserData("Евгения", "Вячеславовна",
            "Тюрикова", "+79169928151", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988");
    submitUserCreation();
    gotoHomePage();
  }

  private void gotoHomePage() {
    driver.findElement(By.linkText("home page")).click();
  }

  private void submitUserCreation() {
    driver.findElement(By.xpath("//input[21]")).click();
  }

  private void fillUserData(String firstname, String middlename, String lastname, String mobilePhone, String email, String bday, String bmonth, String byear) {
    driver.findElement(By.name("firstname")).clear();
    driver.findElement(By.name("firstname")).sendKeys(firstname);
    driver.findElement(By.name("middlename")).clear();
    driver.findElement(By.name("middlename")).sendKeys(middlename);
    driver.findElement(By.name("lastname")).clear();
    driver.findElement(By.name("lastname")).sendKeys(lastname);
    driver.findElement(By.name("mobile")).clear();
    driver.findElement(By.name("mobile")).sendKeys(mobilePhone);
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys(email);
    new Select(driver.findElement(By.name("bday"))).selectByVisibleText(bday);
    new Select(driver.findElement(By.name("bmonth"))).selectByVisibleText(bmonth);
    driver.findElement(By.name("byear")).clear();
    driver.findElement(By.name("byear")).sendKeys(byear);
  }

  private void addNewUser() {
    driver.findElement(By.linkText("add new")).click();
  }

  private void logout() {
    driver.findElement(By.linkText("Logout")).click();
    driver.get("http://localhost/addressbook/");
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    logout();
    driver.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}
