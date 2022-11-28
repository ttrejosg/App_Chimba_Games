package models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Chat {
    private Customer customer1;
    private Customer customer2;
    private ArrayList<Message> messages;

    public Chat(Customer customer1, Customer customer2){
        this.customer1 = customer1;
        this.customer2 = customer2;
        this.messages = new ArrayList<>();
    }

    public Chat(JSONObject chatJSON){
        this.customer1 = new Customer((JSONObject) chatJSON.get("customer1"));
        this.customer2 = new Customer((JSONObject) chatJSON.get("customer2"));
        this.messages = new ArrayList<>();
    }

    public Chat(){this.messages = new ArrayList<>();}

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("customer1", this.customer1.toJSON());
        result.put("customer2", this.customer2.toJSON());
        return  result;
    }

    public Customer getCustomer1() {
        return customer1;
    }

    public void setCustomer1(Customer customer1) {
        this.customer1 = customer1;
    }

    public Customer getCustomer2() {
        return customer2;
    }

    public void setCustomer2(Customer customer2) {
        this.customer2 = customer2;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
