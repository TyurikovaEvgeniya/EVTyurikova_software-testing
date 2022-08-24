package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.generators.ContactDataGenerator.ACCOUNT_IMG;

public class ContactPhoneEmailsAddressTests extends TestBase {


  @BeforeMethod
  public void ensurePreconditions() {
    ContactData contact;
    if (app.contact().all().size() == 0) {
      contact = new ContactData().withFirstName("Проверка").withMiddleName("Предуслововна").withLastName("Тюрикова")
              .withMobilePhone(ContactDataGenerator.randomPhone()).withHomePhone(ContactDataGenerator.randomPhone())
              .withWorkPhone(ContactDataGenerator.randomPhone()).withEmail("ensurePreconditions.testContactPhone1@gmail.com").withEmail2("ensurePreconditions.testContactPhone@gmail.com").withEmail3("ensurePreconditions.testContactPhone@gmail.com")
              .withBday("15").withBmonth("May").withByear("1988").withGroup(null).withPhoto(ACCOUNT_IMG);
      app.contact().mergeEmails(contact);
      app.contact().mergePhones(contact);
      app.contact().addNewContact(contact);
      app.goTo().homePage();
    }
  }

  @Test(enabled = true)
  public void testContactData() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    app.contact().mergePhones(contactInfoFromEditForm);
    app.contact().mergeEmails(contactInfoFromEditForm);
    assertThat(contact.getAllPhones(), equalTo(contactInfoFromEditForm.getAllPhones()));
    assertThat(contact.getAllEmails(), equalTo(contactInfoFromEditForm.getAllEmails()));
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

}
