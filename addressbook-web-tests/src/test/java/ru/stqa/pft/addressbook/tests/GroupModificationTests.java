package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.GroupDataGenerator;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroupsModificationValuesFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader( (new FileReader(new File(app.getTestDataDir() + app.properties.getProperty("gen.group.modification.valid") +".json"))))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += (line);
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType()); //List<GroupData>.class
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName(GroupDataGenerator.randomValidGroupName()).withHeader("ensurePreconditions").withFooter("ensurePreconditions"));
    }
    app.goTo().groupPage();
  }

  @DataProvider
  public Iterator<Object[]> invalidGroupsModificationValuesFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader( (new FileReader(new File(app.getTestDataDir() + app.properties.getProperty("gen.group.modification.invalid") +".json"))))) {
      StringBuilder json = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        json.append(line);
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json.toString(), new TypeToken<List<GroupData>>() {
      }.getType()); //List<GroupData>.class
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

  }


  @Test(dataProvider = "validGroupsModificationValuesFromJson")
  public void testGroupValidModification(GroupData modificationGroupData) {

    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();

    app.group().modify(modifiedGroup, modificationGroupData.withId(modifiedGroup.getId()));
    app.goTo().groupPage();
    assertEquals(app.group().count(), before.size());
    Groups after = app.group().all();

    assertThat(after,
            equalTo(before.without(modifiedGroup).withAdded(modificationGroupData)));
  }

  @Test(dataProvider = "invalidGroupsModificationValuesFromJson")
  public void testGroupInvalidModification(GroupData modificationGroupData) {

    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    app.group().modify(modifiedGroup, modificationGroupData.withId(modifiedGroup.getId()));
    app.goTo().groupPage();
    assertEquals(app.group().count(), before.size());
    Groups after = app.group().all();

    assertThat(after,
            equalTo(before));
  }

}
