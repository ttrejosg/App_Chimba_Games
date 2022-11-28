package services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class Service {
    String server = "";

    public Service(String server) {
        this.server = server;
    }

    public JSONObject GET(String endPoint) {
        StringBuffer response = new StringBuffer();
        try {
            URL urlForGetRequest = new URL(this.server+"/"+endPoint);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                while ((readLine = in.readLine()) != null) response.append(readLine);
                in.close();
            } else System.out.println("GET NOT WORKED");
        } catch (MalformedURLException ex) {} catch (IOException ex) {}

        JSONParser JSONParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) JSONParser.parse(response.toString());
        }catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return jsonObject;
    }
}
