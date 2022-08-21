package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneEmailsAddressTests extends TestBase {

  @Test
  public void testContactPhone() {
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
