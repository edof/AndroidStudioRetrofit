package clientsavvy.com.clientapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import clentsavvy.com.clientapp.model.Model;


public class ModelAdapter extends ArrayAdapter<Model>{

    private Context context;
    private List<Model> modelList;
    private LruCache<Integer,Bitmap> imageCache;
    private RequestQueue queue;

    public ModelAdapter(Context context, int resource , List<Model> objects){

        super(context,resource,objects);
        this.context=context;
        this.modelList=objects;

         //android equation to get max memory

       final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize= maxMemory/8;
        imageCache= new LruCache<>(cacheSize);

        queue= Volley.newRequestQueue(context);
    }

    public View getView(int position ,View convertView , ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.list_item,parent,false);

        //Display name in textview widget

        final Model model=modelList.get(position);
        TextView tv= (TextView) view.findViewById(R.id.textview1);
        TextView tv2= (TextView) view.findViewById(R.id.textview2);
        tv2.setText(model.getStatus());
        tv.setText(model.getName());

        //Display image in imageview
           Bitmap bitmap = imageCache.get(model.getId());
        //setting imageview final so that i can be accesed in onresponse method
        final ImageView imageView= (ImageView) view.findViewById(R.id.imageview1);
        if(bitmap !=null){

            imageView.setImageBitmap(model.getBitmap());

        }else{

            String imageUrl= model.getProfilePic();

            ImageRequest request= new ImageRequest(imageUrl, new com.android.volley.Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap arg0) {
                   imageView.setImageBitmap(arg0);
                    imageCache.put(model.getId(),arg0);
                }
            } , 200, 200, Bitmap.Config.ARGB_8888,
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("ModelAdapter",volleyError.getMessage());
                        }
                    });
            queue.add(request);
        }


        return view;
    }

}
