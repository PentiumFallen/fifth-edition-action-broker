package org.pentiumfallen.fiftheditionactionbroker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Characters {
    @Id
    private ObjectId _id;
    private String name;
    private String race;
    private int level = 0;
    private Map<String, Integer> classes = new HashMap<String, Integer>();
    public ArrayList<CharacterOptions> options;

    public String get_id() { return _id.toHexString(); }

    public void set_id(ObjectId _id) { this._id = _id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getLevel() {
        return level;
    }

    public Map<String, Integer> getClasses() { return classes; }

    public void setClasses(Map<String, Integer> classes) { this.classes = classes; }

    public ArrayList<CharacterOptions> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<CharacterOptions> options) {
        this.options = options;
    }

//	public Characters(String name, String race, Class firstClass) {
//		_id = new ObjectId();
//		level = 1;
//		this.name =  name;
//		this.race = race;
//		charClass.put(firstClass, 1);
//		options = new ArrayList<CharacterOptions>();
//	}

    public Characters(String name, String race, int level) {
        this.name =  name;
        this.race = race;
        this.level = level;
        options = new ArrayList<CharacterOptions>();
    }

    public int levelUp(String nextClass) {
        if (level==20) {return 0;}
        else if (classes.get(nextClass)!=null) {
            classes.put(nextClass, classes.get(nextClass)+1);
            level++;
            return classes.get(nextClass);
        }
        else {
            classes.put(nextClass, 1);
            level++;
            return 1;
        }
    }

    public void levelDown(String current) {
        if (classes.get(current)==null) {
            return;
        }
        else {
            classes.put(current, classes.get(current)-1);
            level--;
        }
    }

    public int getClassLevel(String requestedClass) {
        if (classes.get(requestedClass)==null)
            return 0;
        else
            return classes.get(requestedClass);
    }

    public Document convertToDocument() {
        Map<String, Object> charMap = new HashMap<String, Object>();
        charMap.put("_id", _id);
        charMap.put("name", name);
        charMap.put("race", race);
        charMap.put("level", level);
        charMap.put("class", classes);
        charMap.put("options", options);
        return(new Document(charMap));
    }
}
