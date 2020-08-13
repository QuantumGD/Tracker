package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class report_bar extends Fragment implements View.OnClickListener

    {
        View vrep_bar;
        String day;
        String today;
        String uids;
        String tbf;
        String tct;
        EditText date_from;
        EditText date_to;
        Button re_bshow;
        SharedPreferences myhealth;
        SharedPreferences calft;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            vrep_bar = inflater.inflate(R.layout.re_bar, container, false);
            date_from = (EditText) vrep_bar.findViewById(R.id.re_from_date);
            date_to = (EditText) vrep_bar.findViewById(R.id.re_to_date);
            re_bshow = (Button) vrep_bar.findViewById(R.id.bar_show);
            myhealth = this.getActivity().getSharedPreferences("fname", Context.MODE_PRIVATE);
            uids = myhealth.getString("Uid",null);
            calft = this.getActivity().getSharedPreferences("Calftt",Context.MODE_PRIVATE);
            date_from.setOnClickListener(this);
            date_to.setOnClickListener(this);
            re_bshow.setOnClickListener(this);
            date_from.setInputType(InputType.TYPE_NULL);
            date_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        showDatePickerDialogf();
                    }
                }
            });
            date_to.setInputType(InputType.TYPE_NULL);
            date_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        showDatePickerDialogt();
                    }
                }
            });

        return vrep_bar;
    }
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.re_from_date:

                    showDatePickerDialogf();

                    break;
                case R.id.re_to_date:

                    showDatePickerDialogt();
                    break;
                case R.id.bar_show:
                    day = date_from.getText().toString();
                    today = date_to.getText().toString();
                   setbarChart();
                    start();
                    break;
                default:
                    break;
            }
        }

        protected void showDatePickerDialogf() {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth) {

                    report_bar.this.date_from.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }

        protected void showDatePickerDialogt() {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth) {

                    report_bar.this.date_to.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }

       public void setbarChart()  {

           BarChart chart = (BarChart) vrep_bar.findViewById(R.id.barChart);
           chart.setDescription(null);
           chart.setPinchZoom(false);
           chart.setScaleEnabled(true);
           chart.setDrawBarShadow(false);
           chart.setDrawGridBackground(false);
          int tbf = calft.getInt("tbf",2);
          int tct = calft.getInt("tct",2);

           float barWidth = 0.7f;
           float barSpace = 0f;
           float groupSpace = 0.5f;
           int groupCount =1 ;

           ArrayList<String> xVals = new ArrayList<String>();

           xVals.add("cal situation");
           ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
           ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

           yVals1.add(new BarEntry(1, (float)tbf));
           yVals2.add(new BarEntry(1,  (float)tct));


           BarDataSet set1, set2;
           set1 = new BarDataSet(yVals1, "Total Cal consumed");
           set1.setColor(Color.rgb(255,133,51));
           set2 = new BarDataSet(yVals2, "Total Cal burned");
           set2.setColor(Color.rgb(128,128,255));

           BarData data = new BarData(set1, set2);
           data.setValueFormatter(new LargeValueFormatter());

           //X-axis
           XAxis xAxis = chart.getXAxis();
           xAxis.setGranularity(1f);
           xAxis.setGranularityEnabled(true);
           xAxis.setCenterAxisLabels(true);
           xAxis.setDrawGridLines(false);
           xAxis.setAxisMaximum(4);
           xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
           xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

           //Y-axis
           chart.getAxisRight().setEnabled(false);
           YAxis leftAxis = chart.getAxisLeft();
           leftAxis.setValueFormatter(new LargeValueFormatter());
           leftAxis.setDrawGridLines(false);
           leftAxis.setAxisMinimum(0f);

           // create a MarkerView
           //           MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker);
           //        chart.setMarker(mv);

           chart.setData(data);
           chart.getBarData().setBarWidth(barWidth);
           chart.getXAxis().setAxisMinimum(0);
           chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
           chart.groupBars(0, groupSpace, barSpace);
           chart.getData().setHighlightEnabled(false);
           chart.invalidate();
       }

        public void start(){
            Calfft c = new Calfft();
            c.execute(uids);
        }

        private class Calfft extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                return RestClient.findCaltask2(uids,day,today);
            }

            protected void onPostExecute(String message) {
                findCaltask2(message);
            }

        }
        public String findCaltask2(String result) {

            String snippet = "";

            try {
                JSONArray jsonObjs = new JSONArray(result);

                for (int i = 0; i < jsonObjs.length(); i++) {
                    JSONObject ima = (JSONObject) jsonObjs.get(i);
                    int tbf = ima.getInt("totCaloBurn");
                    int tct = ima.getInt("totCalConsum");
                    SharedPreferences.Editor eFname = calft.edit();
                    eFname.putInt("tbf",tbf);
                    eFname.putInt("tct",tct);
                    eFname.commit();
                    snippet = tbf+"" + ""+tct ;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return snippet ;
        }

}
