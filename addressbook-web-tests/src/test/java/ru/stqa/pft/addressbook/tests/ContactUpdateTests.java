package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactUpdateTests extends TestBase {

  @Test
  public void testContactUpdateDetails() throws Exception {

    if (!app.getContactHelper().isThereContact()) {
      app.getContactHelper().addNewContact(new ContactData("Евгения", "Вячеславовна", "Тюрикова", "+72222222222", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988", "Нечто иное"),
              true);
      app.getNavigationHelper().gotoHomePage();
    }

    app.getNavigationHelper().gotoContactDetails(1);
    app.getNavigationHelper().gotoModifingContactOnDetailsPage();
    app.getContactHelper().modifyContactPhone("+73333333333");
    app.getContactHelper().submitContactUpdating();
  }

  @Test
  public void testContactModificationEdit() throws Exception {
    if (!app.getContactHelper().isThereContact()) {
      app.getContactHelper().addNewContact(new ContactData("Евгения", "Вячеславовна", "Тюрикова", "+74444444444", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988", "Нечто иное"),
              true);
      app.getNavigationHelper().gotoHomePage();
    }

    app.getNavigationHelper().gotoContactEdit(1);
    app.getContactHelper().modifyContactPhone("+75555555555");
    app.getContactHelper().submitContactUpdating();
  }
}
