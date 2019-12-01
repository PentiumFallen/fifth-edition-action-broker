package org.pentiumfallen.fiftheditionactionbroker.utils;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.pentiumfallen.fiftheditionactionbroker.models.CharacterOptions;
import org.pentiumfallen.fiftheditionactionbroker.models.Characters;
import org.pentiumfallen.fiftheditionactionbroker.models.Users;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

public class MongoConnector {
    MongoClient client = MongoClients.create();
    MongoDatabase db = client.getDatabase("action_broker");

    public int validateSignUp(String username, String password) {
        MongoCollection<Document> collection = db.getCollection("users");
        Document result = collection.find(eq("username", username)).first();
        if (result!=null) {
            //username already taken
            return -1;
        }
        else {
            if (password.toString().isBlank()) {
                //password invalid
                return -2;
            }
            else {
                commitUser(new Users(username, password));
                return 0;
            }
        }
    }

    public String validateLogin(String username, char[] password) {
        MongoCollection<Document> collection = db.getCollection("users");
        Document result = collection.find(eq("username", username)).first();
        if (result==null) {
            //user does not exist
            return "User does not exist";
        }
        else if (!result.get("password").equals(password)) {
            //wrong password
            return "Wrong password";
        }
        else {
            return (String) result.get("_id");
        }
    }

    public void commitUser(Users user) {
        MongoCollection<Document> users = db.getCollection("users");
        Document newUser = user.convertToDocument();
        try {
            Document existing = users.find(eq("_id",user.get_id())).first();
            users.replaceOne(existing, newUser);
        } catch (Exception e) {
            users.insertOne(newUser);
        }
    }

    public String commitCharacter(Characters character) {
        MongoCollection<Document> characters = db.getCollection("characters");
        Document newChar = character.convertToDocument();
        try {
            Document existing = characters.find(eq("_id",character.get_id())).first();
            characters.replaceOne(existing, newChar);
            return character.get_id();
        } catch (Exception e) {
            characters.insertOne(newChar);
            return character.get_id();
        }
    }

    public Characters getCharacter(ObjectId id) {
        Document request = db.getCollection("characters").find(eq("_id", id)).first();
        Characters character =  new Characters(request.getString("name"),
                request.getString("race"), request.getInteger("level"));
//		request.get
        return null;
    }
}