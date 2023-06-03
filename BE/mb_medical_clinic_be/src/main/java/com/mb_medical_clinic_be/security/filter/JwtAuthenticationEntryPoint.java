package com.mb_medical_clinic_be.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    protected final Log logger = LogFactory.getLog(this.getClass());


    private final MappingJackson2HttpMessageConverter messageConverter;

    public JwtAuthenticationEntryPoint(MappingJackson2HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException, ServletException {

        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(httpServletRequest);
        logger.error("Responding with unauthorized error. Message: " + StringUtils.defaultIfBlank(uri, ""), authenticationException);
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;


        //httpServletResponse.setContentType("application/json");
        //httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        /*
        ErrorDetails errorDetails =
                new ErrorDetails( authenticationException.getMessage())
                        .setDateTime(LocalDateTime.now())
                        .setCode(httpStatus.toString())
                        .setPath(uri);

        ErrorResource error = new ErrorResource(errorDetails);

        */
        Object error = null;
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(httpServletResponse);
        outputMessage.setStatusCode(httpStatus);

        messageConverter.write(error, MediaType.APPLICATION_JSON_UTF8, outputMessage);
    }
}
