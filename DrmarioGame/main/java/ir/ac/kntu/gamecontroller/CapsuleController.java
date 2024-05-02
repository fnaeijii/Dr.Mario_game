package ir.ac.kntu.gamecontroller;

import com.sun.javafx.scene.traversal.Direction;
import ir.ac.kntu.constants.Condition;
import ir.ac.kntu.gameobjects.Capsule;
import javafx.scene.input.KeyCode;

import java.util.List;

public class CapsuleController {
    private static CapsuleController instance = new CapsuleController();

    public static CapsuleController getInstance() {
        return instance;
    }

    private CapsuleController() {
    }


    public void handleCapsuleMovements(Capsule capsule) {
        List keyboardInputs = EventHandler.getInputList();

        if (keyboardInputs.contains(KeyCode.LEFT)) {
            capsule.move(Capsule.getSpeed(), Direction.LEFT);
        }
        if (keyboardInputs.contains(KeyCode.RIGHT)) {
            capsule.move(Capsule.getSpeed(), Direction.RIGHT);
        }
        if (keyboardInputs.contains(KeyCode.X)) {
            capsule.setCondition(Condition.HORIZONTAL);
            if (keyboardInputs.contains(KeyCode.Z)) {
                capsule.setCondition(Condition.VERTICAL);
            }
        }
    }

}
