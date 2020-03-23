package MyCommands;

import MyExceptions.InvalidCatalogException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface Command {
    /**
     * Aceasta functie are rolul de a extrage argumentul/argumentele functiei. Noi vom primi un String de forma "view(path)"
     * si va extrage doar ce este intre paranteze (path in exemplul nostru)
     * @param command = comanda de la linia de comanda
     */
    public void crop(String command);

    /**
     * Functie ce va executa comenzile primite la linia de comanda
     * @throws InvalidCatalogException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws TikaException
     * @throws SAXException
     */
    public void execute() throws InvalidCatalogException, IOException, ClassNotFoundException, TikaException, SAXException;
}
