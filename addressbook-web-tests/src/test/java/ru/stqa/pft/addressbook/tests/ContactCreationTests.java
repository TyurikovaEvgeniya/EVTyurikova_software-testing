package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Евгения",
            "Вячеславовна",
            "Тюрикова",
            app.getContactHelper().randomPhone(),
            "evgeniya.tyurikova@ligastavok.ru",
            "15",
            "May",
            "1988",
            "Нечто");
    app.getContactHelper().addNewContact(contact);
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();

    if (after.stream().max(Comparator.comparingInt(ContactData::getId)).isPresent()) {
      contact.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
    }
    before.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }

}
