package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by charm on 5/21/14.
 */
public class CompareImages implements Callable, Runnable {
    private static final float D = (float) Math.sqrt(3);
    private static final float xx = 255.0f * 255.0f;
    private final double preciseCompare;
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
    private BufferedImage image1;

    CompareImages(BufferedImage image1, BufferedImage image2, Point start, Point finish,
                  ArrayList<Recognition.Struct> points, float precise) {
        this(image1, image2, start, finish, points, precise, -1);
    }

    CompareImages(BufferedImage image1, BufferedImage image2, Point start, Point finish,
                  ArrayList<Recognition.Struct> points, float precise, int digit) {
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
        this.image1 = image1;

        //FOR TESTS ONLY
        /*
        if (image1.getRaster().getDataBuffer() instanceof DataBufferByte) {

            byte[] pixels = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
            System.out.println(image1.getWidth() * image1.getHeight() * 3);
            System.out.println(pixels.length);
            pixelInt1 = new int[pixels.length];


            int pp = 0;
            for (int i = 0; i < image1.getHeight(); i++) {
                for (int j = 0; j < image1.getWidth(); j++) {
                    int[] rgb = image1.getRaster().getPixel(j,i,new int[3]);
                    int t1 = rgb[2];
                    int t2 = rgb[1];
                    int t3 = rgb[0];
                    int count = (i * image1.getWidth() + j) * 3;
                    count = pp;
                    pp += 3;
                    pixelInt1[count] = (byte) t3;
                    pixelInt1[count + 1] = (byte) t2;
                    pixelInt1[count + 2] = (byte) t1;

                    int p1 = pixels[count];
                    int p2 = pixels[count+1];
                    int p3 = pixels[count+2];
                    if (p1 != t1 || p2 != t2 || p3 != t3) {
                        System.out.println("");
                    }
                    //System.out.println(count);
                }
            }
            int [] tmp = new int[pixels.length];
            for (int i = 0; i < pixels.length; i++) {
                tmp[i] = pixels[i];
            }
            System.out.println(Arrays.equals(tmp,pixelInt1));
            throw new RuntimeException();

        } else
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

                int[] pixel1 = new int[3];
                int p = pixelInt1[(y + y1) * image1Width + (x + x1)];
                pixel1[2] = p & 0xff;
                pixel1[1] = (p >> 8) & 0xFF;
                pixel1[0] = (p >> 16) & 0xFF;


                //FOR TESTS ONLY
                /*
                int[] pixel1_ = image1.getRaster().getPixel(x + x1, y + y1, new int[4]);
                pixel1 = pixel1_;
                if (pixel1[0] != pixel1_[0]) {
                    throw new RuntimeException();
                }
                if (pixel1[1] != pixel1_[1]) {
                    throw new RuntimeException();
                }
                if (pixel1[2] != pixel1_[2]) {
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

    @Override
    public Object call() throws Exception {
        compare();
        return result();
    }
}