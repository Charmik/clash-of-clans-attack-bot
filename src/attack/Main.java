package attack;


import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        //Bot.run();

        Bot.init();
        /*
        for (int i = 1; i < 100; i++) {
            Bot.fight();
            System.out.println("iter");
            Thread.sleep(1000);
        }
        */


        double average = 0;
        int coff = 1;
        Robot robot = new Robot();
        for (; ; ) {
            long t = System.currentTimeMillis();


            if (Bot.goodBase(250000, null)) {
                System.out.println("good");
                //robot.mouseMove(500, 500);
            } else {
                //robot.mouseMove(100, 500);
                //System.out.println("bad");
            }
            //Bot.collect();
            t = System.currentTimeMillis() - t;
            average = (average + t) / coff;
            coff = 2;
            System.out.println(t + " average=" + average);
            if (1 == 2) {
                break;
            }
        }
    }
}
