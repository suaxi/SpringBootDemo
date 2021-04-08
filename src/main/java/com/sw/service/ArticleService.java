package com.sw.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.pojo.Article;

/**
 * @author suaxi
 * @date 2021/4/7 10:02
 * @Description
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    JSONObject findPage(int pageNum, int pageSize);

    /**
     * 模糊查询（分页）
     * @param request
     * @return
     */
    JSONObject findByName(JSONObject request);
}
