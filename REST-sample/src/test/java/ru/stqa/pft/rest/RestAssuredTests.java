package ru.stqa.pft.rest;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;


public class RestAssuredTests {

  public static final String API_ISSUES_JSON = "https://bugify.stqa.ru/api/issues.json";

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490","");
  }

  @Test
  public void testIsClosed() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue();
    int issueId = createIssue(newIssue.withSubject("Ping! From IntelliJ").withDescription("Tyurikova inda house"));
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testIsNotClosed() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue();
    int issueId = createIssue(newIssue.withSubject("Ping! From IntelliJ").withDescription("Tyurikova inda house"));
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssues() throws IOException {
    String json = RestAssured.get(API_ISSUES_JSON).asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues ,new TypeToken<Set<Issue>>() {}.getType());
  }

  private int createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given().parameters("subject", newIssue.getSubject())
            .parameters("description", newIssue.getDescription())
            .post(API_ISSUES_JSON).asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }



}
