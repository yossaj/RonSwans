package com.example.ronswansquoter.utilites;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ronswansquoter.MainActivity;
import com.example.ronswansquoter.R;

import java.util.prefs.Preferences;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private String[] mQuoteArray;
    private boolean largeText = false;
    private String color = "white";
    private boolean lightText = false;
    public Adapter(){

    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder{

        public final TextView mQuoteHolderTextView;
        public AdapterViewHolder(View itemView) {
            super(itemView);

            mQuoteHolderTextView = (TextView) itemView.findViewById(R.id.quote_items);
        }
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.quoter_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new AdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {


        String quoteOfTheMoment = mQuoteArray[position];
        quoteOfTheMoment = quoteOfTheMoment.replaceAll("\\[","").replaceAll("\\]","");
        holder.mQuoteHolderTextView.setText(quoteOfTheMoment);
        holder.mQuoteHolderTextView.setAlpha(0.9f);
        holder.mQuoteHolderTextView.setBackgroundColor(Color.parseColor(color));
        holder.mQuoteHolderTextView.setTextSize(22);
        if(largeText){
            holder.mQuoteHolderTextView.setTextSize(34);
        }
        if(lightText){
            holder.mQuoteHolderTextView.setTextColor(Color.parseColor("#FFF8ED"));
        }
    }

    @Override
    public int getItemCount() {
        if(mQuoteArray == null){ return 0;}
        return mQuoteArray.length;
    }

    public void setmQuoteArray(String[] quoteArray) {
        mQuoteArray = quoteArray;
        notifyDataSetChanged();
        if(color != "white"){
            lightText = true;
        }
    }

    public void getTextPrefs(boolean largeTextOnOff){
        largeText = largeTextOnOff;
    }

    public void getColorPrefs(String prefColor){
        color = prefColor;
    }


}
