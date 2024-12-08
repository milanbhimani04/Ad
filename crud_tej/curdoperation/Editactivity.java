package com.example.curdoperation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Editactivity extends AppCompatActivity {
    List<User> employeeList;
    SQLiteDatabase mDatabase;
    ListView listViewEmployees;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editactivity);

        // Initialize list and database
        employeeList = new ArrayList<>();
        mDatabase = openOrCreateDatabase(MainActivity.Database_NAME, MODE_PRIVATE, null);

        // Initialize ListView
        listViewEmployees = findViewById(R.id.listViewEmployees);

        // Load employees from database
        showEmployeesFromDatabase();
    }

    private void showEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM User", null);

        // Check if the cursor has data
        if (cursorEmployees.moveToFirst()) {
            employeeList.clear();
            do {
                // Add each record to the employee list
                employeeList.add(new User(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4)
                ));
            } while (cursorEmployees.moveToNext());
        }

        // Close the cursor
        cursorEmployees.close();

        // Create and set the adapter
        adapter = new UserAdapter(this, R.layout.list_layout_design, employeeList, mDatabase);
        listViewEmployees.setAdapter(adapter);
    }
}