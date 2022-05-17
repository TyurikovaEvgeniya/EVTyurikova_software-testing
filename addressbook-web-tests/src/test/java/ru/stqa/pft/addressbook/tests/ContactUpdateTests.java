package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactUpdateTests extends TestBase {

  @Test
  public void testContactUpdateDetails() throws Exception {
    app.getNavigationHelper().gotoContactDetails(1);
    app.getNavigationHelper().gotoModifingContactOnDetailsPage();
    app.getContactHelper().modifyContactPhone("+79169928153");
    app.getContactHelper().submitContactUpdate();
  }

  @Test
  public void testContactModificationEdit() throws Exception {
    app.getNavigationHelper().gotoContactEdit(2);
    app.getContactHelper().modifyContactPhone("+79169928152");
    app.getContactHelper().submitContactUpdate();
  }
}
