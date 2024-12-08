package com.example.curdoperation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {


    Context context;
    int listLayoutRes;
    List<User> userlist;
    SQLiteDatabase mDatabase;

     public UserAdapter(Context ctn,int list,List<User> user,SQLiteDatabase lite){

         super(ctn,list,user);

            this.context = ctn;
            this.userlist = user;
            this.listLayoutRes = list;
            this.mDatabase = lite;
     }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(listLayoutRes,null);

        User user = userlist.get(position);

        TextView txtname = view.findViewById(R.id.textView4);
        TextView  txtsurname = view.findViewById(R.id.textView5);
        TextView txtpassword = view.findViewById(R.id.textView6);
        TextView txtdepartment = view.findViewById(R.id.textView7);

        txtname.setText(user.name);
        txtdepartment.setText(user.department);
        txtpassword.setText(user.password);
        txtsurname.setText(user.surname);

        Button btnedit = view.findViewById(R.id.edit);
        Button btndelete = view.findViewById(R.id.delete);


        btnedit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateuser(user);
                    }
                }
        );


        btndelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure ?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sql = "DELETE FROM User WHERE id = ?";
                                mDatabase.execSQL(sql,new Integer[]{user.getId()});
                                reloadEmployeesFromDatabase();

                            }

                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
        );

        return  view;

    }

    private void updateuser(final User user){
  final  AlertDialog.Builder builder = new AlertDialog.Builder(context);

  LayoutInflater inflater = LayoutInflater.from(context);
  View view = inflater.inflate(R.layout.editlayour,null);
  builder.setView(view);

  final EditText name = view.findViewById(R.id.editTextName);
        final EditText surname = view.findViewById(R.id.esurname);
        final EditText password = view.findViewById(R.id.epassword);
        final Spinner department = view.findViewById(R.id.spinnerDepartment);

        final  AlertDialog alertDialog = builder.create();
        alertDialog.show();

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namee = name.getText().toString().trim();

                        String esurname = surname.getText().toString().trim();

                        String passworde = password.getText().toString().trim();

                        String departmente = department.getSelectedItem().toString();

                        String sql = "UPDATE User \n" +
                                "SET NAME = ?, \n" +
                                "SURNAME = ?, \n" +
                                "PASSWORD = ?, \n" +
                                "DEPARTMENT = ? \n" +
                                "WHERE id = ?;\n";

                        mDatabase.execSQL(sql, new String[]{namee, esurname, passworde,departmente, String.valueOf(user.getId())});
                        Toast.makeText(context, "Employee Updated", Toast.LENGTH_SHORT).show();
                        reloadEmployeesFromDatabase();
                    }
                }
        );

    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM User", null);
        if (cursorEmployees.moveToFirst()) {
            userlist.clear();
            do {
                userlist.add(new User(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4)
                ));
            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
        notifyDataSetChanged();
    }
}
