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
    if (!app.getContactHelper().isThereContact(ContactUpdatePosition)) {
      contact = new ContactData("Евгения",
              "Вячеславовна",
              "Тюрикова",
              app.getContactHelper().randomPhone(),
              "evgeniya.tyurikova@ligastavok.ru",
              "15",
              "May",
              "1988",
              null);

      app.getContactHelper().addNewContact(contact);
      app.getNavigationHelper().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    app.getNavigationHelper().gotoContactDetails(ContactUpdatePosition);
    app.getNavigationHelper().gotoModifingContactOnDetailsPage();
    String phone = app.getContactHelper().randomPhone();
    app.getContactHelper().modifyContactPhone(phone);
    app.getContactHelper().submitContactUpdating();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    if (contact == null) {
      contact = before.get(ContactUpdatePosition - 1);
      contact.setMobilePhone(phone);
    }

    before.set(ContactUpdatePosition - 1, contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);

  }

  @Test (enabled = false)
  public void testContactModificationEdit() throws Exception {
    ContactData contact = null;

    int ContactUpdatePosition = 1;
    if (!app.getContactHelper().isThereContact(ContactUpdatePosition)) {
      contact = new ContactData("Евгения",
              "Вячеславовна",
              "Тюрикова",
              app.getContactHelper().randomPhone(),
              "evgeniya.tyurikova@ligastavok.ru",
              "15",
              "May",
              "1988",
              null);

      app.getContactHelper().addNewContact(contact);
      app.getNavigationHelper().gotoHomePage();
    }

    List<ContactData> before = app.getContactHelper().getContactList();

    app.getNavigationHelper().gotoContactEdit(ContactUpdatePosition);
    String phone = app.getContactHelper().randomPhone();
    app.getContactHelper().modifyContactPhone(phone);
    app.getContactHelper().submitContactUpdating();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    if (contact == null) {
      contact = before.get(ContactUpdatePosition - 1);
      contact.setMobilePhone(phone);
    }

    before.set(ContactUpdatePosition - 1, contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}
