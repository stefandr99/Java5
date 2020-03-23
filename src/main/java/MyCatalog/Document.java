package MyCatalog;

import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document implements Serializable {
    String id;
    String name;
    String location;
    Map<String, String> tags = new HashMap<>();


    public Document() {
    }

    public Document(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public void addTag(String key, String obj) {
        tags.put(key, obj);
    }

    /**
     * Are scopul de a reface un document care a fost "codificat" cu toString
     * Folosesc 2 indecsi: start si fin (= final) care sunt delimitatorii fiecarui camp care ne intereseaza (name, id, etc)
     * Iterez prin tot stingul si obtin astfel campurile necesare formarii (sau reformarii) documentului
     * Fiecare camp va fi extras cu functia substring(start, fin) din String-ul primit ca parametru
     * @param doc - String care va fi de forma {id, name, location}
     */
    public void remakeDocument(String doc) {
        int start, fin;
        String aux;
        start = 1;
        fin = start;
        while(doc.charAt(fin) != ',') {
            fin++;
        }
        id = doc.substring(start, fin);
        start = ++fin;
        while(doc.charAt(fin) != ',') {
            fin++;
        }
        name = doc.substring(start, fin);
        start = ++fin;
        while(doc.charAt(fin) != '}') {
            fin++;
        }
        location = doc.substring(start, fin);
    }

    /**
     * Ideea acestui toString a fost de a crea un fel de "JSON de mana" pentru a putea fi manipulat cu usurinta la nevoie (decodificarea stringului pentru refacerea catalogului)
     * @return codificarea String a obiectului Document. O vom folosi la codificarea toString a catalogului
     */
    @Override
    public String toString() {
        String mystr = "{";
        mystr = mystr + id + "," + name + "," + location + "," + "}";
        return mystr;
    }
}
