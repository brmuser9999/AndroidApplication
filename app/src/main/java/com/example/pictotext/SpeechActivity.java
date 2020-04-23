package com.example.pictotext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechActivity extends AppCompatActivity
{
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    TextView mtextTv;
    Button back;
    ImageButton mVoiceBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        mtextTv=findViewById(R.id.textTv);
        mVoiceBtn=findViewById(R.id.voiceBtn);
        back=findViewById(R.id.back);
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            speak();
            }

            private void speak()
            {
                //Starts an activity that will prompt the user for speech and send it through a speech recognizer.
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"Hi ! Speak something");
                try
                {
                    startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
                }
                catch(Exception e)
                {

                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:
                {
              if(requestCode==RESULT_OK && null!=data)
              {
                  ArrayList<String>result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                  mtextTv.setText(result.get(0));
              }
              break;
                }
        }

    }
    public void Back(View view)
    {
        Intent intent = new Intent(SpeechActivity.this, LaunchActivity.class);
        startActivity(intent);
        finish();
    }
}
