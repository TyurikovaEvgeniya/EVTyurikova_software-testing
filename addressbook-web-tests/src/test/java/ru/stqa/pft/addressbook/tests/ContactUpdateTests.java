package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactUpdateTests extends TestBase {

  @BeforeMethod
  public void ensurePrecoditions() {
    ContactData contact = null;
    if (app.contact().all().size() == 0) {
      contact = new ContactData().withFirstName("Евгения").withMiddleName("Вячеславовна").withLastName("Тюрикова")
              .withMobilePhone(app.contact().randomPhone()).withEmail("evgeniya.tyurikova@ligastavok.ru")
              .withBday("15").withBmonth("May").withByear("1988").withGroup(null);

      app.contact().addNewContact(contact);
    }
    app.goTo().homePage();
  }

  @Test(enabled = false)
  public void testContactUpdateDetails() throws Exception {

    Contacts before = app.contact().all();
    ContactData updatedPosition = before.iterator().next();

    String newPhone = app.contact().randomPhone();
    ContactData contact;
    contact = updatedPosition.withMobilePhone(newPhone);

    app.goTo().contactDetails(updatedPosition);
    app.goTo().modifingContactOnDetailsPage();
    app.contact().modify(contact);
    app.goTo().homePage();

    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.without(updatedPosition).withAdded(contact)));


  }


  @Test(enabled = false)
  public void testContactModificationEdit() throws Exception {

    Contacts before = app.contact().all();
    ContactData updatedPosition = before.iterator().next();

    String newPhone = app.contact().randomPhone();
    ContactData contact;
    contact = updatedPosition.withMobilePhone(newPhone);

    app.goTo().contactEdit(updatedPosition);
    app.contact().modify(contact);
    app.goTo().homePage();

    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.without(updatedPosition).withAdded(contact)));

  }
}
