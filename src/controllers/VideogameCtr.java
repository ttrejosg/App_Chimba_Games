package controllers;

import models.Customer;
import models.Videogame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

import java.util.ArrayList;
import java.util.List;

public class VideogameCtr {
    Service service;

    public VideogameCtr(){

    }

    public Videogame get(String id){
        JSONObject response = service.GET("videogames/" + id);
        if(response != null) return new Videogame(response);
        else return null;
    }

    public ArrayList<Videogame> getby(String by, String attribute){
        JSONArray response = service.GET_ALL("videogames/" + by + "/" + attribute);
        ArrayList<Videogame> videogames = new ArrayList<>();
        if(response != null){
            for (Object videogame : response) {
                Videogame newVideogame = new Videogame((JSONObject) videogame);
                videogames.add(newVideogame);
            }
        }
        return videogames;
    }

    public ArrayList<Videogame> getAll(){
        JSONArray response = service.GET_ALL("videogames");
        ArrayList<Videogame> videogames = new ArrayList<>();
        for (Object v : response) {
            Videogame videogame = new Videogame((JSONObject) v);
            videogames.add(videogame);
        }
        return videogames;
    }

    public boolean create(Videogame videogame){
        boolean created = false;
        String response = service.POST("videogames", videogame.toJSON());
        try{
            JSONParser JSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) JSONParser.parse(response.toString());
            Videogame v = new Videogame(jsonObject);
            created = true;
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return created;
    }

    public boolean edit(Videogame videogame){
        boolean edited = false;
        String response = service.PUT("videogames/" + videogame.getId(), videogame.toJSON());
        try{
            JSONParser JSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) JSONParser.parse(response.toString());
            Videogame v = new Videogame(jsonObject);
            edited = true;
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return edited;
    }

    public boolean delete(String id){
        String response = service.DELETE("videogames/" + id);
        return response == "";
    }

    public void setService(Service service){
        this.service = service;
    }
}
