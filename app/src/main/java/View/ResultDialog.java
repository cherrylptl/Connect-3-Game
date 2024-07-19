package View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.connect3game.R;
import com.example.connect3game.databinding.ActivityResultDialogBinding;
import java.util.Objects;

public class ResultDialog extends Dialog {

    private final String message;
    private final Runnable restartCallback;
    private final Context context;

    ActivityResultDialogBinding binding;

    public ResultDialog(@NonNull Context context, String message, Runnable restartCallback) {
        super(context);
        this.message = message;
        this.restartCallback = restartCallback;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.messageText.setText(message);

        //Show match result with image
        if (Objects.equals(message, "Match Draw")) {
            binding.resultImage.setImageResource(R.drawable.draw);
        } else {
            binding.resultImage.setImageResource(R.drawable.trophy);
        }

        binding.startAgainButton.setOnClickListener(view -> {
            if (restartCallback != null) {
                restartCallback.run();
            }
            dismiss();
        });

        binding.quitGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            dismiss();
        });
    }
}