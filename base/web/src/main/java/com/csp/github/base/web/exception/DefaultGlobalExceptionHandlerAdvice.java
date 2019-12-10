package com.csp.github.base.web.exception;


import com.csp.github.base.common.entity.DefaultResultType;
import com.csp.github.base.common.exception.BaseException;
import com.csp.github.base.common.exception.ServiceException;
import com.csp.github.base.common.exception.ServiceSpecialException;
import com.csp.github.base.common.entity.Result;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 陈少平
 * @date 2019-12-03 21:51
 */
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandlerAdvice {

    /**
     * 处理唯一键冲突
     */
    @ExceptionHandler(value = {DuplicateKeyException.class})
    public Result duplicateKeyException(DuplicateKeyException ex) {
        log.error("primary key duplication ex:{}", ex.getMessage());
        return Result.fail(DefaultResultType.DUPLICATE_PRIMARY_KEY);
    }

    @ExceptionHandler(value = {BaseException.class, ServiceException.class})
    public Result serviceException(BaseException ex) {
        log.error("业务异常:{}", ex.getMsg());
        return Result.fail(ex);
    }

    @ExceptionHandler(value = {ServiceSpecialException.class})
    public Result serviceSpecialException(ServiceSpecialException ex) {
        log.error("业务异常:{}, 携带数据:{}", ex.getMsg(), ex.getData());
        return Result.fail(ex);
    }


    /**
     * 处理格式化错误
     */
    @ExceptionHandler(NumberFormatException.class)
    public Object numberFormatException(NumberFormatException ex) {
        log.error("参数格式化异常:{}", ex.getMessage());
        return Result.fail(DefaultResultType.FORMAT_EXCEPTION.getCode(), DefaultResultType.FORMAT_EXCEPTION.getMsg());
    }

    /**
     *  处理自定义验证注解异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object constraintViolationException(ConstraintViolationException ex) {

        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            sb.append(violation.getMessageTemplate());
        }
        String msg = sb.toString();
        log.error("自定义参数校验失败:{}", msg);
        return Result.fail(DefaultResultType.PARAM_INVALID.getCode(), msg);
    }

    /**
     * 处理 hibernate 异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public Result validateException(Exception ex) {
        BindingResult bindResult = null;
        if (ex instanceof BindException) {
            bindResult = ((BindException) ex).getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        }
        if (Objects.nonNull(bindResult) && bindResult.hasErrors()) {
            FieldError fieldError = bindResult.getFieldError();
            String msg = fieldError.getField() + fieldError.getDefaultMessage();
            return Result.fail(DefaultResultType.PARAM_INVALID.getCode(), msg);
        }
        return Result.fail(DefaultResultType.PARAM_INVALID.getCode(), DefaultResultType.PARAM_INVALID.getMsg());
    }

}
