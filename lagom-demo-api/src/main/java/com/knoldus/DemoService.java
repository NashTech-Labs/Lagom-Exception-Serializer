package com.knoldus;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

public interface DemoService extends Service {
    
    @Override
    default Descriptor descriptor() {
        return Service.named("demo")
                .withCalls(Service.restCall(Method.GET, "/user", this::getUserInfo))
                .withAutoAcl(true);
    }
    
    ServiceCall<NotUsed, User> getUserInfo();
}
