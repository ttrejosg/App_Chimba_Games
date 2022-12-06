package controllers;

import models.Customer;
import models.Message;
import models.Videogame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

import java.util.ArrayList;

public class MessageCtr {
    Service service;

    public MessageCtr(){

    }

    public Message get(String id){
        JSONObject response = service.GET("messages/" + id);
        if(response != null) return new Message(response);
        else return null;
    }

    public ArrayList<Message> getMessages(String chatId){
        JSONArray response = service.GET_ALL("messages/chat/" + chatId);
        ArrayList<Message> messages= new ArrayList<>();
        if(response != null){
            for (Object message : response) {
                Message newMessage = new Message((JSONObject) message);
                messages.add(newMessage);
            }
        }
        return messages;
    }

    public boolean create(Message message, String chatId, String customerId){
        boolean created = false;
        String response = service.POST("messages/chat/" + chatId + "/customer/" + customerId, message.toJSON());
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


    public boolean delete(String id){
        String response = service.DELETE("messages/" + id);
        return response == "";
    }

    public void setService(Service service){
        this.service = service;
    }
}
