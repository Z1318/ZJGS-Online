package com.cod.exception;

import com.cod.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by wjn
 */
@Getter
public class CodException extends RuntimeException{
    private Integer code;

    public CodException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CodException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
