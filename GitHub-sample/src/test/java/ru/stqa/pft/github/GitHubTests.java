package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests {


  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("TyurikovaEvgeniya", "EVTyurikova_software-testing")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}

