package com.knoldus;

import akka.NotUsed;
import com.knoldus.exceptions.ExternalExceptionFactory;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.api.transport.TransportException;

import javax.inject.Inject;

public class DemoServiceImpl implements DemoService {
    
    ExternalService externalService;
    
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
                        if (cause instanceof ExternalExceptionFactory.AuthenticationException)
                            throw new TransportException(TransportErrorCode.InternalServerError,
                                    new ExceptionMessage("error", "authentication required"));
                        throw new TransportException(TransportErrorCode.InternalServerError,
                                new ExceptionMessage("error", cause.getMessage()));
                    });
            
        };
    }
}
