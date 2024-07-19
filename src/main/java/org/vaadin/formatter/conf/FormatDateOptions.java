package org.vaadin.formatter.conf;

import elemental.json.Json;
import elemental.json.JsonObject;

public class FormatDateOptions extends AbstractCleaveZenConfiguration {

    public String[] datePattern;
    public String dateMax;
    public String dateMin;
    public String delimiter;
    public Boolean delimiterLazyShow;

    protected JsonObject toJson() {
        JsonObject json = Json.createObject();
        ifNotNull(delimiter, value -> json.put("delimiter", value));
        ifNotNull(delimiterLazyShow, value -> json.put("delimiterLazyShow", value));
        ifNotNullArray(datePattern, value -> json.put("datePattern", value));
        ifNotNull(dateMax, value -> json.put("dateMax", value));
        ifNotNull(dateMin, value -> json.put("dateMin", value));

        return json;
    }

    @Override
    protected String getFormatType() {
        return "date";
    }
}
