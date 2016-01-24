package tests;

import attack.Bot;
import attack.Variables;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Charm
 */
public class PartTests {
    private static final String separator = Variables.separator;
    private final static ArrayList<String> tests = new ArrayList<>();
    private final static ArrayList<Integer> gold = new ArrayList<>();
    private final static ArrayList<Integer> elixir = new ArrayList<>();

    public void init(@Nullable String suffix) throws AWTException, InterruptedException, IOException {
        try {
            String path = new File(".").getCanonicalPath();
            path += separator + "imagesForTests" + separator;
            if (suffix != null) {
                path += suffix + separator;
            }

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

    public BufferedImage getImage(@Nullable String suffix, String nameImage) {
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
            path += separator + "imagesForTests" + separator;
            if (suffix != null) {
                path += separator + suffix + separator;
            }
            path += nameImage;
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
    public void testBadBases() throws AWTException, InterruptedException, IOException {
        String folder = "badBase";
        init(folder);
        int k = 62;
        for (int i = k; i < tests.size(); i++) {
            System.out.println("index=" + i);
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(folder, tests.get(i));
            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);
            boolean flag = Bot.goodBase(goldExpected, image, true);
            if (flag) {
                System.out.println(goldExpected + " " + elixirExpected);
                System.out.println("goodBase");
            }
            Assert.assertEquals(flag, false);
        }
    }

    @Test
    public void testGoodBases() throws AWTException, InterruptedException, IOException {
        String folder = "goodBase";
        init(folder);
        int k = 0;
        for (int i = k; i < tests.size(); i++) {
            System.out.println("index=" + i);
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(folder, tests.get(i));
            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);
            boolean flag = Bot.goodBase(goldExpected, image, true);
            if (!flag) {
                System.out.println(goldExpected + " " + elixirExpected);
                System.out.println("badBase");
            }
            Assert.assertEquals(flag, true);
        }
    }

    @Test
    public void countGoodBases() throws AWTException, InterruptedException, IOException {
        String folder = null;
        init(folder);
        int k = 0;
        int countGoodBases = 0;
        for (int i = k; i < tests.size(); i++) {
            System.out.println("index=" + i);
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(folder, tests.get(i));
            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);

            boolean flag = Bot.goodBase(goldExpected, image, true);
            if (!flag) {
                System.out.println(goldExpected + " " + elixirExpected);
                System.out.println("badBase");
            } else {
                countGoodBases++;
            }
            //Assert.assertEquals(flag, true);
        }
        System.out.println("goodBases=" + countGoodBases + " size=" + tests.size());
    }

    @Test
    public void countBadBases() throws AWTException, InterruptedException, IOException {
        String folder = null;
        init(folder);
        int k = 0;
        int countBadBases = 0;
        for (int i = k; i < tests.size(); i++) {
            System.out.println("index=" + i);
            //System.out.println(gold.get(i) + elixir.get(i));
            BufferedImage image = getImage(folder, tests.get(i));

            int goldExpected = gold.get(i);
            int elixirExpected = elixir.get(i);

            boolean flag = Bot.goodBase(goldExpected, image, true);
            if (flag) {
                System.out.println(goldExpected + " " + elixirExpected);
                System.out.println("goodBase");
            } else {
                countBadBases++;
            }
            Assert.assertEquals(flag, false);
        }
        System.out.println("badBases=" + countBadBases + " size=" + tests.size());
    }


}