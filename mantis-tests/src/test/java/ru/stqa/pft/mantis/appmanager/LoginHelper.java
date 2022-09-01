package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;


public class LoginHelper extends HelperBase {


  public LoginHelper(WebDriver wd, ApplicationManager app) {
    super(wd,app);
  }


  public void as(String username, String password) {
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Вход']"));
    type(By.name("password"),password);
    click(By.cssSelector("input[value='Вход']"));
  }

  public void registerInMantis() {
    Dimension dimension = new Dimension(1920, 1080);
    wd.manage().window().setSize(dimension);
    try {
      System.out.println(app.getProperty("web.baseUrl") + "/signup_page.php");
      wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loginInMantis() {
    Dimension dimension = new Dimension(1920, 1080);
    wd.manage().window().setSize(dimension);
    wd.get(app.getProperty("web.baseUrl") + "/login.php");
  }


}
