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
 * Created by charm on 09.08.2015.
 */
public class Bot {

    public static boolean getArchers() throws InterruptedException, AWTException {
        robot.mouseMove(379, 30);
        waitAndClick(100);
        CompareImages ci = new CompareImages(get_screen(), Variables.barrack,
                firstBarrackStart.x, firstBarrackStart.y, firstBarrackFinish.x, firstBarrackFinish.y, new ArrayList<>(), 0.1f);
        ci.compare();

        if (ci.getAnswer().size() == 1) {
            return false;
        }
        robot.mouseMove(ci.getAnswer().get(0).x + 10, ci.getAnswer().get(0).y + 10);
        waitAndClick(5000);
        robot.mouseMove(buttonTrainTroops.x, buttonTrainTroops.y);
        waitAndClick(5000);
        Thread.sleep(5000);
        for (int step = 0; step < cntOfBarracks; step++) {
            robot.mouseMove(archersInBarrack.x, archersInBarrack.y);
            for (int i = 0; i < trainTroops; i++) {
                waitAndClick(100);
            }
            robot.mouseMove(nextInBarrack.x, nextInBarrack.y);
            waitAndClick(2000);
            Thread.sleep(2000);
        }
        robot.mouseMove(closeBarrack.x, closeBarrack.y);
        waitAndClick(1000);
        Thread.sleep(500);
        return true;
    }

