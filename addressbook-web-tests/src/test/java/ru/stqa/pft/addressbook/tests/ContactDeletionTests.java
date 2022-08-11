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
    if (!app.getContactHelper().isThereContact(contactDeletePosition)) {
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

    app.getContactHelper().clickCertainContactInMainTable(contactDeletePosition);
    app.getContactHelper().DeleteContactOnHomePage();
    app.getContactHelper().confirmContactsDeletion();
    app.getNavigationHelper().gotoHome();

    List<ContactData> after = app.getContactHelper().getContactList();

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
    if (!app.getContactHelper().isThereContact(ContactDeletePosition)) {
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

    app.getNavigationHelper().gotoContactEdit(1);
    app.getContactHelper().DeleteContactOnEditPage();

    List<ContactData> after = app.getContactHelper().getContactList();

    before.remove(ContactDeletePosition - 1);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);
  }
}
