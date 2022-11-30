package models;

import org.json.simple.JSONObject;

public class Admin extends User{

    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(JSONObject adminJSON){
        this.id = (String) adminJSON.get("_id");
        this.username = (String) adminJSON.get("username");
        this.password = (String) adminJSON.get("password");
    }

    public Admin(){}

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("username", this.username);
        result.put("password", this.password);
        return  result;
    }
}
