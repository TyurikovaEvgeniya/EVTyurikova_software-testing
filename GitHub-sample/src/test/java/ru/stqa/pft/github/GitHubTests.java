package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

public class GitHubTests {


  @Test
  public void testCommits() {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("TyurikovaEvgeniya", "EVTyurikova_software-testing")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(commit);
    }
  }
}

