package ir.ac.kntu.gamecontroller;

import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    private static EventHandler instance = new EventHandler();

    public static EventHandler getInstance() {
        return instance;
    }

    private EventHandler() {
    }

    private static ArrayList<KeyCode> inputList = new ArrayList<KeyCode>();

    public void attachEventHandlers(Scene s) {
        s.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (EventHandler.inputList.contains(code)) {
                EventHandler.inputList.remove(code);
            }
        });
        s.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (!EventHandler.inputList.contains(code)) {
                EventHandler.inputList.add(code);
            }
        });
    }

    public static List getInputList() {
        return new ArrayList(inputList);
    }
}
