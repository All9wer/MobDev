package ru.mirea.chirkovia.favoritebook;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class ShareActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        TextView textViewDevBook = findViewById(R.id.textViewDevBook);
        TextView textViewDevQuote = findViewById(R.id.textViewDevQuote);
        EditText editUserBook = findViewById(R.id.editUserBook);
        EditText editUserQuote = findViewById(R.id.editUserQuote);
        Button buttonSendBack = findViewById(R.id.buttonSendBack);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quoteName = extras.getString(MainActivity.QUOTES_KEY);
            textViewDevBook.setText("Любимая книга разработчика: " + bookName);
            textViewDevQuote.setText("Цитата из книги: " + quoteName);}
        buttonSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userBook = editUserBook.getText().toString();
                String userQuote = editUserQuote.getText().toString();
                String message = "Название Вашей любимой книги: " + userBook +
                        ". Цитата: " + userQuote;
                Intent data = new Intent();
                data.putExtra(MainActivity.USER_MESSAGE, message);
                setResult(Activity.RESULT_OK, data);
                finish();}});}}
