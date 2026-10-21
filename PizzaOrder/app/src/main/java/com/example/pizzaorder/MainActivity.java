package com.example.pizzaorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
    String crust = null;

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
        Button btnChooseCrust = findViewById(R.id.btnChooseCrust);
        btnChooseCrust.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Crust");
            builder.setPositiveButton("Thin", (dialog, which) -> {
                btnChooseCrust.setText("Thin");
                crust = "thin";
            });
            builder.setNegativeButton("Thick", (dialog, which) -> {
                btnChooseCrust.setText("Thick");
                crust = "thick";
            });
            builder.show();
        });

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

            if (crust == null) {
                Toast.makeText(MainActivity.this, "בחרו סוג בצק לפני ההזמנה", Toast.LENGTH_SHORT).show();
                return;
            }

            Order order = new Order(size, crust);
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class).putExtra("order", order);

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

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear Order");
            builder.setMessage("Are you sure you want to clear your order?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                rgSize.check(R.id.rbMedium);
                rgCrust.clearCheck();
                Toast.makeText(MainActivity.this, "Order cleared!", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });
    }
}