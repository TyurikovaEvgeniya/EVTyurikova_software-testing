package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {


  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    } else {
      click(By.linkText("groups"));
    }
  }

  public void gotoHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    } else {
      click(By.linkText("home page"));
    }
  }

  public void gotoHome() {
      click(By.linkText("home"));
  }

  public void gotoContactEdit(int position) {
    click(By.xpath(String.format("//tr[%s]//img[@alt=\"Edit\"]", position + 1)));
  }

  public void gotoContactDetails(int position) {
    click(By.xpath(String.format("//tr[%s]//img[@alt=\"Details\"]", position + 1)));
  }

  public void gotoModifingContactOnDetailsPage() {
    click(By.name("modifiy"));
  }
}
