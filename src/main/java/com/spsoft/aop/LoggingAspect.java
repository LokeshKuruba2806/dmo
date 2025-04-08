package com.spsoft.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spsoft.exceptions.CustomException;

@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Pointcut that matches all repositories, services, and Web REST endpoints.
	 */
	@Pointcut("within(@org.springframework.stereotype.Repository *)"
			+ " || within(@org.springframework.stereotype.Service *)"
			+ " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Pointcut definition
	}

	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
	@Pointcut("within(com.spsoft..*)")
	public void applicationPackagePointcut() {
		// Pointcut definition
	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param e         exception
	 */
	@AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception in {}.{}() with message = {}, arguments = {}, stack trace = {}",
				joinPoint.getSignature().getDeclaringTypeName(), // Class name
				joinPoint.getSignature().getName(), // Method name
				e.getMessage() != null ? e.getMessage() : "NULL", // Exception message
				Arrays.toString(joinPoint.getArgs()), // Method arguments
				e); // Exception details
	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws CustomException with contextual information
	 */
	@Around("applicationPackagePointcut() && springBeanPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("Entering Method: {}.{}() with arguments = {}", joinPoint.getSignature().getDeclaringTypeName(), // Class
																														// name
					joinPoint.getSignature().getName(), // Method name
					Arrays.toString(joinPoint.getArgs())); // Method arguments
		}
		try {
			Object result = joinPoint.proceed(); // Proceed with the target method
			if (log.isDebugEnabled()) {
				log.debug("Exiting Method: {}.{}() with response = {}", joinPoint.getSignature().getDeclaringTypeName(), // Class
																															// name
						joinPoint.getSignature().getName(), // Method name
						result); // Method result
			}
			return result;
		} catch (Exception e) {
			log.error("Exception in {}.{}() with message = {}, arguments = {}, stack trace = {}",
					joinPoint.getSignature().getDeclaringTypeName(), // Class name
					joinPoint.getSignature().getName(), // Method name
					e.getMessage() != null ? e.getMessage() : "NULL", // Exception message
					Arrays.toString(joinPoint.getArgs()), // Method arguments
					e); // Exception details

			throw new CustomException(
					String.format("Error in %s.%s() - %s", joinPoint.getSignature().getDeclaringTypeName(),

							joinPoint.getSignature().getName(), e.getMessage() != null ? e.getMessage() : "NULL"), // Exception
																													// message
					e);
		}
	}
}
