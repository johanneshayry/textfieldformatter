package org.vaadin.formatter.conf;

import elemental.json.Json;
import elemental.json.JsonObject;

public class FormatTimeOptions extends AbstractCleaveZenConfiguration {

    public String delimiter;
    public Boolean delimiterLazyShow;
    public String timeFormat;
    public String timePattern;

    protected JsonObject toJson() {
        JsonObject json = Json.createObject();
        ifNotNull(delimiter, value -> json.put("delimiter", value));
        ifNotNull(delimiterLazyShow, value -> json.put("delimiterLazyShow", value));
        ifNotNull(timeFormat, value -> json.put("timeFormat", value));
        ifNotNull(timePattern, value -> json.put("timePattern", value));
        return json;
    }

    @Override
    protected String getFormatType() {
        return "time";
    }
}
