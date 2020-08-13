package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class homepagefrag extends Fragment implements View.OnClickListener {
    View vMain;
    EditText setgoal;
    Button edit;
    Button save;
    String s;
    SharedPreferences setgdata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.content_homepage, container, false);
        setgoal = (EditText) vMain.findViewById(R.id.setcal);
        edit = (Button) vMain.findViewById(R.id.edit);
        save = (Button) vMain.findViewById(R.id.save);
        setgdata = this.getActivity().getSharedPreferences("setgoal", Context.MODE_PRIVATE);
        edit.setOnClickListener(this);
        save.setOnClickListener(this);
        return vMain;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.edit:
                edit();
               break;
            case R.id.save:
                save();
                break;
            default:
                break;
        }
    }

    public void edit(){

        setgoal.setFocusable(true);
        setgoal.setFocusableInTouchMode(true);
        setgoal.requestFocus();
        if (setgoal.getText().toString().length()>2000){
            setgoal.setError("less than 2000");
        }else {
            s = setgoal.getText().toString();
        }
    }
    public void save(){


        SharedPreferences.Editor eGoal = setgdata.edit();
        eGoal.putString("setggo",setgoal.getText().toString());
        eGoal.commit();
        setgoal.setFocusable(false);
        setgoal.setFocusableInTouchMode(false);
    }
}
