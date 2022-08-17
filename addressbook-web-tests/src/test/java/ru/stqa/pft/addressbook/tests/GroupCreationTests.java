package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName(app.group().randomGroupName()).withHeader("test2").withFooter("test3");
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
