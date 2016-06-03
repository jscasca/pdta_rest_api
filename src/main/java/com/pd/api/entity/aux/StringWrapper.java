package com.pd.api.entity.aux;

/**
 * The StringWrapper class is only used to wrap single parameters in a request body since
 * the MVC framework has some bugs using PUT with request parameters
 * 
 * @author tin
 *
 */
public class StringWrapper {

    private String field;
    private String value;
    
    public StringWrapper() {}
    public StringWrapper(String field, String value) {
        this.field = field;
        this.value = value;
    }
    
    public String getField() {
        return field;
    }
    
    public String getValue() {
        return value;
    }
}
