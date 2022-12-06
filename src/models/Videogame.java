package models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Videogame {
    private String id;
    private String name;
    private String cover;
    private String tag;
    private String devs;
    private double cost;
    private long stock;
    private String description;
    private ArrayList<Bill> bills;

    public Videogame(String name, String tag, String devs, double cost, long stock, String description) {
        this.name = name;
        this.cover = "/Images/God_of_War_4_cover.jpg";
        this.tag = tag;
        this.devs = devs;
        this.cost = cost;
        this.stock = stock;
        this.description = description;
        this.bills = new ArrayList<>();
    }

    public Videogame(JSONObject videogameJSON){
        this.id = (String) videogameJSON.get("_id");
        this.name = (String) videogameJSON.get("name");
        this.cover = (String) videogameJSON.get("cover");
        this.tag = (String) videogameJSON.get("tag");
        this.devs = (String) videogameJSON.get("devs");
        this.cost = (Double) videogameJSON.get("cost");
        this.stock = (Long) videogameJSON.get("stock");
        this.description = (String) videogameJSON.get("description");
        this.bills = new ArrayList<>();
    }

    public Videogame(){this.bills = new ArrayList<>();}

    public JSONObject toJSON(){
        JSONObject result = new JSONObject();
        result.put("name", this.name);
        result.put("cover",this.cover);
        result.put("tag",this.tag);
        result.put("devs", this.devs);
        result.put("cost", this.cost);
        result.put("stock", this.stock);
        result.put("description", this.description);
        result.put("bills", this.bills);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
