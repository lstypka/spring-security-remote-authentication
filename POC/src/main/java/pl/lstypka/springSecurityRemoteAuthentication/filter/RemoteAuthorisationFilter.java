package pl.lstypka.springSecurityRemoteAuthentication.filter;


import org.springframework.http.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.lstypka.springSecurityRemoteAuthentication.dto.ResponseWrapper;
import pl.lstypka.springSecurityRemoteAuthentication.dto.UserProfileResponse;
import pl.lstypka.springSecurityRemoteAuthentication.dto.UserRoleResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RemoteAuthorisationFilter extends OncePerRequestFilter {

    private static final String SECURITY_TOKEN_KEY = "";
    private static final String REMOTE_AUTHORISATION_SERVER = "";
    private static final String ADDITIONAL_HEADER_NAME = "";
    private static final String ADDITIONAL_HEADER_VALUE = "";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String xAuth = request.getHeader(SECURITY_TOKEN_KEY);

        if (xAuth == null) {
            throw new UsernameNotFoundException("Forbidden");

        }

        Authentication auth = authUserByToken(xAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private AbstractAuthenticationToken authUserByToken(String token) {
        ResponseEntity<ResponseWrapper> exchange = authenticateOnRemoteServer(token);
        ResponseWrapper result = exchange.getBody();
        UserProfileResponse userResponse = result.getResponse();
        return new UsernamePasswordAuthenticationToken(userResponse, "", rolesToAuthorities(userResponse));
    }

    private ResponseEntity<ResponseWrapper> authenticateOnRemoteServer(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(ADDITIONAL_HEADER_NAME, ADDITIONAL_HEADER_VALUE);
        headers.add(SECURITY_TOKEN_KEY, token);
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        try {
            return restTemplate.exchange(REMOTE_AUTHORISATION_SERVER, HttpMethod.GET, entity, ResponseWrapper.class);
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Forbidden", ex);
        }
    }

    private List<GrantedAuthority> rolesToAuthorities(UserProfileResponse user) {
        List<GrantedAuthority> auths = new ArrayList<>();
        for (UserRoleResponse role : user.getRoles()) {
            System.out.println("++ROLE " + role.getId() + " Label " + role.getLabel());
            auths.add(() -> "ROLE_" + role.getId());
        }
        return auths;
    }
}
