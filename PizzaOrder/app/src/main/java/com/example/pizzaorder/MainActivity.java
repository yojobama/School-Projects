package com.example.pizzaorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgSize;

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

        rgSize = findViewById(R.id.rgSize);

        findViewById(R.id.btnOrder).setOnClickListener(v -> {
            int checkedId = rgSize.getCheckedRadioButtonId();

            String size;
            if (checkedId == R.id.rbSmall) {
                size = "small";
            } else if (checkedId == R.id.rbMedium) {
                size = "medium";
            } else {
                size = "family";
            }

            Toast.makeText(this, "Chose " + size, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SummaryActivity.class));
        });
    }
}