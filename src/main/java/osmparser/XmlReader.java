package osmparser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {

    public long howManyDocuments(){
        try{
            return Files.list(Paths.get("map/")).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Document getDocument(String documentName, int i){
        documentName += "-" + underTen(i) + ".osm";
        try {
            File map = new File("map/" + documentName);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(map);
            return doc;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String underTen(long j){
        if(j < 10){
            return "0" + j;
        } else{
            return "" + j;
        }
    }
}
