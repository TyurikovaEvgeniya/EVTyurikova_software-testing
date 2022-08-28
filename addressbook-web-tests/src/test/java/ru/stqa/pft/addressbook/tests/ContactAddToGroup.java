package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactAddToGroup extends TestBase {

  private ContactData contactCandidateForAdding;

  @BeforeMethod
  public void ensurePreconditions() {
    ContactData contact;
    if (app.db().contacts().size() == 0) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Тюрикова")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePreconditions.delete@gmail.com")
              .withBday("15").withBmonth("May").withByear("1988").withPhoto(app.getPhotoPath());

      app.contact().addNewContact(contact);
    }
    app.goTo().homePage();
  }

  @Test
  public void ContactAddToGroup() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    GroupData targetGroup = null;
    for (ContactData contact : contacts) {
      System.out.println(contact);
      if (contact.getGroups().equals(groups)) {
        logger.info("Контакт есть во всех существующих группах");
      } else {
        targetGroup = groups.stream().filter(s -> !contact.getGroups().contains(s)).findFirst().orElse(null);
        contactCandidateForAdding = contact;
        logger.info("Контакт есть не во всех существующих группах");
        logger.info("Его нет в группе: " + targetGroup);
        break; //Так не смогла разобрать с while с iterator().hasNext(); Уходит в бесконечный цикл
      }
    }
    if (targetGroup == null) {
      ContactData contact = null;
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Групповна")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePrecoditions.update@ligastavok.ru")
              .withBday("15").withBmonth("May").withByear("1988").withPhoto(app.getPhotoPath());

      app.contact().addNewContact(contact);
      contactCandidateForAdding = app.db().getContactById(app.db().maxContactId());
      targetGroup = groups.iterator().next();

    }
    app.goTo().homePage();

    Contacts before = (Contacts) targetGroup.getContacts();
    app.contact().addContactToGroup(contactCandidateForAdding.getId(), targetGroup);
    Contacts after = (Contacts) targetGroup.getContacts();

    //Добавить проверку добавления контакта в targetGroup
    assertEquals(after.size(), before.size() + 1);
    assertThat(after,
            equalTo(before.withAdded(contactCandidateForAdding.toDBTypes().withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }
}


