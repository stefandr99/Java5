package MyCommands;

import MyCatalog.Catalog;
import MyCatalog.CatalogUtil;
import MyCatalog.Document;
import MyExceptions.InvalidCatalogException;

import java.io.IOException;
import java.util.Map;

public class ListCommand implements Command {
    private String argument;

    public ListCommand() {}

    @Override
    public void crop(String command) {
        int start = 5, fin = 5;
        while (command.charAt(fin) != ')')
            fin++;
        argument = command.substring(start, fin);
    }

    @Override
    public void execute() throws InvalidCatalogException, IOException, ClassNotFoundException {
        Catalog catalog = CatalogUtil.load(argument);
        for(Document d : catalog.getDocuments()) {
            String response = "Documentul " + d.getName() + " cu urmatoarele informatii: ";
            response = response + "id-ul = " + d.getId() + ", locatia = " + d.getLocation() + " si tagurile = ";
            for(Map.Entry<String, String> t : d.getTags().entrySet()) {
                response += "Key = " + t.getKey() + ", Value = " + t.getValue() + "; ";
            }
            response += ";";
            System.out.println(response);
        }
    }
}
