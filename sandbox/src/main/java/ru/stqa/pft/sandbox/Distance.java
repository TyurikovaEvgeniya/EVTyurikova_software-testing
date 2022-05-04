package ru.stqa.pft.sandbox;

public class Distance {

  public static void main(String[] args) {

    Point p1 = new Point(-1, 7);
    Point p2 = new Point(7, 1);

    System.out.println("Расстояние между двумя точками на плоскости (" + p1.x +";"+ p1.y + ") и (" + p2.x +";"+ p2.y+ ") = " + Point.distance(p1, p2));

  }
}
