package ir.ac.kntu.save;

import ir.ac.kntu.gameobjects.PlayerInfo;

import java.io.*;
import java.util.ArrayList;

public class BinaryPlayerDAO implements PlayerDAO {
    @Override
    public ArrayList<PlayerInfo> getAllPlayers() {
        File file = new File("src/main/resources/Player.txt");
        ArrayList<PlayerInfo> players = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while (true) {
                try {
                    players.add((PlayerInfo) input.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No Such File found!");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Nothing exists in file!");
        }
        return players;
    }

    @Override
    public void saveAllPlayers(ArrayList<PlayerInfo> list) {
        ArrayList<PlayerInfo> players = getAllPlayers();
        System.out.println(players);
        System.out.println(list);
        for (int i = 0; i < players.size(); i++) {
            for (PlayerInfo playerInfo : list) {
                if (playerInfo.equals(players.get(i))) {
                    players.remove(i);
                    players.add(playerInfo);
                } else {
                    if (!players.contains(playerInfo)) {
                        players.add(playerInfo);
                    }
                }
            }
        }
        if (players.size() == 0) {
            players.addAll(list);
        }
        System.out.println(players);
        File file = new File("src/main/resources/Player.txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false);
             ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            for (PlayerInfo player : players) {
                output.writeObject(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
