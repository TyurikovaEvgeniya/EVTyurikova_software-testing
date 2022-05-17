package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletionHome() throws Exception {
    app.getContactHelper().clickFirstContactInMainTable();
    app.getContactHelper().DeleteContactOnHomePage();
    app.getContactHelper().confirmContactsDeletion();
  }

  @Test
  public void testContactDeletionEdit() throws Exception {
    app.getNavigationHelper().gotoContactEdit(1);
    app.getContactHelper().DeleteContactOnEditPage();
  }
}
