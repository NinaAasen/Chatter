package com.nina.hig.chatter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;



public class MainActivity extends Activity {

    // Using: https://www.youtube.com/watch?v=XANjoeEeQ1Y

    /* output file */
    private String saveFiles;
    private String audioDirs;

    /* Making variables */
    private MediaPlayer mPlay;
    private MediaRecorder mRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Making appDir for savefiles
        File appDir = new File(getExternalCacheDir(), "chatterFiles");
        boolean success = false;
        if (!appDir.exists()) {
            success = appDir.mkdirs();
        }
        if (!success){
            Log.d("", "Folder not created :(");

        } else {
            Log.d("", "Folder created!");
        }

        // Setting up savefiles paths
        audioDirs = getExternalCacheDir() + "/chatterFiles";
        saveFiles = audioDirs + "/1.3gpp";

        loadFilesToView();
        playSelectedFile();
    }

    public void btnPress(View v) {

        /*
        Going trough to get which button
        has been pressed with a switch
        */

        loadFilesToView(); // Update files into listview

        switch (v.getId()) {
            case R.id.btnRec:
                try {
                    startRec();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;

            case R.id.btnStop:
                try {
                    stopRec();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;

            case R.id.btnDone:
                try {
                    stopPlayBack();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;

            case R.id.btnPlay:
                try {
                    playRec();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    private void stopPlayBack() {
        if (mPlay != null) {
            mPlay.stop();
        }
    }

    // Play method
    private void playRec() throws IOException {
        clearMediaPlay(); /* Get ready */
        mPlay = new MediaPlayer();
        mPlay.setDataSource(saveFiles); // file location
        mPlay.prepare(); // prepare to play
        mPlay.start(); // start playing
    }

    // as above but with file name
    private void playRecNum(int name) throws IOException {
        clearMediaPlay();
        mPlay = new MediaPlayer();
        mPlay.setDataSource(audioDirs + "/" + name + ".3gpp");
        mPlay.prepare();
        mPlay.start();
    }

    private void clearMediaPlay() {
        if (mPlay != null) {
            try {
                mPlay.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // stop if recording
    private void stopRec() {
        if (mRec != null) {
            mRec.stop();
            Toast.makeText(this, "audio recorded", Toast.LENGTH_SHORT).show(); //message to user when recording is stopped
        }
    }

    // start recording
    private void startRec() throws IOException {
        int numberOfFiles = 0; // file now, gets next
        File filex = new File(audioDirs); // file dir
        File[] list = filex.listFiles(); // length of files
        for (File f: list) {
            String fname = f.getName();
            if(fname.endsWith(".3gpp")) {
                numberOfFiles++;
            }
        }

        clearMediaRec();
        File outFile = new File(saveFiles);
        if (outFile.exists()) {
            // if file exists make next
            saveFiles = audioDirs + "/" + ++numberOfFiles + ".3gpp";
        }

        mRec = new MediaRecorder();
        mRec.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRec.setOutputFile(saveFiles);
        mRec.prepare();
        mRec.start();
        Toast.makeText(this, "recording", Toast.LENGTH_SHORT).show(); //message to user if recording is started
    }

    private void clearMediaRec() {
        if (mRec != null) {
            mRec.release();
        }
    }

    private void loadFilesToView() {
        // Load files from dir to listview
        // https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
        File dir = new File(audioDirs);
        File[] filelist = dir.listFiles();
        String[] theNamesOfFile = new String[filelist.length];
        for (int i = 0; i < theNamesOfFile.length; i++) {
            theNamesOfFile[i] = filelist[i].getName();
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theNamesOfFile);
        ListView listView = (ListView) findViewById(R.id.lv2);
        listView.setAdapter(itemsAdapter);
    }

    // get clicked id and play that file +1
    private void playSelectedFile() {
        ListView audioToPlay = (ListView) findViewById(R.id.lv2);
        audioToPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position + 1;
                try {
                    playRecNum(pos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}