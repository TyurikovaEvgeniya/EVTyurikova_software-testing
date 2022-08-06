package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

  public static final String FIRST_CONTACT_IN_MAINTABLE = "//table[@id='maintable']/tbody/tr[2]/td/input";
  public static final String PHONE_IN_MAINTABLE = "//*[@id='maintable']/tbody//td[6][text()='%s']";

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//input[1][@type = 'submit'][@value='Enter']"));
  }

  public void fillContactData(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail());
    selectFromDropDownList("bday", contactData.getBday());
    selectFromDropDownList("bmonth", contactData.getBmonth());
    type(By.name("byear"), contactData.getByear());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void clickAddNew() {
    click(By.linkText("add new"));
  }

  public void clickFirstContactInMainTable() {
    click(By.xpath(FIRST_CONTACT_IN_MAINTABLE));
  }

  public void DeleteContactOnHomePage() {
    click(By.xpath("//input[@type = 'button'][@value='Delete']"));
  }

  public void DeleteContactOnEditPage() {
    click(By.xpath("//input[@type = 'submit'][@value='Delete']"));
  }

  public void confirmContactsDeletion() {
    acceptAlert();
  }


  public void modifyContactPhone(String phone) {
    type(By.name("mobile"), phone);
  }

  public void submitContactUpdating() {
    click(By.xpath("//input[1][@type = 'submit'][@value='Update']"));
  }

  public void submitContactCreating() {
    click(By.xpath("//input[1][@type = 'submit'][@value='Enter']"));
  }

  public void addNewContact(ContactData contactData, boolean creation) {
    clickAddNew();
    fillContactData(contactData, creation);
    submitContactCreation();
  }

  public boolean isThereContact() {
    return isElementPresent(By.xpath(FIRST_CONTACT_IN_MAINTABLE));
  }

  public boolean isThereContactWithPhone(String phone) {
    return isElementPresent(By.xpath(String.format(PHONE_IN_MAINTABLE, phone)));
  }
}
