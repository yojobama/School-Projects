package com.example.pizzaorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgSize, rgCrust;

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
        rgCrust = findViewById(R.id.rgCrust);

        findViewById(R.id.btnOrder).setOnClickListener(v -> {
            int checkedId = rgSize.getCheckedRadioButtonId();
            int price = 0;

            String size;
            if (checkedId == R.id.rbSmall) {
                size = "small";
                price += 25;
            } else if (checkedId == R.id.rbMedium) {
                size = "medium";
                price += 35;
            } else {
                size = "family";
                price += 45;
            }

            checkedId = rgCrust.getCheckedRadioButtonId();

            String crust;
            if (checkedId == -1) {
                Toast.makeText(MainActivity.this, "בחרו סוג בצק לפני ההזמנה", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkedId == R.id.rbThin) {
                crust = "thin";
            } else {
                crust = "thick";
                price += 5;
            }

            Intent intent = new Intent(MainActivity.this, SummaryActivity.class).putExtra("size", size).putExtra("crust", crust).putExtra("price", price);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Order Confirmation");
            builder.setMessage("Order a " + size + " pizza with " + crust + " crust?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Toast.makeText(MainActivity.this, "Order placed!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });
    }
}