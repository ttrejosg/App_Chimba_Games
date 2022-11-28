package models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Videogame {
    private String name;
    private String tag;
    private String devs;
    private double cost;
    private int stock;
    private String description;
    private ArrayList<Bill> bills;

    public Videogame(String name, String tag, String devs, double cost, int stock, String description) {
        this.name = name;
        this.tag = tag;
        this.devs = devs;
        this.cost = cost;
        this.stock = stock;
        this.description = description;
        this.bills = new ArrayList<>();
    }

    public Videogame(JSONObject videomageJSON){
        this.name = (String) videomageJSON.get("name");
        this.tag = (String) videomageJSON.get("tag");
        this.devs = (String) videomageJSON.get("devs");
        this.cost = (double) videomageJSON.get("cost");
        this.stock = (int) videomageJSON.get("stock");
        this.description = (String) videomageJSON.get("description");
        this.bills = new ArrayList<>();
    }

    public Videogame(){this.bills = new ArrayList<>();}

    public JSONObject toJSON(){
        JSONObject result = new JSONObject();
        result.put("name", this.name);
        result.put("tag",this.tag);
        result.put("devs", this.devs);
        result.put("cost", this.cost);
        result.put("stock", this.stock);
        result.put("description", this.description);
        result.put("bills", this.bills);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDevs() {
        return devs;
    }

    public void setDevs(String devs) {
        this.devs = devs;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }
}
