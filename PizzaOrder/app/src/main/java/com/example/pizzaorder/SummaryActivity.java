package com.example.pizzaorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvSummary = findViewById(R.id.tvSummary);
        String size = getIntent().getStringExtra("size");
        String crust = getIntent().getStringExtra("crust");
        String price = String.valueOf(getIntent().getIntExtra("price", 0));
        tvSummary.setText("Your order: " + size + " pizza with " + crust + " crust. Price: $" + price);

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.btnPay).setOnClickListener(v -> {
            startActivity(new Intent(SummaryActivity.this, PaymentActivity.class).putExtra("size", size).putExtra("crust", crust));
        });
    }
}