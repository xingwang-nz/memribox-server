package nz.co.xingsoft.memribox.server.persistence.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nz.co.xingsoft.memribox.server.common.Gender;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User
        extends EntityBean
        implements UserDetails {

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birthday", nullable = false)
    private Date birthDay;

    @Column(name = "native_tongue")
    private String nativeTongue;

    @Column(name = "home_country")
    private String homeCountry;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Phone> phones = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Story> stories = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<Role> roles = new HashSet<>();

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(final Date createdTime) {
        this.createdTime = createdTime;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(final Set<Phone> phones) {
        this.phones = phones;
    }

    public Set<Story> getStories() {
        return stories;
    }

    public void setStories(final Set<Story> stories) {
        this.stories = stories;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.getRoles() == null) {
            return Collections.<GrantedAuthority> emptyList();
        }

        final Collection<String> roleList = CollectionUtils.collect(this.getRoles(), new Transformer() {
            @Override
            public Object transform(final Object role) {
                return ((Role) role).getCode();
            }
        });

        if (roleList.isEmpty()) {
            return Collections.<GrantedAuthority> emptyList();
        }

        final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (final String role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @SuppressWarnings("unchecked")
    public List<String> getUserRoles() {
        if (this.getRoles() == null) {
            return Collections.<String> emptyList();
        }

        return new ArrayList<String>(CollectionUtils.collect(this.getRoles(), new Transformer() {
            @Override
            public Object transform(final Object role) {
                return ((Role) role).getCode();
            }
        }));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(final Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getNativeTongue() {
        return nativeTongue;
    }

    public void setNativeTongue(final String nativeTongue) {
        this.nativeTongue = nativeTongue;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(final String homeCountry) {
        this.homeCountry = homeCountry;
    }

}
