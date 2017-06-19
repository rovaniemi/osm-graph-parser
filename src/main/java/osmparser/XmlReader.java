package osmparser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlReader {

    public long howManyDocuments(){
        File[] files = new File("map/").listFiles();
        int counter = 0;
        for (int i = 0; i < files.length; i++) {
            if(files[i].getName().startsWith("map-") && files[i].getName().endsWith(".osm")){
                counter++;
            }
        }
        return counter;
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

    public String underTen(long j){
        if(j < 10){
            return "0" + j;
        }
        return "" + j;
    }
}
