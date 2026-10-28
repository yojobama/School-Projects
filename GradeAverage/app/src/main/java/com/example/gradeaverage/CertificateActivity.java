package com.example.gradeaverage;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CertificateActivity extends AppCompatActivity {

    TextView m_TextViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        m_TextViewResults = findViewById(R.id.tvResult);

        String name = getIntent().getStringExtra("name");
        double average = getIntent().getDoubleExtra("average", -1.0);
        double averageWithBonus = getIntent().getDoubleExtra("averageWithBonus", -1.0);
        boolean includeSubject4 = getIntent().getBooleanExtra("includeSubject4", false);
        String subject4 = getIntent().getStringExtra("subject4");

        String resultText = name + ": Average=" + String.format("%.1f", average) + ", Average With Bonus=" + String.format("%.1f", averageWithBonus);
        if (includeSubject4) {
            resultText += ", Also Includes: " + subject4;
        }
        m_TextViewResults.setText(resultText);
    }
}