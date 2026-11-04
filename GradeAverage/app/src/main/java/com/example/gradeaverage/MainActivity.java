package com.example.gradeaverage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button m_ButtonCalculate, m_ButtonClear, m_AddSubject;
    TextView m_TextViewResult;
    EditText m_EditTextName;

    List<Subject> m_SubjectsList = new ArrayList<>();
    LinearLayout m_SubjectsLayout;

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

        m_SubjectsLayout = findViewById(R.id.subjectsLayout);

        Subject[] m_BaseSubjects = {
                new Subject(MainActivity.this, "Maths"),
                new Subject(MainActivity.this, "English"),
                new Subject(MainActivity.this, "CS"),
                new Subject(MainActivity.this)
        };

        for (Subject subject : m_BaseSubjects) {
            m_SubjectsList.add(subject);
            subject.addToLayout(m_SubjectsLayout);
        }

        m_ButtonCalculate = findViewById(R.id.btnCalc);
        m_ButtonClear = findViewById(R.id.btnClear);
        m_AddSubject = findViewById(R.id.btnAddSubject);

        m_TextViewResult = findViewById(R.id.tvAverage);

        m_EditTextName = findViewById(R.id.etName);

        m_AddSubject.setOnClickListener(v -> {
            Subject subject = new Subject(this);
            m_SubjectsList.add(subject);
            subject.addToLayout(m_SubjectsLayout);
        });

        m_ButtonCalculate.setOnClickListener(v -> {
            String name = m_EditTextName.getText().toString();
            if (name.isEmpty()) {
                m_EditTextName.setError("Fill In A Bloody Name!!!");
                m_EditTextName.requestFocus();
                return;
            }

            int sum = 0, sumWithBonus = 0, units = 0;

            for (Subject subject : m_SubjectsList) {
                if (includeSubject(subject)) {
                    if (subject.getGrade() == -1 || subject.getUnits() == -1) return;
                    if (subject.getSubject().isEmpty()) {
                        subject.getSubjectNameEditText().setError("Fill In A Bloody Name!!!");
                        subject.getSubjectNameEditText().requestFocus();
                    }

                    units += subject.getUnits();
                    sum += subject.getGrade();
                    sumWithBonus += gradeWithBonus(subject.getGrade(), subject.getUnits());
                }
            }

            double average = (double) sum / units;
            double averageWithBonus = (double) sumWithBonus / units;

            m_TextViewResult.setText("Bagroot Average: " + String.format("%.1f", average));

            String[] extraSubjects = getExtraSubjects();

            startActivity(new Intent(this, CertificateActivity.class)
                .putExtra("name", name)
                .putExtra("average", average)
                .putExtra("averageWithBonus", averageWithBonus)
                .putExtra("extraSubjects", extraSubjects)
            );
        });

        m_ButtonClear.setOnClickListener(v -> {
            for (Subject subject : m_SubjectsList) {
                subject.clear();
            }

            m_TextViewResult.setText("Average Will Appear Here");
            m_SubjectsList.get(0).getGradeEditText().requestFocus();
        });
    }

    boolean includeSubject(Subject subject) {
        return !subject.getSubject().isEmpty() ||
                subject.getGradeEditText().getText().toString().isEmpty() ||
                subject.getUnitsEditText().getText().toString().isEmpty();
    }

    String[] getExtraSubjects() {
        List<String> extraSubjects = new ArrayList<>();
        for (int i = 2; i < m_SubjectsList.size(); i++) {
            Subject subject = m_SubjectsList.get(i);
            if (includeSubject(subject)) {
                extraSubjects.add(subject.getSubject());
            }
        }
        String[] extraSubjectsArr = new String[extraSubjects.size()];
        for (int i = 0; i < extraSubjectsArr.length; i++) {
            extraSubjectsArr[i] = extraSubjects.get(i);
        }
        return extraSubjectsArr;
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
}