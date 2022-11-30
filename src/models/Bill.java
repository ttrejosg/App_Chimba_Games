package models;


import org.json.simple.JSONObject;

public class Bill {
    private String id;
    private String date;
    private Customer customer;
    private Videogame videogame;

    public Bill(String date, Customer customer, Videogame videogame) {
        this.date = date;
        this.customer = customer;
        this.videogame = videogame;
    }

    public Bill(JSONObject billJSON){
        this.id = (String)billJSON.get("_id");
        this.date = (String) billJSON.get("date");
        this.customer = new Customer((JSONObject) billJSON.get("customer"));
        this.videogame = new Videogame((JSONObject) billJSON.get("videogame"));
    }

    public Bill(){}

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("date", this.date);
        result.put("customer", this.customer.toJSON());
        result.put("videogame", this.videogame.toJSON());
        return  result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Videogame getVideogame() {
        return videogame;
    }

    public void setVideogame(Videogame videogame) {
        this.videogame = videogame;
    }
}
