package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

import static java.lang.Math.random;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName(String.format("test%s", (int) (random()*1000))).withHeader( "test2").withFooter("test3"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {

    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();

    GroupData deletingGroup = before.iterator().next();
    app.group().delete(deletingGroup);

    app.goTo().groupPage();

    Set<GroupData> after = app.group().all();


    before.remove(deletingGroup);
    Assert.assertEquals(before, after);

  }


}
