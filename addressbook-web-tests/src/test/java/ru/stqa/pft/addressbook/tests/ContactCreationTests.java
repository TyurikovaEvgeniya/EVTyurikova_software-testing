package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {

    app.getContactHelper().addNewContact();
    app.getContactHelper().fillContactData(
            new ContactData("Евгения", "Вячеславовна", "Тюрикова", "+79169928157", "evgeniya.tyurikova@ligastavok.ru", "15", "May", "1988", "Нечто иное"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().gotoHomePage();
  }

}
