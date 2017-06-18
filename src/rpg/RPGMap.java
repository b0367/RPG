package rpg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

public class RPGMap {

    private static String[][] map = new String[8][8];
    private static int px = 0;
    private static int py = 0;
    private static int mapId = 1;
    private static int xp = 0;
    private static int lvl = 1;
    private static int lvlua = 500;
    private static String[][][] fmap = new String[8][8][50];

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("test.csv"), "utf-8"))) {
            writer.write(px + "x");
            writer.write(py + "y");
            writer.write(mapId + "mId");
        }
        RPGMap m = new RPGMap();
    }

    public RPGMap() throws IOException {
        setup("P", "O", "|");
    }

    public static void move(String ps, String ms, String wall) throws FileNotFoundException, IOException {
        for (int i = 0; i < fmap[mapId].length; i++) {
            for (int a = 0; a < fmap[mapId][i].length; a++) {
                if (!fmap[mapId][i][a].equals(wall)) {
                    map[i][a] = fmap[mapId][i][a];
                } else {
                    map[i][a] = wall;
                }

            }
        }
        map[py][px] = ps;

    }

    public static void setup(String ps, String ms, String wall) throws UnsupportedEncodingException, IOException {
        //fmap[0] = map;
        fmap[1] = new String[][]{
            {"O", "O", "O", "O", "O", "O", "O", "O"},
            {"O", "|", "O", "O", "O", "O", "|", "O"},
            {"O", "|", "O", "O", "O", "O", "|", "O"},
            {"O", "|", "O", "O", "O", "O", "|", "O"},
            {"O", "|", "O", "O", "O", "O", "|", "O"},
            {"O", "|", "O", "O", "O", "O", "|", "O"},
            {"O", "O", "O", "O", "O", "O", "O", "O"},
            {"O", "O", "O", "O", "O", "O", "O", "O"}
        };
        String everything;
        try (BufferedReader br = new BufferedReader(new FileReader("test.csv"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }
        px = Integer.parseInt(everything.substring(0, 1));
        py = Integer.parseInt(everything.substring(2, 3));
        //mapId = Integer.parseInt(everything.substring(4, 5));
        /*for (int i = 0; i < 8; i++) {
            String[] line = new String[8];
            for (int a = 0; a < 8; a++) {
                line[a] = ms;
            }
            map[i] = line;
        }*/
        move("P", "O", "|");
        draw();

        Scanner s = new Scanner(System.in);
        while (true) {
            String input = s.next();
            switch (input) {
                case "l":
                    if (px - 1 >= 0 && !(wall.equals(map[py][px - 1]))) {
                        px--;
                    } else {
                        System.out.println("You are blocked by a wall");
                    }
                    break;
                case "r":
                    if (px < map.length - 1 && !(wall.equals(map[py][px + 1]))) {
                        px++;
                    } else {
                        System.out.println("You are blocked by a wall");
                    }
                    break;
                case "u":
                    if (py - 1 >= 0 && !(wall.equals(map[py - 1][px]))) {
                        py--;
                    } else {
                        System.out.println("You are blocked by a wall");
                    }
                    break;
                case "d":
                    if (py + 1 < 8 && !(wall.equals(map[py + 1][px]))) {
                        py++;
                    } else {
                        System.out.println("You are blocked by a wall");
                    }
                    break;
                case "exit":
                    System.exit(999);
                    break;
                case "save":
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream("test.csv"), "utf-8"))) {
                        writer.write(px + "x");
                        writer.write(py + "y");
                        writer.write(mapId + "mId");
                        /*
                        writer.write("map:\nnr");
                        writer.flush();
                        for (String[] arr : map) {
                            String appender = "";
                            for (String sa : arr) {
                                writer.write(appender + sa);
                                appender = ",";
                            }
                            writer.write("nr");
                            writer.flush();
                        }
                        writer.close();
                         */
                    }
                    System.out.println("Saved.");
                    break;
                default:
                    System.out.println("Unknown command");
            }
            switch (px) {
                case 8:
                    px = 0;
                    break;
                case -1:
                    px = 7;
                    break;
            }
            switch (py) {
                case 8:
                    py = 0;
                    break;
                case -1:
                    py = 7;
                    break;
            }
            move(ps, ms, wall);
            draw();

        }
    }

    public static void draw() {
        for (String[] as : map) {
            for (String s : as) {
                System.out.print(s);
            }
            System.out.println();
        }
    }

    public static void cL() {
        if (xp > lvlua) {
            xp -= lvlua;
            lvlua *= 2;
            lvl++;
        }
    }

}
