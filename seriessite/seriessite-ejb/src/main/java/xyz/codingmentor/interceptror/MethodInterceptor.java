package xyz.codingmentor.interceptror;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

public class MethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(MethodInterceptor.class.getName());

    @AroundInvoke
    @AroundTimeout
    public Object logAroundInvoke(InvocationContext ic) throws Exception {
        try {
            return ic.proceed();
        } finally {
            LOGGER.info("(" + ic.getTarget().getClass() + " - " + ic.getMethod().getName() + ")");
        }
    }
}
