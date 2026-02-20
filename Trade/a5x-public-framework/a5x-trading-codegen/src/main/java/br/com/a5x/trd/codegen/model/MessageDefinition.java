package br.com.a5x.trd.codegen.model;

import java.util.List;

public class MessageDefinition {
    private String name;
    private String messageType;
    private String description;
    private List<FieldDefinition> fields;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<FieldDefinition> getFields() { return fields; }
    public void setFields(List<FieldDefinition> fields) { this.fields = fields; }
}
