package com.nina.hig.chatter;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;

public class MainStorageActivity extends Activity {

    private static MediaRecorder mediaRecorder;


    private static ImageButton stopAudio;
    private static ImageButton playAudio;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_storage);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main_storage, menu);
            return true;
        }



    }




