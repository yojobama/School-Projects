package com.example.truefalsequiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    boolean correctAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String explenation = "the correct answer is \"שקר\" because Windows has a proprietary OS kernel (unlike linux). software has to be free (free as in freedom!)";

        Intent baseIntent = new Intent(this, ResultActivity.class)
                .putExtra("stage", 1);

        findViewById(R.id.btnTrue).setOnClickListener(v -> {
            startActivity(baseIntent
                    .putExtra("isCorrect", correctAnswer == true)
                    .putExtra("explenation", explenation)
            );
        });

        findViewById(R.id.btnFalse).setOnClickListener(v -> {
            startActivity(baseIntent
                    .putExtra("isCorrect", correctAnswer == false)
                    .putExtra("explenation", explenation)
            );
        });
    }
}