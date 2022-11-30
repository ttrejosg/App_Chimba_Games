package controllers;

import models.Admin;
import models.Customer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.Service;

import java.util.ArrayList;
import java.util.List;

public class AdminCtr {
    Service service;

    public AdminCtr(){
        this.service = new Service("http://localhost:8080/");
    }

    public ArrayList<Admin> getAll(){
        JSONArray response = service.GET_ALL("admins");
        ArrayList<Admin> admins= new ArrayList<>();
        for (Object admin : response) {
            Admin newAdmin = new Admin((JSONObject) admin);
            admins.add(newAdmin);
        }
        return admins;
    }

    public Admin get(String id){
        JSONObject response = service.GET("admins/" + id);
        if(response != null) return new Admin(response);
        else return null;
    }
}
