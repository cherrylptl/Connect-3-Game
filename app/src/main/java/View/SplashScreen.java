package View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.connect3game.databinding.ActivitySplashScreenBinding;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Delay for 3 seconds
        new Handler().postDelayed(this::navigateToMainActivity, 3000);
    }

    //Navigate to the MainActivity
    public void navigateToMainActivity() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}