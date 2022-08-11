package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.random;

public class GroupDeletionTests extends TestBase {

  public static final int LIST_POSITION_TO_DELETE = 1;

  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGroupPage();

    if (!app.getGroupHelper().isThereGroup()) {
      app.getGroupHelper().createGroup(new GroupData(String.format("test%s", (int) (random()*1000)), "test2", "test3"));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList();
    int deletingGroupPosition = Math.abs(LIST_POSITION_TO_DELETE - before.size());
    app.getGroupHelper().selectGroup(deletingGroupPosition);
    app.getGroupHelper().deleteSelectedGroups();
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();


    before.remove(deletingGroupPosition);
    Assert.assertEquals(after.size(), before.size());
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }

}
