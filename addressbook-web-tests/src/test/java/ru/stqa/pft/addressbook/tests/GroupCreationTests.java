package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.Set;

import static java.lang.Math.random;


public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("Нечто").withHeader("test2").withFooter("test3");
    app.group().create(group);
    app.goTo().groupPage();
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() + 1);


//    if (after.stream().max(Comparator.comparingInt(GroupData::getId)).isPresent()) {
//      group.setId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
//    }
    if (after.stream().mapToInt(GroupData::getId).max().isPresent()) {
      group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt());
    }
    before.add(group);
    Comparator<? super GroupData> byId
            = Comparator.comparingInt(GroupData::getId);

    Assert.assertEquals(before, after);
  }

}
