package com.phellipe.barber_agenda_api.exception;

import java.time.LocalDateTime;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName) {
        super(String.format("No %s found.", resourceName));
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("No %s found with id %s.", resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, LocalDateTime dateTime) {
        super(String.format("No %s found with DateTime: %s.", resourceName, dateTime));
    }

}
