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
    String[] crusts = {"Thin", "Thick"};
    int checkedCrustIndex = -1;

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
            builder.setSingleChoiceItems(crusts, checkedCrustIndex, (dialog, which) -> {
                checkedCrustIndex = which;
                if (which == 0) {
                    btnChooseCrust.setText(crusts[0]);
                } else {
                    btnChooseCrust.setText(crusts[1]);
                }
            });
            builder.setPositiveButton("OK", null);
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

            if (checkedCrustIndex == -1) {
                Toast.makeText(MainActivity.this, "בחרו סוג בצק לפני ההזמנה", Toast.LENGTH_SHORT).show();
                return;
            }
            String crust = crusts[checkedCrustIndex].toLowerCase();

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
                checkedCrustIndex = -1;
                btnChooseCrust.setText("Choose Crust");
                Toast.makeText(MainActivity.this, "Order cleared!", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });
    }
}