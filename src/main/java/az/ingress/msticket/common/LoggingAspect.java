package az.ingress.msticket.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Before("execution(* az.ingress.msticket.*.*.*(..))")
    public void logMethodExecutionBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        log.info("Before method execution intercepted: {} - Arguments: {}", methodName, Arrays.toString(methodArgs));
    }

    @AfterReturning(pointcut = "execution(* az.ingress.msticket.*.*.*(..))",returning = "result")
    public void logMethodExecutionAfterReturning(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        log.info("Method execution completed: %s - Result: %s ", methodName, result);
    }

    @AfterThrowing(pointcut = "execution(* az.ingress.msticket.*.*.*(..))", throwing = "exception")
    public void logMethodExecutionAfterThrowing(JoinPoint joinPoint, Exception exception){
        String methodName = joinPoint.getSignature().getName();
        log.warn("Exception occurred during method execution: %s - Exception: %s", methodName, exception);
    }
}
