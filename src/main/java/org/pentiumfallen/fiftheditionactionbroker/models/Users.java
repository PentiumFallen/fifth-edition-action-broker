package org.pentiumfallen.fiftheditionactionbroker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Users {
    @Id
    public ObjectId _id;
    private String username;
    private String password;
    ArrayList<ObjectId> characters = new ArrayList<ObjectId>(10);

    public Users() {}

    public Users(String user, String pass) {
        _id = new ObjectId();
        setUsername(user);
        setPassword(pass);
    }

    public String get_id() { return _id.toHexString(); }

    public void set_id(ObjectId _id) { this._id = _id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getCharacters() {
        ArrayList<String> result = new ArrayList<String>();
        for (ObjectId objectId : characters) {
            result.add(objectId.toHexString());
        }
        return result;
    }

    public void addCharacter(ObjectId newChar) {
        characters.add(newChar);
    }

//	public Characters getChar(String id) {
//		for (Characters character : profiles) {
//			if (character.get_id().equals(id)) {
//				return character;
//			}
//		}
//		return null;
//	}

    public void deleteCharacter(ObjectId dropChar) {
        characters.remove(dropChar);
    }

//	public ArrayList<Map<String, String>> getCharacters() {
//		ArrayList<Map<String, String>> allChars = new ArrayList<Map<String,String>>();
//		for (Characters prof : profiles) {
//			Map<String, String> convert = new HashMap<String, String>();
//			convert.put("name", prof.getName());
//			convert.put("_id", prof.get_id());
//			allChars.add(convert);
//		}
//		return allChars;
//	}

//	public ArrayList<String> getCharacterRefs() {
//		ArrayList<String> allChars = new ArrayList<String>();
//		for (ObjectId prof : characters) {
//			allChars.add(prof.toHexString());
//		}
//		return allChars;
//	}

    public Document convertToDocument() {
        Map<String, Object> charMap = new HashMap<String, Object>();
        charMap.put("_id", _id.toHexString());
        charMap.put("username", username);
        charMap.put("password", password.toString());
        charMap.put("characters", characters);
        return(new Document(charMap));
    }

}
