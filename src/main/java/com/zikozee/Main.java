package com.zikozee;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class Main {

    public static void main(String[] args) {
	// write your code here
        JSONParser parser = new JSONParser();

        String appKey = "YOUR_API_KEY";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=Bahamas&appid=" + appKey;
        HttpURLConnection conn = null;
        try{
            URL url = new URL(urlString);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(responseCode != 200)
                throw new RuntimeException("HttpResponseCOde: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while((inputLine = reader.readLine()) != null){
                JSONObject jsonObject = (JSONObject) parser.parse(inputLine);
                log.info("jsonObject: "+jsonObject);
                JSONObject anotherObject = (JSONObject)jsonObject.get("main");
                log.info("another JSON: " + anotherObject);
                double temp = (Double) anotherObject.get("temp");
                log.info("TEMP: " +temp);

                Long zone = (Long)jsonObject.get("timezone");
                log.info("ZONE: " + zone);

                JSONArray jsonArray = (JSONArray)jsonObject.get("weather");
                log.info("JSONARRAY" +jsonArray);
                for(int i=0; i< jsonArray.size(); i++){
                    log.info("WEATHER " + (i + 1) +":" +jsonArray.get(i));
                }
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }

    }
}
