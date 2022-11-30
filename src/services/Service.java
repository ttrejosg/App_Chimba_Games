package services;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {
    String server = "";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public Service(String server) {
        this.server = server;
    }

    public JSONObject GET(String endPoint) {
        String response = "";
        System.out.println("solicitando a: " + this.server + endPoint);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.server + endPoint))
                .build();
        HttpResponse<String> r;
        try {
            r = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(r.body());
            response = r.body();
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (InterruptedException ex) {
            System.out.println("Error: " + ex);
        }

        JSONParser JSONParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) JSONParser.parse(response.toString());
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return jsonObject;
    }

    public JSONArray GET_ALL(String endPoint) {
        String response = "";
        System.out.println("solicitando a: " + this.server + endPoint);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.server + endPoint))
                .build();
        HttpResponse<String> r;
        try {
            r = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(r.body());
            response = r.body();
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (InterruptedException ex) {
            System.out.println("Error: " + ex);
        }

        JSONParser JSONParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray= (JSONArray) JSONParser.parse(response);
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return jsonArray;
    }

    public String POST(String endPoint, JSONObject data) {
        System.out.println("solicitando a: " + this.server + endPoint);
        String respuesta = "";
        try {
            String postEndpoint = this.server + endPoint;
            String inputJson = data.toJSONString();
            System.out.println("data " + inputJson);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(postEndpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            respuesta = (String) response.body();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return respuesta;
    }

    public String PUT(String endPoint, JSONObject data) {
        System.out.println("solicitando a: " + this.server + endPoint);
        String respuesta = "";
        try {
            String postEndpoint = this.server + endPoint;
            String inputJson = data.toJSONString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(postEndpoint))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(inputJson))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            respuesta = (String) response.body();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return respuesta;
    }

    public String DELETE(String endPoint) {
        System.out.println("solicitando a: " + this.server + endPoint);
        String respuesta = "";
        try {
            String deleteEndpoint = this.server + endPoint;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(deleteEndpoint))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return respuesta;
    }
}