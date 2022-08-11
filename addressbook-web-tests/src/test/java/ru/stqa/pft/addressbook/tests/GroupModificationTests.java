package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.random;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData(String.format("test%s", (int) (random()*1000)), "test2", "test3"));
    }
  }

  @Test
  public void testGroupModification() {

    List<GroupData> before = app.group().list();
    int positionToModify = 1 - before.size();
    GroupData group = new GroupData(before.get(Math.abs(positionToModify)).getId(), "test1", null, null);

    app.group().modify(positionToModify, group);
    app.goTo().groupPage();

    List<GroupData> after = app.group().list();

    before.remove(Math.abs(positionToModify));
    before.add(group);
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }

}
