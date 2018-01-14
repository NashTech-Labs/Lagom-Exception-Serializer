package com.knoldus;

import akka.NotUsed;
import com.knoldus.exceptions.ExternalExceptionSerializer;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

public interface ExternalService extends Service {
    
    @Override
    default Descriptor descriptor() {
        return Service.named("external-service")
                .withCalls(
                        Service.restCall(Method.GET, "/user", this::getUser)
                )
                .withExceptionSerializer(ExternalExceptionSerializer.INSTANCE)
                .withAutoAcl(true);
    }
    
     ServiceCall<NotUsed, User> getUser();
}
