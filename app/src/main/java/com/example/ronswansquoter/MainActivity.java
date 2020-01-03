package com.example.ronswansquoter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ronswansquoter.utilites.Adapter;
import com.example.ronswansquoter.utilites.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mInitalQuote;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<String> tempArray;
    private String quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_quoter);
        mInitalQuote = (TextView)findViewById(R.id.error_message_display);
        newQuote(mInitalQuote);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        tempArray = new ArrayList<>();


    }

    public void newQuote(View view){
        new QuoteRequest().execute();
    }

    public class QuoteRequest extends AsyncTask<URL, Void, String[]> {



        @Override
        protected String[] doInBackground(URL... urls) {
            String tempQuote = null;

            try {
                /* network response returns a string rather than a json object - this needs to be turned into a String[] in order for recycleView to accept it*/
                tempQuote = NetworkUtils.getResponseFromHttpUrl();
                tempArray.add(tempQuote);
            } catch (IOException e) {
                e.printStackTrace();
            }



            String[] stringArrayQuotes = new String[tempArray.size()];

            for(int i = 0; i < tempArray.size(); i++){
                /*tempQuote = tempQuote.replaceAll("\\[","").replaceAll("\\]","");*/
                stringArrayQuotes[i] = tempQuote;
            }

            return stringArrayQuotes;
        }

        @Override
        protected void onPostExecute(String[] quotes) {
            mAdapter.setmQuoteArray(quotes);
            super.onPostExecute(quotes);
        }
    }
}
