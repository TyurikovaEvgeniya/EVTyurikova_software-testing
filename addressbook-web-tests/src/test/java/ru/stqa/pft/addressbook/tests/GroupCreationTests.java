package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test66", "test2", "test3");
    app.getGroupHelper().createGroup(group);
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);


    if (after.stream().max(Comparator.comparingInt(GroupData::getId)).isPresent()) {
      group.setId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
    }
    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

}
