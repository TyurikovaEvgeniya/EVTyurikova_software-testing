package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactUpdateTests extends TestBase {

  @Test (enabled = false)
  public void testContactUpdateDetails() throws Exception {
    ContactData contact = null;

    int ContactUpdatePosition = 1;
    if (!app.contact().isThereContact(ContactUpdatePosition)) {
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

    String phone = app.contact().randomPhone();

    app.goTo().contactDetails(ContactUpdatePosition);


    app.goTo().modifingContactOnDetailsPage();
    app.contact().modify(phone);
    app.goTo().homePage();

    List<ContactData> after = app.contact().list();

    if (contact == null) {
      contact = before.get(ContactUpdatePosition - 1);
      contact.setMobilePhone(phone);
    }

    contact = before.get(ContactUpdatePosition - 1);

    if (after.stream().max(Comparator.comparingInt(ContactData::getId)).isPresent()
            && after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId() == Integer.MAX_VALUE) {
      contact.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
    }

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);

  }


  @Test (enabled = false)
  public void testContactModificationEdit() throws Exception {
    ContactData contact = null;

    int ContactUpdatePosition = 1;
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

    String phone = app.contact().randomPhone();

    app.goTo().contactEdit(ContactUpdatePosition);
    app.contact().modify(phone);
    app.goTo().homePage();

    List<ContactData> after = app.contact().list();


    contact = before.get(ContactUpdatePosition - 1);

    if (after.stream().max(Comparator.comparingInt(ContactData::getId)).isPresent()
            && after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId() == Integer.MAX_VALUE) {
      contact.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
    }

    contact.setMobilePhone(phone);

    before.set(ContactUpdatePosition - 1, contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}
