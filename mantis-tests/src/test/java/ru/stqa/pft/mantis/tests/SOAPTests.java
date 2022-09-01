package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SOAPTests extends TestBase {


  private Set<Project> projects;

  @BeforeMethod
  public void init() throws RemoteException, ServiceException, MalformedURLException {
    projects = app.soap().getProjects();
  }

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    String currentMethodName = new Object() {}
            .getClass()
            .getEnclosingMethod()
            .getName();
    BigInteger issueWithSummary = app.soap().getIdFromSummary(currentMethodName);
    skipIfNotFixed(issueWithSummary);


    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    String currentMethodName = new Object() {}
            .getClass()
            .getEnclosingMethod()
            .getName();
    BigInteger issueWithSummary = app.soap().getIdFromSummary(currentMethodName);
    skipIfNotFixed(issueWithSummary);

    Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description")
            .withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    Assert.assertEquals(issue.getSummary(), created.getSummary());
  }

  @Test
  public void issueCreatingTest() throws RemoteException, ServiceException, MalformedURLException {
    String currentMethodName = new Object() {}
            .getClass()
            .getEnclosingMethod()
            .getName();
    BigInteger issueWithSummary = app.soap().getIdFromSummary(currentMethodName);
    skipIfNotFixed(issueWithSummary);

    Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description")
            .withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    Assert.assertEquals(issue.getSummary(), created.getSummary());
    System.out.println("Тест " + currentMethodName+ " выполнен");
  }

  //Статус дефекта проверяется по наличию в наименовании названия выполняемого теста
  public void checkNotClosedIssues(String testName) throws RemoteException, ServiceException, MalformedURLException {
    BigInteger issueWithSummary = app.soap().getIdFromSummary(testName);
    if (Integer.parseInt(String.valueOf(issueWithSummary)) != 0) {
      if (app.soap().checkIssueClosed(issueWithSummary)) {
        throw new SkipException("Ignored because of issue " + issueWithSummary);
      }
    }
  }


}
