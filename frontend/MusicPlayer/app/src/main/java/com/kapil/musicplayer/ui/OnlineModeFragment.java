package com.kapil.musicplayer.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapil.musicplayer.R;
import com.kapil.musicplayer.adapters.HomePageAdapter;
import com.kapil.musicplayer.helpers.FavouriteListHelper;
import com.kapil.musicplayer.helpers.OnlineModeListHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineModeFragment extends Fragment {


    public OnlineModeFragment() {
        // Required empty public constructor
    }



    private static final String TAG = "FavouriteFragment";

    private RecyclerView recyclerView;
    private onlineModeFragmentCallback onlineModeFragmentCallback;
    private HomePageAdapter homePageAdapter;
    private TextView preText;

    interface onlineModeFragmentCallback {
        void onClick(int position);
    }

    public void setListener (onlineModeFragmentCallback listener) {
        this.onlineModeFragmentCallback = listener;
    }

//    public void dataUpdated() {
//        if (OnlineModeListHelper.onlineModeListHelper.onlineList.size() != 0) {
//            preText.setVisibility(View.GONE);
//        } else {
//            preText.setVisibility(View.VISIBLE);
//        }
//
//        homePageAdapter.setAudioList(FavouriteListHelper.favouriteListHelper.favouriteList);
//        homePageAdapter.notifyDataSetChanged();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_online_mode, container, false);
        preText = (TextView) v.findViewById(R.id.preText);


        if (OnlineModeListHelper.onlineModeListHelper.onlineList != null &&  OnlineModeListHelper.onlineModeListHelper.onlineList.size() != 0) {
            preText.setVisibility(View.GONE);
        }

        if (OnlineModeListHelper.onlineModeListHelper.onlineList != null)
            initRecyclerView(v);
        return v;
    }

    private void initRecyclerView (View view) {
        Log.d(TAG, "initRecyclerView: ");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        homePageAdapter = new HomePageAdapter(getContext(),OnlineModeListHelper.onlineModeListHelper.onlineList);
        recyclerView.setAdapter(homePageAdapter);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        homePageAdapter.setListener(new HomePageAdapter.Listener() {
            @Override
            public void onClick(int position) {
                if (onlineModeFragmentCallback != null)
                    onlineModeFragmentCallback.onClick(position);
            }
        });
    }

}
