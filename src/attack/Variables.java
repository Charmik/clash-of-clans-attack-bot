package attack;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Charm on 15.06.14.
 */
public class Variables {

    public static final Point attackButton = new Point(140, 630);
    public static final Point findMatch = new Point(300, 520);
    public static final Point next = new Point(1170, 520);
    public static final Point exitBattle = new Point(670, 570);
    public static final Point firstBarrackStart = new Point(275, 75);
    public static final Point firstBarrackFinish = new Point(600, 570);
    public static final Point buttonTrainTroops = new Point(810, 605);
    public static final Point archersInBarrack = new Point(560, 350);
    public static final Point warInBarrack = new Point(450, 350);
    public static final Point goblinInBarrack = new Point(810, 350);
    public static final Point nextInBarrack = new Point(1050, 370);

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
    public static final Point agreeFight = new Point(776, 424);
    public static final Point barTroopsStart = new Point(250, 580);
    public static final Point barTroopsEnd = new Point(600, 677);
    public static final Point startGold = new Point(105, 70); // for real
    //public static final Point startGold = new Point(95, 95);
    public static final Point endGold = new Point(225, 95);
    //public static final Point endGold = new Point(200,120);
    public static final Point startElixir = new Point(100, 102);
    //public static final Point startElixir = new Point(55, 102);
    public static final Point endElixir = new Point(225, 127);
    public static final Point t1 = new Point(544, 6);
    public static final Point t2 = new Point(800, 3);
    public static final Point t3 = new Point(1270, 372);
    public static final Point t4 = new Point(985, 591);
    public static final Point t5 = new Point(358, 577);
    public static final Point t6 = new Point(107, 373);
    public static final Point EmulatorOnDekstop = new Point(40, 141);
    public static final int cntOfBarracks = 4;
    public static final Point startCamp = new Point(5, 5);
    public static final Point endCamp = new Point(800, 700);
    public static final Point returnToHome = new Point(140, 630);
    public static final Point tryTheseApps = new Point(960, 510);
    public static final Point disconnectStart = new Point(306, 269);
    public static final Point disconnectFinish = new Point(515, 321);
    public static final Point collectStart = new Point(380, 100);
    public static final Point collectFinish = new Point(950, 400);
    public static final Point closeBarrack = new Point(1015, 111);
    public static final Point clanCastleFightStart = new Point(311, 600);
    public static final Point clanCastleFightFinish = new Point(488, 717);
    public static final String separator;
    public static int countDecreaseZoom = 10;
    public static int trainTroops;
    static Robot robot;
    static BufferedImage king;
    static BufferedImage queen;
    static BufferedImage elixirFullStorage;
    static BufferedImage goldFullStorage;
    static BufferedImage emptyElixir;
    static BufferedImage emptyElixir2;
    static BufferedImage fullCamp;
    static BufferedImage goldCircle;
    static BufferedImage barrack;
    static BufferedImage clanCastle;
    static BufferedImage clanCastleFight;
    static BufferedImage disconnect;

    static AtomicBoolean needToWait;
    static long sleepAfterDisconnect = 60000;

    static {
        if ("Mac OS X".equals(System.getProperty("os.name"))) {
            separator = "/";
        } else {
            separator = "\\";
        }
    }

    //public static int money/Elixir;
    //delay.
}
