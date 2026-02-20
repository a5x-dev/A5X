package br.com.a5x.trd.codegen.nativ;

import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.model.FieldDefinition;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.util.*;

public class NativeXmlParser {

    public List<MessageDefinition> parse(InputStream xmlStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlStream);
        
        List<MessageDefinition> messages = new ArrayList<>();
        NodeList messageNodes = doc.getElementsByTagNameNS("http://a5x.com.br/native", "message");
        
        for (int i = 0; i < messageNodes.getLength(); i++) {
            Element msgElement = (Element) messageNodes.item(i);
            MessageDefinition msg = new MessageDefinition();
            msg.setName(msgElement.getAttribute("name"));
            msg.setMessageType(msgElement.getAttribute("messageType"));
            msg.setDescription(msgElement.getAttribute("description"));
            
            List<FieldDefinition> fields = new ArrayList<>();
            NodeList fieldNodes = msgElement.getElementsByTagNameNS("http://a5x.com.br/native", "field");
            
            for (int j = 0; j < fieldNodes.getLength(); j++) {
                Element fieldElement = (Element) fieldNodes.item(j);
                FieldDefinition field = new FieldDefinition();
                field.setName(fieldElement.getAttribute("name"));
                field.setType(fieldElement.getAttribute("type"));
                field.setDescription(fieldElement.getAttribute("description"));
                
                String lengthStr = fieldElement.getAttribute("length");
                if (!lengthStr.isEmpty()) {
                    field.setLength(Integer.parseInt(lengthStr));
                }
                
                fields.add(field);
            }
            
            msg.setFields(fields);
            messages.add(msg);
        }
        
        return messages;
    }
}
