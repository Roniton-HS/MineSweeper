package Worlds;

import Input.KeyHandler;
import Input.MouseHandler;
import Main.Game;

import java.awt.*;
import java.util.Random;

public class World1 extends Worlds {
    MouseHandler mouseHandler = new MouseHandler();
    int[][] map = new int[10][10];
    int[][] clicked = new int[10][10];

    /**
     * Constructor
     */
    public World1(Game game) {
        super(game);
        initBombs();
        initNum();
    }

    @Override
    public void tick() {
        int clickX = mouseHandler.getClickX() / 50;
        int clickY = mouseHandler.getClickY() / 50;
        if (clickX < clicked.length && clickY < clicked.length && clicked[clickX][clickY] != 1) {

            if (game.getKeyHandler().space && clicked[clickX][clickY] != 2) {
                clicked[clickX][clickY] = 2;
            } else if(clicked[clickX][clickY] != 2) {
                if (map[clickX][clickY] == 8) {
                    System.out.println("BOMB");
                }
                if (map[clickX][clickY] == 0) {
                    reveal(clickX, clickY);
                } else {
                    clicked[clickX][clickY] = 1;
                }
            }else if(game.getKeyHandler().space && clicked[clickX][clickY] == 2){
                clicked[clickX][clickY] = 0;
            }
            mouseHandler.reset();

        }
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
        for (int i = 0; i < 7; i++) {
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
        int size = 50;
        renderNum(g);
        for (int i = 0; i < clicked.length; i++) {
            for (int j = 0; j < clicked.length; j++) {
                if (clicked[j][i] == 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * size, i * size, size, size);
                } else if (clicked[j][i] == 2) {
                    g.setColor(Color.red);
                    g.fillRect(j * size, i * size, size, size);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * size, i * size, size, size);
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
                    g.drawString("x", xOff + j * 50, yOff + i * 50);
                }else if(entry == 0){
                    continue;
                } else {
                    String value = String.valueOf(entry);
                    g.drawString(value, xOff + j * 50, yOff + i * 50);
                }
            }
        }
    }
}
