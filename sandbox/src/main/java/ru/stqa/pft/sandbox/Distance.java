package ru.stqa.pft.sandbox;

public class Distance {

  public static void main(String[] args) {

    Point p1 = new Point(-1, 7);
    Point p2 = new Point(7, 1);

    System.out.println("Расстояние от точки (" + p1.x +";"+ p1.y + ") до точки (" + p2.x +";"+ p2.y+ ") на плоскости = "
            + p1.distanceToPoint(p2));

  }
}
