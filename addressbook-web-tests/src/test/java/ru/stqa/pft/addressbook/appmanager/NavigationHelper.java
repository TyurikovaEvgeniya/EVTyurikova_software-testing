package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

  public void contactEdit(int position) {
    click(By.xpath(String.format("//tr[%s]//img[@alt=\"Edit\"]", position + 1)));
  }

  public void contactDetails(int position) {
    click(By.xpath(String.format("//tr[%s]//img[@alt=\"Details\"]", position + 1)));
  }

  public void modifingContactOnDetailsPage() {
    click(By.name("modifiy"));
  }
}
