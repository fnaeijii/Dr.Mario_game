package ir.ac.kntu.gamecontroller;

import ir.ac.kntu.GameLoop;
import ir.ac.kntu.gameobjects.BorderPill;
import ir.ac.kntu.random.AddRandomObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Handling implements Runnable {

    private long delay = 100L;

    public BorderPill[][] borderPills = new BorderPill[8][16];

    private int level = AddRandomObject.numberOfVirus;
    private int virus = 4 * (level);

    public int pillX;
    public int pillY;

    public boolean pillSide = true;
    public int pillCol;
    public int pillCol2;

    public boolean pillVisible = true;

    private int queueId = 0;

    public int ticksToWait = 0;
    public int commandId = 0;

    private int fallWait = 10;
    private int fallWaitFast = 1;

    private int ticksUntilFall = fallWait;

    public boolean fastFall = false;

    private int gameType = 0;

    public BorderPill[][] getBorderPills() {
        return borderPills;
    }

    public Handling() {
        while (virus > 0) {
            int i = (int) Math.floor(Math.random() * 8);
            int j = 15 - (int) Math.floor(Math.random() * 13);

            byte newCol = (byte) Math.floor(Math.random() * 3);
            if (borderPills[i][j] == null) {
                int xSum = 1;

                int x = i - 1;
                while (x >= 0 && i - x <= 3 && borderPills[x][j] != null) {
                    if (borderPills[x][j].col == newCol) {
                        xSum++;
                    } else {
                        break;
                    }

                    x--;
                }

                x = i + 1;
                while (x < 8 && x - i <= 3 && borderPills[x][j] != null) {
                    if (borderPills[x][j].col == newCol) {
                        xSum++;
                        if (xSum == 4) {
                            break;
                        }
                    } else {
                        break;
                    }

                    x++;
                }

                int ySum = 1;

                int y = j - 1;
                while (y >= 0 && j - y <= 3 && borderPills[i][y] != null) {
                    if (borderPills[i][y].col == newCol) {
                        ySum++;
                    } else {
                        break;
                    }

                    y--;
                }

                y = j + 1;
                while (y < 16 && y - j <= 3 && borderPills[i][y] != null) {
                    if (borderPills[i][y].col == newCol) {
                        ySum++;
                        if (ySum == 4) {
                            break;
                        }
                    } else {
                        break;
                    }

                    y++;
                }

                if (xSum != 4 && ySum != 4) {
                    borderPills[i][j] = new BorderPill(newCol, (byte) 6);
                    virus--;
                }
            }
        }

        newPill();
    }


    public void run() {

        long startTime = System.currentTimeMillis();
        virusAnim();
        if (ticksToWait == 0) {
            switch (commandId) {
                case 0 -> {
                    if (ticksUntilFall == 0) {
                        fall();
                    }
                    if (ticksUntilFall > 0) {
                        ticksUntilFall--;
                    }
                }
                case 1 -> checkClears();
                case 2 -> {
                    try {
                        clearCleared();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> collapse();
            }
        }

        if (ticksToWait > 0) {
            ticksToWait--;
        }

        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        if (elapsed < delay) {
            Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), e -> run()));
            tl.setCycleCount(Timeline.INDEFINITE);

        } else {
            delay = elapsed;
        }
    }


    private void virusAnim() {
        for (int i = 0; i < 8; i++) {
            for (int j = 3; j < 16; j++) {
                if (borderPills[i][j] != null) {
                    if (borderPills[i][j].type == 6 || borderPills[i][j].type == 7) {
                        borderPills[i][j].type = (byte) (13 - borderPills[i][j].type);
                    }
                }
            }
        }
    }

    public void moveRight() {
        if (borderPills[pillX][pillY] != null || borderPills[pillX + (pillSide ? 1 : 0)][pillY] != null) {
            return;
        }

        if (pillX < (pillSide ? 6 : 7)) {
            if (borderPills[pillX + 1][pillY] == null && borderPills[pillX + 1 + (pillSide ? 1 : 0)][pillY - (!pillSide && pillY > 0 ? 1 : 0)] == null) {
                pillX++;
            }
        }
    }

    public void moveLeft() {
        if (borderPills[pillX][pillY] != null || borderPills[pillX + (pillSide ? 1 : 0)][pillY] != null) {
            return;
        }

        if (pillX > 0) {
            if (borderPills[pillX - 1][pillY] == null && borderPills[pillX - 1][pillY - ((!pillSide && pillY > 0) ? 1 : 0)] == null) {
                pillX--;
            }
        }
    }

    public void rotate() {
        if (pillY < 0) {
            return;
        }

        if (pillSide && borderPills[pillX][pillY - (pillY > 0 ? 1 : 0)] != null) {
            return;
        }
        if (!pillSide && (borderPills[pillX + (pillX < 7 ? 1 : 0)][pillY] != null || pillX == 7)) {
            if (pillX > 0 && borderPills[pillX - (pillX > 0 ? 1 : 0)][pillY] == null) {
                pillX--;
            } else {
                return;
            }
        }

        if (pillSide) {
            pillCol += pillCol2;
            pillCol2 = (byte) (pillCol - pillCol2);
            pillCol -= pillCol2;
        }

        pillSide = !pillSide;
    }

    public void collapse() {
        boolean fell = false;

        for (int j = 14; j >= 0; j--) {
            for (int i = 0; i < 8; i++) {
                if (borderPills[i][j] != null) {
                    if (borderPills[i][j].type < 5) {
                        if (borderPills[i][j + 1] == null) {
                            if ((borderPills[i][j].type == 2 && borderPills[i + 1][j + 1] != null) || (borderPills[i][j].type == 0 && borderPills[i - 1][j] != null)) {
                                continue;
                            }

                            borderPills[i][j + 1] = new BorderPill(borderPills[i][j].col, borderPills[i][j].type);
                            borderPills[i][j] = null;
                            fell = true;
                        }
                    }
                }
            }
        }

        if (fell) {
            ticksToWait = 2;
            commandId = 3;
        } else {
            ticksToWait = 2;
            commandId = 1;
        }
    }

    public void fall() {
        ticksUntilFall = (fastFall) ? fallWaitFast : fallWait;
        if (borderPills[pillX][pillY] != null || borderPills[pillX + (pillX < 7 ? 1 : 0)][pillY] != null && pillY == 0) {
            placePill();
        } else if (pillY == 15) {
            placePill();
        } else if (borderPills[pillX][pillY + 1] == null && borderPills[pillX + (pillSide ? 1 : 0)][pillY + 1] == null) {
            pillY++;
        } else {
            placePill();
        }
    }

    private void placePill() {
        boolean ending = false;

        if (borderPills[pillX][pillY] != null || borderPills[pillX + (pillX < 7 ? 1 : 0)][pillY] != null && pillY == 0) {
            ending = true;
        }

        borderPills[pillX][pillY] = new BorderPill(pillCol, (byte) (pillSide ? 2 : 3));
        if (!(!pillSide && pillY == 0)) {
            borderPills[pillX + (pillSide ? 1 : 0)][pillY - (pillSide ? 0 : 1)] = new BorderPill(pillCol2, (byte) (pillSide ? 0 : 1));
        }

        if (ending) {
            gameOver();
            return;
        }

        pillVisible = false;
        ticksToWait = 1;
        commandId = 1;

    }

    public void gameOver() {
        GameLoop.end = true;
        System.exit(0);
    }

    public void gameWon() {
        GameLoop.end = true;
        System.exit(0);
    }

    private void clearCleared() throws FileNotFoundException {
        boolean cleared = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                if (borderPills[i][j] != null) {
                    if (borderPills[i][j].type == 5) {
                        borderPills[i][j] = null;
                        cleared = true;
                    }
                }
            }
        }

        if (cleared) {
            ticksToWait = 2;
            commandId = 3;
        } else {
            if (!checkForGameWin()) {
                commandId = 0;

                newPill();

            } else {
                gameWon();
            }
        }
    }

    public boolean checkForGameWin() {
        switch (gameType) {
            case 0:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 16; j++) {
                        if (borderPills[i][j] != null) {
                            return false;
                        }
                    }
                }
                return true;
            case 1:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 16; j++) {
                        if (borderPills[i][j] == null) {
                            continue;
                        }

                        if (borderPills[i][j].type == 6 || borderPills[i][j].type == 7) {
                            return false;
                        }
                    }
                }
                return true;
            default:
                return true;
        }
    }

    private void checkClears() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                if (borderPills[i][j] != null) {
                    if (borderPills[i][j].type != 5) {
                        checkClearsAt(i, j, borderPills[i][j].col);
                    }
                }
            }
        }

        ticksToWait = 1;
        commandId = 2;
    }

    private void checkClearsAt(int i, int j, int newCol) {
        int xSum = 1;

        int x = i - 1;
        while (x >= 0 && borderPills[x][j] != null) {
            if (borderPills[x][j].col == newCol) {
                xSum++;
            } else {
                break;
            }

            x--;
        }

        int x0 = x + 1;

        x = i + 1;
        while (x < 8 && borderPills[x][j] != null) {
            if (borderPills[x][j].col == newCol) {
                xSum++;
            } else {
                break;
            }

            x++;
        }

        x--;

        if (xSum >= 4) {
            for (int a = x0; a <= x; a++) {
                switch (borderPills[a][j].type) {
                    case 0 -> borderPills[a - 1][j].type = 4;
                    case 1 -> borderPills[a][j + 1].type = 4;
                    case 2 -> borderPills[a + 1][j].type = 4;
                    case 3 -> borderPills[a][j - 1].type = 4;
                }

                borderPills[a][j].type = 5;
            }
        }

        int ySum = 1;

        int y = j - 1;
        while (y >= 0 && borderPills[i][y] != null) {
            if (borderPills[i][y].col == newCol) {
                ySum++;
            } else {
                break;
            }

            y--;
        }

        int y0 = y + 1;

        y = j + 1;
        while (y < 16 && borderPills[i][y] != null) {
            if (borderPills[i][y].col == newCol) {
                ySum++;
            } else {
                break;
            }

            y++;
        }

        y--;

        if (ySum >= 4) {
            for (int a = y0; a <= y; a++) {
                switch (borderPills[i][a].type) {
                    case 0 -> borderPills[i - 1][a].type = 4;
                    case 1 -> borderPills[i][a + 1].type = 4;
                    case 2 -> borderPills[i + 1][a].type = 4;
                    case 3 -> {
                        if (a == 0) {
                            break;
                        }
                        borderPills[i][a - 1].type = 4;
                    }
                }

                borderPills[i][a].type = 5;
            }
        }
    }

    private void newPill() {
        pillX = 4;
        pillY = 2;
        pillSide = true;
        pillVisible = true;
        genPillCol();
        if (!fastFall) {
            ticksUntilFall = fallWait;
        }
    }

    private void genPillCol() {
        boolean[] virusCols = new boolean[3];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                if (borderPills[i][j] == null) {
                    continue;
                }

                switch (gameType) {
                    case 0:
                        virusCols[borderPills[i][j].col] = true;
                        break;
                    case 1:
                        if (borderPills[i][j].type == 6 || borderPills[i][j].type == 7) {
                            virusCols[borderPills[i][j].col] = true;
                        }
                        break;
                }
            }
        }

        do {
            pillCol = (byte) (Math.random() * 3);
        } while (!virusCols[pillCol]);

        do {
            pillCol2 = (byte) (Math.random() * 3);
        } while (!virusCols[pillCol2]);
    }
}
