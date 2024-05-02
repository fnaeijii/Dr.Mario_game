package ir.ac.kntu.random;

import ir.ac.kntu.constants.ColorPills;
import ir.ac.kntu.gameobjects.Capsule;
import ir.ac.kntu.gameobjects.Cell;
import ir.ac.kntu.gameobjects.SmallVirus;


import java.security.SecureRandom;
import java.util.*;

public class AddRandomObject {
    public static int numberOfVirus;
    private static final SecureRandom GENERATOR = new SecureRandom();


    public static List<SmallVirus> randomVirus() {
        ArrayList<SmallVirus> virus = new ArrayList<>(4);
        Cell cell1 = randomCell();
        virus.add(new SmallVirus(cell1.getRowIndex(), cell1.getColumnIndex() * RandomHelper.nextInt(10), ColorPills.RED));
        Cell cell2 = randomCell();
        virus.add(new SmallVirus(cell2.getRowIndex(), cell2.getColumnIndex() * RandomHelper.nextInt(15), ColorPills.BLUE));
        Cell cell3 = randomCell();
        virus.add(new SmallVirus(cell3.getRowIndex(), cell3.getColumnIndex() * RandomHelper.nextInt(5), ColorPills.YELLOW));
        Cell cell = randomCell();
        int random = GENERATOR.nextInt(3);
        if (random == 0) {
            virus.add(new SmallVirus(cell.getRowIndex(), cell.getColumnIndex(), ColorPills.BLUE));
        } else if (random == 1) {
            virus.add(new SmallVirus(cell.getRowIndex(), cell.getColumnIndex(), ColorPills.RED));
        } else {
            virus.add(new SmallVirus(cell.getRowIndex(), cell.getColumnIndex(), ColorPills.YELLOW));
        }
        return virus;
    }

    public static Capsule randomPill() {
        int random1 = GENERATOR.nextInt(3);
        int random2 = GENERATOR.nextInt(3);
        if (random1 == 0) {
            if (random2 == 0) {
                return new Capsule(ColorPills.BLUE, ColorPills.RED, 200, 300);
            } else if (random2 == 1) {
                return new Capsule(ColorPills.BLUE, ColorPills.YELLOW, 200, 300);
            } else {
                return new Capsule(ColorPills.BLUE, ColorPills.BLUE, 200, 300);

            }
        } else if (random1 == 1) {
            if (random2 == 0) {
                return new Capsule(ColorPills.RED, ColorPills.RED, 200, 300);
            } else if (random2 == 1) {
                return new Capsule(ColorPills.RED, ColorPills.YELLOW, 200, 300);
            } else {
                return new Capsule(ColorPills.RED, ColorPills.BLUE, 200, 300);

            }
        } else {
            if (random2 == 0) {
                return new Capsule(ColorPills.YELLOW, ColorPills.RED, 200, 300);
            } else if (random2 == 1) {
                return new Capsule(ColorPills.YELLOW, ColorPills.YELLOW, 200, 300);
            } else {
                return new Capsule(ColorPills.YELLOW, ColorPills.BLUE, 200, 300);

            }
        }
    }

    public static Cell randomCell() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Cell cell = null;
            for (int j = 0; j < 16; j++)
                cell = new Cell(i, j);
            cells.add(cell);
        }
        return cells.get(GENERATOR.nextInt(cells.size()));
    }

}
