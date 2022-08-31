package ru.stqa.pft.mantis.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

//  @BeforeMethod
//  public void startMailServer(){
//    app.mail().start();
//  }

  Logger logger = LoggerFactory.getLogger(RegistrationTests.class);

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user1%s", now);
    String email = String.format("user1%s@localhost", now);
    String password = "password";

    String realname = "Евгения";
    logger.info("Данные нового клиента:" + user + " " + email);

    app.james().createUser(user, password);

    app.registration().start(user, email);
//    List<MailMessage> mailMessages = app.mail().waitForMAil(2, 10000);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 100000);
    String confirmationLink = findConfirmationLink(mailMessages, email);


    app.registration().finish(confirmationLink, password, realname);
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter(s -> s.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

//  @AfterMethod(alwaysRun = true)
//  public void stopMailServer() {
//    app.mail().stop();
//  }

  @AfterMethod(alwaysRun = true)
  public void stopBrowser(){
    app.stop();

  }
}