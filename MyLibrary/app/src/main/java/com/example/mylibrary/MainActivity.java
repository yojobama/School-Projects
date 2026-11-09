package com.example.mylibrary;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText m_BookNameEditText, m_AuthorNameEditText, m_PageCountEditText, m_ReadCountEditText;
    Button m_SaveButton, m_LoadButton;
    TextView m_StatusTextView;
    String m_LoadedTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        m_SaveButton = findViewById(R.id.btnSave);
        m_LoadButton = findViewById(R.id.btnLoad);

        m_BookNameEditText = findViewById(R.id.etBook);
        m_AuthorNameEditText = findViewById(R.id.etAuthor);
        m_PageCountEditText = findViewById(R.id.etPages);
        m_ReadCountEditText = findViewById(R.id.etRead);

        m_StatusTextView = findViewById(R.id.tvStatus);

        m_SaveButton.setOnClickListener(v -> {
            if (checkEditTextEmpty(m_BookNameEditText) && checkEditTextEmpty(m_AuthorNameEditText) && checkEditTextEmpty(m_PageCountEditText))
                Toast.makeText(this, "Data Is Valid", LENGTH_LONG).show();
            else return;

            String readStr = m_ReadCountEditText.getText().toString();
            if (readStr.isEmpty() || Integer.parseInt(readStr) > Integer.parseInt(m_PageCountEditText.getText().toString()) || Integer.parseInt(readStr) < 0)
                return;

            Map<String, Object> book = new HashMap<>();
            book.put("title", m_BookNameEditText.getText().toString());
            book.put("author", m_AuthorNameEditText.getText().toString());
            book.put("pages", Integer.parseInt(m_PageCountEditText.getText().toString()));
            book.put("pagesRead", Integer.parseInt(readStr));

            m_StatusTextView.setText("שומר...");

            db.collection("books").document("current").set(book)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            m_StatusTextView.setText("נשמר בענן");
                            Toast.makeText(MainActivity.this, "הספר נשמר", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            m_StatusTextView.setText("השמירה נכשלה: " + e.getMessage());
                        }
                    });
        });

        m_LoadButton.setOnClickListener(v -> {
            m_StatusTextView.setText("טוען...");

            db.collection("books").document("current").get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot doc) {
                            if (!doc.exists()) {
                                m_StatusTextView.setText("עוד אין ספר שמור");
                                return;
                            }

                            m_BookNameEditText.setText(doc.getString("title"));
                            m_AuthorNameEditText.setText(doc.getString("author"));

                            Long pages = doc.getLong("pages");
                            if (pages != null) {
                                m_PageCountEditText.setText(String.valueOf(pages));
                            } else {
                                m_PageCountEditText.setText("");
                            }
                            m_StatusTextView.setText("נטען מהענן");

                            Long read = doc.getLong("pagesRead");
                            if (read != null) {
                                m_ReadCountEditText.setText(String.valueOf(read));
                                int percent = (int) ((double) read / pages * 100);
                                m_StatusTextView.setText("read " + percent + "% of the book");
                            } else {
                                m_ReadCountEditText.setText("");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            m_StatusTextView.setText("הטעינה נכשלה: " + e.getMessage());
                        }
                    });
        });
    }

    boolean checkEditTextEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Fill In The Bloody Thing");
            return false;
        }
        return true;
    }
}