package org.orient.flashsalesystem.excepthion;

import lombok.extern.slf4j.Slf4j;
import org.orient.flashsalesystem.vo.RespBean;
import org.orient.flashsalesystem.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean exception(Exception e) {
        if (e instanceof GlobalException globalException){
            return RespBean.error(globalException.getRespBeanEnum());
        } else if (e instanceof BindException bindException){
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常: "+bindException.getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        log.error(e.getMessage(), e);
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
