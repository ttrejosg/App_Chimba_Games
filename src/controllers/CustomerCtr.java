package controllers;

import models.Admin;
import models.Customer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

import java.util.ArrayList;

public class CustomerCtr {

    Service service;

    public CustomerCtr(){

    }

    public Customer get(String id){
        JSONObject response = service.GET("customers/" + id);
        if(response != null) return new Customer(response);
        else return null;
    }

    public ArrayList<Customer> getAll(){
        JSONArray response = service.GET_ALL("customers");
        ArrayList<Customer> customers= new ArrayList<>();
        for (Object customer : response) {
            Customer newCustomer = new Customer((JSONObject) customer);
            customers.add(newCustomer);
        }
        return customers;
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

    public boolean delete(String id){
        String response = service.DELETE("customers/" + id);
        return response == "";
    }

    public Customer getByUsername(String username){
        JSONObject response = service.GET("customers/username/" + username);
        if(response != null) return new Customer(response);
        else return null;
    }

    public void setService(Service service){
        this.service = service;
    }
}
