package ru.stqa.pft.addressbook.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.generators.GroupDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeleteFromGroup extends TestBase {

  Logger logger = LoggerFactory.getLogger(ContactDeleteFromGroup.class);
  private Contacts contacts;
  private Groups groups;
  private ContactData contactCandidateForDeleting;
  private GroupData targetGroup;

  @BeforeMethod
  public void ensurePreconditions() {
    contacts = app.db().contacts();
    groups = app.db().groups();
    ContactData contact;

    if (contacts.size() == 0) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Тюрикова")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePreconditions.delete@gmail.com")
              .withBday("15").withBmonth("May").withByear("1988").withPhoto(app.getPhotoPath());

      app.contact().addNewContact(contact);
      contacts = app.db().contacts();
      contactCandidateForDeleting = contacts.iterator().next();
      app.goTo().homePage();
    }
    if (groups.size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(GroupDataGenerator.randomValidGroupName()).
              withHeader("ensurePreconditions").withFooter("ensurePreconditions"));
      groups = app.db().groups();
      targetGroup = groups.iterator().next();
      app.goTo().groupPage();
    }


    //Если не создавались ни группа, ни контакт, выбираем контакт, у которого есть какая-либо из групп
    if (targetGroup == null && contactCandidateForDeleting != null) {
      targetGroup = groups.iterator().next();
    } else if (targetGroup != null && contactCandidateForDeleting == null) {
      contactCandidateForDeleting = contacts.iterator().next();
      app.goTo().home();
      app.contact().addContactToGroup(contactCandidateForDeleting.getId(), targetGroup);
      app.goTo().homePage();
    } else if (targetGroup == null) {
      contacts = app.db().contacts();
      groups = app.db().groups();

      for (ContactData cont : contacts) {
        if (cont.getGroups().equals(groups)) {
          logger.info("Контакт есть во всех существующих группах: " + cont.getId());
          targetGroup = groups.iterator().next();
          contactCandidateForDeleting = cont;
        } else {
          targetGroup = groups.stream().filter(s -> cont.getGroups().contains(s)).findFirst().orElse(null);
          if (targetGroup != null) break;
        }
      }

      //Если группу так и не получилось задать, то ни один контакт не добавлен в группу
      //Создаём новый контакт
      //Добавляем в любую группу
      if (targetGroup == null) {
        groups = app.db().groups();
        contactCandidateForDeleting =contacts.iterator().next();
        targetGroup = groups.iterator().next();

        GroupData finalTargetGroup = targetGroup;
        //Добавляем случайный контакт в только что созданную группу
        Contacts before = (Contacts) groups.stream().filter(s -> s.getId() == finalTargetGroup.getId())
                .map(GroupData::getContacts).findFirst().orElse(null);

        app.goTo().home();
        app.contact().addContactToGroup(contactCandidateForDeleting.getId(), targetGroup);
        app.goTo().homePage();
        groups = app.db().groups();
        contacts = app.db().contacts();
        //БД. Поиск контактов группу после добавления в неё контакта
        Contacts after = (Contacts) groups.stream().filter(s -> s.getId() == finalTargetGroup.getId())
                .map(GroupData::getContacts).findFirst().orElse(null);

        assert after != null;
        assert before != null;
        assertEquals(after.size(), before.size() + 1);
        assertThat(after,
                equalTo(before.withAdded(contactCandidateForDeleting.toDBTypes()
                        .withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
      }
      else if (contactCandidateForDeleting == null) {
        contactCandidateForDeleting = contacts.iterator().next();
      }
    }


    logger.info("Группа для удаления контакта: " + targetGroup);
    logger.info("Контакт для удаления из группы: " + contactCandidateForDeleting);
  }


  @Test
  public void ContactDeleteFromGroup() {


    Contacts before = (Contacts) app.db().groups().stream().filter(s -> s.getId() == targetGroup.getId())
            .map(GroupData::getContacts).findFirst().orElse(null);
    logger.info("Количество контактов в группе до удаления: " + before.size());

    app.goTo().home();
    app.contact().setGroupFilter(targetGroup.getId());


//  contactCandidateForDeleting = targetGroup.getContacts().stream().findFirst().get();
    logger.info("Контакт для удаления из группы: " + contactCandidateForDeleting);
    app.contact().selectContactById(contactCandidateForDeleting.getId());
    app.contact().submitContactDeletingFromGroup(targetGroup.getName());
    app.contact().toGroupPageAfterRemovingContact(targetGroup.getName());

    // получить список контактов в группе через БД после удаления контакта
    Contacts after = (Contacts) app.db().groups().stream().filter(s -> s.getId() == targetGroup.getId())
            .map(GroupData::getContacts).findFirst().orElse(null);
    logger.info("Количество контактов в группе после удаления: " + after);

    assert before != null;
    assert after != null;
    assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.without(contactCandidateForDeleting)));

  }
}
