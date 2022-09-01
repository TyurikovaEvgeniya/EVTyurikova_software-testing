package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.pft.addressbook.tests.GroupCreationTests;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager extends TestBase {
  public Properties properties;
  WebDriver wd;

  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private ContactHelper contactHelper;
  private final String browser;
  private DBHelper dbHelper;


  public ApplicationManager(String browser){
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    dbHelper = new DBHelper();

    if (browser.equals(Browser.FIREFOX.browserName()))
    { wd = new FirefoxDriver();
    } else if (browser.equals(Browser.CHROME.browserName())) {
      wd = new ChromeDriver();
    } else if (browser.equals(Browser.IE.browserName())) {
      wd = new InternetExplorerDriver();
    }

    String target = System.getProperty("target","local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    wd.get(properties.getProperty("web.baseUrl"));


    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    contactHelper = new ContactHelper(wd);
    sessionHelper.login(properties.getProperty("web.admin.login"), properties.getProperty("web.admin.password"));
  }

  public void stop() {
    wd.quit();
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public String getTestDataDir() {
    return this.properties.getProperty("web.TestDataDir","src/test/resources/");
  }

  public String getPhotoPath() {
    return this.properties.getProperty("web.PhotoPath","src/test/resources/OW.PNG");
  }

  public DBHelper db() {
    return dbHelper;
  }
}
