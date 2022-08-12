package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class NavigationHelper extends HelperBase {


  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void groupPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    } else {
      click(By.linkText("groups"));
    }
  }

  public void homePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    } else {
      click(By.linkText("home page"));
    }
  }

  public void home() {
      click(By.linkText("home"));
  }

  public void contactEdit(ContactData contact) {
    click(By.xpath(String.format("//tr//input[@value='%s']/following::img[@alt=\"Edit\"][1]", contact.getId())));
  }

  public void contactDetails(ContactData contact) {
    click(By.xpath(String.format("//tr//input[@value='%s']/following::img[@alt=\"Details\"][1]", contact.getId())));
  }

  public void modifingContactOnDetailsPage() {
    click(By.name("modifiy"));
  }

}
