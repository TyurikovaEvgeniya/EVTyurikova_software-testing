package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test (enabled = false)
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData("Евгения",
            "Вячеславовна",
            "Тюрикова",
            app.contact().randomPhone(),
            "evgeniya.tyurikova@ligastavok.ru",
            "15",
            "May",
            "1988",
            "Нечто");
    app.contact().addNewContact(contact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();

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
