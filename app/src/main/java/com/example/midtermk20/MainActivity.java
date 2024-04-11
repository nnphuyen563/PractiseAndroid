package com.example.midtermk20;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adapters.BookAdapter;
import com.example.midtermk20.databinding.ActivityMainBinding;
import com.example.models.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Databases db;
    BookAdapter adapter;
    ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createDb();
        loadDb();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void createDb() {
        db = new Databases(MainActivity.this);
        db.createSampleData();
    }


    private void loadDb() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.lvBooks.setLayoutManager(layoutManager);

        books = new ArrayList<>();

        Cursor cursor = db.queryData("SELECT * FROM " + Databases.TBL_NAME);
        while (cursor.moveToNext()){
            books.add(new Book(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    cursor.getBlob(5)));
        }
        cursor.close();

        adapter = new BookAdapter(MainActivity.this, books);
        binding.lvBooks.setAdapter(adapter);
    }


}