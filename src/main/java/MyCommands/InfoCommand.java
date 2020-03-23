package MyCommands;

import MyExceptions.InvalidCatalogException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

public class InfoCommand implements Command {
    private String argument;

    @Override
    public void crop(String command) {
        int start = 5, fin = 5;
        while(command.charAt(fin) != ')')
            fin++;
        argument = command.substring(start, fin);
    }

    @Override
    public void execute() throws  IOException, TikaException, SAXException {
        File file = new File(argument);

        //Parser method parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(file);
        ParseContext context = new ParseContext();

        parser.parse(inputStream, handler, metadata, context);
        System.out.println(handler.toString());
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
    }
}
