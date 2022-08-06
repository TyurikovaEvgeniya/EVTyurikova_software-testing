package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletionHome() throws Exception {
    if (!app.getContactHelper().isThereContact()) {
      app.getContactHelper().addNewContact(new ContactData("Евгения", "Вячеславовна", "Тюрикова", "+76666666666", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988", "Нечто иное"),
              true);
      app.getNavigationHelper().gotoHomePage();
    }
    app.getContactHelper().clickFirstContactInMainTable();
    app.getContactHelper().DeleteContactOnHomePage();
    app.getContactHelper().confirmContactsDeletion();
  }

  @Test
  public void testContactDeletionEdit() throws Exception {
    if (!app.getContactHelper().isThereContact()) {
      app.getContactHelper().addNewContact(new ContactData("Евгения", "Вячеславовна", "Тюрикова", "+77777777777", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988", "Нечто иное"),
              true);
      app.getNavigationHelper().gotoHomePage();
    }
    app.getNavigationHelper().gotoContactEdit(1);
    app.getContactHelper().DeleteContactOnEditPage();
  }
}
