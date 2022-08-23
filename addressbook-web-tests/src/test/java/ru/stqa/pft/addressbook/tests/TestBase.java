package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.generators.ContactDataGenerator;
import ru.stqa.pft.addressbook.generators.GroupDataGenerator;

public class TestBase {

  protected static final ApplicationManager app = new ApplicationManager(Browser.CHROME);
  protected static final GroupDataGenerator groupGen = new GroupDataGenerator();
  protected static final ContactDataGenerator contactGen = new ContactDataGenerator();

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

}
