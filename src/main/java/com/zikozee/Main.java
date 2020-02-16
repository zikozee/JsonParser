package com.zikozee;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
	// write your code here
        JSONParser parser = new JSONParser();

        String appKey = "YOUR_API_KEY";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=Bahamas&appid=" + appKey;
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn  = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(responseCode != 200)
                throw new RuntimeException("HttpResponseCOde: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while((inputLine = reader.readLine()) != null){
                JSONObject jsonObject = (JSONObject) parser.parse(inputLine);
                JSONObject anotherObject = (JSONObject)jsonObject.get("main");
                System.out.println(anotherObject);
                double temp = (Double) anotherObject.get("temp");
                System.out.println(temp);

                Long zone = (Long)jsonObject.get("timezone");
                System.out.println(zone);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
