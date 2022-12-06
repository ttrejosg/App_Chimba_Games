package controllers;

import models.Bill;
import models.Chat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.Service;

import java.util.ArrayList;

public class BillCtr {
    Service service;

    public BillCtr(){
    }

    public Bill get(String id){
        JSONObject response = service.GET("bills/" + id);
        if(response != null) return new Bill(response);
        else return null;
    }

    public ArrayList<Bill> getBills(String customerId){
        JSONArray response = service.GET_ALL("bills/customer/" + customerId);
        ArrayList<Bill> bills = new ArrayList<>();
        if(response != null){
            for (Object bill : response) {
                Bill new_bill = new Bill((JSONObject) bill);
                bills.add(new_bill);
            }
        }
        return bills;
    }

    public boolean create(Bill bill, String customerId, String videogameId){
        boolean created = false;
        String response = service.POST("bills/customer/" + customerId + "/videogame/" + videogameId, bill.toJSON());
        try{
            JSONParser JSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) JSONParser.parse(response.toString());
            Bill c = new Bill(jsonObject);
            created = true;
        } catch (ParseException e) {
            System.out.println("Error in JsonParser");
        }
        return created;
    }


    public boolean delete(String id){
        String response = service.DELETE("bills/" + id);
        return response == "";
    }

    public void setService(Service service){
        this.service = service;
    }
}
