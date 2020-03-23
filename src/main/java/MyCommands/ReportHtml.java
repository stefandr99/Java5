package MyCommands;

import MyCatalog.Catalog;
import MyCatalog.CatalogUtil;
import MyCatalog.Document;
import MyExceptions.InvalidCatalogException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ReportHtml implements Command {
    public String argument;

    public ReportHtml() {}

    /**
     * Aceeasi functionalitate ca la celelalte comenzi pe care le putem primi la linia de comanda
     * @param command = comanda de la linia de comanda
     */
    @Override
    public void crop(String command) {
        int start = 5, fin = 5;
        while(command.charAt(fin) != ')')
            fin++;
        argument = command.substring(start, fin);
    }

    /**
     * Se va crea un String html care va contine intregul cod html ce va fi scris in documentul myhtml.html
     * Voi folosi fluxuri de caractere pentru ca acestea sunt potrivite in acest caz
     * Incarcam catalogul pe care l-am primit ca parametru la comanda
     * Apoi pentru fiecare document din catalog, creem un String cu toate informatiile documentului.
     * Voi scrie apoi in Stringul html ce am obtinut intre tagurile <p></p>
     * @throws InvalidCatalogException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void execute() throws InvalidCatalogException, IOException, ClassNotFoundException {
        String html = "<h1>Our catalog contains</h1>";
        File file = new File("d:/Stefan/myhtml.html");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Catalog catalog = CatalogUtil.load(argument);
            for(Document d : catalog.getDocuments()) {
                String response = "Documentul " + d.getName() + " cu urmatoarele informatii: ";
                response = response + "id-ul = " + d.getId() + ", locatia = " + d.getLocation() + " si tagurile = ";
                for(Map.Entry<String, String> t : d.getTags().entrySet()) {
                    response += "Key = " + t.getKey() + ", Value = " + t.getValue() + "; ";
                }
                response += ";";
                html = html + "<p>" + response + "</p><br>";
            }
            writer.write(html);
        }
    }
}
