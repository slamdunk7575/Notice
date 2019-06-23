package notice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggerAspect {

    @Around("execution(* notice.controller.*Controller.*(..)) or " +
            "execution(* notice.service.*Impl.*(..)) or " +
            "execution(* notice.mapper.*Mapper.*(..))")
    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {

        StringBuilder type = new StringBuilder();
        String name = joinPoint.getSignature().getDeclaringTypeName();

        if (name.indexOf("Controller") > -1) {
            // type = "Controller  \t:  ";
            type.append("Controller  \t:  ");
        }
        else if (name.indexOf("Service") > -1) {
            // type = "ServiceImpl  \t:  ";
            type.append("ServiceImpl  \t:  ");

        }
        else if (name.indexOf("Mapper") > -1) {
            // type = "Mapper  \t\t:  ";
            type.append("Mapper  \t\t:  ");
        }

        log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }
}
