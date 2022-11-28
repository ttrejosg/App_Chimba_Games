package models;

import org.json.simple.JSONObject;

public class Message {
    private String date;
    private Customer customer;

    public Message(String date, Customer customer){
        this.date = date;
        this.customer = customer;
    }

    public Message(JSONObject messageJSON){
        this.date = (String) messageJSON.get("date");
        this.customer = new Customer((JSONObject) messageJSON.get("customer"));
    }

    public Message(){}

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("date", this.date);
        result.put("customer", this.customer.toJSON());
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
}
