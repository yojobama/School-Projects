package com.example.truefalsequiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondQuestionActivity extends AppCompatActivity {

    static boolean correctAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String explenation = "the correct answer is \"שקר\" because no one ever bothered to support ROCm, it is a lie, even AMD doesn't, and ZLUDA doesn't work...";

        Intent baseIntent = new Intent(this, ResultActivity.class)
                .putExtra("explenation", explenation)
                .putExtra("stage", 2);

        findViewById(R.id.btnTrue).setOnClickListener(v -> {
            startActivity(baseIntent.putExtra("isCorrect", correctAnswer == true));
        });

        findViewById(R.id.btnFalse).setOnClickListener(v -> {
            startActivity(baseIntent.putExtra("isCorrect", correctAnswer == false));
        });
    }
}