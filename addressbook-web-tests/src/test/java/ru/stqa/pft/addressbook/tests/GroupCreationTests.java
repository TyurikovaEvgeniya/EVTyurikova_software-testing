package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
//    File file = new File(".");
//    System.out.println(file.getAbsolutePath());

    BufferedReader reader = new BufferedReader( (new FileReader(new File("src/test/resources/groups.xml"))));
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

  @Test(dataProvider = "validGroups")
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

  @Test
  public void testBadGroupCreation() throws Exception {

    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName(app.group().randomGroupName() + "'");
    app.group().create(group);
    app.goTo().groupPage();
    assertEquals(app.group().count(), before.size());
    Groups after = app.group().all();
    assertThat(after,
            equalTo(before));

  }

}
