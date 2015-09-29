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

        // Making appDir for save files
        File appDir = new File(getExternalCacheDir(), "chatterFiles");
        boolean success = false;
        if (!appDir.exists()) {
            success = appDir.mkdirs();
        }


        if (!success){
            Log.d("", "Folder not created :(");
        } else {
            Log.d("", "Folder created! <3");
        }

        // Setting up save files paths
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

        // Update files into list view
        loadFilesToView();

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

    // If a file is playing in the player
    // then stop the player.
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
            // message to user when recording is stopped
            Toast.makeText(this, "audio recorded", Toast.LENGTH_SHORT).show();
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

        // If mRec is in use, release it
        clearMediaRec();

        // New file, in save file location
        File outFile = new File(saveFiles);

        // if the file exists, make next with the name x + 1
        if (outFile.exists()) {
            saveFiles = audioDirs + "/" + ++numberOfFiles + ".3gpp";
        }

        // making a media recorder,
        // using the mic,
        // setting format to 3gpp
        // Encoding to AMR_NB
        // where to save the file
        // preparing and starting the recording.

        mRec = new MediaRecorder();
        mRec.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRec.setOutputFile(saveFiles);
        mRec.prepare();
        mRec.start();

        // message to user if recording is started
        Toast.makeText(this, "recording", Toast.LENGTH_SHORT).show();
    }

    // If the mRec is in use, release it to
    // be used next for next call.
    private void clearMediaRec() {
        if (mRec != null) {
            mRec.release();
        }
    }

    // Load files from dir to list view
    // https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    // multiple youtube videos was used but we only picked up some knowledge of each. the link
    // provided did help the most.

    private void loadFilesToView() {
        File dir = new File(audioDirs);
        File[] filelist = dir.listFiles();
        String[] theNamesOfFile = new String[filelist.length];
        for (int i = 0; i < theNamesOfFile.length; i++) {
            theNamesOfFile[i] = filelist[i].getName();
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theNamesOfFile);
        ListView listView = (ListView) findViewById(R.id.lv2);
        listView.setAdapter(itemsAdapter);

        Toast.makeText(this, "List updated with new files.", Toast.LENGTH_SHORT).show();
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