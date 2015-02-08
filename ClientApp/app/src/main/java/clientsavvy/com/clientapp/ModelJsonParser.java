package clientsavvy.com.clientapp;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clentsavvy.com.clientapp.model.Model;

public class ModelJsonParser {

    public static List<Model> parseFeed(String content){


        try {
            JSONArray ar = new JSONArray(content);
            List<Model> modelList = new ArrayList<>();
            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Model model = new Model();

                model.setId(obj.getInt("id"));
                model.setName(obj.getString("name"));
                model.setImage(obj.getString("image"));
                model.setStatus(obj.getString("status"));
                model.setProfilePic(obj.getString("profilePic"));
                model.setTimeStamp(obj.getDouble("timeStamp"));
                model.setUrl(obj.getString("url"));

                modelList.add(model);

            }
            return modelList;
        }catch (Exception e){

            e.printStackTrace();
            return null;
        }
    }
}
