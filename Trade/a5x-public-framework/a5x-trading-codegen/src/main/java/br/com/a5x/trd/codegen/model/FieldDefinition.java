package br.com.a5x.trd.codegen.model;

public class FieldDefinition {
    private String name;
    private String type;
    private Integer length;
    private Integer decimals;
    private String description;
    private boolean signMagnitude;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    public Integer getDecimals() { return decimals; }
    public void setDecimals(Integer decimals) { this.decimals = decimals; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * True when the field's type declares negativeEncoding="sign-magnitude": the most
     * significant bit is the sign and the remaining bits the absolute value, instead of
     * two's complement. MITCH sends negative prices this way; Native does not.
     */
    public boolean isSignMagnitude() { return signMagnitude; }
    public void setSignMagnitude(boolean signMagnitude) { this.signMagnitude = signMagnitude; }
}
