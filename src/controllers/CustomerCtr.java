package controllers;

import models.Customer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

public class CustomerCtr {

    Service service;

    public CustomerCtr(){
        this.service = new Service("http://localhost:8080/");
    }

    public Customer get(String id){
        JSONObject response = service.GET("customers/" + id);
        if(response != null) return new Customer(response);
        else return null;
    }

    public boolean create(Customer customer){
        boolean created = false;
        String response = service.POST("customers", customer.toJSON());
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

    public boolean edit(Customer customer){
        boolean edited = false;
        String response = service.PUT("customers/" + customer.getId(), customer.toJSON());
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

    public boolean setMessage(String id, String idMessage){
        boolean edited = false;
        String response = service.PUT("customers/" + id + "/message/" + idMessage, null);
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
        String response = service.DELETE("customers/" + id);
        return response == "";
    }
}
