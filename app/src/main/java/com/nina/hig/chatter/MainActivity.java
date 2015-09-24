package com.nina.hig.chatter;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends Activity {

    private static MediaRecorder mediaRecorder;

    private static String audioFilePath;
    private static ImageButton stopButton;
    private static ImageButton playButton;
    private static ImageButton recButton;

    private boolean isRecording = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recButton = (ImageButton) findViewById(R.id.recButton);
        playButton = (ImageButton) findViewById(R.id.playButton);
        stopButton = (ImageButton) findViewById(R.id.stopButton);

        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myaudio.3gp";
    }

    public void recordAudio(View view) throws IOException {
        boolean isRecording = true;
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        recButton.setEnabled(false);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
        Toast.makeText(this, "recording", Toast.LENGTH_SHORT).show();
        }

    public void stopAudio(View view) throws IOException {
           mediaRecorder.stop();
           mediaRecorder.reset();
           mediaRecorder.release();
           mediaRecorder = null;
           Toast.makeText(this, "audio recorded", Toast.LENGTH_SHORT).show();
      }
   }