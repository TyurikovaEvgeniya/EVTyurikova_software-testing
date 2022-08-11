package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactUpdateTests extends TestBase {

  public static final int UPDATE_POSITION = 1;

  @Test (enabled = false)
  public void testContactUpdateDetails() throws Exception {
    ContactData contact = null;

    if (!app.getContactHelper().isThereContact(UPDATE_POSITION)) {
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

    app.getNavigationHelper().gotoContactDetails(UPDATE_POSITION);
    app.getNavigationHelper().gotoModifingContactOnDetailsPage();
    String phone = app.getContactHelper().randomPhone();
    app.getContactHelper().modifyContactPhone(phone);
    app.getContactHelper().submitContactUpdating();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    if (contact == null) {
      contact = before.get(UPDATE_POSITION - 1);
      contact.setMobilePhone(phone);
    }

    before.set(UPDATE_POSITION - 1, contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);

  }

  @Test (enabled = false)
  public void testContactModificationEdit() throws Exception {
    ContactData contact = null;

    if (!app.getContactHelper().isThereContact(UPDATE_POSITION)) {
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

    app.getNavigationHelper().gotoContactEdit(UPDATE_POSITION);
    String phone = app.getContactHelper().randomPhone();
    app.getContactHelper().modifyContactPhone(phone);
    app.getContactHelper().submitContactUpdating();
    app.getNavigationHelper().gotoHomePage();

    List<ContactData> after = app.getContactHelper().getContactList();

    if (contact == null) {
      contact = before.get(UPDATE_POSITION - 1);
      contact.setMobilePhone(phone);
    }

    before.set(UPDATE_POSITION - 1, contact);

    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }
}
