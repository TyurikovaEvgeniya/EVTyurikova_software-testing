package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

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

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

  private String mergePhones(ContactData contact) {
    return Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .filter((s) -> !s.equals(""))
            .map(ContactPhoneEmailsAddressTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeEmails(ContactData contact) {
    return Stream.of(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }
}
