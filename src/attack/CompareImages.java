package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by charm on 5/21/14.
 */
public class CompareImages implements Runnable {
    private static final float D = (float) Math.sqrt(3);
    private static final float xx = 255.0f;
    private final float preciseCompare;
    private BufferedImage image1;
    private BufferedImage image2;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private ArrayList<Recognition.Struct> points;
    private float precise;
    private ArrayList<Point> answer;
    private float denominator;
    private int digit;
    private long index;

    CompareImages(BufferedImage image1, BufferedImage image2, int x1, int y1, int x2, int y2,
                  ArrayList<Recognition.Struct> points) {
        this(image1, image2, x1, y1, x2, y2, points, 0.06f, -1);
    }

    CompareImages(BufferedImage image1, BufferedImage image2, int x1, int y1, int x2, int y2,
                  ArrayList<Recognition.Struct> points, float precise, long index) {
        this(image1, image2, x1, y1, x2, y2, points, precise, -1, index);
    }

    CompareImages(BufferedImage image1, BufferedImage image2, int x1, int y1, int x2, int y2,
                  ArrayList<Recognition.Struct> points, float precise, int digit, long index) {
        this.image1 = image1;
        this.image2 = image2;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.points = points;
        this.precise = precise;
        answer = new ArrayList<>();
        denominator = D * (image2.getHeight() * image2.getWidth());
        preciseCompare = precise * denominator;
        this.digit = digit;
        this.index = index;
    }

    public CompareImages compare() {
        int w2 = image2.getWidth();
        int h2 = image2.getHeight();
        int bestX = 0;
        int bestY = 0;
        float lowestDiff = 10050000;
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2 - h2; y++) {
                float comp = compareImages(image1.getSubimage(x, y, w2, h2), image2);
                if (comp < lowestDiff) {
                    boolean flag = false;
                    for (Recognition.Struct point1 : points) {
                        Point point = point1.point;
                        if ((x - point.x == 1) && digit == 1 && point1.digit == 5) {
                            break;
                        }
                        if (point.x == x || Math.abs(point.x - x) < 2) {
                            flag = true;
                            break;
                        }
                        if ((x - point.x == 3 || x - point.x == 4 || x - point.x == 5) && digit == 1 && point1.digit == 4) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        continue;
                    bestX = x;
                    bestY = y;
                    lowestDiff = comp;
                }
            }
        }
        answer.add(new Point(bestX, bestY));
        if (lowestDiff < precise) {
            answer.add(new Point(10000, 10000));
        }
        System.out.println("index=" + index);
        return this;
    }

    private float compareImages(BufferedImage im1, BufferedImage im2) {
        float variation = 0.0f;
        for (int x = 0; x < im1.getWidth(); x++) {
            for (int y = 0; y < im1.getHeight(); y++) {
                int rgb1 = im1.getRGB(x, y);
                int rgb2 = im2.getRGB(x, y);
                float r1 = ((rgb1 >> 16) & 0xFF);
                float r2 = ((rgb2 >> 16) & 0xFF);
                float g1 = ((rgb1 >> 8) & 0xFF);
                float g2 = ((rgb2 >> 8) & 0xFF);
                float b1 = (rgb1 & 0xFF);
                float b2 = (rgb2 & 0xFF);
                variation += Math.sqrt(((r1 - r2) / xx) * ((r1 - r2) / xx) +
                        ((g1 - g2) / xx) * ((g1 - g2) / xx) + ((b1 - b2) / xx) * ((b1 - b2) / xx));
                if (variation > preciseCompare) {
                    return 100500;
                }
            }
        }
        return variation / denominator;
    }


    public ArrayList<Point> getAnswer() {
        return answer;
    }

    @Override
    public void run() {
        compare();
    }
}