package Worlds;

import Input.MouseHandler;
import Main.Game;

import java.awt.*;
import java.util.Random;

public class MineSweeper extends Worlds {
    int blockSize;
    int mapSize;
    MouseHandler mouseHandler = new MouseHandler();
    int[][] map;
    int[][] clicked;

    /**
     * leere Felder müssen diagonal aufdecken
     */
    /**
     * Constructor
     */
    public MineSweeper(Game game, int blockSize, int mapSize) {
        super(game);
        this.blockSize = blockSize;
        this.mapSize = mapSize;
        map = new int[mapSize][mapSize];
        clicked = new int[mapSize][mapSize];


        initBombs();
        initNum();
    }



    @Override
    public void tick() {
        input();
    }

    private void input() {
        int clickX = mouseHandler.getClickX() / blockSize;
        int clickY = mouseHandler.getClickY() / blockSize;
        if (clickX < clicked.length && clickY < clicked.length && clicked[clickX][clickY] != 1) {
            if (clicked[clickX][clickY] != 2) {
                if (game.getKeyHandler().space) { //mark
                    clicked[clickX][clickY] = 2;
                } else if (map[clickX][clickY] == 8) { //clicked bomb
                    clicked[clickX][clickY] = 1;
                    bomb();
                } else if (map[clickX][clickY] == 0) { //clicked empty
                    reveal(clickX, clickY);
                } else { //clicked num
                    clicked[clickX][clickY] = 1;
                }
            } else {
                if (game.getKeyHandler().space) { //remove mark
                    clicked[clickX][clickY] = 0;
                }
            }
        }
        mouseHandler.reset();
    }

    private void bomb() {
        System.out.println("BOMB");
    }

    private void reveal(int x, int y) {
        if (map[x][y] != 0 && clicked[x][y] == 0) {
            clicked[x][y] = 1;
            return;
        }
        if (map[x][y] == 0 && clicked[x][y] == 0) {
            clicked[x][y] = 1;
            if (x < map.length - 1) {
                reveal(x + 1, y);
            }
            if (y < map.length - 1) {
                reveal(x, y + 1);
            }
            if (x > 0) {
                reveal(x - 1, y);
            }
            if (y > 0) {
                reveal(x, y - 1);
            }
        }
    }


    private void initBombs() {
        Random r = new Random();
        int ranX, ranY;
        int bombs = mapSize * mapSize / 7;
        for (int i = 0; i < bombs; i++) {
            ranX = r.nextInt(map.length);
            ranY = r.nextInt(map.length);
            if (map[ranX][ranY] != 8) {
                map[ranX][ranY] = 8;
            } else {
                i--;
            }
        }
    }

    private void initNum() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] == 8) {
                    continue;
                }
                int bombsNear = 0;
                if (j + 1 < map.length && map[j + 1][i] == 8) {
                    bombsNear++;
                }
                if (j - 1 >= 0 && map[j - 1][i] == 8) {
                    bombsNear++;
                }
                if (i + 1 < map.length && map[j][i + 1] == 8) {
                    bombsNear++;
                }
                if (i - 1 >= 0 && map[j][i - 1] == 8) {
                    bombsNear++;
                }
                if (j + 1 < map.length && i + 1 < map.length && map[j + 1][i + 1] == 8) {
                    bombsNear++;
                }
                if (j - 1 >= 0 && i - 1 >= 0 && map[j - 1][i - 1] == 8) {
                    bombsNear++;
                }
                if (j + 1 < map.length && i - 1 >= 0 && map[j + 1][i - 1] == 8) {
                    bombsNear++;
                }
                if (j - 1 >= 0 && i + 1 < map.length && map[j - 1][i + 1] == 8) {
                    bombsNear++;
                }

                map[j][i] = bombsNear;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        renderNum(g);
        for (int i = 0; i < clicked.length; i++) {
            for (int j = 0; j < clicked.length; j++) {
                if (clicked[j][i] == 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                } else if (clicked[j][i] == 2) {
                    g.setColor(Color.red);
                    g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * blockSize, i * blockSize, blockSize, blockSize);
            }
        }

    }

    private void renderNum(Graphics g) {
        int xOff = 15;
        int yOff = 40;
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int entry = map[j][i];
                if (entry == 8) {
                    g.drawString("x", xOff + j * blockSize, yOff + i * blockSize);
                } else if (entry == 0) {
                    continue;
                } else {
                    String value = String.valueOf(entry);
                    g.drawString(value, xOff + j * blockSize, yOff + i * blockSize);
                }
            }
        }
    }
}
