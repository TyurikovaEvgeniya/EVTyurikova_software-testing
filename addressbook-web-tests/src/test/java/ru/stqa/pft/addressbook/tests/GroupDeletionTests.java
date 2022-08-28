package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.generators.GroupDataGenerator;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName(GroupDataGenerator.randomValidGroupName()).withHeader("ensurePreconditions").withFooter("ensurePreconditions"));
    }
    app.goTo().groupPage();
  }

  @Test(enabled = true)
  public void testGroupDeletion() throws Exception {
      Groups before = app.db().groups();

      GroupData deletedGroup = before.iterator().next();
      app.group().delete(deletedGroup);

      app.goTo().groupPage();
      assertEquals(app.group().count(), before.size() - 1);
      Groups after = app.db().groups();


      assertThat(after,
              equalTo(before.without(deletedGroup)));
  }

}
