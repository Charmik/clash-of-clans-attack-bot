package attack;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {


            /*
        for (int i = 0; i < 100000;) {
            //System.out.println(Bot.fullCamp());
            //System.out.println(Bot.getGold(null) + "  "  + Bot.getElixir(null));
            //System.out.println(Bot.getElixir(null));
            //Bot.collect();
            Bot.getTroops();
            //BufferedImage image = Bot.get_screen();
            //int gold = Bot.getGold(image);
            //if (gold == 0) {
            //    continue;
            //}
            //System.out.println(Bot.goodBase(gold, image, false));


        }
        */


        Robot robot = new Robot();

        Bot.run();

        //Bot.init();
        /*
        for (int i = 1; i < 100; i++) {
            Bot.fight();
            System.out.println("iter");
            Thread.sleep(1000);
        }
        */


        //Bot.init();
        long sum = 0;
        int index = 0;
        BufferedImage screen = Bot.get_screen();
        for (; ; ) {
            long t = System.currentTimeMillis();
            if (Bot.goodBase(-1, screen)) {
                System.out.println("good");
                //robot.mouseMove(500, 500);
            } else {
                //robot.mouseMove(100, 500);
                System.out.println("bad");
            }
            //Bot.collect();
            t = System.currentTimeMillis() - t;

            index++;
            sum += t;
            System.out.println("index=" + index + " average=" + (sum / index));
        }
    }
}
