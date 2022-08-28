package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    ContactData contact;
    if (app.contact().all().size() == 0) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Тюрикова")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePreconditions.delete@gmail.com")
              .withBday("15").withBmonth("May").withByear("1988").withPhoto(app.getPhotoPath());

      app.contact().addNewContact(contact);

    }
    app.goTo().homePage();
  }

  @Test(enabled = true)
  public void testContactDeletionHome() throws Exception {

    Contacts before = app.db().contacts();
    ContactData deletedPosition = before.iterator().next() ;
    app.contact().deleteOnHomePage(deletedPosition);
    app.goTo().home();
    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(deletedPosition)));

  }


  @Test(enabled = true)
  public void testContactDeletionEdit() throws Exception {
    Contacts before = app.db().contacts();
    ContactData deletedPosition = before.iterator().next() ;


    app.goTo().contactEdit(deletedPosition);
    app.contact().deleteOnEditPage(deletedPosition);
    app.goTo().home();

    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(deletedPosition)));

  }
}
