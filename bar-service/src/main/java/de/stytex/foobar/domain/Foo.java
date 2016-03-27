package de.stytex.foobar.domain;

/**
 * Created by on 26.03.16.
 *
 * @author David Steiman
 */
public class Foo {
    private long id;
    public String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
