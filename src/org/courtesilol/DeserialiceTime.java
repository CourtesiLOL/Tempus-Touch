package org.courtesilol;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.courtesilol.dto.Condition;

import org.courtesilol.dto.SampleTime;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Javier
 */
public class DeserialiceTime {
    private final DocumentBuilderFactory dbFactory;
    private final DocumentBuilder dBuilder;
    
    public DeserialiceTime() throws ParserConfigurationException {
        this.dbFactory = DocumentBuilderFactory.newInstance();
        this.dBuilder = dbFactory.newDocumentBuilder();
    }
    
    public synchronized SampleTime sampleDataDeserialice(InputStream data) throws SAXException, IOException {
        Document doc = dBuilder.parse(data);
        doc.getDocumentElement().normalize();
        
        NodeList nList;
        
        String name, temp_c, wind_kph, feelslike_c, uv;
        
        nList = doc.getElementsByTagName("name");
        name = nList.item(0).getTextContent();
        
        nList = doc.getElementsByTagName("temp_c");
        temp_c = nList.item(0).getTextContent();
        
        nList = doc.getElementsByTagName("wind_kph");
        wind_kph = nList.item(0).getTextContent();
        
        nList = doc.getElementsByTagName("feelslike_c");
        feelslike_c = nList.item(0).getTextContent();
        
        nList = doc.getElementsByTagName("uv");
        uv = nList.item(0).getTextContent();
        
        String text, icon, code;
        
        nList = doc.getElementsByTagName("text");
        text = nList.item(0).getTextContent();
        
        nList = doc.getElementsByTagName("icon");
        icon = nList.item(0).getTextContent();
        
        nList = doc.getElementsByTagName("code");
        code = nList.item(0).getTextContent();
        
        doc = null;
        
        return new SampleTime(name, temp_c, Condition.of(text, icon, code), wind_kph, feelslike_c, uv);
    }
}
