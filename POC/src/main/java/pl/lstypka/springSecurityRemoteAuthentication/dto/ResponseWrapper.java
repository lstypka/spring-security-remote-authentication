package pl.lstypka.springSecurityRemoteAuthentication.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int status;

    private final UserProfileResponse response;

    @JsonCreator
    public ResponseWrapper(@JsonProperty("status") int status, @JsonProperty("response") UserProfileResponse response) {
        this.status = status;
        this.response = response;
    }

    public UserProfileResponse getResponse() {
        return response;
    }

    public int getStatus() {
        return status;
    }

}
