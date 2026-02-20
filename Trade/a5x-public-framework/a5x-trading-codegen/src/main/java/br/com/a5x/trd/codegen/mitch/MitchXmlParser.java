package br.com.a5x.trd.codegen.mitch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.com.a5x.trd.codegen.model.FieldDefinition;
import br.com.a5x.trd.codegen.model.MessageDefinition;

public class MitchXmlParser {
    
    public List<MessageDefinition> parse(InputStream xmlStream) throws Exception {
        List<MessageDefinition> messages = new ArrayList<>();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlStream);
        
        NodeList messageNodes = doc.getElementsByTagNameNS("http://a5x.com.br/mitch", "message");
        
        for (int i = 0; i < messageNodes.getLength(); i++) {
            Element messageElement = (Element) messageNodes.item(i);
            MessageDefinition message = new MessageDefinition();
            
            message.setName(messageElement.getAttribute("name"));
            message.setMessageType(messageElement.getAttribute("messageType"));
            message.setDescription(messageElement.getAttribute("description"));
            
            List<FieldDefinition> fields = new ArrayList<>();
            NodeList fieldNodes = messageElement.getElementsByTagNameNS("http://a5x.com.br/mitch", "field");
            
            for (int j = 0; j < fieldNodes.getLength(); j++) {
                Element fieldElement = (Element) fieldNodes.item(j);
                FieldDefinition field = new FieldDefinition();
                
                field.setName(fieldElement.getAttribute("name"));
                field.setType(fieldElement.getAttribute("type"));
                field.setDescription(fieldElement.getAttribute("description"));
                
                String lengthStr = fieldElement.getAttribute("length");
                if (lengthStr != null && !lengthStr.isEmpty()) {
                    field.setLength(Integer.parseInt(lengthStr));
                }
                
                String decimalsStr = fieldElement.getAttribute("decimals");
                if (decimalsStr != null && !decimalsStr.isEmpty()) {
                    field.setDecimals(Integer.parseInt(decimalsStr));
                }
                
                fields.add(field);
            }
            
            message.setFields(fields);
            messages.add(message);
        }
        
        return messages;
    }
}
