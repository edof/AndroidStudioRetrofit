package clientsavvy.com.clientapp;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import clentsavvy.com.clientapp.model.Model;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class MainActivity extends ListActivity {

    ProgressBar pb;
    public static final String PHOTOS_URL= "https://dl.dropboxusercontent.com/s/8ddcfel4p1rv7sg/test.json";
    public static final String ENDPOINT = "https://dl.dropboxusercontent.com/s/8ddcfel4p1rv7sg";
    List<Model> modelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb= (ProgressBar) findViewById(R.id.progressBar);

         pb.setVisibility(View.INVISIBLE);

        if (isOnline()){

            requestData(PHOTOS_URL);
        } else {

            Toast.makeText(this,"Network is not Available",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
              if (isOnline()){

                  requestData(PHOTOS_URL);
              } else {

                  Toast.makeText(this,"Network is not Available",Toast.LENGTH_LONG).show();
              }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestData(String uri) {

        //RETROFIT code here
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();
        ModelApi api= adapter.create(ModelApi.class);

        api.getFeed(new Callback<List<Model>>() {
            @Override
            public void success(List<Model> arg0, retrofit.client.Response response) {

                modelList=arg0;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });

    }

    protected void updateDisplay(){

        ModelAdapter adapter = new ModelAdapter(this,R.layout.list_item,modelList);
        setListAdapter(adapter);

    }

    protected boolean isOnline(){

        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo!= null&&netInfo.isConnectedOrConnecting()){
               return  true;

        }else {
            return false;
        }
    }
}
