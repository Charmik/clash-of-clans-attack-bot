package attack;


import java.awt.*;
import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {


        //Robot robot = new Robot();
        /*
        for (int i = 0; i < 1;) {
            //Bot.fetchCart();
            //Bot.getTroops();
            //Bot.getArmy();
            //System.out.println(Bot.fullCamp());
            //Thread.sleep(10000);
            System.out.println(Bot.getGold(null) + "  "  + Bot.getElixir(null));
        }
        */

        Bot.run();
        
            //System.out.println(Bot.getElixir(null));\

            /*
            Thread.sleep(1000);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(3000);
            int gold = Bot.getGold(null);
            int elixir = Bot.getElixir(null);
            Bot.saveImage(gold,elixir);
            Thread.sleep(1000);


            Point point = new CompareImages(Bot.get_screen(), queen, barTroopsStart.x, barTroopsStart.y,
                    barTroopsEnd.x, barTroopsEnd.y, new ArrayList<>(),0.07f).compare().result();
            if (point != null) {
                System.out.println("nawel");
            }
            else {
                System.out.println("netu korolya");
            }
            */

        //Bot.collect();
        //BufferedImage image = Bot.get_screen();
        //int gold = Bot.getGold(image);
        //if (gold == 0) {
        //    continue;
        //}
        //System.out.println(Bot.goodBase(gold, image, false));

        // Bot.fetchCart();
        //}


        //Thread.sleep(5000);
        //Bot.startAttack();
        /*
        for (;;) {
            if (Bot.isDisconnect()) {
                System.out.println("disc");
            }
            else {
                System.out.println("fine");
            }
            Thread.sleep(3000);
        }
        */
        /*
        long sum = 0;
        int index = 0;
        BufferedImage screen = Bot.get_screen();
        for (; ; ) {
            long t = System.currentTimeMillis();
            if (Bot.goodBase(-1, screen, true)) {
                System.out.println("good");
                //robot.mouseMove(500, 500);\
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
        */

        //Bot.init();
        /*
        for (int i = 1; i < 100; i++) {
            Bot.startAttack();
            System.out.println("iter");
            Thread.sleep(1000);
        }
        */


    }
}
