package com.sw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author suaxi
 * @date 2021/4/6 17:25
 * @Description Controller基类
 */
public abstract class BaseController {

    /**
     * 成功操作调用
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> ResponseEntity<T> success(T t) {
        return ResponseEntity.ok().body(t);
    }



    /**
     * 失败操作调用
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> ResponseEntity<T> fail(T t) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t);
    }

    /**
     * 失败操作调用
     *
     * @return
     */
    protected ResponseEntity<?> operationFail() {
        Map<String, Object> res = new HashMap<>(2);
        res.put("data", null);
        res.put("message", "操作失败");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    /**
     * 异常操作调用
     *
     * @param exception
     * @return
     */
    protected ResponseEntity<String> exception(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(exception.getMessage());
    }
}
