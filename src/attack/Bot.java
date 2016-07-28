package attack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static attack.Variables.*;

/**
 * Created by charm
 */
public class Bot {

    private static final ArrayList<CompareImages> listOfTasks = new ArrayList<>();
    private static Point startLeft = new Point(200, 200);
    private static Point finishLeft = new Point(600, 600);
    private static Point startTop = new Point(300, 30);
    private static Point finishTop = new Point(900, 400);
    private static Point startRight = new Point(700, 150);
    private static Point finishRight = new Point(1100, 600);
    private static ArrayList<BufferedImage> heroes = new ArrayList<>();

    static {
        System.out.println("CORES=" + Runtime.getRuntime().availableProcessors());
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String separator = Variables.separator;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
        try {
            path = path + separator + "images" + separator;
            System.out.println(path);
            queen = ImageIO.read(new File(path + separator + "queen.png"));
            king = ImageIO.read(new File(path + separator + "king.png"));
            warden = ImageIO.read(new File(path + separator + "warden.png"));
            fullElixirStorage3 = ImageIO.read(new File(path + separator + "fullElixirStorage3.png"));
            fullElixirStorageBoost1 = ImageIO.read(new File(path + separator + "fullElixirStorageBoost1.png"));
            fullElixirStorageBoost2 = ImageIO.read(new File(path + separator + "fullElixirStorageBoost2.png"));
            fullElixirStorageBoost3 = ImageIO.read(new File(path + separator + "fullElixirStorageBoost3.png"));
            fullElixirStorageBoost4 = ImageIO.read(new File(path + separator + "fullElixirStorageBoost4.png"));
            emptyElixir = ImageIO.read(new File(path + separator + "emptyElixir.png"));
            emptyElixir2 = ImageIO.read(new File(path + separator + "emptyElixir2.png"));
            emptyElixir3 = ImageIO.read(new File(path + separator + "emptyElixir3.png"));
            emptyElixir4 = ImageIO.read(new File(path + separator + "emptyElixir4.png"));
            emptyElixir5 = ImageIO.read(new File(path + separator + "emptyElixir5.png"));
            emptyElixir6 = ImageIO.read(new File(path + separator + "emptyElixir6.png"));
            emptyElixir7 = ImageIO.read(new File(path + separator + "emptyElixir7.png"));
            emptyElixir8 = ImageIO.read(new File(path + separator + "emptyElixir8.png"));
            fullCamp = ImageIO.read(new File(path + separator + "fullCamp.png"));
            barrackIsFull = ImageIO.read(new File(path + separator + "barrackIsFull.png"));
            goldCircle = ImageIO.read(new File(path + separator + "goldCircle.png"));
            barrack = ImageIO.read(new File(path + separator + "barrack.png"));
            clanCastle = ImageIO.read(new File(path + separator + "clanCastle.png"));
            clanCastleFight = ImageIO.read(new File(path + separator + "clanCastleFight.png"));
            disconnect = ImageIO.read(new File(path + separator + "disconnect.png"));
            cart = ImageIO.read(new File(path + separator + "cart.png"));
            minions = ImageIO.read(new File(path + separator + "minions.png"));

            heroes.add(king);
            heroes.add(warden);
            heroes.add(queen);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        needToWait = new AtomicBoolean();
        //Disconnect disconnect = new Disconnect();
        //Thread thread = new Thread(disconnect);
        //thread.start();

    }

    static void getArmy() throws InterruptedException, AWTException {
        int barracksCount = cntOfBarracks;
        robot.mouseMove(430, 30);
        waitAndClick(100);
        CompareImages ci = new CompareImages(get_screen(), Variables.barrack,
                firstBarrackStart, firstBarrackFinish, new ArrayList<>(), 0.1f);
        ci.compare();

        if (ci.result() == null) {
            return;
        }
        robot.mouseMove(ci.result().x + 10, ci.result().y + 10);
        waitAndClick(5000);
        robot.mouseMove(buttonTrainTroops.x, buttonTrainTroops.y);
        waitAndClick(5000);
        Thread.sleep(5000);
        for (int step = 0; step < barracksCount; step++) {
            if (step < 4) {
                robot.mouseMove(goblinsInBarrack.x, goblinsInBarrack.y);
            } else {
                robot.mouseMove(minionsInBarrack.x, minionsInBarrack.y);
            }

            for (int i = 0; i < trainTroops; i++) {
                waitAndClick(30);
            }
            robot.mouseMove(nextInBarrack.x, nextInBarrack.y);
            waitAndClick(2000);
            Thread.sleep(2000);
        }
        robot.mouseMove(closeBarrack.x, closeBarrack.y);
        waitAndClick(1000);
        Thread.sleep(500);
    }

    static void pushTroops(Point start, Point finish, int cnt) throws InterruptedException {
        int x0 = Math.abs(start.x - finish.x) + 1;
        int y0 = Math.abs(start.y - finish.y) + 1;
        for (int i = 1; i <= cnt; i++) {
            double x = start.x + ((double) x0 / cnt) * i;
            double y;
            if (start.getY() > finish.getY())
                y = start.y - ((double) y0 / cnt) * i;
            else
                y = start.y + ((double) y0 / cnt) * i;
            robot.mouseMove((int) x, (int) y);
            waitAndClick(10);
        }
    }

    static void startAttack() throws InterruptedException, AWTException {
        System.out.println("start attack");
        ArrayList<Point> coordinatesHeroes = new ArrayList<>();
        //TODO: check heroes < 2 then dont use.
        for (int i = 0; i < 3; i++) {
            Point point = new CompareImages(get_screen(), heroes.get(i), barTroopsStart,
                    barTroopsEnd, new ArrayList<>(), 0.07f).compare().result();
            if (point != null) {
                coordinatesHeroes.add(point);
            }
        }
        Point minionsResult = new CompareImages(get_screen(), minions, barTroopsStart,
                barTroopsEnd, new ArrayList<>(), 0.07f).compare().result();
        /*
        CompareImages ci = new CompareImages(get_screen(), Variables.clanCastleFight,
                clanCastleFightStart, clanCastleFightFinish, new ArrayList<>(), 0.7f);
        ci.compare();
        Point clanCastleCoordinates = ci.result();

        if (clanCastleCoordinates != null) {
            robot.mouseMove(clanCastleCoordinates.x, clanCastleCoordinates.y);
            waitAndClick(100);
            pushTroops(t1, t2, 10);
            pushTroops(t2, t3, 10);
        }*/

        for (Point coordinatesHero : coordinatesHeroes) {
            Thread.sleep(50);
            robot.mouseMove(coordinatesHero.x, coordinatesHero.y);
            waitAndClick(50);
            Thread.sleep(50);
            pushTroops(t1, t2, 3);
        }

        Thread.sleep(10000);

        int[] troopsArray;
        if (minionsResult == null) {
            troopsArray = new int[]{25, 45, 45, 40, 45, 35};
        } else {
            troopsArray = new int[]{20, 20, 20, 25, 20, 15};
        }

        int cycles = minionsResult == null ? 1 : 2;
        for (int k = 0; k < cycles; k++) {
            if (k == 0) robot.mouseMove(firstInFight.x, firstInFight.y);
            if (k == 1) robot.mouseMove(secondInFight.x, secondInFight.y);
            waitAndClick(50);
            int circles = 2;
            if (k == 1) circles = 3;
            for (int i = 0; i < circles; i++) {
                if (k == 1) troopsArray = new int[]{5, 5, 5, 8, 5, 5};
                pushTroops(t1, t2, troopsArray[0]);
                pushTroops(t2, t3, troopsArray[1]);
                pushTroops(t4, t3, troopsArray[2]);
                if (i == 0 || k == 1) {
                    pushTroops(t5, t4, troopsArray[3]);
                    pushTroops(t6, t5, troopsArray[4]);
                    pushTroops(t6, t1, troopsArray[5]);
                }
            }
        }

        if (coordinatesHeroes.size() != 0) {
            Thread.sleep(9000);
        }

        for (int i = 0; i < coordinatesHeroes.size(); i++) {
            Thread.sleep(50);
            if (i == 1) {
                Thread.sleep(3000);
            }
            if (i == 2) {
                Thread.sleep(12000);
            }
            robot.mouseMove(coordinatesHeroes.get(i).x, coordinatesHeroes.get(i).y);
            waitAndClick(50);
        }
    }

    private static void addCommonElixir(ArrayList<CompareImages> list,
                                        BufferedImage bigImage, BufferedImage smallImage, float precise) {
        list.add(new CompareImages(bigImage, smallImage, //left
                startLeft, finishLeft, new ArrayList<>(), precise));
        list.add(new CompareImages(bigImage, smallImage, //top
                startTop, finishTop, new ArrayList<>(), precise));
        list.add(new CompareImages(bigImage, smallImage, //right
                startRight, finishRight, new ArrayList<>(), precise));
    }

    public static boolean goodBase(int gold, BufferedImage bf, boolean test) throws AWTException, InterruptedException, IOException {
        int localGold = 150000;
        int localElixir = 150000;
        if (cntOfBarracks < 4) {
            localElixir = localGold = 200000;
        }
        if (!test) {
            if (gold < localGold && gold != -1) {
                //System.out.println("gold < then we need");
                return false;
            }
        }
        if (bf == null) {
            bf = get_screen();
        }
        listOfTasks.clear();
        addCommonElixir(listOfTasks, bf, Variables.emptyElixir2, 0.1f);
        addCommonElixir(listOfTasks, bf, Variables.emptyElixir3, 0.1f);
        addCommonElixir(listOfTasks, bf, Variables.emptyElixir4, 0.1f);
        addCommonElixir(listOfTasks, bf, Variables.emptyElixir7, 0.1f);
        addCommonElixir(listOfTasks, bf, Variables.emptyElixir8, 0.03f);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
                        , 25, 25, TimeUnit.SECONDS, new ArrayBlockingQueue<>(25));

        //threadPoolExecutor.invokeAll(listOfTasks);

        //TODO: try Future tasks + invoke all
        //threadPoolExecutor.invokeAll()
        listOfTasks.forEach(threadPoolExecutor::execute);
        threadPoolExecutor.shutdown();

        int elixir = getElixir(bf);
        //System.out.println(gold + " " + elixir);

        if (gold < localGold || elixir < localElixir) {
            if (gold != -1 && !test) {
                threadPoolExecutor.shutdownNow();
                //System.out.println("gold or elixir < then we need");
                return false;
            }
        }

        threadPoolExecutor.awaitTermination(25, TimeUnit.SECONDS);

        for (CompareImages e : listOfTasks) {
            if (e.result() != null) {
                //if (!test) {
                //System.out.println("bad base because of " + i);
                //}
                return false;
            }
        }
        /*
        if (!test) {
            System.out.println("SAVE THE BASE");
            saveImage(gold, elixir);
        }
        System.out.println();
        System.out.println(gold + " " + elixir);
        */
        return true;

    }

