package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.Set;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName(app.group().randomGroupName()));
    }
  }


  @Test
  public void testGroupModification() {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData modifyingGroup = before.iterator().next();
    GroupData group = new GroupData().withId(modifyingGroup.getId()).withName(app.group().randomGroupName());

    app.group().modify(group);
    app.goTo().groupPage();

    Set<GroupData> after = app.group().all();

    before.remove(modifyingGroup);
    before.add(group);
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);

    Assert.assertEquals(before, after);

  }

}
