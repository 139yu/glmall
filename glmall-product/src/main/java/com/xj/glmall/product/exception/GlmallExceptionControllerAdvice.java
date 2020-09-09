package com.xj.glmall.product.exception;

import com.xj.glmall.common.exception.BizCodeEnum;
import com.xj.glmall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.xj.glmall.product.controller")
public class GlmallExceptionControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlmallExceptionControllerAdvice.class);
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e) {
        LOGGER.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());
        BindingResult result = e.getBindingResult();
        Map<String,String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        }));
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),BizCodeEnum.VALID_EXCEPTION.getMessage()).put("data",errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        LOGGER.error("错误",throwable);
        return R.error(BizCodeEnum.UNKONW_EXCEPTION.getCode(),BizCodeEnum.UNKONW_EXCEPTION.getMessage());
    }
}
