package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
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
  public Properties properties;
  WebDriver wd;

  private final String browser;


  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {

    if (browser.equals(Browser.FIREFOX.browserName())) {
      wd = new FirefoxDriver();
    } else if (browser.equals(Browser.CHROME.browserName())) {
      wd = new ChromeDriver();
    } else if (browser.equals(Browser.IE.browserName())) {
      wd = new InternetExplorerDriver();
    }

    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    wd.get(properties.getProperty("web.baseUrl"));


  }

  public void stop() {
    wd.findElement(By.linkText("Logout")).click();
    wd.get(properties.getProperty("web.baseUrl"));
    wd.quit();
  }
}

