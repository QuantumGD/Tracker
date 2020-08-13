package com.example.calorietracker;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class my_daily_diet extends Fragment implements View.OnClickListener {
    Button search;
    SearchView sear;
    ImageView image;
    TextView con;
    TextView fooditem;
    View vmy_daily_diet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vmy_daily_diet = inflater.inflate(R.layout.activity_my_daily_diet, container, false);

        sear =(SearchView) vmy_daily_diet.findViewById(R.id.getsearch);
        con = (TextView) vmy_daily_diet.findViewById(R.id.des);
        fooditem = (TextView) vmy_daily_diet.findViewById(R.id.fooditem);
        image =(ImageView) vmy_daily_diet.findViewById(R.id.ima);
        search = (Button) vmy_daily_diet.findViewById(R.id.search);
        search.setOnClickListener(this);
        return vmy_daily_diet;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.search:
                getSearch();

          //      Intent intent = new Intent(signup.this, MainActivity.class);
           //     startActivity(intent);
                break;
            default:
                break;
        }

    }
    public  void getSearch(){
        String keyword = sear.getQuery().toString();
        Seschimage si = new Seschimage();
        si.execute(keyword);
        SearchAsyncTask s = new SearchAsyncTask();
        s.execute(keyword);
        SeschFood ss = new SeschFood();
        ss.execute(keyword);
    }

    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override protected String doInBackground(String... params) {
            return API.search(params[0], new String[]{"num"}, new String[]{"1"});
        }

        @Override
        protected void onPostExecute(String result) {
            con.setText(API.getSnippet(result));
        }
    }

    private class Seschimage extends AsyncTask<String, Void, Bitmap> {
        @Override protected Bitmap doInBackground(String... params) {
            return API.getBitmapFromURL(API.getImage(API.searchImage(params[0], new String[]{"num"}, new String[]{"1"})));
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result );
        }
    }

    private class SeschFood extends AsyncTask<String, Void, String> {
        @Override protected String doInBackground(String... params) {
            return API.searchFood(params[0], new String[]{"num"}, new String[]{"1"});
        }

        @Override
        protected void onPostExecute(String result) {
            String fat = "";
            String cal ="";
            try {
                JSONArray jsonObjs = new JSONObject(result).getJSONArray("parsed");
                for (int i = 0; i < jsonObjs.length(); i++) {
                    JSONObject ima = (JSONObject) jsonObjs.get(0);
                    JSONObject MM = ima.getJSONObject("food");
                    JSONObject ff = MM.getJSONObject("nutrients");
                     cal = ff.getString("ENERC_KCAL");
                     fat = ff.getString("FAT");
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            fooditem.setText("Cal:"+cal+"  fat:"+ fat +"per 500g");
        }
    }
}




