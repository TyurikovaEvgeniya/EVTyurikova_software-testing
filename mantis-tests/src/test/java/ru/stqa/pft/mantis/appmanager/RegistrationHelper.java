package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String userName, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), userName);
    type(By.name("email"),email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
    click(By.xpath("*//a[contains(text(),'Продолжить')]"));
    System.out.println(wd.getPageSource());

  }

  public void finish(String confirmationLink, String password, String realname) {
    wd.get(confirmationLink);
    type(By.name("realname"),realname);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.xpath("*//span[contains(text(),'Изменить пользователя')]"));
    click(By.xpath("*//a[contains(text(),'Продолжить')]"));
  }
}
