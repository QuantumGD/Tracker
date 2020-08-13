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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class report extends Fragment implements View.OnClickListener {
    View vrep;
    EditText date;
    String day;
    String uids;
    String tb;
    String tc;
    String rem;
    Button re_show;
    SharedPreferences myhealth;
    SharedPreferences calgett;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vrep = inflater.inflate(R.layout.reports, container, false);
        date = (EditText) vrep.findViewById(R.id.re_date);
        re_show = (Button) vrep.findViewById(R.id.re_show);
        myhealth = this.getActivity().getSharedPreferences("fname", Context.MODE_PRIVATE);
        uids = myhealth.getString("Uid",null);
        calgett = this.getActivity().getSharedPreferences("calgget",Context.MODE_PRIVATE);
        date.setOnClickListener(this);
        re_show.setOnClickListener(this);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDatePickerDialog();
                }
            }
        });


        return vrep;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.re_show:
                day = date.getText().toString();
                setPieChart();
                start();
                break;
            case R.id.re_date:
                 showDatePickerDialog();
                 break;
            default:
                break;
        }
    }
    protected void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear , int dayOfMonth) {

                report.this.date.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    public void setPieChart() {
        PieChart p = (PieChart) vrep.findViewById(R.id.piechart);
        p.setUsePercentValues(true);
        p.getDescription().setEnabled(true);
        p.setExtraOffsets(5, 10, 30, 5);
        p.setDragDecelerationFrictionCoef(0.9f);
        p.setTransparentCircleRadius(61f);
        p.setHoleColor(Color.WHITE);
        p.animateY(1000, Easing.EasingOption.EaseInOutCubic);
       int tb = calgett.getInt("tb",2);
       int tc = calgett.getInt("tc",2);
       int rem = calgett.getInt("rem",2);

       Float tbf = (float)tb;
       Float tcf = (float)tc;
       Float remf = (float)rem;

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(tbf, "total cal consumed"));
        yValues.add(new PieEntry(tcf, "total cal burn"));
        yValues.add(new PieEntry(remf, "remaining"));

        PieDataSet dataSet = new PieDataSet(yValues, "Cal report");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);
        p.setData(pieData);

    }
    public void start(){
        Calget c = new Calget();
        c.execute(uids);
    }
    private class Calget extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return RestClient.findCal(uids,day);
        }

        protected void onPostExecute(String message) {
            getCal(message);
        }

    }
    public  String getCal(String result) {
        String snippet = "";

        try {
            JSONArray jsonObjs = new JSONArray(result);

            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject ima = (JSONObject) jsonObjs.get(i);
                int tb = ima.getInt("totCaloBurn");
                int tc = ima.getInt("totCalConsum");
                int rem = ima.getInt("remaining");
                SharedPreferences.Editor eFname = calgett.edit();
                eFname.putInt("tb",tb);
                eFname.putInt("tc",tc);
                eFname.putInt("rem",rem);
                eFname.commit();
                snippet = tb+"" + ""+tc +""+""+rem;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return snippet ;
    }
}
