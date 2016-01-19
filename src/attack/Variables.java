package attack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Charm on 15.06.14.
 */
public class Variables {

    public static final String separator;
    static final Point attackButton = new Point(140, 630);
    static final Point findMatch = new Point(300, 520);
    static final Point next = new Point(1170, 520);
    //static final Point exitBattle = new Point(670, 570);
    static final Point firstBarrackStart = new Point(275, 75);
    static final Point firstBarrackFinish = new Point(600, 570);
    static final Point buttonTrainTroops = new Point(810, 605);
    static final Point archersInBarrack = new Point(560, 350);
    //static final Point warInBarrack = new Point(450, 350);
    //static final Point goblinInBarrack = new Point(810, 350);
    static final Point nextInBarrack = new Point(1050, 350);
    /*static Point reload = new Point(590, 520);
static Point attack = new Point(70, 750); //comp
static Point findMatch = new Point(170, 650); //comp
static Point next = new Point(1200, 650);
static Point exitBattle = new Point(620, 700);
static Point firstBarrack = new Point(820, 160);
static Point buttonTrainTroops = new Point(820, 760);
static Point archersInBarrack = new Point(520, 460);
static Point nextInBarrack = new Point(1000, 500);
static Point drill = new Point(1120, 520);
static Point agreeFight = new Point(700, 550);
static Point barTroopsStart = new Point(300, 690);
static Point barTroopsEnd = new Point(450, 770);
static Point startGold = new Point(43,213);
static Point endGold = new Point(140,240);
static Point startElixir = new Point(45,240);
static Point endElixir = new Point(140,265);
static Point t1 = new Point(500, 165);
static Point t2 = new Point(880, 170);
static Point t3 = new Point(1200, 470);
static Point t4 = new Point(910, 670);
static Point t5 = new Point(400, 670);
static Point t6 = new Point(150, 470);
*/
    static final Point agreeFight = new Point(776, 424);
    static final Point barTroopsStart = new Point(250, 580);
    static final Point barTroopsEnd = new Point(600, 677);
    static final Point startGold = new Point(105, 70); // for real
    static final Point endGold = new Point(225, 95);
    static final Point startElixir = new Point(100, 102);
    static final Point endElixir = new Point(225, 127);
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
    static final Point clanCastleBuildingStart = new Point(930, 1);
    static final Point clanCastleBuildingFinish = new Point(1171, 484);
    static final int countDecreaseZoom = 10;
    static final int trainTroops = 75;
    static final long sleepAfterDisconnect = 60000;
    static Robot robot;
    static BufferedImage king;
    static BufferedImage queen;
    static BufferedImage fullElixirStorage3;
    static BufferedImage fullElixirStorageBoost1;
    static BufferedImage fullElixirStorageBoost2;
    static BufferedImage emptyElixir;
    static BufferedImage emptyElixir2;
    static BufferedImage emptyElixir3;
    static BufferedImage fullCamp;
    static BufferedImage goldCircle;
    static BufferedImage barrack;
    static BufferedImage clanCastle;
    static BufferedImage clanCastleFight;
    static BufferedImage disconnect;
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
