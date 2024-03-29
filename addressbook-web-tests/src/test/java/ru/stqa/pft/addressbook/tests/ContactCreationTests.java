package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

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
  public static Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader( (new FileReader(new File(app.getTestDataDir() + app.properties.getProperty("gen.contact.creation.valid") +".json"))))) {
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
  public static void testContactCreation(ContactData contact) throws Exception {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    contact = contact.inGroup(groups.iterator().next());
    app.contact().addNewContact(contact);
    app.goTo().homePage();
    Contacts after = app.db().contacts();
    assertEquals(after.size(), before.size() + 1);
    assertThat(after,
            equalTo(before.withAdded(contact.toDBTypes().withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));


  }

}
