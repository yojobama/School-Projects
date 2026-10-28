package com.example.gradeaverage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button m_ButtonCalculate, m_ButtonClear;
    TextView m_TextViewResult;
    EditText m_EditTextName, m_EditTextGrade1, m_EditTextGrade2, m_EditTextGrade3, m_EditTextGrade4, m_EditTextUnits1, m_EditTextUnits2, m_EditTextUnits3, m_EditTextUnits4, m_EditTextSubject4;

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

        m_ButtonCalculate = findViewById(R.id.btnCalc);
        m_ButtonClear = findViewById(R.id.btnClear);

        m_TextViewResult = findViewById(R.id.tvAverage);

        m_EditTextName = findViewById(R.id.etName);
        m_EditTextGrade1 = findViewById(R.id.etGrade1);
        m_EditTextGrade2 = findViewById(R.id.etGrade2);
        m_EditTextGrade3 = findViewById(R.id.etGrade3);
        m_EditTextGrade4 = findViewById(R.id.etGrade4);

        m_EditTextUnits1 = findViewById(R.id.etUnits1);
        m_EditTextUnits2 = findViewById(R.id.etUnits2);
        m_EditTextUnits3 = findViewById(R.id.etUnits3);
        m_EditTextUnits4 = findViewById(R.id.etUnits4);

        m_EditTextSubject4 = findViewById(R.id.etSubject4);

        m_ButtonCalculate.setOnClickListener(v -> {
            int g1 = readNumber(m_EditTextGrade1, 0, 100);
            if (g1 == -1) return;
            int u1 = readNumber(m_EditTextUnits1, 1, 5);
            if (u1 == -1) return;

            int g2 = readNumber(m_EditTextGrade2, 0, 100);
            if (g2 == -1) return;
            int u2 = readNumber(m_EditTextUnits2, 1, 5);
            if (u2 == -1) return;

            int g3 = readNumber(m_EditTextGrade3, 0, 100);
            if (g3 == -1) return;
            int u3 = readNumber(m_EditTextUnits3, 1, 5);
            if (u3 == -1) return;



            int g4 = 0, u4 = 0;

            String subject4 = m_EditTextSubject4.getText().toString();

            boolean includeSubject4 = !subject4.isEmpty() ||
                    !m_EditTextGrade4.getText().toString().isEmpty() ||
                    !m_EditTextUnits4.getText().toString().isEmpty();

            if (includeSubject4) {
                if (subject4.isEmpty()) {
                    m_EditTextSubject4.setError("Fill In A Bloody Subject Name!!!");
                    m_EditTextSubject4.requestFocus();
                    return;
                }

                g4 = readNumber(m_EditTextGrade4, 0, 100);
                if (g4 == -1) return;
                u4 = readNumber(m_EditTextUnits4, 1, 5);
                if (u4 == -1) return;
            }

            String name = m_EditTextName.getText().toString();
            if (name.isEmpty()) {
                m_EditTextName.setError("Fill In A Bloody Name!!!");
                m_EditTextName.requestFocus();
                return;
            }

            int sumBonus = gradeWithBonus(g1, u1) * u1 + gradeWithBonus(g2, u2) * u2 + gradeWithBonus(g3, u3) * u3 + gradeWithBonus(g4, u4) * u4;
            int sum = g1 * u1 + g2 * u2 + g3 * u3 + g4 * u4;
            int totalUnits = u1 + u2 + u3 + u4;
            double average = (double) sum / totalUnits;
            double averageWithBonus = (double) sumBonus / totalUnits;

            m_TextViewResult.setText("Bagroot Average: " + String.format("%.1f", average));

            startActivity(new Intent(this, CertificateActivity.class)
                .putExtra("name", name)
                .putExtra("average", average)
                .putExtra("averageWithBonus", averageWithBonus)
                .putExtra("subject4", subject4)
                .putExtra("includeSubject4", includeSubject4));
        });

        m_ButtonClear.setOnClickListener(v -> {
            m_EditTextGrade1.setText("");
            m_EditTextGrade2.setText("");
            m_EditTextGrade3.setText("");
            m_EditTextUnits1.setText("");
            m_EditTextUnits2.setText("");
            m_EditTextUnits3.setText("");
            m_EditTextName.setText("");

            m_TextViewResult.setText("Average Will Appear Here");
            m_EditTextGrade1.requestFocus();
        });
    }

    int gradeWithBonus(int grade, int units) {
        if (units == 5) {
            return grade + 20;
        } else if (units == 4) {
            return grade + 10;
        } else {
            return grade;
        }
    }

    int readNumber(EditText editText, int min, int max) {
        String text = editText.getText().toString();

        if (text.isEmpty()) {
            editText.setError("Fill In A Bloody Number!!!");
            editText.requestFocus();
            return -1;
        }

        int value = Integer.parseInt(text);

        if (value < min || value > max) {
            editText.setError("Has To Be Between " + min + " And " + max);
            editText.requestFocus();
            return -1;
        }

        return value;
    }
}