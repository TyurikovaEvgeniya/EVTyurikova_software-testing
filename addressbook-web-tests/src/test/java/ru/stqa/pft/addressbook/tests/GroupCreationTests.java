package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.random;


public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    app.goTo().groupPage();
    List<GroupData> before = app.group().list();
    GroupData group = new GroupData(String.format("Нечто", (int) (random()*1000)), "test2", "test3");
    app.group().create(group);
    app.goTo().groupPage();
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size() + 1);


    if (after.stream().max(Comparator.comparingInt(GroupData::getId)).isPresent()) {
      group.setId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
    }
    before.add(group);
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);
  }

}
