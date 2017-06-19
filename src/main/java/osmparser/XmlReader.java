package osmparser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlReader {

    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    public Document getDocument(File map) {
        try {
            return documentBuilderFactory.newDocumentBuilder().parse(map);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
