package com.kapil.musicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kapil.musicplayer.helpers.AudioListHelper;
import com.kapil.musicplayer.R;
import com.kapil.musicplayer.model.Audio;

import java.util.ArrayList;

/**
 * Created by kapil on 19-03-2018.
 * generic adapter for all 3 fragments
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ItemViewHolder> {

    Context context;
    private Listener listener;
    private ArrayList<Audio> audioList = null;

    public interface Listener {
        void onClick (int position);
    }

    public void setAudioList(ArrayList<Audio> audioList) {
        this.audioList = audioList;
    }

    public void setListener (Listener listener) {
        this.listener = listener;
    }

    public HomePageAdapter(Context context,ArrayList<Audio> favouriteList) {
        this.context = context;
        this.audioList = favouriteList;
    }

    @Override
    public HomePageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_caption_image,parent,false);

        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder,final int position) {
        Audio activeAudio;
        activeAudio = audioList.get(position);

        CardView cardView = holder.cardView;

        TextView songName = (TextView) cardView.findViewById(R.id.text_view);
        TextView songDuration = (TextView) cardView.findViewById(R.id.duration_text);
        TextView artistName = (TextView) cardView.findViewById(R.id.artist_name);
        final ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);

        String albumArtUri = activeAudio.getAlbumArtUri();
        imageView.setAdjustViewBounds(true);

        if (albumArtUri == null || albumArtUri.equals("")) {
            imageView.setImageDrawable(context.getDrawable(R.mipmap.ic_launcher));
        } else {
            //imageView.setImageURI();
            Glide.with(context).asBitmap().load(activeAudio.getAlbumArtUri())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(imageView);
        }
        //imageView.setImageBitmap(activeAudio.getBitmap());
        songDuration.setText(convertMilliToTime(activeAudio.getSongDuration()));
        songName.setText(activeAudio.getTitle());
        artistName.setText(activeAudio.getArtist());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    private String convertMilliToTime (int milli) {

        int seconds = (milli / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        String sec;
        if (seconds > 9)
            sec = Integer.toString(seconds);
        else
            sec = "0" + Integer.toString(seconds);

        String min;
        if (minutes > 9)
            min = Integer.toString(minutes);
        else
            min = "0" + Integer.toString(minutes);

        return (min + ":" + sec);
    }

    @Override
    public int getItemCount() {
        if (audioList == null)
            return AudioListHelper.audioListHelper.audioArrayList.size();
        return audioList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ItemViewHolder(CardView itemView) {
            super(itemView);
            this.cardView = itemView;

        }
    }

}