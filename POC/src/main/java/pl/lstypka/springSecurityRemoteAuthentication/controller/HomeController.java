package pl.lstypka.springSecurityRemoteAuthentication.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HomeController {

    @RequestMapping("/time")
    public String hello() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @RequestMapping("/secured/time")
    public String securedTime() {
        return "Secured time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @RequestMapping("/admin/time")
    public String adminTime() {
        return "Admin time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @RequestMapping("/user/time")
    public String userTime() {
        return "User time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @RequestMapping("/secured/admin/time")
    @Secured("ROLE_8132451")
    public String securedAdminTime() {
        return "Secured admin time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @RequestMapping("/secured/user/time")
    @Secured("ROLE_74925")
    public String securedUserTime() {
        return "Secured user time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


}
