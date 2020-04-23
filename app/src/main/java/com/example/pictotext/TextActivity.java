package com.example.pictotext;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
        import android.speech.tts.TextToSpeech;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.SeekBar;

        import java.util.Locale;

public class TextActivity extends AppCompatActivity
{
    private TextToSpeech mtts;
    private EditText medittext;
    private SeekBar mseekbarpitch;
    private SeekBar mseekbarSpeed;
    private Button mbuttonspeak;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonspeak =findViewById(R.id.button_speak);
        back=findViewById(R.id.backtologin);

        mtts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status)
            {
                if (status==TextToSpeech.SUCCESS)
                {
                    int result=mtts.setLanguage(Locale.ENGLISH);
                    if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("TTS","Language not supported");
                    }
                    else
                    {
                        mbuttonspeak.setEnabled(true);
                    }

                }
                else
                {
                    Log.e("TTS","Initialization failed");
                }
            }
        });
        medittext=findViewById(R.id.edit_text);
        mseekbarpitch=findViewById(R.id.seek_bar_pitch);
        mseekbarSpeed=findViewById(R.id.seek_bar_speed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TextActivity.this, LaunchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mbuttonspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }
    private void speak()
    {
        String text=medittext.getText().toString();

        float pitch=(float)mseekbarpitch.getProgress()/50;
        if(pitch<0.1)
            pitch=0.1f;

        float speed=(float)mseekbarSpeed.getProgress()/50;
        if(speed<0.1)
            speed=0.1f;
        mtts.setPitch(pitch);
        mtts.setSpeechRate(speed);
        mtts.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    protected void onDestroy()
    {
        if(mtts!=null)
        {
            mtts.stop();
            mtts.shutdown();
        }
        super.onDestroy();
    }

}
