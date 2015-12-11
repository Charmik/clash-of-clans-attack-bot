package attack;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {

        System.out.println("CORES=" + Runtime.getRuntime().availableProcessors());
        
        /*
        Bot.init();
        for(int i = 0; i < 1000; i++) {
        	//System.out.println(Bot.fullCamp());
        	//System.out.println(Bot.getGold(null) + "  "  + Bot.getElixir(null));
	        //System.out.println(Bot.getElixir(null));
	        Bot.collect();
        }
        */
        


        Bot.run();

        Bot.init();
        /*
        for (int i = 1; i < 100; i++) {
            Bot.fight();
            System.out.println("iter");
            Thread.sleep(1000);
        }
        */


        Robot robot = new Robot();
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
            if (1 == 2) {
                break;
            }
        }
    }
}
