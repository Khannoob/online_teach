package com.sysu.guli.service.base.exception;

import com.sysu.guli.service.base.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    ResultCodeEnum resultCodeEnum;
}
