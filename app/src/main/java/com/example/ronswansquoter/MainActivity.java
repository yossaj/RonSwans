package com.example.ronswansquoter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]> {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<String> tempArray;
    private static final int LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_quoter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        tempArray = new ArrayList<>();
        LoaderManager.LoaderCallbacks<String[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(LOADER_ID, bundleForLoader, callback);
    }

    public void newQuote(View view){
        getSupportLoaderManager().getLoader(LOADER_ID).forceLoad();
    }


    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {



        return  new AsyncTaskLoader<String[]>(this) {

            String[] mQuoteData = null;

            @Override
            protected void onStartLoading() {
                if(mQuoteData!= null){
                    deliverResult(mQuoteData);
                }else {
                    forceLoad();
                }

            }

            @Override
            public String[] loadInBackground() {
                String tempQuote = null;

                try {
                    tempQuote = NetworkUtils.getResponseFromHttpUrl();
                    tempArray.add(0, tempQuote);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] stringArrayQuotes = new String[tempArray.size()];
                for(int i = 0; i < tempArray.size(); i++){
                    stringArrayQuotes[i] = tempArray.get(i);
                }
                return stringArrayQuotes;
            }

            @Override
            public void deliverResult( String[] data) {
                mQuoteData = data;
                super.deliverResult(data);
            }
        };


        }

    @Override
    public void onLoadFinished(@NonNull Loader<String[]> loader, String[] data) {
        mAdapter.setmQuoteArray(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String[]> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuitems,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.quote_reset){
            tempArray = new ArrayList<>();
            mAdapter.setmQuoteArray(null);

        }
        return super.onOptionsItemSelected(item);
    }
}
