package com.example.quakequack;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static ArrayList<Earthquake> fetchEarthquakeData(String requestUrl){

        URL url=createURL(requestUrl);
        String jsonResponse=null;
        try{
            jsonResponse=makeHttpRequest(url);
        }
        catch(IOException e){
            Log.e(LOG_TAG,"Error closing input stream",e);
        }
        return extractEarthquakes(jsonResponse);
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output=new StringBuilder();
        if(inputStream != null){
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line=br.readLine();
            while(line != null){
                output.append(line);
                line=br.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String requestUrl) {
        URL url=null;
        try{
            url=new URL(requestUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error in creating",e);
        }
        return url;
    }

    public static ArrayList<Earthquake> extractEarthquakes(String JSon){
        if(TextUtils.isEmpty(JSon)){
            return null;
        }

        try {
            ArrayList<Earthquake> earthquakes=new ArrayList<>();
            JSONObject jsonObject = new JSONObject(JSon);
            JSONArray array =jsonObject.optJSONArray("features");
            for(int i=0;i<array.length();i++){
                JSONObject object=array.optJSONObject(i);
                JSONObject objectp=object.optJSONObject("properties");
                double magnitude=objectp.optDouble("mag");
                String place=objectp.optString("place");
                long time=objectp.optLong("time");
                String uri = objectp.optString("url");
                earthquakes.add(new Earthquake(magnitude,place,time,uri));
            }
            return earthquakes;
        }
        catch(JSONException j){
            Log.e(LOG_TAG,"Problem in parsing JSON results",j);
        }

        return null;
    }
}

