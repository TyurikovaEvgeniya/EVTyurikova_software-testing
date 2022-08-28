package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteFromGroup  extends TestBase {


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
  public void ContactDeleteFromGroup(){
    /*Найти группу, которая содержит хотя бы один контакт
    Отфильтровать по ней главную таблицу
    Выбрать первый контакт в таблице
    Удалить контакт
    * */

    Groups groups = app.db().groups();
    GroupData targetGroup = groups.stream().filter(s -> s.getContacts().size() != 0).findFirst().orElse(null);
    Contacts before = (Contacts) targetGroup.getContacts();
    logger.info("Группа для удаления контакта: " + targetGroup);
    app.contact().setGroupFilter(targetGroup.getId());
    logger.info("Группа для удаления контакта: " + targetGroup);
    ContactData deletingContact = targetGroup.getContacts().stream().findFirst().get();
    app.contact().selectContactById(deletingContact.getId());
    app.contact().submitContactDeletingFromGroup(targetGroup.getName());
    app.contact().toGroupPageAfterRemovingContact(targetGroup.getName());
    // получить список контактов в группе через БД после удаления контакта
    Contacts after = (Contacts) app.db().groups().stream().filter(s -> s.getId()== targetGroup.getId()).map(GroupData::getContacts).findFirst().get();
    assertThat(after, equalTo(before.without(deletingContact)));

  }
}
