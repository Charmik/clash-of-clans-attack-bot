package tests;

import attack.Bot;
import attack.Variables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class BotTest {

    private static final String separator = Variables.separator;
    private final static ArrayList<String> tests = new ArrayList<>();
    private final static ArrayList<Integer> gold = new ArrayList<>();
    private final static ArrayList<Integer> elixir = new ArrayList<>();

    @Before
    public void init() throws AWTException, InterruptedException, IOException {
        //Bot.init();
        try {
            String path = new File(".").getCanonicalPath();
            path += separator + "imagesForTests" + separator;

            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();

            assert listOfFiles != null;
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    String name = listOfFile.getName();
                    int index = name.indexOf("_");
                    String goldStr = name.substring(0, index);
                    String elixirStr = name.substring(index + 1, name.length() - 4);
                    try {
                        int gold_ = Integer.parseInt(goldStr);
                        int elixir_ = Integer.parseInt(elixirStr);
                        tests.add(name);
                        gold.add(gold_);
                        elixir.add(elixir_);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(String nameImage) {
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
            path += separator + "imagesForTests" + separator + nameImage;

        } catch (IOException e) {
            System.out.println("could read file" + nameImage);
        }
        try {
            assert path != null;
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testAllGoldAndElixir() throws AWTException, InterruptedException, IOException {
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(i + " size=" + tests.size());
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(tests.get(i));
            int gold_ = Bot.getGold(image);
            int goldExpected = gold.get(i);
            System.out.println("i=" + i + " goldExpected=" + goldExpected);
            Assert.assertEquals("gold", goldExpected, gold_);

            int elixir_ = Bot.getElixir(image);
            int elixirExpected = elixir.get(i);
            Assert.assertEquals("elixir", elixirExpected, elixir_);
        }
    }

    @Test
    public void testOneTest() throws AWTException, InterruptedException, IOException {
        //System.out.println(gold.get(i) + elixir.get(i));
        BufferedImage image = getImage(tests.get(0));
        int gold_ = Bot.getGold(image);
        int elixir_ = Bot.getElixir(image);

        int goldExpected = gold.get(0);
        int elixirExpected = elixir.get(0);
        //Bot.goodBase(goldExpected, image);
        Assert.assertEquals("gold", goldExpected, gold_);
        Assert.assertEquals("elixir", elixirExpected, elixir_);
    }

    @Test
    public void testGold() throws IOException, AWTException {
        BufferedImage image = getImage(tests.get(0));
        int gold_ = Bot.getGold(image);
        int goldExpected = gold.get(0);
        Assert.assertEquals("gold", goldExpected, gold_);
    }

    @Test
    public void testElixir() throws IOException, AWTException {
        BufferedImage image = getImage(tests.get(0));
        int elixir_ = Bot.getElixir(image);
        int elixirExpected = elixir.get(0);
        Assert.assertEquals("elixir", elixirExpected, elixir_);

    }

    @Test
    public void isGoodBase() throws AWTException, InterruptedException, IOException {
        int k = 0; //tests.size
        for (int i = k; i < tests.size(); i++) {
            System.out.println("index=" + i);
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(tests.get(i));

            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);

            System.out.println(goldExpected + " " + elixirExpected);
            boolean flag = Bot.goodBase(goldExpected, image, true);
            if (!flag) {
                System.out.println("bad");
            } else {
                System.out.println("good base!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            Assert.assertEquals(flag, true);

        }
    }

    @Test
    public void isBadBase() throws AWTException, InterruptedException, IOException {
        int k = 0; //tests.size
        for (int i = k; i < tests.size(); i++) {
            System.out.println("index=" + i);
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(tests.get(i));

            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);

            System.out.println(goldExpected + " " + elixirExpected);
            boolean flag = Bot.goodBase(goldExpected, image, false);
            if (!flag) {
                //System.out.println("bad");
            } else {
                System.out.println("good base!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Path pathSource =
                        FileSystems.getDefault().getPath(new File(".").getCanonicalPath() +
                                separator + "imagesForTests" + separator + tests.get(i));
                Path pathDestination = FileSystems.getDefault().getPath(new File(".").getCanonicalPath() +
                        separator + "imagesForTests" + separator + "goodBase" + separator + tests.get(i));
                Files.move(pathSource,pathDestination, StandardCopyOption.REPLACE_EXISTING);
            }
            //Assert.assertEquals(flag, false);

        }
    }

}