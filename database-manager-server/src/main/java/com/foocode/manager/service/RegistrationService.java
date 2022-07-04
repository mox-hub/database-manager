package com.foocode.manager.service;

import com.foocode.manager.model.Registration;

import java.util.Map;

public interface RegistrationService {
    Object getAllRegistration();

    Object queryRegistration(Map<String,String> data);

    Object createRegistration(Registration registration);

    Object updateRegistration(Map<String,String> data);

    Object deleteRegistration(String id);
}
