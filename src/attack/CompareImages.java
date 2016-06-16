package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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
    byte[] pixelByte2;

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

        //int[] pixels1 = ((DataBufferInt) image1.getRaster().getDataBuffer()).getData();
        pixelByte2 = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();

    }

    public CompareImages compare() {
        int w2 = image2.getWidth();
        int h2 = image2.getHeight();
        int bestX = 0;
        int bestY = 0;
        double lowestDiff = 10050000;
        for (int x = finish.x; x >= start.x; x--) {
            for (int y = finish.y; y >= start.y; y--) {
                double comp = compareImages(image1.getSubimage(x, y, w2, h2), image2);
                if (comp < lowestDiff) {
                    boolean flag = false;
                    for (Recognition.Struct point1 : points) {
                        Point point = point1.point;
                        /*if (Math.abs(x - point.x) == 1 && digit == 1 && point1.digit == 5) {
                            break;
                        }*/

                        if (Math.abs(x - point.x) == 2) {
                            if ((digit == 1 && point1.digit == 1) || (digit == 7 && point1.digit == 7)) {
                                flag = true;
                                break;
                            }
                        }
                        if (Math.abs(x - point.x) == 3) {
                            if ((digit == 7 && point1.digit == 2) || (digit == 7 && point1.digit == 5)) {
                                flag = true;
                                break;
                            }
                        }

                        if (point.x == x || Math.abs(point.x - x) < 2) {
                            flag = true;
                            break;
                        }
                        if (digit == 1 && point1.digit == 4 && (x - point.x >= 3 && x - point.x <= 5)) {
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
        for (int x = im1.getWidth() - 1; x >= 0; x--) {
            for (int y = im1.getHeight() - 1; y >= 0; y--) {
                int[] pixel1 = im1.getRaster().getPixel(x, y, new int[4]);

                int[] pixel2 = new int[3];
                int pos = (y * 3 * im2.getWidth()) + (x * 3);
                pixel2[2] = ((int) pixelByte2[pos++] & 0xff); // blue
                pixel2[1] = (((int) pixelByte2[pos++] & 0xff)); // green
                pixel2[0] = (((int) pixelByte2[pos] & 0xff)); // red

                variation += Math.sqrt(((pixel1[0] - pixel2[0]) *
                        (pixel1[0] - pixel2[0]) + (pixel1[1] - pixel2[1]) *
                        (pixel1[1] - pixel2[1]) + (pixel1[2] - pixel2[2]) *
                        (pixel1[2] - pixel2[2])) / xx);
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