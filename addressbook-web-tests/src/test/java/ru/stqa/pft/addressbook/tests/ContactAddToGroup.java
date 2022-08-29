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

public class ContactAddToGroup extends TestBase {

  private ContactData contactCandidateForAdding;
  private GroupData targetGroup = null;
  private Contacts contacts;
  private Groups groups;
  Logger logger = LoggerFactory.getLogger(ContactAddToGroup.class);

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
      contactCandidateForAdding = contacts.iterator().next();
    }
    if (groups.size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName(GroupDataGenerator.randomValidGroupName()).
              withHeader("ensurePreconditions").withFooter("ensurePreconditions"));
      groups = app.db().groups();
      targetGroup = groups.iterator().next();
    }

    app.goTo().home();


    if (targetGroup == null && contactCandidateForAdding != null) {
      targetGroup = groups.iterator().next();
    } else if (targetGroup != null && contactCandidateForAdding == null) {
      contactCandidateForAdding = contacts.iterator().next();
      //выбираем контакт, у которого нет какой-либо из групп
    } else if (targetGroup == null) {
      for (ContactData cont : contacts) {
        System.out.println(cont);
        if (cont.getGroups().equals(groups)) {
          logger.info("Контакт есть во всех существующих группах");
        } else {
          //Целевая группа = первая группа, которую не содержит контакт из итератора цикла, либо null
          targetGroup = groups.stream().filter(s -> !cont.getGroups().contains(s)).findFirst().orElse(null);
          contactCandidateForAdding = cont;
          logger.info("Контакт есть не во всех существующих группах");
          logger.info("Его нет в группе: " + targetGroup);
          break; //Так не смогла разобрать с while с iterator().hasNext(); Уходит в бесконечный цикл
        }
      }
    }

    //Если не нашлось группы, в которой не было бы хотя бы одного существующего контакта, то создаём новый контакт
    if (targetGroup == null) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Групповна")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withEmail("ensurePrecoditions.update@ligastavok.ru")
              .withBday("15").withBmonth("May").withByear("1988").withPhoto(app.getPhotoPath());

      app.contact().addNewContact(contact);
      contacts = app.db().contacts();
      //Так как нет ограничений на уникальность полей в БД, кроме id записи в каждой таблице,
      //то знать достоверно точно ли последний контакт заведён в текущем сеансе нельзя, поэтому maxContactId()
      contactCandidateForAdding = app.db().getContactById(app.db().maxContactId());
      //Целевая группа выбирается случайным образом
      targetGroup = groups.iterator().next();

    } else {
      contactCandidateForAdding = contacts.iterator().next();
      logger.info("Контакт для добавления: " + contactCandidateForAdding);
      logger.info("группа для пополнения: " + targetGroup);
    }
  }


  @Test
  public void ContactAddToGroup() {


    //БД. Получаем текущий список контактов в группе
    Contacts before = (Contacts) targetGroup.getContacts();

    //Добавляем контакт в ранее выбранную группу
    app.goTo().home();
    app.contact().addContactToGroup(contactCandidateForAdding.getId(), targetGroup);

    //БД. Поиск контактов группу после добавления в неё контакта
    GroupData finalTargetGroup = targetGroup;
    Contacts after = (Contacts) app.db().groups().stream().filter(s -> s.getId() == finalTargetGroup.getId())
            .map(GroupData::getContacts).findFirst().orElse(null);


    assertEquals(after.size(), before.size() + 1);
    assertThat(after,
            equalTo(before.withAdded(contactCandidateForAdding.toDBTypes().withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }
}


