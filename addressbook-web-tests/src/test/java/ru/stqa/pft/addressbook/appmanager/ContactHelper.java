package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  Logger logger = LoggerFactory.getLogger(ContactHelper.class);

  public void fillContactData(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("middlename"), contactData.getMiddleName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("fax"), contactData.getFax());
    type(By.name("phone2"), contactData.getPhone2());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("address"), contactData.getAddress());
    type(By.name("address2"), contactData.getAddress2());
    selectFromDropDownList("bday", contactData.getBday());
    selectFromDropDownList("bmonth", contactData.getBmonth());
    type(By.name("byear"), contactData.getByear());
    attach(By.name("photo"), new File(contactData.getPhoto()));

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        try {
          Assert.assertTrue(contactData.getGroups().size() == 1);
          new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
        } catch (NoSuchElementException e) {
          System.out.println("Группа \"" + contactData.getGroups() + "\" не найдена.");
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

  public void clickGotoGroupPage() {
    click(By.linkText("group page"));
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

  public void addNewContact(ContactData contact) {
    clickAddNew();
    fillContactData(contact, true);
    submitContactCreating();
    mergePhones(contact);
    mergeEmails(contact);
  }


  public void modify(ContactData contact) {
    fillContactData(contact, false);
    infoFromEditForm(contact);
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
              .withAllPhones(allPhones)

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

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public ContactData infoFromEditForm(ContactData contact) {

    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String fax = wd.findElement(By.name("fax")).getAttribute("value");
    String phone2 = wd.findElement(By.name("phone2")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String address2 = wd.findElement(By.name("address2")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    contact.withFirstName(firstName)
            .withLastName(lastname)
            .withMobilePhone(mobile)
            .withHomePhone(home)
            .withWorkPhone(work)
            .withFax(fax)
            .withPhone2(phone2)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3)
            .withAddress2(address2);
    mergePhones(contact);
    mergeEmails(contact);

    return contact;
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
  }

  public void mergePhones(ContactData contact) {
    contact.withAllPhones(
            Stream.of(
                    Optional.ofNullable(contact.getHomePhone()).orElse("")
                    , Optional.ofNullable(contact.getMobilePhone()).orElse("")
                    , Optional.ofNullable(contact.getWorkPhone()).orElse("")
                    , Optional.ofNullable(contact.getPhone2()).orElse(""))
                    .filter((s) -> !s.equals(""))
                    .map(this::cleaned)
                    .collect(Collectors.joining("\n")));
  }

  public void mergeEmails(ContactData contact) {
    contact.withAllEmails(
            Stream.of(
                    Optional.ofNullable(contact.getEmail()).orElse("")
                    , Optional.ofNullable(contact.getEmail2()).orElse("")
                    , Optional.ofNullable(contact.getEmail3()).orElse(""))
                    .filter((s) -> !s.equals(""))
                    .collect(Collectors.joining("\n")));
  }

  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }


  public void addContactToGroup(int id, GroupData targetGroup) {
    selectContactById(id);
    chooseGroupForAddition(targetGroup.getId());
    submitAddingContactToGroup();
    toGroupPageAfterRemovingContact(targetGroup.getName());
  }

  private void submitAddingContactToGroup() {
    wd.findElement(By.cssSelector(("input[type='submit'][value='Add to']"))).click();
  }

  public void chooseGroupForAddition(int id) {
    wd.findElement(By.cssSelector("select[name='to_group']")).click();
    wd.findElement(By.cssSelector(String.format("select[name='to_group']>option[value='%s']", id))).click();

  }

  public void submitContactDeletingFromGroup(String groupName) {
    wd.findElement(By.cssSelector(String.format("input[type='submit'][value='Remove from \"%s\"']", groupName))).click();
  }

  public void setGroupFilter(int groupId) {
    wd.findElement(By.cssSelector("select[name='group']")).click();
    wd.findElement(By.cssSelector(String.format("select[name='group']>option[value='%s']", groupId))).click();
  }

  public void toGroupPageAfterRemovingContact(String name) {
    wd.findElement(By.xpath(String.format("//a[contains(text(),'group page \"%s\"')]", name))).click();
  }
}
