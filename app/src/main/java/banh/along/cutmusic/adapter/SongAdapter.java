package banh.along.cutmusic.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import banh.along.cutmusic.R;
import banh.along.cutmusic.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    ArrayList<Song> listSong;
    Context context;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.author.setText(listSong.get(i).getAuthor());
        viewHolder.author.setPaintFlags(0);

        viewHolder.resource.setText(listSong.get(i).getResource());
        viewHolder.resource.setPaintFlags(0);

        viewHolder.nameOfSong.setText(listSong.get(i).getNameOfSong());
        viewHolder.nameOfSong.setPaintFlags(0);

        viewHolder.iconType.setImageResource(listSong.get(i).getIconType());
        viewHolder.iconMenu.setImageResource(listSong.get(i).getIconMenu());
    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView nameOfSong;
        TextView resource;
        ImageView iconType;
        ImageView iconMenu;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            nameOfSong = itemView.findViewById(R.id.nameOfSong);
            resource = itemView.findViewById(R.id.resource);
            iconType = itemView.findViewById(R.id.iconType);
            iconMenu = itemView.findViewById(R.id.iconMenu);
            this.view = itemView;
        }
    }
}
