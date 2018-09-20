package sample;

public class Point {

    double pointX, pointY;
    String frequency;
    double[] points_length;
    boolean checkFrequency;

    public Point(double pointX, double pointY, String frequency, double[] points_length, boolean checkFrequency) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.frequency = frequency;
        this.points_length = points_length;
        this.checkFrequency = checkFrequency;
    }
}