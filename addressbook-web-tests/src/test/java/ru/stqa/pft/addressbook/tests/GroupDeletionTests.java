package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static java.lang.Math.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName(String.format("test%s", (int) (random()*1000))).withHeader( "test2").withFooter("test3"));
    }
    app.goTo().groupPage();
  }

  @Test
  public void testGroupDeletion() throws Exception {

    Groups before = app.group().all();

    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);

    app.goTo().groupPage();

    Groups after = app.group().all();

    before.remove(deletedGroup);
    assertThat(after, CoreMatchers.equalTo(before.without(deletedGroup)));
    assertEquals(before, after);

  }


}
