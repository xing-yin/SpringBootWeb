package com.alany.common.core.controller;

import com.alany.common.core.controller.model.ResponseResults;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BaseApiController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    protected void handleIllegalArgumentException(HttpServletResponse response, Exception e) throws IOException {
        ResponseResults responseData = ResponseResults.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        response.getWriter().println(JSON.toJSONString(responseData));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected void handleMissingParameterException(HttpServletResponse response, Exception e) throws IOException {
        ResponseResults responseData = ResponseResults.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        response.getWriter().println(JSON.toJSONString(responseData));
    }

}
