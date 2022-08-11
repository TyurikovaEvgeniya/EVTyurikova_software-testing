package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public static final String CONTACT_IN_MAINTABLE = "//table[@id='maintable']/tbody/tr[%s]/td/input";
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
      if (contactData.getGroup() != null) {
        try {
          new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } catch (NoSuchElementException e) {
          System.out.println("Группа \""+ contactData.getGroup() + "\" не найдена.");
          new Select(wd.findElement(By.name("new_group"))).selectByVisibleText("[none]");

        }
      } else {
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText("[none]");
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void clickAddNew() {
    click(By.linkText("add new"));
  }

  public void clickCertainContactInMainTable(int position) {
    click(By.xpath(String.format(CONTACT_IN_MAINTABLE, position + 1)));
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

  public void addNewContact(ContactData contactData) {
    clickAddNew();
    fillContactData(contactData, true);
    submitContactCreation();
  }

  public boolean isThereContact(int position) {
    return isElementPresent(By.xpath(String.format(CONTACT_IN_MAINTABLE, position + 1)));
  }

  public boolean isThereContactWithPhone(String phone) {
    return isElementPresent(By.xpath(String.format(PHONE_IN_MAINTABLE, phone)));
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<>();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement element : elements) {
      String el = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = element.findElement(By.xpath("./td[2]")).getText();
      String firstname = element.findElement(By.xpath("./td[3]")).getText();
      String phone = String.valueOf(element.findElement(By.xpath("./td[6]")).getText());


      ContactData contact = new ContactData(id, firstname, null, lastname, phone, null, null, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }

  public String randomPhone() {
    return "+7777777" + (int) (100 + Math.random() * 1000) % 1000;
  }
}
