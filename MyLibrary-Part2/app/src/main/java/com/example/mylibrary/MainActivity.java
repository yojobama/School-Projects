package com.example.mylibrary;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    Button m_SaveButton, m_LoadButton, m_DeleteButton;
    TextView m_StatusTextView;

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
        m_DeleteButton = findViewById(R.id.btnDelete);

        m_BookNameEditText = findViewById(R.id.etBook);
        m_AuthorNameEditText = findViewById(R.id.etAuthor);
        m_PageCountEditText = findViewById(R.id.etPages);
        m_ReadCountEditText = findViewById(R.id.etRead);

        m_StatusTextView = findViewById(R.id.tvStatus);

        m_SaveButton.setOnClickListener(v -> {
            if (checkEditTextEmpty(m_BookNameEditText) && checkEditTextEmpty(m_AuthorNameEditText) && checkEditTextEmpty(m_PageCountEditText)) {
                String readStr = m_ReadCountEditText.getText().toString();
                if (readStr.isEmpty() || Integer.parseInt(readStr) > Integer.parseInt(m_PageCountEditText.getText().toString()) || Integer.parseInt(readStr) < 0) {
                    m_ReadCountEditText.setError("You Wee Idiot!!!");
                    return;
                }
                Toast.makeText(this, "Data Is Valid", LENGTH_LONG).show();
            }
            else return;

            Book book = new Book(m_BookNameEditText.getText().toString(),
                    m_AuthorNameEditText.getText().toString(),
                    Integer.parseInt(m_PageCountEditText.getText().toString()),
                    Integer.parseInt(m_ReadCountEditText.getText().toString()));

            m_StatusTextView.setText("שומר...");

            db.collection("books").document("current").set(book)
                    .addOnSuccessListener(unused -> {
                        m_StatusTextView.setText("נשמר בענן");
                        Toast.makeText(MainActivity.this, "הספר נשמר", LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> m_StatusTextView.setText("השמירה נכשלה: " + e.getMessage()));
        });

        m_LoadButton.setOnClickListener(v -> {
            m_StatusTextView.setText("טוען...");

            db.collection("books").document("current").get()
                    .addOnSuccessListener(doc -> {
                        if (!doc.exists()) {
                            m_StatusTextView.setText("עוד אין ספר שמור");
                            return;
                        }

                        Book book = doc.toObject(Book.class);

                        m_BookNameEditText.setText(book.getTitle());
                        m_AuthorNameEditText.setText(book.getAuthor());

                        m_PageCountEditText.setText(String.valueOf(book.getPages()));

                        m_StatusTextView.setText("נטען מהענן");

                        m_ReadCountEditText.setText(String.valueOf(book.getPagesRead()));
                        int percent = (int) ((double) book.getPagesRead() / book.getPages() * 100);
                        m_StatusTextView.setText("read " + percent + "% of the book");
                    })
                    .addOnFailureListener(e -> m_StatusTextView.setText("הטעינה נכשלה: " + e.getMessage()));
        });

        m_DeleteButton.setOnClickListener(v -> {
            Log.d("DELETE_BUTTON", "delete button was called");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are You Sure You Wish To Delete All The Data?").setTitle("Confirmation");
            Log.d("DELETE_BUTTON", "Alert Dialog message and title were set");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                db.collection("books").document("current").delete()
                        .addOnSuccessListener(doc -> {
                            m_BookNameEditText.setText("");
                            m_AuthorNameEditText.setText("");
                            m_PageCountEditText.setText("");
                            m_ReadCountEditText.setText("");

                            m_StatusTextView.setText("All Data Was Succesfuly Deleted!");
                        }).addOnFailureListener(exception -> {
                            m_StatusTextView.setText("Data Deletion Failed! " + exception.getMessage());
                            Log.wtf("Error Deleting DB", exception);
                        });
            });
            Log.d("DELETE_BUTTON", "Alert Dialog positive button was set");
            builder.setNegativeButton("No", (dialog, which) -> {
            });
            Log.d("DELETE_BUTTON", "Alert Dialog negative button set");
            builder.show();
            Log.d("DELETE_BUTTON", "Alert Dialog was created");
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