package ru.stqa.pft.rest;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class IsIssueClosed extends TestBase {



  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490","");
  }

  @Test
  public void IssueIsClosed() throws IOException {
    skipIfNotFixed(2238);
  }


  @Test
  public void IssueIsNotClosed() throws IOException {
    skipIfNotFixed(2237);

  }
}
