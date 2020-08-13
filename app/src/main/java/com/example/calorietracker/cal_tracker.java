package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cal_tracker extends Fragment
        implements View.OnClickListener {
    View vcal;
    String ss;
    String tots;
    String uids;
    String now;
    Double totcoum;
    Double tot;
    TextView setGoal;
    TextView totStep;
    TextView consum;
    TextView burn;
    Date day;
    Button cshow;
    SharedPreferences setgdata;
    SharedPreferences toalsteps;
    SharedPreferences myhealth;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vcal = inflater.inflate(R.layout.calorie_tracker, container, false);
        setGoal = (TextView) vcal.findViewById(R.id.ck_setg);
        totStep = (TextView) vcal.findViewById(R.id.ck_toyStep);
        consum = (TextView) vcal.findViewById(R.id.ck_consum);
        burn = (TextView) vcal.findViewById(R.id.ck_burn);
        cshow = (Button) vcal.findViewById(R.id.ck_show);
        setgdata = this.getActivity().getSharedPreferences("setgoal", Context.MODE_PRIVATE);
        toalsteps = this.getActivity().getSharedPreferences("tot",Context.MODE_PRIVATE);
        myhealth = this.getActivity().getSharedPreferences("fname",Context.MODE_PRIVATE);
        tots = toalsteps.getString("totTal",null);
        ss = setgdata.getString("setggo", null);
        uids = myhealth.getString("Uid",null);
        cshow.setOnClickListener(this);
        day = new Date();
        SimpleDateFormat sud = new SimpleDateFormat("yyyy-MM-dd");
        now = sud.format(day);
        return vcal;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ck_show:
                show();
                break;

        }

    }

    public void show() {
        Caldata c = new Caldata();
        c.execute(uids);

        Calburn cal = new Calburn();
        cal.execute(uids);

        if (!setGoal.getText().toString().isEmpty()||!totStep.getText().toString().isEmpty()) {

            setGoal.setError("no data");
            totStep.setError("no data");
        } else {
            setGoal.setText(ss);
            totStep.setText(tots);
        }
    }

    private class Caldata extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
           return RestClient.findTotcalconsum(uids,now);
        }
        protected void onPostExecute(String message) {
            String totcoum =message;
            consum.setText(totcoum);
        }
    }


        private  class Calburn extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.findTotcalburenperstep(uids);
            }

            protected void onPostExecute(String message) {
                if (message.isEmpty()){
                    burn.setError("no data");
                }else{
                totcoum = Double.parseDouble(message);
                tot = totcoum * Double.parseDouble(tots);
                DecimalFormat df = new DecimalFormat("0.000");
                String s = df.format(tot);
                burn.setText(s);
                }
            }


        }
}




