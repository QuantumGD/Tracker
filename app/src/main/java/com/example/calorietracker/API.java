package com.example.calorietracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.UrlQuerySanitizer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;

public class API {
    private static final String API_KEY = "AIzaSyBSId8iowPVKb50RRliQJ5Qti8U81-SiTE";
    private static final String SEARCH_ID_cx = "011054919769573919664:_-atibpstyu";
    private static final String Application_Keys = "55447fad8b5b1e51d20b581546770662";
    private static final String applicationID ="33c4772c";

    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");

        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }

        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" +
                    API_KEY + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + query_parameter);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }

    public static String searchFood(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");

        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }

        try {
            url = new URL("https://api.edamam.com/api/food-database/parser?ingr="+keyword+"&app_id="+applicationID+"&app_key="+Application_Keys);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }


    public static String searchImage(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");

        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }

        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" +
                    API_KEY + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + query_parameter + "&searchType=image&alt=json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }


    public static String getSnippet(String result) {
        String snippet = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray != null && jsonArray.length() > 0) {
                snippet = jsonArray.getJSONObject(0).getString("snippet");
            }

            String split = ".";
            StringTokenizer token = new StringTokenizer(snippet, split);
            snippet = token.nextToken();
        } catch (Exception e) {
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    public static String getImage(String result) {
        String snippet = "";
        try {
            JSONArray jsonObjs = new JSONObject(result).getJSONArray("items");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject ima = (JSONObject) jsonObjs.get(i);
                JSONObject ims = new JSONObject(ima.getString("image"));
                snippet = ims.getString("thumbnailLink");

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return snippet;
    }
    public static Bitmap getBitmapFromURL(String image) {
        Bitmap myBitmap =null;
        try {
            URL url = new URL(image);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream input = conn.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            e.printStackTrace();
        } return myBitmap;

    }

}
