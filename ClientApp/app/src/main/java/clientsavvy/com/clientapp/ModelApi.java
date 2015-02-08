package clientsavvy.com.clientapp;


import java.util.List;

import clentsavvy.com.clientapp.model.Model;
import retrofit.http.GET;
import retrofit.Callback;

public interface ModelApi {

    @GET("/test.json")
    public void getFeed(Callback<List<Model>> response);


}
