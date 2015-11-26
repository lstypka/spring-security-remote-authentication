package pl.lstypka.springSecurityRemoteAuthentication.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserProfileResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer partyNumber;
    private final String username;
    private final String name;
    private final String email;
    private final List<UserRoleResponse> roles;

    @JsonCreator
    public UserProfileResponse(@JsonProperty("partyNumber") Integer partyNumber,
            @JsonProperty("username") String username, @JsonProperty("name") String name,
            @JsonProperty("email") String email, @JsonProperty("roles") List<UserRoleResponse> roles) {
        this.partyNumber = partyNumber;
        this.username = username;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public Integer getPartyNumber() {
        return partyNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<UserRoleResponse> getRoles() {
        return roles;
    }

}
