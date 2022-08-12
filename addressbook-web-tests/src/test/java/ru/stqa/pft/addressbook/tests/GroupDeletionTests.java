package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.random;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName(String.format("test%s", (int) (random()*1000))).withHeader( "test2").withFooter("test3"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {

    app.goTo().groupPage();
    List<GroupData> before = app.group().list();

    int deletingGroupPosition = Math.abs(1 - before.size());

    app.group().delete(deletingGroupPosition);
    app.goTo().groupPage();

    List<GroupData> after = app.group().list();


    before.remove(deletingGroupPosition);
    Assert.assertEquals(after.size(), before.size());
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }


}
