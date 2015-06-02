package nz.co.xingsoft.memribox.server.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * AOP Interceptor to use Hibernate bean validator to validate parameters with <code>@Valid</code>
 */
@Aspect
@Component
public class ValidationInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationInterceptor.class);

    @Inject
    private Validator validator;

    @SuppressWarnings("unchecked")
    @Before("execution(* *(.., @javax.validation.Valid (*), ..))")
    public void valid(final JoinPoint joinPoint)
            throws NoSuchMethodException, SecurityException {
        final Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();

        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        final Method interfaceMethod = signature.getMethod();

        LOGGER.debug("Invoking method {}", interfaceMethod.getName());

        final List<Object> interfaceArgs = getAnnotatedParametersFromMethod(joinPoint, interfaceMethod, Valid.class);

        // Get the annotated parameters from interface and implementation class and validate those with the @Valid annotation
        final Method implementationMethod = joinPoint.getTarget().getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());

        final List<Object> implementationArgs = getAnnotatedParametersFromMethod(joinPoint, implementationMethod, Valid.class);

        final List<Object> args = new ArrayList<Object>(CollectionUtils.union(CollectionUtils.intersection(interfaceArgs, implementationArgs),
                CollectionUtils.disjunction(interfaceArgs, implementationArgs)));

        for (final Object arg : args) {
            violations.addAll(validator.validate(arg));
        }

        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }

    private List<Object> getAnnotatedParametersFromMethod(final JoinPoint joinPoint, final Method method, final Class<?> annotationClass) {
        final List<Object> args = new ArrayList<>();

        final Annotation[][] annotationParameters = method.getParameterAnnotations();

        for (int i = 0; i < annotationParameters.length; i++) {
            final Annotation[] annotations = annotationParameters[i];
            for (final Annotation annotation : annotations) {
                if (annotation.annotationType().equals(annotationClass)) {

                    args.add(joinPoint.getArgs()[i]);
                }
            }
        }
        return args;
    }
}
