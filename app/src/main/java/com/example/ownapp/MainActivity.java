package com.example.ownapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText editid, editName, editdesig;
    Button btnAdd, btnDelete, btnModify, btnView, btnViewall, btnShow, btnshowinfo;
    SQLiteDatabase db;

    Spinner s,s1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editid = findViewById(R.id.editid);
        editName = findViewById(R.id.editName);
        editdesig = findViewById(R.id.editDesig);
        btnAdd = findViewById(R.id.btnadd);
        btnDelete = findViewById(R.id.btndelete);
        btnModify = findViewById(R.id.btnmodify);
        btnView = findViewById(R.id.btnview);
        btnViewall = findViewById(R.id.btnviewall);
        btnShow = findViewById(R.id.btnshow);


        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewall.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        s=findViewById(R.id.spinner);
        s1=findViewById(R.id.spinner1);







        db = openOrCreateDatabase("EmployeeDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS employee(id VARCHAR,name VARCHAR,desig VARCHAR,dept VARCHAR,branch VARCHAR);");


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btnadd:
                if (view == btnAdd) {
                   /* if (editid.getText().toString().trim().length() == 0 ||
                            editName.getText().toString().trim().length() == 0 ||
                            editdesig.getText().toString().trim().length() == 0
                           ) {
                        Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
                        shwmsg("Error", "Invalid Input");
                        return;
                    }*/
                    Toast.makeText(this, "aaa"+s.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    //db.execSQL("INSERT INTO employee VALUES('"+editid.getText()+"','"+editName.getText()+
                   //         "','"+editdesig.getText()+"','"+s.getSelectedItem().toString()+"','"+s1.getSelectedItem().toString()+"');");
                    shwmsg("Success", "Record added");
                    clearText();
                }
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btndelete:
                if(editid.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter id");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM employee WHERE id='"+editid.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM employee WHERE id='"+editid.getText()+"'");
                    shwmsg("Success", "Record Deleted");
                }
                else
                {
                    shwmsg("Error", "Invalid id");
                }
                clearText();


                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnmodify:
                if(editid.getText().toString().trim().length() == 0 ||
                        editName.getText().toString().trim().length() == 0 ||
                        editdesig.getText().toString().trim().length() == 0)
                {
                    shwmsg("Error", "Error");
                    return;
                }
                Cursor c1=db.rawQuery("SELECT * FROM employee WHERE id='"+editid.getText()+"'", null);
                if(c1.moveToFirst())
                {
                    db.execSQL("UPDATE employee SET name='"+editName.getText()+"',desig='"+editdesig.getText()+
                            "' WHERE id='"+editid.getText()+"',dept='"+s.getSelectedItem().toString()+"',branch='"+s1.getSelectedItem().toString()+"'");
                    shwmsg("Success", "Record Modified");
                }
                else
                {
                    shwmsg("Error", "Invalid id");
                }
                clearText();


                Toast.makeText(this, "Modify", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnview:
                if(editid.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter id");
                    return;
                }
                Cursor c2=db.rawQuery("SELECT * FROM employee WHERE id='"+editid.getText()+"'", null);
                if(c2.moveToFirst())
                {
                    editName.setText(c2.getString(1));
                    editdesig.setText(c2.getString(2));

                }
                else {
                    shwmsg("Error", "Invalid id");
                    clearText();
                }
                Toast.makeText(this, "View", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnviewall:
                Cursor c3=db.rawQuery("SELECT * FROM employee", null);
                if(c3.getCount()==0)
                {
                    shwmsg("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c3.moveToNext())
                {
                    buffer.append("ID: "+c3.getString(0)+"\n");
                    buffer.append("Name: "+c3.getString(1)+"\n");
                    buffer.append("Designation: "+c3.getString(2)+"\n");
                    buffer.append("Department: "+c3.getString(3)+"\n");
                    buffer.append("Branch: "+c3.getString(4)+"\n");
                }
                shwmsg("Employee Details", buffer.toString());

                Toast.makeText(this, "View All", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnshow:
                Toast.makeText(this, "Show", Toast.LENGTH_SHORT).show();
                shwmsg("Developed By-","Mr.Jitendra Sulgekar");
                break;
        }
    }

    public void shwmsg(String title, String msg)
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher_round);
        alertDialog.show();

    }
    public void clearText()
    {
        editid.setText("");
        editName.setText("");
        editdesig.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText( MainActivity.this, "Clicked"+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT ).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}






