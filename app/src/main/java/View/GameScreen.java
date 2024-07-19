package View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.connect3game.R;
import com.example.connect3game.databinding.ActivityGameScreenBinding;

import ViewModel.GameViewModel;

public class GameScreen extends AppCompatActivity {
    private ActivityGameScreenBinding binding;
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        initializePlayerNames();
        initializeGameBoard();
        observeViewModel();
    }

    private void initializePlayerNames() {
        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");

        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);
    }

    private void initializeGameBoard() {
        ImageView[] board = new ImageView[]{
                binding.image1, binding.image2, binding.image3,
                binding.image4, binding.image5, binding.image6,
                binding.image7, binding.image8, binding.image9
        };

        for (int i = 0; i < board.length; i++) {
            final int position = i;
            board[i].setOnClickListener(view -> {
                if (isBoxSelectable(position)) {
                    performAction((ImageView) view, position);
                }
            });
        }
    }

    private void performAction(ImageView imageView, int selectedBoxPosition) {
        int resId = (viewModel.getPlayerTurn().getValue() == 1) ? R.mipmap.xplayer : R.mipmap.oplayer;

        // Get the coordinates of the clicked ImageView
        int[] location = new int[2];
        imageView.getLocationInWindow(location);
        int targetX = location[0];
        int targetY = location[1];

        // Animate the floating image from top to the clicked position
        animateImageFromTop(resId, targetX, targetY);

        // Update the ViewModel with the selected position
        viewModel.performAction(selectedBoxPosition,
                binding.playerOneName.getText().toString(),
                binding.playerTwoName.getText().toString());
    }

    private void animateImageFromTop(int resId, int targetX, int targetY) {
        // Create a new ImageView to float from the top
        ImageView floatingImage = new ImageView(this);
        floatingImage.setImageResource(resId);
        floatingImage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        floatingImage.setX(targetX);
        floatingImage.setY(-floatingImage.getHeight());

        // Add the floating image to the root view
        binding.getRoot().addView(floatingImage);

        // Animate the floating image to its target position
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(floatingImage, "translationX", targetX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(floatingImage, "translationY", targetY);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(500);
        animatorSet.start();

        // Remove the floating image after animation ends
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.getRoot().removeView(floatingImage);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getBoxPositions().observe(this, this::updateGameBoard);

        viewModel.getPlayerTurn().observe(this, this::updatePlayerTurn);

        viewModel.getResultMessage().observe(this, message -> {
            if (message != null) {
                showResultDialog(message);
            }
        });
    }

    private void updateGameBoard(int[] positions) {
        ImageView[] board = new ImageView[]{
                binding.image1, binding.image2, binding.image3,
                binding.image4, binding.image5, binding.image6,
                binding.image7, binding.image8, binding.image9
        };

        for (int i = 0; i < board.length; i++) {
            if (positions[i] == 1) {
                board[i].setImageResource(R.mipmap.xplayer);
            } else if (positions[i] == 2) {
                board[i].setImageResource(R.mipmap.oplayer);
            } else {
                board[i].setImageResource(R.drawable.boardtile);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updatePlayerTurn(int turn) {
        // Update the background color for the player layouts
        if (turn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.green_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box);
            binding.playerTurnText.setText(binding.playerOneName.getText().toString() + "'sTurn");
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.green_border);
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box);
            binding.playerTurnText.setText(binding.playerTwoName.getText().toString() + "'s Turn");
        }
    }

    private boolean isBoxSelectable(int boxPosition) {
        int[] positions = viewModel.getBoxPositions().getValue();
        return positions != null && positions[boxPosition] == 0;
    }

    private void showResultDialog(String message) {
        ResultDialog resultDialog = new ResultDialog(GameScreen.this, message, this::restartMatch);
        resultDialog.setCancelable(false);
        resultDialog.show();
    }

    private void restartMatch() {
        viewModel.restartMatch();
    }
}