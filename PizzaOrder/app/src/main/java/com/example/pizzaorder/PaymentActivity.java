package com.example.pizzaorder;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentActivity extends AppCompatActivity {

    RadioGroup rgMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rgMethod = findViewById(R.id.rgMethod);

        TextView tvSummary = findViewById(R.id.tvSummary);
        String size = getIntent().getStringExtra("size");
        String crust = getIntent().getStringExtra("crust");
        tvSummary.setText("Your order: " + size + " pizza with " + crust + " crust");


        findViewById(R.id.btnDone).setOnClickListener(v -> {

            String paymentMethod;
            int checkedId = rgMethod.getCheckedRadioButtonId();
            if (checkedId == -1) {
                Toast.makeText(PaymentActivity.this, "Please select a payment method.", Toast.LENGTH_SHORT).show();
                return;
            } else if (checkedId == R.id.rbCard) {
                paymentMethod = "Card";
            } else {
                paymentMethod = "Cash";
            }

            Toast.makeText(PaymentActivity.this, "Paid with " + paymentMethod + "! Thank you for your order.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}