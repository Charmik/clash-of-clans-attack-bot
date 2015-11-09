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
import java.util.ArrayList;


public class Test1 {

    private static ArrayList<String> tests = new ArrayList<>();
    private static ArrayList<Integer> gold = new ArrayList<>();
    private static ArrayList<Integer> elixir = new ArrayList<>();

    private static final String separator = Variables.separator;

    @Before
    public void init() throws AWTException, InterruptedException, IOException {
        Bot.init();
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
            e.printStackTrace();
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
    public void test() throws AWTException, InterruptedException, IOException {
        for (int i = 0; i < tests.size(); i++) {
            System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(tests.get(i));
            int gold_ = Bot.getGold(image);
            int elixir_ = Bot.getElixir(image);
            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);
            System.out.println("i=" + i);
            Assert.assertEquals("gold", goldExpected, gold_);
            Assert.assertEquals("elixir", elixirExpected, elixir_);
        }
    }

    @Test
    public void testOneTest() throws AWTException, InterruptedException, IOException {
        int k = 16;
        for (int i = k; i < k + 1; i++) {
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(tests.get(i));
            int gold_ = 300000;//Bot.getGold(image);
            int elixir_ = 300000;//Bot.getElixir(image);

            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);
            Bot.goodBase(goldExpected, image);
            //Assert.assertEquals("gold", goldExpected, gold_);
            //Assert.assertEquals("elixir", elixirExpected, elixir_);
        }
    }

    @Test
    public void isGoodBase() throws AWTException, InterruptedException, IOException {
        int k = 0; //tests.size
        for (int i = k; i < tests.size(); i++) {
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(tests.get(i));

            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);

            boolean flag = Bot.goodBase(goldExpected, image);
            if (!flag) {
                System.out.println(1);
                //System.out.println("bad");
            } else {
                System.out.println(2);
                //System.out.println("good base!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            //Assert.assertEquals(flag, false);

        }
    }

}