package ru.stqa.pft.sandbox;

import static java.lang.Math.*;

public class Point {

  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distanceToPoint(Point TargetPoint) {
    return sqrt(pow((TargetPoint.x - this.x),2) + pow((TargetPoint.y - this.y),2));
  }
}