    public static void pushTroops(Point start, Point finish, int cnt) throws InterruptedException {
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
            waitAndClick(50);
        }
    }

    public static void fight() throws InterruptedException, AWTException {
        ArrayList<BufferedImage> heroes = new ArrayList<>();
        heroes.add(king);
        heroes.add(queen);
        ArrayList<Point> coordinatesHeroes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ArrayList<Point> list = new CompareImages(get_screen(), heroes.get(i), barTroopsStart.x, barTroopsStart.y,
                    barTroopsEnd.x, barTroopsEnd.y, new ArrayList<>()).compare().getAnswer();
            if (list.size() == 2) {
                coordinatesHeroes.add(list.get(0));
            }
        }

        CompareImages ci = new CompareImages(get_screen(), Variables.clanCastleFight,
                clanCastleFightStart.x, clanCastleFightStart.y,
                clanCastleFightFinish.x, clanCastleFightFinish.y, new ArrayList<>(), 0.7f);
        ci.compare();
        Point clanCastleCoordinates = null;
        if (ci.getAnswer().size() == 2) {
            clanCastleCoordinates = ci.getAnswer().get(0);

        }

        for (int i = 0; i < 2; i++) {
            pushTroops(t1, t2, 20);
            pushTroops(t2, t3, 45);
            pushTroops(t4, t3, 45);
            if (i == 0) {
                pushTroops(t5, t4, 30);
                pushTroops(t6, t5, 45);
                pushTroops(t6, t1, 35);
            }
        }
        if (clanCastleCoordinates != null) {
            robot.mouseMove(clanCastleCoordinates.x, clanCastleCoordinates.y);
            waitAndClick(500);
            pushTroops(t1, t2, 10);
            pushTroops(t2, t3, 10);
        }

        for (Point coordinatesHero : coordinatesHeroes) {
            Thread.sleep(100);
            robot.mouseMove(coordinatesHero.x, coordinatesHeroes.get(0).y);
            waitAndClick(100);
            pushTroops(t1, t2, 3);
            waitAndClick(100);
        }
        Thread.sleep(13000);

        for (int i = 0; i < coordinatesHeroes.size(); i++) {
            Thread.sleep(100);
            if (i == 1) {
                Thread.sleep(12000);
            }
            robot.mouseMove(coordinatesHeroes.get(i).x, coordinatesHeroes.get(i).y);
            waitAndClick(100);
        }
    }


    public static boolean goodBase(int gold, BufferedImage bf) throws AWTException, InterruptedException, IOException {
        int localGold = 200000;
        int localElixir = 200000;
        ArrayList<CompareImages> list = new ArrayList<>();
        if (bf == null) {
            bf = get_screen();
        }
        if (gold < localGold && gold != -1) {
            return false;
        }

        list.add(new CompareImages(bf, Variables.goldFullStorage,
                200, 100, 1000, 750, new ArrayList<>(), 0.09f));
        list.add(new CompareImages(bf, Variables.elixirFullStorage,
                200, 100, 1000, 750, new ArrayList<>(), 0.09f));

        list.add(new CompareImages(bf, Variables.emptyElixir, //left
                200, 200, 600, 600, new ArrayList<>(), 0.1f));
        list.add(new CompareImages(bf, Variables.emptyElixir, //top
                300, 30, 900, 400, new ArrayList<>(), 0.1f));
        list.add(new CompareImages(bf, Variables.emptyElixir, //right
                700, 150, 1100, 600, new ArrayList<>(), 0.1f));
        list.add(new CompareImages(bf, Variables.emptyElixir, //bot
                400, 200, 800, 500, new ArrayList<>(), 0.1f));

        list.add(new CompareImages(bf, Variables.emptyElixir2, //top
                200, 200, 600, 600, new ArrayList<>(), 0.1f));
        list.add(new CompareImages(bf, Variables.emptyElixir2, //left
                300, 30, 900, 400, new ArrayList<>(), 0.1f));
        list.add(new CompareImages(bf, Variables.emptyElixir2, //right
                700, 150, 1100, 600, new ArrayList<>(), 0.1f));
        list.add(new CompareImages(bf, Variables.emptyElixir2, //bot
                400, 200, 800, 500, new ArrayList<>(), 0.1f));

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
                , 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        for (CompareImages aList : list) {
            threadPoolExecutor.execute(aList);
        }
        threadPoolExecutor.shutdown();

        int elixir = getElixir(bf);

        System.out.println(gold + " " + elixir);
        if (gold < localGold || elixir < localElixir) {
            if (gold != -1) {
                threadPoolExecutor.shutdownNow();
                return false;
            }
        }

        threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);

        for (CompareImages aList : list) {
            if (aList.getAnswer().size() == 2) {
                return false;
            }
        }
        //saveImage(gold, elixir);
        //System.out.println();
        System.out.println(gold + " " + elixir);
        return true;

    }

    public static boolean fullCamp() throws AWTException, InterruptedException, IOException {
        CompareImages ci = new CompareImages(get_screen(), Variables.fullCamp, //left
                startCamp.x, startCamp.y, endCamp.x, endCamp.y, new ArrayList<>(), 0.09f);
        ci.compare();
        return ci.getAnswer().size() == 2;
    }

    public static void waitAndClick(int time) throws InterruptedException {
        Thread.sleep(time);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public static void decreaseZoom() throws InterruptedException {
        for (int w = 0; w < countDecreaseZoom; w++) {
            robot.keyPress(KeyEvent.VK_DOWN);
            Thread.sleep(3000);
        }
    }

    public static void collect() throws AWTException, IOException, InterruptedException {
        BufferedImage bf = get_screen();
        CompareImages ci = new CompareImages(bf, Variables.goldCircle,
                collectStart.x, collectStart.y, collectFinish.x, collectFinish.y, new ArrayList<>(), 0.7f);
        ci.compare();
        if (ci.getAnswer().size() == 2) {
            Point point = ci.getAnswer().get(0);
            robot.mouseMove(point.x, point.y);
            waitAndClick(1000);
        }
    }

    public static void restart() throws InterruptedException {
        Thread.sleep(1000);
        robot.mouseMove(75, 10);
        waitAndClick(2000);
        robot.mouseMove(1330, 740);
        waitAndClick(2000);
        robot.mouseMove(EmulatorOnDekstop.x, EmulatorOnDekstop.y);

        waitAndClick(2000);
        waitAndClick(2000);
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_ENTER);

        Thread.sleep(30 * 1000);

        /* robot.mouseMove(130, 639);
        waitAndClick(2000);
        */
        robot.mouseMove(tryTheseApps.x, tryTheseApps.y);
        waitAndClick(2000);
        robot.mouseMove(75, 10);
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

    public static void init() throws AWTException, IOException, InterruptedException {
        String path = new File(".").getCanonicalPath();
        String separater = Variables.separator;
        System.out.println("path = " + path);
        king = null;
        queen = null;
        elixirFullStorage = null;
        fullCamp = null;
        robot = new Robot();
        /*
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        */
        try {
            queen = ImageIO.read(new File(path + separater + "queen.png"));
            king = ImageIO.read(new File(path + separater + "king.png"));
            elixirFullStorage = ImageIO.read(new File(path + separater + "fullElixirStorage.png"));
            emptyElixir = ImageIO.read(new File(path + separater + "emptyElixir.png"));
            emptyElixir2 = ImageIO.read(new File(path + separater + "emptyElixir2.png"));
            goldFullStorage = ImageIO.read(new File(path + separater + "fullGoldStorage.png"));
            fullCamp = ImageIO.read(new File(path + separater + "fullCamp.png"));
            goldCircle = ImageIO.read(new File(path + separater + "goldCircle.png"));
            barrack = ImageIO.read(new File(path + separater + "barrack.png"));
            clanCastle = ImageIO.read(new File(path + separater + "clanCastle.png"));
            clanCastleFight = ImageIO.read(new File(path + separater + "clanCastleFight.png"));
            disconnect = ImageIO.read(new File(path + separater + "disconnect.png"));
        } catch (IOException e) {
            System.out.println("cant download image_king/queen/elixir" + e.getMessage() + e);
            return;
        }
        needToWait = new AtomicBoolean();
        Disconnect disconnect = new Disconnect();
        Thread thread = new Thread(disconnect);
        //thread.start();

        Thread.sleep(2000);
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
        CompareImages ci = new CompareImages(bf, Variables.clanCastle, //left
                950, 140, 1171, 484, new ArrayList<>(), 0.3f);
        ci.compare();
        if (ci.getAnswer().size() == 2) {
            Point point = ci.getAnswer().get(0);
            robot.mouseMove(point.x + 5, point.y + 15);
            waitAndClick(1000);
            robot.mouseMove(623, 618);
            waitAndClick(1000);
            robot.mouseMove(856, 260);
            waitAndClick(1000);
        }
        for (int i = 0; i < 2; i++) {
            robot.mouseMove(85, 10);
            waitAndClick(500);
        }
    }

    public static void run() throws AWTException, IOException, InterruptedException {
        init();
        boolean needRestart = true;

        while (true) {
            if (needRestart) {
                restart();
            }
            decreaseZoom();
            robot.mouseMove(660, 580);  //exit fight
            waitAndClick(300);
            int count = -1;
            boolean restartAfterBuild = false;
            Thread.sleep(300);
            robot.mouseMove(10, 10);
            waitAndClick(300);
            while (!fullCamp()) {
                decreaseZoom();
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyPress(KeyEvent.VK_ENTER);
                count++;
                if (fullCamp()) {
                    break;
                }
                if (count == 15) {
                    restartAfterBuild = true;
                    break;
                }
                if (count % 5 == 0) {
                    getArchers();
                }
                if (fullCamp()) {
                    break;
                }
                if (fullCamp()) {
                    break;
                }
                for (int i = 0; i < 5; i++) {
                    //checkDisconnectAndWait();
                    collect();
                }
                if (fullCamp()) {
                    break;
                }
                getTroops();
            }
            if (restartAfterBuild) {
                needRestart = true;
                continue;
            }

            decreaseZoom();
            getTroops();
            if (!fullCamp()) {
                continue;
            }
            waitAndClick(2000);
            robot.mouseMove(attackButton.x, attackButton.y);
            waitAndClick(5000);
            Thread.sleep(5000);

            robot.mouseMove(findMatch.x, findMatch.y);
            waitAndClick(1000);
            Thread.sleep(1000);

            robot.mouseMove(agreeFight.x, agreeFight.y);
            waitAndClick(5000);


            int iterations = 0;

            int sum = 1000000;
            int gold;
            while (true) {
                Thread.sleep(2000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyPress(KeyEvent.VK_ENTER);
                gold = 0;
                iterations++;
                if (iterations == 150) {
                    break;
                }

                long startTime = System.currentTimeMillis();
                long currentTime = System.currentTimeMillis();
                while (currentTime - startTime < 30000) {
                    gold = getGold(null);
                    if (gold > 0) {
                        break;
                    }
                    currentTime = System.currentTimeMillis();
                }

                if (gold == 0) {
                    break;
                }
                if (goodBase(gold, null)) {
                    break;
                }
                if (gold + sum == 0) {
                    break;
                }
                sum = gold;
                if (System.currentTimeMillis() - startTime < 15000) {
                    Thread.sleep(5000);
                }
                robot.mouseMove(next.x, next.y);
                waitAndClick(300);
            }

            if (gold != 0) {
                fight();
            }
            decreaseZoom();
            robot.mouseMove(660, 580);
            waitAndClick(300);
            needRestart = !fullCamp();
        }
    }

    public static void saveImage(int gold, int elixir) throws AWTException, IOException {
        BufferedImage bf = get_screen();
        File outputFile = new File("screenshots/" + String.valueOf(gold) + "_" + String.valueOf(elixir) + ".png");
        System.out.println(outputFile.getAbsolutePath());
        ImageIO.write(bf, "png", outputFile);
    }

    private static void checkDisconnectAndWait() {
        if (needToWait.get()) {
            try {
                Thread.sleep(Variables.sleepAfterDisconnect);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}