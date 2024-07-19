package org.vaadin.formatter.conf;

import elemental.json.Json;
import elemental.json.JsonObject;

public class FormatNumeralOptions extends AbstractCleaveZenConfiguration {


    public String delimiter;
    public Integer numeralIntegerScale;
    public Integer numeralDecimalScale;
    public String numeralDecimalMark;
    public Boolean numeralPositiveOnly;
    public String numeralThousandsGroupStyle;
    public Boolean signBeforePrefix;
    public Boolean stripLeadingZeroes;
    public String prefix;
    public Boolean tailPrefix;

    protected JsonObject toJson() {
        JsonObject json = Json.createObject();
        ifNotNull(delimiter, value -> json.put("delimiter", value));
        ifNotNull(prefix, value -> json.put("prefix", value));
        ifNotNull(tailPrefix, value -> json.put("tailPrefix", value));
        ifNotNull(numeralIntegerScale, value -> json.put("numeralIntegerScale", value));
        ifNotNull(numeralDecimalScale, value -> json.put("numeralDecimalScale", value));
        ifNotNull(numeralDecimalMark, value -> json.put("numeralDecimalMark", value));
        ifNotNull(numeralPositiveOnly, value -> json.put("numeralPositiveOnly", value));
        ifNotNull(numeralThousandsGroupStyle, value -> json.put("numeralThousandsGroupStyle", value));
        ifNotNull(signBeforePrefix, value -> json.put("signBeforePrefix", value));
        ifNotNull(stripLeadingZeroes, value -> json.put("stripLeadingZeroes", value));
        return json;
    }

    @Override
    protected String getFormatType() {
        return "numeral";
    }
}
