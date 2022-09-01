package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(WebDriver wd, ApplicationManager app) {
    super(wd,app);
  }

  public void start(String userName, String email) {
    type(By.name("username"), userName);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
    click(By.xpath("*//a[contains(text(),'Продолжить')]"));

  }

  public void finish(String password, String realname) {
    type(By.name("realname"), realname);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("*//span[contains(text(),'Изменить пользователя')]"));
    click(By.xpath("*//a[contains(text(),'Продолжить')]"));
  }

  public void changePassword(String confirmationLink, String password) {
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("*//span[contains(text(),'Изменить пользователя')]"));
    click(By.xpath("*//a[contains(text(),'Продолжить')]"));
  }

  public void login(String user, String password) {
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("*//span[contains(text(),'Изменить пользователя')]"));
    click(By.xpath("*//a[contains(text(),'Продолжить')]"));
  }
}
