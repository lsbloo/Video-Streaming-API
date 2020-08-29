package com.apiservice.apiv1.context;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author osvaldoairon
 */

public class ContextUtil {
    /**
     *
     * @return username authenticated
     */
    public static String getAuthenticationUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
