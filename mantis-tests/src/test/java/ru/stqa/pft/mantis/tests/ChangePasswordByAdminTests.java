package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordByAdminTests extends TestBase {

  Logger logger = LoggerFactory.getLogger(ChangePasswordByAdminTests.class);
  private UserData user;

  @Test
  public void resetPasswordByAdminTest() throws IOException, MessagingException, SQLException {
    user = app.db().getUser();

    System.out.println("Данные нового клиента:" + user.getUsername() + " " + user.getEmail());

    app.login().loginInMantis();
    app.login().as(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"));
    app.goTo().management();
    app.goTo().userManagement();
    app.goTo().drillDownToUserProfile(user.getUsername());
    app.goTo().resetPassword();

    String mailPassword = app.getProperty("user.password");

    System.out.println("Данные нового клиента:" + user.getUsername() + " " + user.getEmail());

    List<MailMessage> mailMessages = app.james().waitForMail(user.getUsername(), mailPassword, 100000);
    String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());

    app.goTo().confirm(confirmationLink);
    app.registration().changePassword(confirmationLink, mailPassword);


    assertTrue(app.newSession().login(user.getUsername(), mailPassword));


  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(s -> s.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopBrowser(){
    app.stop();
  }

}
