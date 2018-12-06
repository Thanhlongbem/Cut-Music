package banh.along.cutmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import banh.along.cutmusic.adapter.SongAdapter;
import banh.along.cutmusic.model.Song;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListMusic;
    private SongAdapter songAdapter;
    private Song song;
    ArrayList<Song> songsDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }





    public void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvListMusic = findViewById(R.id.rvListMusic);
        rvListMusic.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvListMusic.setItemAnimator(new DefaultItemAnimator());
        rvListMusic.setLayoutManager(linearLayoutManager);

    }

    public void initData(){


        //songsDevice.add(new Song(R.drawable.music_24, R.drawable.menu_24,"Mr.Siro", "Dấu mưa", "https://mp3.zing.vn/bai-hat/Dau-Mua-Trung-Quan-Idol/ZW67W08A.html"));
        getListSongFromDevice();


        songAdapter = new SongAdapter(songsDevice, this);
        rvListMusic.setAdapter(songAdapter);
    }




    public void getListSongFromDevice(){
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };

        Cursor cursor = this.managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        songsDevice = new ArrayList<>();
        while(cursor.moveToNext()) {
            //songsDevice.add(R.drawable.music_24, R.drawable.menu_24, cursor.getString(1).toString(), cursor.getString(4).toString(), cursor.getString(3).toString());
            Song song = new Song();
            song.setAuthor(cursor.getString(1));
            song.setNameOfSong(cursor.getString(4));
            song.setResource(cursor.getString(5));
            song.setIconMenu(R.drawable.menu_24);
            song.setIconType(R.drawable.music_24);

            songsDevice.add(song);
        }
    }





    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAbout:
                Toast.makeText(this, "MENU ABOUT", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuListMusic:
                Toast.makeText(this, "MENU LIST MUSIC", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_search:
                Toast.makeText(this, "SEARCH MUSIC", Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
