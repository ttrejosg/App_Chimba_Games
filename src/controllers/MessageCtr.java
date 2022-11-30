package controllers;

import models.Customer;
import models.Message;
import models.Videogame;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

public class MessageCtr {
    Service service;

    public MessageCtr(){
        this.service = new Service("http://localhost:8080/");
    }

    public Message get(String id){
        JSONObject response = service.GET("messages/" + id);
        if(response != null) return new Message(response);
        else return null;
    }

    public boolean create(Message message, String chatId){
        boolean created = false;
        String response = service.POST("videogames/chat/" + chatId, message.toJSON());
        try{
            JSONParser JSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) JSONParser.parse(response.toString());
            Message c = new Message(jsonObject);
            created = true;
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return created;
    }


    public boolean delet(String id){
        String response = service.DELETE("messages/" + id);
        return response == "";
    }
}
