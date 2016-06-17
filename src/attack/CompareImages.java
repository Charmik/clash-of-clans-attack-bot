package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
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
    private int[] pixelInt1;
    private byte[] pixelByte2;
    private int image1Width;
    private int image2Width;
    private int image2Height;

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
        this.image1Width = image1.getWidth();
        this.image2Width = image2.getWidth();
        this.image2Height = image2.getHeight();

        //FOR TESTS ONLY
        /*
        if (image1.getRaster().getDataBuffer() instanceof DataBufferByte) {
            byte[] pixels = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
            pixelInt1 = new int[pixels.length / 4];
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += 4) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                pixelInt1[pixel/4] = argb;
                col++;
                if (col == image1.getWidth()) {
                    col = 0;
                    row++;
                }
            }
        }
        else
        */
        {
            pixelInt1 = ((DataBufferInt) image1.getRaster().getDataBuffer()).getData();
        }
        pixelByte2 = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
    }

    public CompareImages compare() {
        int bestX = 0;
        int bestY = 0;
        double lowestDiff = 10050000;
        for (int x = finish.x; x >= start.x; x--) {
            for (int y = finish.y; y >= start.y; y--) {
                double comp = compareImages(x, y);
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

    private double compareImages(int x1, int y1) {
        double variation = 0.0f;
        for (int y = image2Height - 1; y >= 0; y--) {
            for (int x = image2Width - 1; x >= 0; x--) {
                //int[] pixel1_ = image1.getRaster().getPixel(x + x1, y + y1, new int[4]);

                int[] pixel1 = new int[3];
                int p = pixelInt1[(y + y1) * image1Width + (x + x1)];
                pixel1[2] = p & 0xff;
                pixel1[1] = (p >> 8) & 0xFF;
                pixel1[0] = (p >> 16) & 0xFF;

                /*
                if (pixel1[0] != pixel1_[0]) {
                    throw new RuntimeException();
                }
                if (pixel1[1] != pixel1_[1]) {
                    throw new RuntimeException();
                }if (pixel1[2] != pixel1_[2]) {
                    throw new RuntimeException();
                }
                */



                int[] pixel2 = new int[3];
                int pos = (y * 3 * image2Width) + (x * 3);
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