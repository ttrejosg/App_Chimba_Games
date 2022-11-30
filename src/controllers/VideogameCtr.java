package controllers;

import models.Customer;
import models.Videogame;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

public class VideogameCtr {
    Service service;

    public VideogameCtr(){
        this.service = new Service("http://localhost:8080/");
    }

    public Videogame get(String id){
        JSONObject response = service.GET("videogames/" + id);
        if(response != null) return new Videogame(response);
        else return null;
    }

    public boolean create(Videogame videogame){
        boolean created = false;
        String response = service.POST("videogames", videogame.toJSON());
        try{
            JSONParser JSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) JSONParser.parse(response.toString());
            Customer c = new Customer(jsonObject);
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
            Customer c = new Customer(jsonObject);
            edited = true;
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return edited;
    }

    public boolean delet(String id){
        String response = service.DELETE("videogames/" + id);
        return response == "";
    }
}
