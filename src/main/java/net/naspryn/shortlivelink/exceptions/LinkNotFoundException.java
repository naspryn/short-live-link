package net.naspryn.shortlivelink.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Link for given token not found")
public class LinkNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 101L;
}
