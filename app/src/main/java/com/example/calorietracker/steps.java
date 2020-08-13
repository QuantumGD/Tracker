package com.example.calorietracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class steps extends Fragment implements View.OnClickListener {
    View vstep;
    Basesteps db = null;
    Button addbtn;
    Button removebtn;
    Button showr;
    EditText adds;
    EditText rmoves;
    TextView l_steps;
    TextView conv;
    SharedPreferences toalsteps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vstep = inflater.inflate(R.layout.steps, container, false);
        db = Room.databaseBuilder(getActivity().getApplicationContext(), Basesteps.class, "stepsDatabase")
                .fallbackToDestructiveMigration()
                .build();
        addbtn = (Button) vstep.findViewById(R.id.addstep);
        removebtn = (Button) vstep.findViewById(R.id.btnrestep);
        showr =(Button) vstep.findViewById(R.id.btnShowr);
        adds = (EditText) vstep.findViewById(R.id.v_steps);
        rmoves = (EditText) vstep.findViewById(R.id.v_removeSteps);
        toalsteps = this.getActivity().getSharedPreferences("tot", Context.MODE_PRIVATE);
        l_steps = (TextView) vstep.findViewById(R.id.v_lsteps);
        conv = (TextView) vstep.findViewById(R.id.conv_steps);
        addbtn.setOnClickListener(this);
        removebtn.setOnClickListener(this);
        showr.setOnClickListener(this);
        return vstep;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.addstep:
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();
                break;
            case R.id.btnrestep:
                String tt =rmoves.getText().toString();
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute(tt);
                break;
            case R.id.btnShowr:
                ReadDatabase ReadDatabase = new ReadDatabase();
                ReadDatabase.execute();
                break;
        }

    }





   private class InsertDatabase extends AsyncTask<Void, Void, String> {

       @Override
       protected String doInBackground(Void... params) {
           if (adds.getText().toString().isEmpty()|| Integer.parseInt(adds.getText().toString()) <1 ||Integer.parseInt(adds.getText().toString())>40000 ){
               return "less than 4000";
           } else {
               Date dat = new Date();
               SimpleDateFormat sud = new SimpleDateFormat("yyyy-MM-dd HH:mm");
               String now = sud.format(dat);
               String s = adds.getText().toString();
               Stepdata stepdata = new Stepdata(Integer.parseInt(s),dat);
               long id = db.stepsDAO().insert(stepdata);

               return "success";
           }
       }

        @Override
       protected void onPostExecute(String details) {

           conv.setText(details);
        }
   }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Stepdata> steps = db.stepsDAO().getAll();
            int ittoal = db.stepsDAO().sumAll();
            String ttoal =Integer.toString(ittoal);
            SharedPreferences.Editor eFname = toalsteps.edit();
            eFname.putString("totTal",ttoal);
            eFname.commit();
            SimpleDateFormat sud = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (!(steps.isEmpty() || steps == null) ){
                String all = "";
                for (Stepdata temp : steps) {
                    String stepsstr = ("Your step record:" + temp.getsteps() + " " + "Time:"+  sud.format(temp.getDate()) +"\n" +"your total step is: "+ ttoal +" , "+"\n" );
                    all = all + stepsstr;
                }
                return all;
            } else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {

            l_steps.setText(details);
        }
    }
    private class DeleteDatabase extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            int i = Integer.parseInt(params[0]);
            db.stepsDAO().deleByID(i);

            return null;
        }
        protected void onPostExecute(Void param) {

        }
    }
}
