package MyCatalog;

import MyExceptions.InvalidCatalogException;
import java.awt.*;
import java.io.*;
import java.net.URI;

public class CatalogUtil {
    /**
     * Salvarea unui catalog in path-ul indicat in obiectul catalog
     * @param catalog catalogul care va fi salvat in locatia indicata de campul path
     * @throws IOException exceptie pe care o poate arunca fluxul primitiv FileOutputStream, dar si de functia writeObject
     */
    public static void save(Catalog catalog) throws IOException{
        try (ObjectOutputStream mycat = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))) {
            mycat.writeObject(catalog);
        }
    }

    /**
     * Functie care va salva "codificarea" catalogului in locatia indicata de campul path
     * Codificarea aceasta este de tipul String, deci poate fi scrisa prin intermediul functiei write a decoratorului BufferedWriter
     * @param catalog catalogul pe care il vom salva "codificat" in locatia indicata de campul path
     * @throws IOException Identic cu cea de mai sus: exceptie care poate fi aruncata de catre fluxul primitiv FileWriter, dar si de catre functia write a decoratorului
     */
    public static void save2(Catalog catalog) throws IOException {
        try (BufferedWriter mycat = new BufferedWriter(new FileWriter(catalog.getPath()))) {
            mycat.write(catalog.toString());
        }
    }

    /**
     * Functie ce are rolul de citi un catalog din fisierul indicat in parametrul path
     * @param path indica adresa fisierului care contine catalogul
     * @return  catalogul de la acea adresa
     * @throws InvalidCatalogException exceptie creata de mine pentru a verifica validitatea catalogului gasit in fisierul indicat de parametrul path
     * @throws IOException Identica cu exceptia de la "save"
     * @throws ClassNotFoundException exceptie care va fi aruncata in cazul in care nu se gaseste obiectul cautat in fisier
     */
    public static Catalog load(String path) throws InvalidCatalogException, IOException, ClassNotFoundException {
        try (ObjectInputStream mycatalog = new ObjectInputStream(new FileInputStream(path))) {
            Catalog catalog = (Catalog)mycatalog.readObject();
            if (catalog == null)
                throw new InvalidCatalogException();
            return catalog;
        }
    }

    /**
     * Va citi din fisierul indicat de argumentul "path" un String care reprezinta "codificarea" in String a catalogului.
     * Acesta va fi decodificat cu ajutorul functiei remakeCatalog din clasa Catalog, in final fiind obtinut un nou obiect Catalog :)
     * @param path path catre fisierul de unde vom incarca catalogul
     * @return catalogul din fisierul indicat de argumentul "path"
     * @throws InvalidCatalogException Exceptie aruncata in cazul in care catalogul format nu este valid
     * @throws IOException Ca si in exemplele de mai sus, exceptie care poate fi aruncata de catre fluxul primitiv FileReader, dar si de catre functia readLine()
     */
    public static Catalog load2(String path) throws InvalidCatalogException, IOException {
        try(BufferedReader mycat = new BufferedReader(new FileReader(path))) {
            String cat = mycat.readLine();
            Catalog catalog = new Catalog();
            catalog.remakeCatalog(cat);
            if(catalog == null)
                throw new InvalidCatalogException();
            return catalog;
        }
    }

    /**
     * Functie care va verificara validitatea unui uri primit ca parametru.
     * Se incearca creearea unui nou URI, daca se reuseste, inseamna ca String-ul primit ca parametru este un URI valabil, altfel se returneaza false dupa prinderea exceptiei
     * @param uri String-ul a carui validitate va fi verificata
     * @return true daca e un URI valid, false altfel
     */
    public static boolean isValid(String uri) {
        try {
            URI.create(uri);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Se va crea un obiect de tipul Desktop care ne va ajuta sa executam documentul pe care l-am primit ca parametru
     * Verificam daca este un URI valid; in caz afirmativ, se va crea un nou obiect URI si se va lansa
     * Daca nu este URI, este un fisier, deci il vom deschide cu functia open a clasei Desktop
     * Exceptie IO pentru functiile browse si open
     * @param document documentul pe care dorim sa-l vizalizam
     */
    public static void view (Document document) {
        Desktop desktop = Desktop.getDesktop();
        try {
            if(isValid(document.getLocation())) {
                URI uri = URI.create(document.getLocation());
                desktop.browse(uri);
            }
            else {
                File file = new File(document.getLocation());
                desktop.open(file);
            }
        } catch (IOException e) {
            System.out.println("Unexpected error opening the file!");
        }
    }
}
