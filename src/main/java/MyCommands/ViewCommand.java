package MyCommands;

import MyExceptions.InvalidCatalogException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ViewCommand implements Command{
    private String argument;

    public ViewCommand() {}

    /**
     * Functie menita sa verifice daca argumentul primit de functie este de tip URI sau este un fisier
     * @param url argumentul comenzii
     * @return true - daca e de tip URI, false altfel
     */
    public static boolean isValid(String url) {
        try {
            URI.create(url);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Functie ce va extrage din comanda primita la linia de comanda path-ul catre documentul ce va fi deschis
     * De exemplu avem comanda view(https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf), se va extrage doar ce este intre paranteze
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
     * Se va crea un obiect de tipul Desktop care ne va ajuta sa executam documentul pe care l-am primit ca parametru
     * Verificam daca este un URI valid; in caz afirmativ, se va crea un nou obiect URI si se va lansa
     * Daca nu este URI, este un fisier, deci il vom deschide cu functia open a clasei Desktop
     * Exceptie IO pentru functiile browse si open
     * @throws IOException
     */
    @Override
    public void execute() throws IOException {
        Desktop desktop = Desktop.getDesktop();
        if(isValid(argument)) {
            URI uri = URI.create(argument);
            desktop.browse(uri);
        }
        else {
            File file = new File(argument);
            desktop.open(file);
        }
    }
}
