package controllers;

import models.Chat;
import models.Customer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

public class ChatCtr {
    Service service;

    public ChatCtr(){
        this.service = new Service("http://localhost:8080/");
    }

    public Chat get(String id){
        JSONObject response = service.GET("chats/" + id);
        if(response != null) return new Chat(response);
        else return null;
    }

    public boolean create(Chat chat, String id1, String id2){
        boolean created = false;
        String response = service.POST("chats/customers/" + id1 + "/" + id2, chat.toJSON());
        try{
            JSONParser JSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) JSONParser.parse(response.toString());
            Chat c = new Chat(jsonObject);
            created = true;
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return created;
    }


    public boolean delet(String id){
        String response = service.DELETE("chats/" + id);
        return response == "";
    }
}
