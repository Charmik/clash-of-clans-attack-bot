package attack;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Charm on 18/11/2015.
 */
class Disconnect implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                CompareImages ci = new CompareImages(Bot.get_screen(), Variables.disconnect,
                        Variables.disconnectStart.x, Variables.disconnectStart.y,
                        Variables.disconnectFinish.x, Variables.disconnectFinish.y, new ArrayList<>(), 0.1f);
                ci.compare();
                if (ci.result() != null) {
                    System.out.println("DISCONNECT");
                    Variables.needToWait.set(true);
                    try {
                        Thread.sleep(Variables.sleepAfterDisconnect + 10000);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (AWTException e) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
