package com.sw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.mapper.ArticleMapper;
import com.sw.pojo.Article;
import com.sw.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author suaxi
 * @date 2021/4/7 10:03
 * @Description
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper mapper;

    @Override
    public JSONObject findPage(int pageNum, int pageSize) {
        JSONObject result = new JSONObject();
        try {
            //System.out.println(pageNum);
            //System.out.println(pageSize);
            Page<Article> page = new Page<>(pageNum, pageSize);
            Page<Article> articlePage = mapper.selectPage(page, null);
            result.put("code", "0");
            result.put("msg", "查询成功");
            result.put("data", articlePage.getRecords());
            result.put("count", articlePage.getTotal());
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "查询失败（分页）！");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JSONObject findByName(JSONObject request) {
        JSONObject result = new JSONObject();
        try {
            //System.out.println(request);
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");
            String title = request.getString("title");
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            Page<Article> page = new Page<>(pageNum, pageSize);
            //like需根据 column val 查询，而不能传入json字符串
            wrapper.like("title", title);
            IPage<Article> articlePage = mapper.selectPage(page, wrapper);
            result.put("code", "0");
            result.put("msg", "查询成功");
            result.put("data", articlePage.getRecords());
            result.put("count", articlePage.getTotal());
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "文章信息查询失败！");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JSONObject deleteBatch(String articleIds) {
        JSONObject result = new JSONObject();
        try {
            mapper.deleteBatchIds(Arrays.stream(articleIds.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
            result.put("code", "0");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "删除失败！");
            e.printStackTrace();
        }
        return result;
    }

}
