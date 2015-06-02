package nz.co.xingsoft.memribox.server.business.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthToken
        implements Serializable {

    private String token;

    public AuthToken() {

    }

    public AuthToken(final String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

}
