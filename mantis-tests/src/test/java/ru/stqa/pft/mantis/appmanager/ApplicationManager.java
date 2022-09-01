package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Browser;
import ru.stqa.pft.mantis.tests.TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager extends TestBase {
  private Properties properties;
  WebDriver wd;

  private final String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private DBHelper dbHelper;
  private LoginHelper loginHelper;
  private NavigationHelper navigationHelper;
  private SoapHelper soapHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    dbHelper = this.db();
    wd = getDriver();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

  }

  public DBHelper db() {
    dbHelper = new DBHelper();
    return dbHelper;
  }

  public void stop() {
    if (wd != null) {
      wd.quit();
    }
  }

  public void closeBrSession() {
    if (wd != null) {
      wd.close();
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }



  public WebDriver getDriver() {
    if (wd == null) {
      if (browser.equals(Browser.FIREFOX.browserName())) {
        wd = new FirefoxDriver();
      } else if (browser.equals(Browser.CHROME.browserName())) {
        wd = new ChromeDriver();
      } else if (browser.equals(Browser.IE.browserName())) {
        wd = new InternetExplorerDriver();
      }
    }
    return wd;
  }

  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }

  public JamesHelper james() {
    if (jamesHelper == null) {
      jamesHelper = new JamesHelper(this);
    }
    return jamesHelper;
  }


  public LoginHelper login() {
    if (loginHelper == null) {
      loginHelper = new LoginHelper(getDriver(), app);
    }
    return loginHelper;
  }

  public NavigationHelper goTo() {
    if (navigationHelper == null) {
      navigationHelper = new NavigationHelper(getDriver(), app);
    }
    return navigationHelper;
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(getDriver(), app);
    }
    return registrationHelper;
  }

  public SoapHelper soap(){
    if (soapHelper == null) {
      soapHelper = new SoapHelper(this);
    }
    return soapHelper;
  }

}


