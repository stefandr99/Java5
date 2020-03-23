package MyCatalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class Catalog implements Serializable {
    private String name;
    private String path;
    private List<Document> documents = new ArrayList<>();

    public Catalog() {}

    public Catalog(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void add(Document doc) {
        documents.add(doc);
    }

    /**
     * Functie care are rolul de a cauta dupa id un document in catalog
     * @param id - id-ul documentului pe care il cautam in catalog
     * @return documentul pe care il cautam sau null in cazul in care nu este gasit
     */
    public Document findById(String id) {        
        return documents.stream()
                .filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Functie foarte asemanatoare cu remakeDocument, doar ca aici refacem un catalog
     * Primele doua operatii sunt simple: extragem numele si path-ul catalogului
     * Rolul acelui "boolean ok" este de a indica sfarsitul listei de documente din String-ul primit ca parametru.
     * In bucla while citesc si adaug in lista "documents" cat timp exista documente in catalog (evident, in reprezentarea String a acestuia)
     * Cum ar putea arata un astfel de String:
     *  {Java resources,d:/Stefan/Java/Java5/catalog.ser,{java1,Java course 1,https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf,},}
     * @param str Codificarea String a catalogului. Codificare pe care o vom folosi pentru a reforma catalogul
     *            Cum ar putea arata un astfel de String:
         *          {Java resources,d:/Stefan/Java/Java5/catalog.ser,{java1,Java course 1,https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf,},}
     */
    public void remakeCatalog(String str) {
        int start, fin;
        start = 1;
        fin = start;
        while(str.charAt(fin) != ',') {
            fin++;
        }
        name = str.substring(start, fin);
        start = ++fin;
        while(str.charAt(fin) != ',') {
            fin++;
        }
        path = str.substring(start, fin);
        start = ++fin;
        boolean ok = false;
        while (!ok) {
            Document doc = new Document();
            while(str.charAt(fin) != '}')
                fin++;
            fin++; //pe virgula
            doc.remakeDocument(str.substring(start, fin));
            documents.add(doc);
            start = ++fin; //dupa virgula pentru ca asa este codificat toString catalogul
            if(str.charAt(fin) == '}')
                ok = true;
        }
    }

    /**
     * Fix pe aceeasi idee ca toString-ul clasei Document
     * @return "codificarea" catalogului
     */
    @Override
    public String toString() {
        String mystring = "{";
        mystring = mystring + name + "," + path + ",";
        for(Document doc : documents) {
            mystring = mystring + doc.toString() + ",";
        }
        mystring = mystring + "}";
        return mystring;
    }
}
