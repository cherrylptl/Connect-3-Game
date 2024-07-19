package View;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect3game.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startGameBt.setOnClickListener(v -> {

            String getPlayerOneName = binding.playerOne.getText().toString();
            String getPlayerTwoName = binding.playerTwo.getText().toString();

            // Validate the player names
            if (validatePlayerNames(getPlayerOneName, getPlayerTwoName)) {
                Intent intent = new Intent(MainActivity.this, GameScreen.class);
                intent.putExtra("playerOne", getPlayerOneName);
                intent.putExtra("playerTwo", getPlayerTwoName);
                startActivity(intent);
            }
        });
    }

    private boolean validatePlayerNames(String playerOneName, String playerTwoName) {
        if (playerOneName.trim().isEmpty() || playerTwoName.trim().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter player name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}