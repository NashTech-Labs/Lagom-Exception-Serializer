package com.knoldus;

import akka.NotUsed;
import com.knoldus.exceptions.ExceptionFactory;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.api.transport.TransportException;

import javax.inject.Inject;

public class DemoServiceImpl implements DemoService {
    
    private final ExternalService externalService;
    
    @Inject
    public DemoServiceImpl(ExternalService externalService) {
        this.externalService = externalService;
    }
    
    @Override
    public ServiceCall<NotUsed, User> getUserInfo() {
        return request ->
        {
            return externalService.getUser().invoke()
                    .exceptionally(throwable -> {
                        Throwable cause = throwable.getCause();
                        if (cause instanceof ExceptionFactory.AuthenticationException)
                            throw new TransportException(TransportErrorCode.InternalServerError,
                                    new ExceptionMessage("AuthenticationException", "Authentication is required."));
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("error", cause.getMessage()));
                    });
            
        };
    }
}
