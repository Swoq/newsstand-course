package com.swoqe.newsstand.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";
    public static final String NOT_FOUND_ERROR_MSG = "The page you requested was not found.";
    public static final String INTERNAL_ERROR_MSG = "Sorry, we have some problems with our site. We will establish connection as soon as possible.";

    @ExceptionHandler(value = ResponseStatusException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, ResponseStatusException e) {
        log.error(String.valueOf(e));

        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", e.getReason());
        mav.addObject("statusCode", e.getRawStatusCode());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        if (e instanceof MethodArgumentTypeMismatchException)
            return this.defaultErrorHandler(req, new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR_MSG));

        e.printStackTrace();
        log.error(String.valueOf(e));
        mav.addObject("message", INTERNAL_ERROR_MSG);
        return mav;
    }

}