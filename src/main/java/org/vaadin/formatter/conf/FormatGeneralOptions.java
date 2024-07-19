package org.vaadin.formatter.conf;

import elemental.json.Json;
import elemental.json.JsonObject;

public class FormatGeneralOptions extends AbstractCleaveZenConfiguration {

    public int[] blocks;
    public String delimiter;
    public Boolean delimiterLazyShow;
    public String[] delimiters;
    public Boolean uppercase;
    public Boolean lowercase;
    public Boolean numericOnly;
    public String prefix;

    protected JsonObject toJson() {
        JsonObject json = Json.createObject();
        ifNotNull(delimiter, value -> json.put("delimiter", value));
        ifNotNullArray(blocks, value -> json.put("blocks", value));
        ifNotNullArray(delimiters, value -> json.put("delimiters", value));
        ifNotNull(delimiterLazyShow, value -> json.put("delimiterLazyShow", value));
        ifNotNull(uppercase, value -> json.put("uppercase", value));
        ifNotNull(lowercase, value -> json.put("lowercase", value));
        ifNotNull(numericOnly, value -> json.put("numericOnly", value));
        ifNotNull(prefix, value -> json.put("prefix", value));

        return json;
    }

    @Override
    protected String getFormatType() {
        return "general";
    }
}
