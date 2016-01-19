package attack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Charm on 14.06.14.
 */
class Recognition {

    public static int getNumber(Point start, Point finish, String add, BufferedImage screen) throws IOException {
        String path = new File(".").getCanonicalPath();
        ArrayList<Integer> digits = new ArrayList<>();
        ArrayList<Struct> answer = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            if (i == 1) {
                continue;
            }
            int digit;
            if (i == 10) {
                digit = 1;
            } else {
                digit = i;
            }
            /*
            if (i == 4) {
                digit = 7;
            }
            else if (i == 7) {
                digit = 4;
            }
            */
            try {
                String addFrontToOne = "";
                int cycle = 1;
                if (digit == 1) {
                    cycle = 8; 
                } else if (digit == 7) {
                    cycle = 3;
                } else if (digit == 4) {
                    cycle = 2;
                } else if (digit == 8) {
                    cycle = 2;
                }

                for (int j = 0; j < cycle; j++) {
                    if ((digit == 1 || digit == 7 || digit == 4 || digit == 8) && j > 0) {
                        addFrontToOne += "_";
                    }
                    BufferedImage image = ImageIO.read(new File(path + Variables.separator + addFrontToOne + digit + add + ".png"));
                    while (true) {
                        Point tmp;
                        float precise = 0.06f;
                        if (digit == 1) {
                            precise = 0.0139f;
                        }
                        if (digit == 2) {
                            precise = 0.065f;
                        } else if (digit == 4) {
                            precise = 0.086f;
                        } else if (digit == 8) {
                            precise = 0.07f;
                        } else if (digit == 0 || digit == 7 || digit == 5 || digit == 3) {
                            precise = 0.08f;
                        }
                        tmp = new CompareImages(screen, image,
                                start.x, start.y, finish.x, finish.y, answer, precise, digit).compare().result();

                        if (tmp == null) {
                            break;
                        }
                        answer.add(new Struct(tmp, digit));
                        digits.add(digit);
                    }
                }
            } catch (IOException e) {
                //System.out.println("cant download image");
            }
        }

        for (int i = 0; i < answer.size(); i++) {
            for (int j = 0; j < answer.size(); j++) {
                if (answer.get(i).point.x < answer.get(j).point.x) {
                    Struct tmp = answer.get(i);
                    answer.set(i, answer.get(j));
                    answer.set(j, tmp);
                    Integer tmp2 = digits.get(i);
                    digits.set(i, digits.get(j));
                    digits.set(j, tmp2);
                }
            }
        }
        //for (int i = 0; i < answer.size(); i++) {
        //System.out.print(answer.get(i).x + " " + digits.get(i) + ";");
        //}
        //System.out.println();
        int ans = 0;
        for (Integer digit : digits) {
            ans = ans * 10 + digit;
        }
        return ans;
    }

    public static class Struct {
        final Point point;
        final int digit;

        Struct(Point point, int digit) {
            this.point = point;
            this.digit = digit;
        }
    }

}