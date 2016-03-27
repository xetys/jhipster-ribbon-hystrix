package de.stytex.foobar.domain;

/**
 * Created by on 27.03.16.
 *
 * @author David Steiman
 */
public class Bar {
    private long id;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
