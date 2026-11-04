package com.example.gradeaverage;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

class Subject {
    protected EditText m_Units, m_Grade;
    protected TextView m_SubjectName;

    public EditText getUnitsEditText() {
        return m_Units;
    }
    public EditText getGradeEditText() {
        return m_Grade;
    }
    public TextView getSubjectNameEditText() {
        return m_SubjectName;
    }

    protected EditText createEditText(Context context, int weight, int inputType) {
        EditText editText = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                weight
        );
        params.gravity = Gravity.CENTER_HORIZONTAL;
        editText.setLayoutParams(params);
        editText.setInputType(inputType);
        return editText;
    }

    protected TextView createTextView(Context context, int weight, String text) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                weight
        );
        params.gravity = Gravity.CENTER_HORIZONTAL;
        textView.setLayoutParams(params);
        textView.setText(text);
        return textView;
    }

    public Subject(Context context, String subject) {
        m_Units = createEditText(context, 1, InputType.TYPE_CLASS_NUMBER);
        m_Grade = createEditText(context, 1, InputType.TYPE_CLASS_NUMBER);
        m_SubjectName = createTextView(context, 2, subject);
    }

    public Subject(Context context) {
        m_Units = createEditText(context, 1, InputType.TYPE_CLASS_NUMBER);
        m_Grade = createEditText(context, 1, InputType.TYPE_CLASS_NUMBER);
        m_SubjectName = createEditText(context, 2, InputType.TYPE_CLASS_TEXT);
    }

    public void addToLayout(LinearLayout layout) {
        LinearLayout subjectLayout = new LinearLayout(layout.getContext());
        subjectLayout.addView(m_SubjectName);
        subjectLayout.addView(m_Units);
        subjectLayout.addView(m_Grade);
        layout.addView(subjectLayout);
    }

    public String getSubject() {
        return m_SubjectName.getText().toString();
    }

    public int getGrade() {
        String text = m_Grade.getText().toString();
        if (text.isEmpty()) {
            m_Grade.setError("Fill In A Bloody Number!!!");
            m_Grade.requestFocus();
            return -1;
        }

        int grade = Integer.parseInt(text);

        if (grade < 0 || grade > 100) {
            m_Grade.setError("Has To Be Between " + 0 + " And " + 100);
            m_Grade.requestFocus();
            return -1;
        }

        return grade;
    }

    public int getUnits() {
        String text = m_Grade.getText().toString();
        if (text.isEmpty()) {
            m_Units.setError("Fill In A Bloody Number!!!");
            m_Units.requestFocus();
            return -1;
        }

        int units = Integer.parseInt(text);

        if (units < 1 || units > 5) {
            m_Units.setError("Has To Be Between " + 1 + " And " + 5);
            m_Units.requestFocus();
            return -1;
        }

        return units;
    }

    public void clear() {
        m_Units.setText("");
        m_Grade.setText("");

        if (m_SubjectName instanceof EditText) {
            m_SubjectName.setText("");
        }
    }
}
