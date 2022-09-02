package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

  @Test
  public void testDistanceBetweenTwoPoints(){
    Point p1 = new Point(-1, 10);
    Point p2 = new Point(5, 9);
    Assert.assertEquals(p1.distanceToPoint(p2), 6);
  }

  @Test
  public void testDistanceBetweenTwoPoints2(){
    Point p1 = new Point(-1, 9);
    Point p2 = new Point(-1, 2);
    Assert.assertEquals(p1.distanceToPoint(p2), 7);
  }
}
