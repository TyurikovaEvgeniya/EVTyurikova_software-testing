package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class GroupCreationTests extends TestBase {

  public static final String TEST_DATA_FILE_DIR = "src/test/resources/";

  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    BufferedReader reader = new BufferedReader( (new FileReader(new File(TEST_DATA_FILE_DIR + "groups.xml"))));
    String xml = "";
    String line = reader.readLine();
    while (line != null){
      xml += (line);
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader( (new FileReader(new File(TEST_DATA_FILE_DIR + "GroupsValid.json"))));
    String json = "";
    String line = reader.readLine();
    while (line != null){
      json += (line);
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType()); //List<GroupData>.class
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> invalidGroupsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader( (new FileReader(new File(TEST_DATA_FILE_DIR + "GroupsInvalid.json"))));
    String json = "";
    String line = reader.readLine();
    while (line != null){
      json += (line);
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType()); //List<GroupData>.class
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "validGroupsFromJson", enabled = false)
  public void testGroupCreation(GroupData group) throws Exception {

    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    app.goTo().groupPage();
    assertEquals(app.group().count(), before.size() + 1);
    Groups after = app.group().all();
    assertThat(after,
            equalTo(before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));

  }

  @Test(dataProvider = "invalidGroupsFromJson", enabled = false)
  public void testBadGroupCreation(GroupData group) throws Exception {

    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    app.goTo().groupPage();
    assertEquals(app.group().count(), before.size());
    Groups after = app.group().all();
    assertThat(after,
            equalTo(before));

  }

}
