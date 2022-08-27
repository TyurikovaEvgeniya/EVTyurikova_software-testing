package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactUpdateTests extends TestBase {

  @BeforeMethod
  public void ensurePrecoditions() {
    ContactData contact = null;
    if (app.contact().all().size() == 0) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Тюрикова")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePrecoditions.update@ligastavok.ru")
              .withBday("15").withBmonth("May").withByear("1988").withGroup(null).withPhoto(app.getPhotoPath());

      app.contact().addNewContact(contact);

    }
    app.goTo().homePage();
  }

  @DataProvider
  public Iterator<Object[]> validContactModificationValuesFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader( (new FileReader(new File(app.getTestDataDir() + app.properties.getProperty("gen.contact.modification.valid") +".json"))))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += (line);
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType()); //List<ContactData>.class
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(enabled = true, dataProvider = "validContactModificationValuesFromJson")
  public void testContactUpdateDetails(ContactData modificationContactData) throws Exception {

    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();

    app.goTo().contactDetails(modifiedContact);
    app.goTo().modifingContactOnDetailsPage();
    app.contact().modify(modificationContactData);

    app.goTo().homePage();

    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(modifiedContact)
            .withAdded(modificationContactData.withId(modifiedContact.getId()).toDBTypes())));


  }


  @Test(enabled = true, dataProvider = "validContactModificationValuesFromJson")
  public void testContactModificationEdit(ContactData modificationContactData) throws Exception {

    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();

    app.goTo().contactEdit(modifiedContact);
    app.contact().modify(modificationContactData);

    app.goTo().homePage();

    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(modifiedContact)
            .withAdded(modificationContactData.withId(modifiedContact.getId()).toDBTypes())));

  }
}
