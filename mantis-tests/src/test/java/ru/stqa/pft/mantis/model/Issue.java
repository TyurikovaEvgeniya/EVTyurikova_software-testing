package ru.stqa.pft.mantis.model;

import java.math.BigInteger;

public class Issue {

  private BigInteger id;
  private String summary;
  private String tags;
  private String description;

  public String getLabel() {
    return tags;
  }

  public Issue withLabel(String label) {
    this.tags = label;
    return this;
  }

  private Project project;

  public BigInteger getId() {
    return id;
  }

  public Issue withId(BigInteger id) {
    this.id = id;
    return this;
  }

  public String getSummary() {
    return summary;
  }

  public Issue withSummary(String summary) {
    this.summary = summary;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  public Project getProject() {
    return project;
  }

  public Issue withProject(Project project) {
    this.project = project;
    return this;
  }
}
