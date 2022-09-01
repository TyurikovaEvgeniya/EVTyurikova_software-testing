package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SoapHelper{

  private final ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, RemoteException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"));
    return Arrays.asList(projects).stream().map((p) -> new Project().withId(p.getId()).withName(p.getName())).collect(Collectors.toSet());
  }

  public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("soap.wsdl")));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), issue.getProject().getId());
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(issue.getProject().getId(), issue.getProject().getName()));

    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), issueData);
    IssueData createdIssueData = mc.mc_issue_get(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), issueId);

    return new Issue().withId(createdIssueData.getId())
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId())
                    .withName(createdIssueData.getProject().getName()));
  }


  public BigInteger getIdFromSummary(String summary) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    return mc.mc_issue_get_id_from_summary(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), summary);
  }

  public Set<String> getIssueStatusById(BigInteger issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), issueId);

    ObjectRef status = issueData.getStatus();
    return Stream.of(status).map(ObjectRef::getName).collect(Collectors.toSet());
  }

  public BigInteger getIssueWithMaxId(Project project) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    return mc.mc_issue_get_biggest_id(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), project.getId());
  }

  public boolean checkIssueClosed(BigInteger issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();

    IssueData issue = null;
    if (mc.mc_issue_exists(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), issueId)) {
      issue = mc.mc_issue_get(app.getProperty("web.admin.login"), app.getProperty("web.admin.password"), issueId);
    } else {
      return false;
    }

    return Arrays.asList(issue.getStatus()).stream().map(ObjectRef::getName).collect(Collectors.toSet()).contains("closed");

  }
}
