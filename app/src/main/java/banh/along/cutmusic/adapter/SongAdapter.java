package banh.along.cutmusic.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import banh.along.cutmusic.EditSongActivity;
import banh.along.cutmusic.R;
import banh.along.cutmusic.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    ArrayList<Song> listSong;
    Context context;
    MediaPlayer mp = null;

    public SongAdapter(ArrayList<Song> listSong, Context context) {
        this.listSong = listSong;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_song, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {


        viewHolder.author.setText(listSong.get(i).getAuthor());
        viewHolder.author.setPaintFlags(0);

        /*int time = Integer.parseInt(listSong.get(i).getTime());
        int gio, phut, giay;*/
        long millis = Integer.parseInt(listSong.get(i).getTime());
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


        viewHolder.time.setText(hms);
        viewHolder.time.setPaintFlags(0);


        viewHolder.nameOfSong.setText(listSong.get(i).getNameOfSong());
        viewHolder.nameOfSong.setPaintFlags(0);
        viewHolder.nameOfSong.setSelected(true);


        viewHolder.iconMenu.setImageResource(listSong.get(i).getIconMenu());

        viewHolder.lnSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();*/


                final String path = listSong.get(i).getPath().toString();

                Intent intent = new Intent(context, EditSongActivity.class);
                intent.putExtra("SongPath", path);
                context.startActivity(intent);




            }
        });

    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout lnSong;
        TextView author;
        TextView nameOfSong;
        TextView time;
        ImageView iconMenu;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnSong = itemView.findViewById(R.id.lnSong);
            author = itemView.findViewById(R.id.author);
            nameOfSong = itemView.findViewById(R.id.nameOfSong);
            time = itemView.findViewById(R.id.time);
            iconMenu = itemView.findViewById(R.id.iconMenu);
            this.view = itemView;
        }
    }
}
