package pl.lstypka.springSecurityRemoteAuthentication.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRoleResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String label;

    @JsonCreator
    public UserRoleResponse(@JsonProperty("id") String id, @JsonProperty("label") String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}