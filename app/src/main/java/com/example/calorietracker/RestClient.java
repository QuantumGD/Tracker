package com.example.calorietracker;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class RestClient {
    private static final String BASE_URL =
            "http://192.168.0.103:8080/task/webresources/";  // course not UPPER task1.food

    public static String findUsername(String Uname) {
        final String methodPath = "task1.credential/findByUsername/" +Uname ; //initialise
        URL url = null ;
        HttpURLConnection conn = null;
        String Uifo = ""; //Makin HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                Uifo += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return Uifo;
    }

    public static String findTotcalconsum(String uid,String time) {
        final String methodPath = "task1.consumption/findTotcalconsum/"+ uid + "/"+ time ; //initialise timr yyyy-MM-dd
        URL url = null ;
        HttpURLConnection conn = null;
        String Uifo = ""; //Makin HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                Uifo += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return Uifo;
    }

    public static String findTotcalburenperstep(String uid) {
        final String methodPath = "task1.seruser/findCalburenStepByUserid/"+ uid  ; //initialise
        URL url = null ;
        HttpURLConnection conn = null;
        String Uifo = ""; //Makin HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                Uifo += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return Uifo;
    }
    public static String findCal(String uid,String time) {
        final String methodPath = "task1.report/findTotcal/"+ uid + "/"+ time ; //initialise timr yyyy-MM-dd
        URL url = null ;
        HttpURLConnection conn = null;
        String Uifo = ""; //Makin HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                Uifo += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return Uifo;
    }

    public static String findCaltask2(String uid, String timef, String timet) {
        final String methodPath = "task1.report/findTimeperiod/"+ uid + "/"+ timef + "/"+timet ; //initialise timr yyyy-MM-dd
        URL url = null ;
        HttpURLConnection conn = null;
        String Uifo = ""; //Makin HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                Uifo += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return Uifo;
    }

    public static void createCourse(Course seruser){
        //initialise
         URL url = null;
         HttpURLConnection conn = null;
         final String methodPath="/task1.seruser/";
         try { Gson gson =new Gson();
             String stringCourseJson=gson.toJson(seruser);
             url = new URL(BASE_URL + methodPath);
             //open the connection
              conn = (HttpURLConnection) url.openConnection();
              //set the timeout
              conn.setReadTimeout(10000);
              conn.setConnectTimeout(15000);
              //set the connection method to POST
             conn.setRequestMethod("POST");
             // set the output to true
              conn.setDoOutput(true);
              //set length of the data you want to send
              conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
              //add HTTP headers
             conn.setRequestProperty("Content-Type", "application/json");
             //Send the POST out
              PrintWriter out= new PrintWriter(conn.getOutputStream());
              out.print(stringCourseJson); out.close();
              Log.i("error",new Integer(conn.getResponseCode()).toString());
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             conn.disconnect();
         }
    }
    }





