package ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameViewModel extends ViewModel {

    private final MutableLiveData<int[]> boxPositions = new MutableLiveData<>(new int[9]);
    private final MutableLiveData<Integer> playerTurn = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> totalSelectedBoxes = new MutableLiveData<>(0);
    private final MutableLiveData<String> resultMessage = new MutableLiveData<>();

    private final List<int[]> combinationList = new ArrayList<>();

    public GameViewModel() {
        initializeCombinationList();
    }

    private void initializeCombinationList() {
        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});
    }

    public LiveData<int[]> getBoxPositions() {
        return boxPositions;
    }

    public LiveData<Integer> getPlayerTurn() {
        return playerTurn;
    }

    public LiveData<String> getResultMessage() {
        return resultMessage;
    }

    public void performAction(int selectedBoxPosition, String playerOneName, String playerTwoName) {
        int[] positions = boxPositions.getValue();
        Integer turn = playerTurn.getValue();
        Integer selectedBoxes = totalSelectedBoxes.getValue();

        if (positions != null && turn != null && selectedBoxes != null) {
            if (positions[selectedBoxPosition] == 0) {
                positions[selectedBoxPosition] = turn;
                boxPositions.setValue(positions);

                if (checkResults(positions, turn)) {
                    String winnerName = (turn == 1) ? playerOneName : playerTwoName;
                    resultMessage.setValue(winnerName + " is a Winner!");
                } else if (selectedBoxes == 8) {
                    resultMessage.setValue("Match Draw");
                } else {
                    totalSelectedBoxes.setValue(selectedBoxes + 1);
                    playerTurn.setValue((turn == 1) ? 2 : 1);
                }
            }
        }
    }


    //Check the match result
    private boolean checkResults(int[] positions, int turn) {
        for (int[] combination : combinationList) {
            if (positions[combination[0]] == turn && positions[combination[1]] == turn && positions[combination[2]] == turn) {
                return true;
            }
        }
        return false;
    }

    //Restart match
    public void restartMatch() {
        boxPositions.setValue(new int[9]);
        playerTurn.setValue(1);
        totalSelectedBoxes.setValue(0);
        resultMessage.setValue(null);
    }
}
