package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {

  private final String API_ISSUES_ISSUE_JSON = "https://bugify.stqa.ru/api/issues/%s.json";

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    Set<Issue> issues = getIssueById(issueId);
    if (issues.iterator().next().getState_name().equals("Closed")) {
      return true;
    } else {
      return false;
    }
  }

  private Set<Issue> getIssueById(int issueId) throws IOException {
    String json = RestAssured.get(String.format(API_ISSUES_ISSUE_JSON, issueId)).asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues ,new TypeToken<Set<Issue>>() {}.getType());
  }

}
