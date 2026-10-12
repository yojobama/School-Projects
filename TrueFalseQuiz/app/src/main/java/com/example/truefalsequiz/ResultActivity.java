package com.example.truefalsequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View mainView = findViewById(R.id.main);
        TextView tvResult = findViewById(R.id.tvResult);

        ((TextView)findViewById(R.id.tvExplenation)).setText(getIntent().getStringExtra("explenation"));

        if (getIntent().getBooleanExtra("isCorrect", false)) {
            tvResult.setText("Correct!!!");
            mainView.setBackgroundColor(getResources().getColor(R.color.result_correct));
        } else {
            tvResult.setText("Wrong!!!");
            mainView.setBackgroundColor(getResources().getColor(R.color.result_wrong));
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            finish();
        });

        Button btnNext = findViewById(R.id.btnNext);
        int stageValue = getIntent().getIntExtra("stage", 1);
        if (stageValue == 2) {
            btnNext.setVisibility(View.GONE);
        }
        btnNext.setOnClickListener(v -> {
            if (stageValue == 1) {
                startActivity(new Intent(this, SecondQuestionActivity.class));
            }
        });
    }
}