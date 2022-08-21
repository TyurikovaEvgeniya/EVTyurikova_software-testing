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
          System.out.println("Группа \"" + contactData.getGroup() + "\" не найдена.");
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
    List<WebElement> rows = wd.findElements(By.xpath("//tr[@name='entry']"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();

      contacts.add(new ContactData()

              .withId(id)
              .withFirstName(firstname)
              .withAddress(address)
              .withAllEmails(allEmails)
              .withLastName(lastname)
              .withAllPhones( allPhones)

      );
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

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String address2 = wd.findElement(By.name("address2")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withFirstName(firstName)
            .withLastName(lastname)
            .withMobilePhone(mobile)
            .withHomePhone(home)
            .withWorkPhone(work)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3)
            .withAddress2(address2);
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
  }
}
