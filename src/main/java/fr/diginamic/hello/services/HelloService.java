package fr.diginamic.hello.services;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    /**
     * Generates a greeting message.
     *
     * @return a string containing the greeting message.
     */
    public String salutations() {
        return "I'm the class of service and I'd like to say hello.";
    }
}
