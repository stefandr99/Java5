package MyCatalog;

import MyCommands.*;
import MyExceptions.InvalidCatalogException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

public class Main {
    public static void main(String args[]) throws IOException, InvalidCatalogException, ClassNotFoundException {
        Main app = new Main();
        app.createSave();
        app.loadView();
        //app.shell();
        //app.shell2();
    }


    /**
     * Functie menita sa verifice daca argumentul primit de functie este de tip URI sau este un fisier
     * @param url argumentul comenzii
     * @return true - daca e de tip URI, false - altfel
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
     * Functie care va crea shell-ul
     * Primim la linia de comanda cate o comanda si o executam daca o recunoastem
     * Pentru fiecare comanda in parte exista explicatie in clasele lor.
     * Adica pentru comanda view, este explicatia in clasa "ViewCommand", pentru comanda load in clasa "LoadCommand", etc.
     * @throws IOException
     * @throws NullPointerException
     * @throws InvalidCatalogException
     * @throws ClassNotFoundException
     */
    private void shell() throws IOException, NullPointerException, InvalidCatalogException, ClassNotFoundException {
        String command;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));
        while(true) {
            System.out.print("shell>");
            command = console.readLine();
            if (command.equals(""))
                continue;
            if(command.trim().startsWith("view")) {
                Desktop desktop = Desktop.getDesktop();
                String argument;
                try {
                    int start = 5, fin = 5;
                    while(command.charAt(fin) != ')')
                        fin++;
                    argument = command.substring(start, fin);
                    if(isValid(argument)) {
                        URI uri = URI.create(argument);
                        desktop.browse(uri);
                    }
                    else {
                        File file = new File(argument);
                        desktop.open(file);
                    }
                } catch (IOException e) {
                    System.out.println("Unexpected error opening the file!");
                    continue;
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Your argument is incorrect!");
                    continue;
                }
            }
            else if(command.trim().startsWith("load")) { // load(d:/Stefan/Java/Java5/catalog.ser,java1)
                String argument1, argument2;
                try {
                    int start = 5, fin = 5;
                    while (command.charAt(fin) != ',')
                        fin++;
                    argument1 = command.substring(start, fin);
                    start = ++fin;
                    while (command.charAt(fin) != ')')
                        fin++;
                    argument2 = command.substring(start, fin);
                    Catalog catalog = CatalogUtil.load(argument1);
                    Document doc = catalog.findById(argument2);
                    CatalogUtil.view(doc);
                } catch (ClassNotFoundException e) {
                    System.out.println("Unexpected error occurred!");
                }
            }
            else if(command.trim().startsWith("list")) {
                String argument = "";
                try {
                    int start = 5, fin = 5;
                    while (command.charAt(fin) != ')')
                        fin++;
                    argument += command.substring(start, fin);
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

                } catch (InvalidCatalogException e) {
                    System.out.println("Invalid catalog file.");
                }
            }
            else if (command.trim().startsWith("quit")) {
                break;
            }
        }
    }


    /**
     * Functie ce va crea shell-ul, dar care de data aceasta va lucra cu obiecte, fiecare comanda pe care o primim la linia de comanda va crea un obiect de
     * tipul acelei comenzi si va apela functiile aferente
     * Explicatiile fiecarei comenzi sunt in clasa destinata fiecarei comenzi, ca mai sus
     * @throws IOException
     * @throws NullPointerException
     * @throws InvalidCatalogException
     * @throws ClassNotFoundException
     * @throws TikaException
     * @throws SAXException
     */
    public void shell2() throws IOException, NullPointerException, InvalidCatalogException, ClassNotFoundException, TikaException, SAXException {
        String command;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));
        while(true) {
            System.out.print("shell>");
            command = console.readLine();
            if (command.equals(""))
                continue;
            if (command.trim().startsWith("view")) { //view(https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf)
                ViewCommand view = new ViewCommand();
                view.crop(command);
                view.execute();
            }
            else if(command.trim().startsWith("load")) { //load(d:/Stefan/Java/Java5/catalog.ser,java1)
                LoadCommand load = new LoadCommand();
                load.crop(command);
                load.execute();
            }
            else if (command.trim().startsWith("list")) { // list(d:/Stefan/Java/Java5/catalog.ser)
                ListCommand list = new ListCommand();
                list.crop(command);
                list.execute();
            }
            else if (command.trim().startsWith("html")) { //html(d:/Stefan/Java/Java5/catalog.ser)
                ReportHtml report = new ReportHtml();
                report.crop(command);
                report.execute();
            }
            else if(command.trim().startsWith("info")) {
                InfoCommand info = new InfoCommand();
                info.crop(command);
                info.execute();
            }
            else if (command.trim().startsWith("quit")) {
                break;
            }
            else {
                continue;
            }
        }
    }

    /**
     * Creez un obiect Catalog si un Document pe care il adaug in lista de documente a obiectului catalog
     * @throws IOException poate fi aruncata de catre functia save a clasei CatalogUtil
     */
    private void createSave() throws IOException {
        Catalog catalog = new Catalog("Java resources", "d:/Stefan/Java/Java5/catalog.ser");
        Document doc = new Document("java1", "Java course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        catalog.add(doc);

        CatalogUtil.save(catalog);
    }

    /**
     * Incarc un catalog ce se gaseste in fisierul indicat de parametrul de intrare "path"
     * Caut apoi cu ajutorul functiei findById documentul dorit in catalog
     * Apelez functia view pentru a lansa documentul
     * @throws InvalidCatalogException aruncata de functia load a clasei CatalogUtil
     * @throws IOException Idem
     * @throws ClassNotFoundException Idem
     */
    private void loadView() throws InvalidCatalogException, IOException, ClassNotFoundException {
        Catalog catalog = CatalogUtil.load("d:/Stefan/Java/Java5/catalog.ser");
        Document doc = catalog.findById("java1");
        CatalogUtil.view(doc);
    }
}
