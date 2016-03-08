package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Charm on 15.06.14.
 */
public class Variables {

    public static final String separator;
    public static final Point attackButton = new Point(140, 660);
    static final Point findMatch = new Point(320, 600);
    static final Point next = new Point(1170, 580);
    static final Point firstBarrackStart = new Point(275, 75);
    static final Point firstBarrackFinish = new Point(600, 570);
    static final Point buttonTrainTroops = new Point(850, 650);
    static final Point archersInBarrack = new Point(534, 385);
    static final Point nextInBarrack = new Point(1170, 360);
    static final Point agreeFight = new Point(776, 424);
    static final Point barTroopsStart = new Point(250, 580);
    static final Point barTroopsEnd = new Point(600, 730);
    static final Point startGold = new Point(70, 20); // for real
    static final Point endGold = new Point(300, 150);
    static final Point startElixir = new Point(123,112);
    static final Point endElixir = new Point(230,135);
    static final Point t1 = new Point(544, 6);
    static final Point t2 = new Point(800, 3);
    static final Point t3 = new Point(1270, 372);
    static final Point t4 = new Point(985, 591);
    static final Point t5 = new Point(358, 577);
    static final Point t6 = new Point(107, 373);
    static final Point EmulatorOnDekstop = new Point(40, 141);
    static final int cntOfBarracks = 4;
    static final Point startCamp = new Point(5, 5);
    static final Point endCamp = new Point(800, 700);
    static final Point returnToHome = new Point(140, 630);
    static final Point tryTheseApps = new Point(960, 510);
    static final Point disconnectStart = new Point(306, 269);
    static final Point disconnectFinish = new Point(515, 321);
    static final Point collectStart = new Point(380, 100);
    static final Point collectFinish = new Point(950, 400);
    static final Point closeBarrack = new Point(1015, 111);
    static final Point clanCastleFightStart = new Point(311, 600);
    static final Point clanCastleFightFinish = new Point(488, 717);
    static final Point clanCastleBuildingStart = new Point(800, 300);
    static final Point clanCastleBuildingFinish = new Point(1171, 500);
    static final Point cartStart = new Point(0,0);
    static final Point cartFinish = new Point(1220, 650);
    static final int countDecreaseZoom = 10;
    static final int trainTroops = 75;
    static final long sleepAfterDisconnect = 60000;
    static Robot robot;
    static BufferedImage king;
    static BufferedImage queen;
    static BufferedImage warden;
    static BufferedImage fullElixirStorage3;
    static BufferedImage fullElixirStorageBoost1;
    static BufferedImage fullElixirStorageBoost2;
    static BufferedImage fullElixirStorageBoost3;
    static BufferedImage fullElixirStorageBoost4;
    static BufferedImage emptyElixir;
    static BufferedImage emptyElixir2;
    static BufferedImage emptyElixir3;
    static BufferedImage emptyElixir4;
    static BufferedImage emptyElixir5;
    static BufferedImage emptyElixir6;
    static BufferedImage emptyElixir7;
    static BufferedImage fullCamp;
    static BufferedImage goldCircle;
    static BufferedImage barrack;
    static BufferedImage clanCastle;
    static BufferedImage clanCastleFight;
    static BufferedImage disconnect;
    static BufferedImage cart;
    static AtomicBoolean needToWait;

    static {
        if ("Mac OS X".equals(System.getProperty("os.name"))) {
            separator = "/";
        } else {
            separator = "\\";
        }
    }

    // static int money/Elixir;
    //delay.
}
