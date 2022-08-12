package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillContactData(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
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

  public void deleteContactOnHomePage() {
    click(By.xpath("//input[@type = 'button'][@value='Delete']"));
  }

  public void deleteContactOnEditPage() {
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
    submitContactCreating();
  }

  public String randomPhone() {
    return "+7777777" + (int) (100 + Math.random() * 1000) % 1000;
  }


  public void modify(ContactData contact) {
    modifyContactPhone(contact.getMobilePhone());
    submitContactUpdating();
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = element.findElement(By.xpath("./td[2]")).getText();
      String firstname = element.findElement(By.xpath("./td[3]")).getText();
      String phone = String.valueOf(element.findElement(By.xpath("./td[6]")).getText());

      contacts.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname).withMobilePhone(phone));
    }
    return contacts;
  }

  public void deleteOnHomePage(ContactData deletedContact) {
   selectContactById(deletedContact.getId());
   deleteContactOnHomePage();
   confirmContactsDeletion();
  }

  public void deleteOnEditPage(ContactData deletedContact) {
    deleteContactOnEditPage();
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }
}
