package nz.co.xingsoft.memribox.server.business.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDetailsDto {
    private static final long serialVersionUID = 1996603804904645031L;

    private Long id;

    private String username;

    private List<String> userRoles;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(final List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
