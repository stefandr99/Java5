package MyCommands;

import MyCatalog.Catalog;
import MyCatalog.CatalogUtil;
import MyCatalog.Document;
import MyExceptions.InvalidCatalogException;

import java.io.IOException;

public class LoadCommand implements Command{
    private String argument1;
    private String argument2;

    public LoadCommand() {}


    /**
     * Functia va asigna celor doua String-uri "argument1", respectiv "argument2", cele doua argumente pe care la asteapta functia load
     * @param command = comanda de la linia de comanda
     */
    @Override
    public void crop(String command) {
        int start = 5, fin = 5;
        while (command.charAt(fin) != ',')
            fin++;
        argument1 = command.substring(start, fin);
        start = ++fin;
        while (command.charAt(fin) != ')')
            fin++;
        argument2 = command.substring(start, fin);

    }

    /**
     * Primul argument va fi path-ul catre un folder de tipul .ser care contine catalogul nostru
     * Vom incarca catalogul prin intermdiul functiei load din CatalogUtil si-l vom asigna unei variabile locale (catalog)
     * Vom extrage documentul care ne intereseaza (indicat prin argumentul2 al comenzii) prin intermediul functiei findById
     * Vom dechide documentul pe care l-am extras din catalog
     * @throws InvalidCatalogException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public void execute() throws InvalidCatalogException, IOException, ClassNotFoundException {
        Catalog catalog = CatalogUtil.load(argument1);
        Document doc = catalog.findById(argument2);
        CatalogUtil.view(doc);
    }
}

