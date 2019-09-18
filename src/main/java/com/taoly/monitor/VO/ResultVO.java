package com.taoly.monitor.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/2 14:43
 * @ Description：
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 4206256059681137797L;

    private Integer code;

    private String msg;

    private T data;
}

