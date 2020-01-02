package com.example.ronswansquoter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ronswansquoter.utilites.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mInitalQuote;
    private String quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInitalQuote = (TextView)findViewById(R.id.inital_quote);
        new QuoteRequest().execute();


    }

    public class QuoteRequest extends AsyncTask<URL, Void, String> {



        @Override
        protected String doInBackground(URL... urls) {
            String tempQuote = null;
            try {
                tempQuote = NetworkUtils.getResponseFromHttpUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return tempQuote;
        }

        @Override
        protected void onPostExecute(String quote) {
            quote = quote.replaceAll("\\[","").replaceAll("\\]","");
            mInitalQuote.setText(quote);
            super.onPostExecute(quote);
        }
    }
}
