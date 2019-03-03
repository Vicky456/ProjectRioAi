package com.vicky.projectrioai;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.mapzen.speakerbox.Speakerbox;

public class HomeActivity extends AppCompatActivity implements AIListener {


    private Application application;
    private Context context;
    private AIService aiService;
    private TextView t;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=getApplicationContext();
        application=getApplication();


        t=findViewById(R.id.textView7Date);
        t1=findViewById(R.id.textViewShow);

        int permission = ContextCompat.checkSelfPermission(context,Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

        final AIConfiguration config = new AIConfiguration("40e02cdba0514759aecfbfc59cd67932",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);


        aiService = AIService.getService(context, config);
        aiService.setListener(this);

        findViewById(R.id.imageButtonSpeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiService.startListening();
            }
        });


    }



    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length == 0|| grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            } else {
            }
            return;
        }
    }

    @Override
    public void onResult(AIResponse result) {

        Log.d("anu",result.toString());
        Result result1=result.getResult();
        String textResult=result1.getFulfillment().getSpeech();
//        t.setText("Query "+result1.getResolvedQuery()+" action: "+textResult);
//        t.setText("Query "+result1.getResolvedQuery()+" action: "+textResult);
        t.setText(result1.getResolvedQuery());
        t1.setText(textResult);

        Speakerbox speakerbox = new Speakerbox(application);

        speakerbox.play(textResult);



    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        aiService.cancel();
    }
}
