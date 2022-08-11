package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test (enabled = false)
  public void testContactDeletionHome() throws Exception {
    ContactData contact = null;

    int contactDeletePosition = 1;
    if (app.contact().list().size() == 0) {
      contact = new ContactData("Евгения",
              "Вячеславовна",
              "Тюрикова",
              app.contact().randomPhone(),
              "evgeniya.tyurikova@ligastavok.ru",
              "15",
              "May",
              "1988",
              null);

      app.contact().addNewContact(contact);
      app.goTo().homePage();
    }

    List<ContactData> before = app.contact().list();

    app.contact().delete(contactDeletePosition);
    app.goTo().home();

    List<ContactData> after = app.contact().list();

    before.remove(contactDeletePosition - 1);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }



  @Test (enabled = false)
  public void testContactDeletionEdit() throws Exception {

    ContactData contact = null;
    int ContactDeletePosition = 1;
    if (!app.contact().isThereContact(ContactDeletePosition)) {
      contact = new ContactData("Евгения",
              "Вячеславовна",
              "Тюрикова",
              app.contact().randomPhone(),
              "evgeniya.tyurikova@ligastavok.ru",
              "15",
              "May",
              "1988",
              null);

      app.contact().addNewContact(contact);
      app.goTo().homePage();

    }

    List<ContactData> before = app.contact().list();

    app.goTo().contactEdit(1);
    app.contact().deleteContactOnEditPage();

    List<ContactData> after = app.contact().list();

    before.remove(ContactDeletePosition - 1);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);
  }
}
