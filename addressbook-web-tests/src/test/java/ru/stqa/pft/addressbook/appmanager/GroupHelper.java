package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;

public class GroupHelper extends HelperBase {
  private WebDriverWait wait;


  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }


  public void selectGroupById(int id) {
//    wait  = new WebDriverWait(wd, ofSeconds(10));
//    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("input[value='" + id + "']")));
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    groupCache = null;
  }
  private Groups groupCache = null;

  public Groups all() {
    if (groupCache != null){
      return new Groups(groupCache);
    }

    groupCache = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span,group"));
    for (WebElement element : elements) {
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      groupCache.add(new GroupData().withId(id).withName(name));
    }
    return new Groups(groupCache);
  }

  public void modify(GroupData modifiedGroup, GroupData modificationGroupData) {
    selectGroupById(modifiedGroup.getId());
    initGroupModification();
    fillGroupForm( modificationGroupData);
    submitGroupModification();
    groupCache = null;
  }



  public void delete(GroupData deletingGroup) {
    selectGroupById(deletingGroup.getId());
    deleteSelectedGroups();
    groupCache = null;
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }
}
