package com.yao.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author yao
 * @create 2022-11-02 14:41
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> sqlExceptionHandler(SQLIntegrityConstraintViolationException exception){
        String message = exception.getMessage();
        if(message.contains("Duplicate entry")){
            String[] msg = message.split(" ");
            return R.error(msg[2]+"already exist!");
        }
        return R.error("添加失败");
    }
    @ExceptionHandler(CustomerException.class)
    public R<String> sqlExceptionHandler(CustomerException exception){
        String message = exception.getMessage();

        return R.error(message);
    }
}
