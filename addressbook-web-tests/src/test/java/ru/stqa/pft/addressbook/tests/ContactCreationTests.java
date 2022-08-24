package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader( (new FileReader(new File(TEST_DATA_FILE_DIR + "ContactValuesValid.json"))))) {
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

  @Test(dataProvider = "validContactsFromJson", enabled = true)
  public void testContactCreation(ContactData contact) throws Exception {
    Contacts before = app.contact().all();


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
