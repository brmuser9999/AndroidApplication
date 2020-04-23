package com.example.pictotext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    DatabaseHelper db;
    EditText T1,T2,T3;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=new DatabaseHelper(this);
        T1=(EditText) findViewById(R.id.editText);
        T2=(EditText) findViewById(R.id.editText2);
        T3=(EditText) findViewById(R.id.editText3);
        b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=T1.getText().toString();
                String s2=T2.getText().toString();
                String s3=T3.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else
                    {

                    if(s2.equals(s3))
                    {
                        Boolean checkmail=db.chkmail(s1);
                        if(checkmail==true)
                        {
                            Boolean insert=db.insert(s1,s2);
                            if(insert==true)
                            {
                                Toast.makeText(getApplicationContext(),"Registered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,LaunchActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }
                        else
                            {
                            Toast.makeText(getApplicationContext(),"Email already exists",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,LaunchActivity.class);
                                startActivity(intent);
                                finish();

                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }



}