package com.sw.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author suaxi
 * @date 2021/4/7 9:51
 * @Description
 */
@Data
@AllArgsConstructor
@ApiModel(description = "通用返回体")
public class Response<T> {

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 返回对象
     */
    private T data;

//    public static Response<T> success(int code, String msg, T data){
//        return new Response(200, msg);
//    }
}
