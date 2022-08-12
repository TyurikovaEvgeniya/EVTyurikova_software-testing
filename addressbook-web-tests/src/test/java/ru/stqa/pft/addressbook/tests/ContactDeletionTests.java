package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    ContactData contact;
    if (app.contact().all().size() == 0) {
      contact = new ContactData().withFirstName("Евгения").withMiddleName("Вячеславовна").withLastName("Тюрикова")
              .withMobilePhone(app.contact().randomPhone()).withEmail("evgeniya.tyurikova@ligastavok.ru")
              .withBday("15").withBmonth("May").withByear("1988").withGroup(null);

      app.contact().addNewContact(contact);
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactDeletionHome() throws Exception {
    Contacts before = app.contact().all();
    ContactData deletedPosition = before.iterator().next() ;

    app.goTo().home();
    app.contact().deleteOnHomePage(deletedPosition);


    Contacts after = app.contact().all();


    assertThat(after, equalTo(before.without(deletedPosition)));

  }


  @Test
  public void testContactDeletionEdit() throws Exception {
    Contacts before = app.contact().all();
    ContactData deletedPosition = before.iterator().next() ;


    app.goTo().contactEdit(deletedPosition);
    app.contact().deleteOnEditPage(deletedPosition);
    app.goTo().home();

    Contacts after = app.contact().all();

    assertThat(after, equalTo(before.without(deletedPosition)));

  }
}
