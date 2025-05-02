package com.pragma.plaza_service.infrastructure.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

   @Override
    public void apply(RequestTemplate requestTemplate){
       String authorization = getAuthorizationHeader();
         if (authorization != null) {
              requestTemplate.header(AUTHORIZATION_HEADER, authorization);
         }
   }

   private String getAuthorizationHeader(){
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         if (attributes != null) {
             HttpServletRequest request = attributes.getRequest();
             return request.getHeader(AUTHORIZATION_HEADER);
         }
         return null;
   }
}