    public static boolean fullCamp() throws AWTException, InterruptedException {
        robot.mouseMove(340, 160);
        waitAndClick(500);
        CompareImages ci = new CompareImages(get_screen(), Variables.fullCamp,
                startCamp, endCamp, new ArrayList<>(), 0.1f);
        ci.compare();
        if (ci.result() != null) {
            return true;
        }
        ci = new CompareImages(get_screen(), Variables.barrackIsFull,
                startBarrack, endBarrack, new ArrayList<>(), 0.1f);
        ci.compare();
        return ci.result() != null;
    }

    public static void waitAndClick(int time) throws InterruptedException {
        Thread.sleep(time);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public static void decreaseZoom() throws InterruptedException {
        for (int w = 0; w < countDecreaseZoom; w++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            Thread.sleep(1000);
        }
    }

    public static void collect() throws AWTException, InterruptedException {
        BufferedImage bf = get_screen();
        CompareImages ci = new CompareImages(bf, Variables.goldCircle,
                collectStart, collectFinish, new ArrayList<>(), 0.7f);
        ci.compare();
        if (ci.result() != null) {
            Point point = ci.result();
            robot.mouseMove(point.x, point.y);
            waitAndClick(1000);
        }
    }

    public static void fetchCart() throws AWTException, InterruptedException {
        BufferedImage bf = get_screen();
        CompareImages ci = new CompareImages(bf, Variables.cart,
                cartStart, cartFinish, new ArrayList<>(), 0.3f);
        ci.compare();
        if (ci.result() != null) {
            Point point = ci.result();
            robot.mouseMove(point.x, point.y);
            waitAndClick(1000);
            robot.mouseMove(670, 674);
            waitAndClick(1000);
        }
    }

    public static void restart() throws InterruptedException {
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_M);  // VK_WINDOWS key still pressed
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        Thread.sleep(1000);
        robot.mouseMove(75, 10);
        waitAndClick(2000);
        robot.mouseMove(1330, 740);
        waitAndClick(2000);

        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_M);  // VK_WINDOWS key still pressed
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        Thread.sleep(500);

