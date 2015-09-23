# Chatter
/*activity main knapp/*
<Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="StopRecording"
        android:id="@+id/stopRecording"
        android:layout_alignTop="@+id/recButton"
        android:layout_centerHorizontal="true"
        android:onClick="buttonTapped" />
        
        
        
        
        
        
        
        /* Test kode for later use
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OUTPUT_FILE= Environment.getExternalStorageDirectory()+"/audiorecorder.3gpp";
    }
    public void buttonTapped(View view){
        switch (view.getId()){
            case R.id.recButton:
                try{
                    beginRecording();
                } catch (Exception e){
                    e.printStackTrace();
                }

                break;

            case R.id.stopRecording:
                try {
                    stopRecording ();
                }catch (Exception e){
                    e.printStackTrace();}

                break;

            case R.id.playButton:
                try {
                    playRecording();
                }catch (Exception e){
                    e.printStackTrace();}

                break;

            case R.id.stopButton:
                try {
                    stopPlayback();
                }catch (Exception e){
                    e.printStackTrace();}
                }
    }

    private void stopPlayback() {
   if(mediaPlayer !=null)
       mediaPlayer.start();
    }

    private void playRecording() throws Exception{
   ditchMediaPlayer();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void ditchMediaPlayer() {
        if (mediaPlayer != null){
            try {
                mediaPlayer.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void stopRecording() {
    if (recorder != null)
        recorder.stop();
    }

    private void beginRecording() throws IOException {
    ditchMediaRecorder();
        File outFile = new File(OUTPUT_FILE);

        if (outFile.exists())
        outFile.delete();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();
    }

    private void ditchMediaRecorder() {
        if(recorder != null)
            recorder.release();

    }
}
