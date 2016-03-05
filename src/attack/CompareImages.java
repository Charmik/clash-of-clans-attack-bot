package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by charm on 5/21/14.
 */
public class CompareImages implements Runnable {
    private static final float D = (float) Math.sqrt(3);
    private static final float xx = 255.0f * 255.0f;
    private final double preciseCompare;
    private final BufferedImage image1;
    private final BufferedImage image2;
    private final Point start;
    private final Point finish;
    private final ArrayList<Recognition.Struct> points;
    private final float precise;
    private final double denominator;
    private final int digit;
    private Point answer;

    CompareImages(BufferedImage image1, BufferedImage image2, Point start, Point finish,
                  ArrayList<Recognition.Struct> points) {
        this(image1, image2, start, finish, points, 0.06f, -1);
    }

    CompareImages(BufferedImage image1, BufferedImage image2, Point start, Point finish,
                  ArrayList<Recognition.Struct> points, float precise) {
        this(image1, image2, start, finish, points, precise, -1);
    }

    CompareImages(BufferedImage image1, BufferedImage image2, Point start, Point finish,
                  ArrayList<Recognition.Struct> points, float precise, int digit) {
        this.image1 = image1;
        this.image2 = image2;
        this.start = start;
        this.finish = finish;
        this.points = points;
        this.precise = precise;
        this.answer = null;
        denominator = D * (image2.getHeight() * image2.getWidth());
        preciseCompare = precise * denominator;
        this.digit = digit;
    }

    public CompareImages compare() {
        int w2 = image2.getWidth();
        int h2 = image2.getHeight();
        int bestX = 0;
        int bestY = 0;
        double lowestDiff = 10050000;
        for (int x = start.x; x < finish.x; x++) {
            for (int y = start.y; y < finish.y - h2; y++) {
                double comp = compareImages(image1.getSubimage(x, y, w2, h2), image2);
                if (comp < lowestDiff) {
                    boolean flag = false;
                    for (Recognition.Struct point1 : points) {
                        Point point = point1.point;
                        if (Math.abs(x - point.x) == 1 && digit == 1 && point1.digit == 5) {
                            break;
                        }
                        if (Math.abs(x - point.x) == 2 && digit == 1 && point1.digit == 1) {
                            flag = true;
                            break;
                        }
                        if (Math.abs(x - point.x) == 3 && digit == 7 && point1.digit == 2) {
                            flag = true;
                            break;
                        }
                        if (Math.abs(x - point.x) == 2 && digit == 7 && point1.digit == 7) {
                            flag = true;
                            break;
                        }
                        if (Math.abs(x - point.x) == 3 && digit == 7 && point1.digit == 5) {
                            flag = true;
                            break;
                        }
                        if (point.x == x || Math.abs(point.x - x) < 2) {
                            flag = true;
                            break;
                        }
                        if (digit == 1 && point1.digit == 4 && (x - point.x == 3 || x - point.x == 4 || x - point.x == 5)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        continue;
                    }
                    bestX = x;
                    bestY = y;
                    lowestDiff = comp;
                }
            }
        }
        //int kk = digit;
        answer = lowestDiff < precise ? new Point(bestX, bestY) : null;
        //System.out.println(lowestDiff + "    " + precise);
        return this;
    }

    private double compareImages(BufferedImage im1, BufferedImage im2) {
        double variation = 0.0f;
        for (int x = 0; x < im1.getWidth(); x++) {
            for (int y = 0; y < im1.getHeight(); y++) {
                int rgb1 = im1.getRGB(x, y);
                int rgb2 = im2.getRGB(x, y);
                //TODO: inline
                int r1 = ((rgb1 >> 16) & 0xFF);
                int r2 = ((rgb2 >> 16) & 0xFF);
                int g1 = ((rgb1 >> 8) & 0xFF);
                int g2 = ((rgb2 >> 8) & 0xFF);
                int b1 = (rgb1 & 0xFF);
                int b2 = (rgb2 & 0xFF);
                int q1 = r1 - r2;
                int q2 = g1 - g2;
                int q3 = b1 - b2;
                variation += Math.sqrt((q1 * q1 +
                        q2 * q2 + q3 * q3) / xx);
                if (variation > preciseCompare) {
                    return 10050000;
                }
            }
        }
        return variation / denominator;
    }


    public Point result() {
        return answer;
    }

    @Override
    public void run() {
        compare();
    }
}