package banh.along.cutmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class EditSongActivity extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_edit_activity);
        mp = new MediaPlayer();
        playSong();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }



    public void playSong(){

        Intent intent = getIntent();



        final String path = intent.getStringExtra("SongPath");

        if(mp.isPlaying()){
            try {
                mp.stop();
                mp = new MediaPlayer();
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
