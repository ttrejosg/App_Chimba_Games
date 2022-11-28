package models;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Customer extends User{
    private String name;
    private String lastname;
    private String cellphone;
    private String email;
    private String description;
    private long age;
    private boolean online;
    private String avatar;
    private ArrayList<Chat> chats;
    private ArrayList<Bill> bills;

    public Customer(String username, String password, String name, String lastname, String cellphone, String email, int age) {
        super(username, password);
        this.name = name;
        this.lastname = lastname;
        this.cellphone = cellphone;
        this.email = email;
        this.description = "";
        this.age = age;
        this.online = false;
        this.avatar = "ruta por defecto";
        this.chats = new ArrayList<>();
        this.bills = new ArrayList<>();
    }

    public Customer(JSONObject customerJSON){
        this.username = (String) customerJSON.get("username");
        this.password = (String) customerJSON.get("password");
        this.name = (String) customerJSON.get("name");
        this.lastname = (String) customerJSON.get("lastname");
        this.cellphone = (String) customerJSON.get("cellphone");
        this.email = (String) customerJSON.get("email");
        this.description = (String) customerJSON.get("description");
        this.age = (Long) customerJSON.get("age");
        this.online = (Boolean) customerJSON.get("online");
        this.avatar = (String) customerJSON.get("avatar");
        this.chats = new ArrayList<>();
        this.bills = new ArrayList<>();
    }

    public Customer(){
        this.chats = new ArrayList<>();
        this.bills = new ArrayList<>();
    }

    public JSONObject toJSON(){
        JSONObject result = new JSONObject();
        result.put("username", this.username);
        result.put("password",this.password);
        result.put("name", this.name);
        result.put("lastname", this.lastname);
        result.put("cellphone", this.cellphone);
        result.put("email", this.email);
        result.put("description", this.description);
        result.put("age", this.age);
        result.put("online", this.online);
        result.put("avatar", this.avatar);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }
}