        robot.mouseMove(EmulatorOnDekstop.x, EmulatorOnDekstop.y);
        waitAndClick(2000);
        waitAndClick(2000);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(30 * 1000);
        robot.mouseMove(tryTheseApps.x, tryTheseApps.y);
        waitAndClick(2000);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mouseMove(75, 200);
        waitAndClick(2000);
        robot.mouseMove(500, 500);
        waitAndClick(2000);

    }

    public static BufferedImage get_screen() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        return robot.createScreenCapture(screenRectangle);
    }

    public static int getGold(BufferedImage image) throws AWTException, IOException {
        if (image == null) {
            image = get_screen();
        }
        return Recognition.getNumber(startGold, endGold, "", image);
    }

    public static int getElixir(BufferedImage image) throws AWTException, IOException {
        if (image == null) {
            image = get_screen();
        }
        return Recognition.getNumber(startElixir, endElixir, "_", image);
    }

    public static void getTroops() throws AWTException, InterruptedException {
        robot.mouseMove(85, 10);
        waitAndClick(500);
        BufferedImage bf = get_screen();
        CompareImages ci = new CompareImages(bf, Variables.clanCastle,
                clanCastleBuildingStart,
                clanCastleBuildingFinish, new ArrayList<>(), 0.3f);
        ci.compare();
        if (ci.result() != null) {
            Point point = ci.result();
            robot.mouseMove(point.x + 5, point.y + 15);
            waitAndClick(1000);
            robot.mouseMove(623, 650);
            waitAndClick(1000);
            robot.mouseMove(856, 300);
            waitAndClick(1000);
        }
        for (int i = 0; i < 2; i++) {
            robot.mouseMove(85, 10);
            waitAndClick(500);
        }
    }

    public static void run() throws AWTException, IOException, InterruptedException {
        while (true) {
            restartWifi();
            restart();
            decreaseZoom();
            cameraToUp();
            //saveImage(0, (int) System.currentTimeMillis());
            robot.mouseMove(660, 580);  //exit startAttack
            waitAndClick(300);
            int count = -1;
            boolean restartAfterBuild = false;
            Thread.sleep(300);
            robot.mouseMove(10, 10);
            waitAndClick(300);

            for (int i = 0; i < 3; i++) {
                collect();
            }

            while (!fullCamp()) {
                decreaseZoom();
                cameraToUp();
                count++;
                if (checkFullCamp()) break;
                if (count == 10) {
                    restartAfterBuild = true;
                    break;
                }
                if (count % 2 == 0) {
                    getArmy();
                }
                if (checkFullCamp()) break;
                for (int i = 0; i < 12; i++) {
                    collect();
                }
                if (checkFullCamp()) break;
                getTroops();
                fetchCart();
                boolean needToAttack = false;
                for (int i = 0; i < 10; i++) {
                    if (checkFullCamp()) {
                        needToAttack = true;
                        break;
                    }
                    CompareImages ci = new CompareImages(get_screen(), Variables.barrack,
                            firstBarrackStart, firstBarrackFinish, new ArrayList<>(), 0.1f);
                    ci.compare();
                    if (ci.result() == null) {
                        break;
                    }
                    Thread.sleep(10000);
                }
                if (needToAttack) {
                    break;
                }
            }
            if (restartAfterBuild) {
                continue;
            }
            decreaseZoom();
            getTroops();
            if (!fullCamp()) {
                continue;
            }
            System.gc();
            waitAndClick(500);
            robot.mouseMove(attackButton.x, attackButton.y);
            waitAndClick(500);
            Thread.sleep(5000);

            robot.mouseMove(findMatch.x, findMatch.y);
            waitAndClick(500);
            Thread.sleep(1000);

            robot.mouseMove(agreeFight.x, agreeFight.y);
            waitAndClick(5000);

            int iterations = 0;
            int prevGold = 0;
            int gold;
            while (true) {
                Thread.sleep(2000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyPress(KeyEvent.VK_ENTER);
                gold = 0;
                iterations++;
                if (iterations == 500) {
                    break;
                }

                long currentTime;
                long startTime = currentTime = System.currentTimeMillis();
                while (currentTime - startTime < 15000) {
                    gold = getGold(null);
                    if (gold > 0) {
                        break;
                    }
                    currentTime = System.currentTimeMillis();
                }
                if (gold == 0) {
                    break;
                }
                if (goodBase(gold, null, false)) {
                    break;
                }
                /*
                int elixir = getElixir(null);
                if (gold > 200000 && elixir > 200000) {
                    saveImage(gold, elixir);
                }
                */
                if (prevGold == gold) {
                    break;
                }
                prevGold = gold;
                if (System.currentTimeMillis() - startTime < 5000) {
                    Thread.sleep(5000);
                }
                robot.mouseMove(next.x, next.y);
                waitAndClick(300);
            }
            if (gold != 0 && prevGold != gold) {
                System.out.println("iterations=" + iterations);
                startAttack();
            }
        }
    }

    private static boolean checkFullCamp() throws AWTException, InterruptedException {
        if (fullCamp()) {
            return true;
        }
        Thread.sleep(30000);
        return fullCamp();
    }

    private static void restartWifi() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(new String[]{"cmd.exe", "/c", "netsh interface set interface \"Wireless Network Connection\" enable"});
        } catch (IOException e) {
            System.out.println("can't restartWifi" + e.getMessage());
        }
    }

    private static void cameraToUp() throws InterruptedException {
        robot.mouseMove(35, 300);
        Thread.sleep(300);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        for (int i = 0; i < 100; i++) {
            int mov_x = ((35 * i) / 100) + (35 * (100 - i) / 100);
            int mov_y = ((1300 * i) / 100) + (2 * (100 - i) / 100);
            robot.mouseMove(mov_x, mov_y);
            robot.delay(10);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Thread.sleep(1000);
    }


    public static void saveImage(int gold, int elixir) throws AWTException {
        BufferedImage bf = get_screen();
        File outputFile = new File("screenshots/" + String.valueOf(gold) + "_" + String.valueOf(elixir) + ".png");
        System.out.println(outputFile.getAbsolutePath());
        try {
            ImageIO.write(bf, "png", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDisconnect() throws AWTException {
        BufferedImage bf = get_screen();
        return bf.getRGB(30, 30) == -16777216;
    }

    /*
    private static void checkDisconnectAndWait() {
        if (needToWait.get()) {
            try {
                Thread.sleep(Variables.sleepAfterDisconnect);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    */


}