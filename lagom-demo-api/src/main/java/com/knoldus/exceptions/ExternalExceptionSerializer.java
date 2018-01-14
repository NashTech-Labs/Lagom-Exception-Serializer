package com.knoldus.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.api.deser.ExceptionSerializer;
import com.lightbend.lagom.javadsl.api.deser.RawExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;

import java.io.IOException;
import java.util.Collection;

public class ExternalExceptionSerializer implements ExceptionSerializer {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ExternalExceptionSerializer INSTANCE = new ExternalExceptionSerializer();
    
    @Override
    public RawExceptionMessage serialize(Throwable exception, Collection<MessageProtocol> accept) {
        return null;
    }
    
    @Override
    public Throwable deserialize(RawExceptionMessage message) {
        return ExternalExceptionFactory.getInstance(mapExceptionToFault(message));
    }
    
    Fault mapExceptionToFault(RawExceptionMessage message) {
        Fault fault;
        
        try {
            String errorJson = message.messageAsText();
            System.out.println("\nerror : "+errorJson);
            fault = OBJECT_MAPPER.readValue(errorJson, Fault.class);
        } catch (IOException ex) {
            
            fault = Fault.builder()
                    .errorMessage("No payload available")
                    .build();
        }
        System.out.println("\n\nfault : "+fault);
    
        return fault;
    }
}
