package nz.co.xingsoft.memribox.server.common;

public class QueryParameter {
    private String name;
    private Object value;

    public QueryParameter(final String name, final Object value) {
        super();
        this.name = name;
        this.value = value;
    }

    public QueryParameter() {

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

}
