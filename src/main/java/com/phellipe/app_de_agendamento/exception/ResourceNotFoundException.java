package com.phellipe.app_de_agendamento.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName) {
        super(String.format("No %s found.", resourceName));
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("No %s found with id %s.", resourceName, id));
    }

    public ResourceNotFoundException(String firstResourceName, Long firstResourceId, String secondResourceName, Long secondResourceId) {
        super(String.format("No %s found with id %s in the %s with id %s.", firstResourceName, firstResourceId, secondResourceName, secondResourceId));
    }

    public ResourceNotFoundException(String firstResourceName, UUID firstResourceId, String secondResourceName, Long secondResourceId) {
        super(String.format("No %s found with id %s in the %s with id %s.", firstResourceName, firstResourceId, secondResourceName, secondResourceId));
    }

}
