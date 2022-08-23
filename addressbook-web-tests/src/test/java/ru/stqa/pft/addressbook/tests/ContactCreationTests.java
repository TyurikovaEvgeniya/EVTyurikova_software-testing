package ru.stqa.pft.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @Test(enabled = false)
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/OW.PNG");
    ContactData contact;
    contact = new ContactData();
    contact.withFirstName("Евгения");
    contact.withMiddleName("Вячеславовна");
    contact.withLastName("Тюрикова");
    contact.withMobilePhone(app.contact().randomPhone());
    contact.withEmail("evgeniya.tyurikova@ligastavok.ru");
    contact.withBday("15");
    contact.withBmonth("May");
    contact.withByear("1988");
    contact.withPhoto(photo);

    app.contact().addNewContact(contact);
    app.contact().mergeEmails(contact);
    app.contact().mergePhones(contact);
    app.goTo().homePage();
    Contacts after = app.contact().all();

    assertEquals(after.size(), before.size() + 1);
    assertThat(after,
            equalTo(before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt())
                    .withAllEmails(contact.getAllEmails())
                    .withAllPhones(contact.getAllPhones()))));

  }

}
