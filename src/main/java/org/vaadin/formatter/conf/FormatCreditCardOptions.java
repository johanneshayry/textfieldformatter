package org.vaadin.formatter.conf;

import elemental.json.Json;
import elemental.json.JsonObject;

public class FormatCreditCardOptions extends AbstractCleaveZenConfiguration {
    public Boolean creditCardStrictMode;
    public String delimiter;
    public Boolean delimiterLazyShow;

    protected JsonObject toJson() {
        JsonObject json = Json.createObject();
        ifNotNull(creditCardStrictMode, value -> json.put("strictMode", value));
        ifNotNull(delimiter, value -> json.put("delimiter", value));
        ifNotNull(delimiterLazyShow, value -> json.put("delimiterLazyShow", value));
        return json;
    }

    @Override
    protected String getFormatType() {
        return "creditCard";
    }
}
