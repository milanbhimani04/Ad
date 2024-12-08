package com.example.curdoperation;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static  final   String Database_NAME = "User";
    EditText name,surname,password;
    Spinner department;
    Button btnsave,btnnext;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.editName);
        surname = findViewById(R.id.editsurnamee);
        password = findViewById(R.id.editTextTextPassword);
        department = findViewById(R.id.spinner);
        btnsave = findViewById(R.id.btnadd);
        btnnext = findViewById(R.id.btnnext);

        sqLiteDatabase = openOrCreateDatabase(Database_NAME,MODE_PRIVATE,null);

        createDatabase();

        btnnext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, Editactivity.class));
                        Toast.makeText(getApplicationContext(),"this",Toast.LENGTH_LONG).show();
                    }
                }
        );



        btnsave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adduser();
                    }


                }
        );
    }

    private void createDatabase(){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME VARCHAR(200) NOT NULL, " +
                "SURNAME VARCHAR(200) NOT NULL, " +
                "PASSWORD VARCHAR(200) NOT NULL, " +
                "DEPARTMENT VARCHAR(200) NOT NULL" +
                ");");


    }
    private void adduser() {

        String addname = name.getText().toString().trim();
        String addsurname = surname.getText().toString().trim();
        String adddepartment = department.getSelectedItem().toString();
        String passwordadd = password.getText().toString().trim();


        String adduser = "INSERT INTO User (NAME,SURNAME,PASSWORD,DEPARTMENT) VALUES (? ,? ,? ,?)";

        sqLiteDatabase.execSQL(adduser,new String[]{
                addname,addsurname,passwordadd,adddepartment
        });

        Toast.makeText(getApplicationContext(),"The user added successfully",Toast.LENGTH_LONG).show();
    }

}
