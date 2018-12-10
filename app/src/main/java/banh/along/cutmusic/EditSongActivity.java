package banh.along.cutmusic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.newventuresoftware.waveform.WaveformView;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.io.IOUtils.toByteArray;


public class EditSongActivity extends AppCompatActivity {

    MediaPlayer mp;
    RangeSeekBar editSongRangeBar;
    TextView tvCurrentTime;
    private Handler mHandler;
    FloatingActionButton playFab;
    String LOG_TAG = "BANHBEO";
    private WaveformView mRealtimeWaveformView;
    private RecordingThread mRecordingThread;
    private PlaybackThread mPlaybackThread;
    private static final int REQUEST_RECORD_AUDIO = 13;
    WaveformView mPlaybackView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_edit_activity);

        initView();

    }


    public void initView(){
        mHandler = new Handler();
        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);
        editSongRangeBar = findViewById(R.id.editSongRangeBar);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        playFab = findViewById(R.id.playFab);
        mPlaybackView = (WaveformView) findViewById(R.id.playbackWaveformView);
        mp = new MediaPlayer();
        playSong();
    }

    public void playSong(){

        short[] samples = null;
        try {
            samples = getAudioSample();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (samples != null) {
            final FloatingActionButton playFab = (FloatingActionButton) findViewById(R.id.playFab);

            mPlaybackThread = new PlaybackThread(samples, new PlaybackListener() {
                @Override
                public void onProgress(int progress) {
                    mPlaybackView.setMarkerPosition(progress);
                }
                @Override
                public void onCompletion() {
                    mPlaybackView.setMarkerPosition(mPlaybackView.getAudioLength());
                    playFab.setImageResource(android.R.drawable.ic_media_play);
                }
            });
            mPlaybackView.setChannels(1);
            mPlaybackView.setSampleRate(PlaybackThread.SAMPLE_RATE);
            mPlaybackView.setSamples(samples);

            playFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPlaybackThread.playing()) {
                        mPlaybackThread.startPlayback();
                        playFab.setImageResource(android.R.drawable.ic_media_pause);
                    } else {
                        mPlaybackThread.stopPlayback();
                        playFab.setImageResource(android.R.drawable.ic_media_play);
                    }
                }
            });
        }




        /*if(mp.isPlaying()){
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
        }*/
        mHandler.postDelayed(updateSeekBar,1000);
        editSongRangeBar.setRangeValues(0, mp.getDuration());
        editSongRangeBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                mp.seekTo(minValue);
                mp.start();

                if(mp.getCurrentPosition() == maxValue) {
                    mp.stop();
                }


            }
        });



    }




    private short[] getAudioSample() throws IOException{

        Intent intent = getIntent();
        String songPath = intent.getStringExtra("SongPath");
        byte[] data;
        InputStream is  = getContentResolver().openInputStream(Uri.fromFile(new File(songPath)));
        //InputStream is = getResources().openRawResource(R.raw.jinglebells);
        //InputStream is = new FileInputStream(String.valueOf(Uri.fromFile(new File(songPath))));


        try {
            data = IOUtils.toByteArray(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

        ShortBuffer sb = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        short[] samples = new short[sb.limit()];
        sb.get(samples);
        return samples;
    }


    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            long max = mp.getDuration();
            long millis = mp. getCurrentPosition();
            @SuppressLint("DefaultLocale") String crTime = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            tvCurrentTime.setText(crTime);
            mHandler.postDelayed(this, 1000);
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_toolbar_menu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mPlaybackThread.stopPlayback();
    }



}
