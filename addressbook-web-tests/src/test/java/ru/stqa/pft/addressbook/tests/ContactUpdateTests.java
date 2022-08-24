package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.generators.ContactDataGenerator.ACCOUNT_IMG;

public class ContactUpdateTests extends TestBase {

  @BeforeMethod
  public void ensurePrecoditions() {
    ContactData contact = null;
    if (app.contact().all().size() == 0) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Тюрикова")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePrecoditions.update@ligastavok.ru")
              .withBday("15").withBmonth("May").withByear("1988").withGroup(null).withPhoto(ACCOUNT_IMG);
      app.contact().mergeEmails(contact);
      app.contact().mergePhones(contact);
      app.contact().addNewContact(contact);
    }
    app.goTo().homePage();
  }

  @Test(enabled = true)
  public void testContactUpdateDetails() throws Exception {

    Contacts before = app.contact().all();
    ContactData updatedPosition = before.iterator().next();

    String newPhone = ContactDataGenerator.randomPhone();
    ContactData contact;
    contact = updatedPosition.withMobilePhone(newPhone);

    app.goTo().contactDetails(updatedPosition);
    app.goTo().modifingContactOnDetailsPage();
    app.contact().modify(contact);
    app.contact().mergePhones(contact);
    app.goTo().homePage();

    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.without(updatedPosition).withAdded(contact)));


  }


  @Test(enabled = true)
  public void testContactModificationEdit() throws Exception {

    Contacts before = app.contact().all();
    ContactData updatedPosition = before.iterator().next();

    String newPhone = ContactDataGenerator.randomPhone();
    ContactData contact;
    contact = updatedPosition.withMobilePhone(newPhone);

    app.goTo().contactEdit(updatedPosition);
    app.contact().modify(contact);
    app.contact().mergePhones(contact);
    app.goTo().homePage();

    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.without(updatedPosition).withAdded(contact)));

  }
}
