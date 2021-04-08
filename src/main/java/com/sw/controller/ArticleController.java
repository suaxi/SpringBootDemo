package com.sw.controller;

import com.alibaba.fastjson.JSONObject;
import com.sw.pojo.Article;
import com.sw.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author suaxi
 * @date 2021/4/7 10:16
 * @Description
 */
@Api(tags = "文章接口")
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService service;

    @ApiOperation("新增文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "article", value = "文章", required = true, paramType = "body", dataType = "form")
    })
    @PostMapping("/addArticle")
    public ResponseEntity<?> addArticle(@RequestBody Article article) {
        //System.out.println(article);
        JSONObject result = new JSONObject();
        try {
            service.save(article);
            result.put("code", "0");
            result.put("msg", "添加成功");
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "添加失败");
            e.printStackTrace();
        }
        return success(result);
    }

    @ApiOperation("删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "文章id", required = true, paramType = "body", dataType = "String")
    })
    @DeleteMapping("/{aid}")
    public ResponseEntity<?> deleteById(@PathVariable("aid") String aid) {
        JSONObject result = new JSONObject();
        try {
            service.removeById(aid);
            result.put("code", "0");
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "删除失败");
            e.printStackTrace();
        }
        return success(result);
    }

    @ApiOperation("更新文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "article", value = "文章", required = true, paramType = "body", dataType = "form")
    })
    @PostMapping("/updateArticle")
    public ResponseEntity<?> update(@RequestBody Article article) {
        JSONObject result = new JSONObject();
        try {
            service.updateById(article);
            result.put("code", "0");
            result.put("msg", "更新成功");
        } catch (Exception e) {
            result.put("code", "500");
            result.put("msg", "更新失败");
            e.printStackTrace();
        }
        return success(result);
    }

    @ApiOperation("分页查询")
    @GetMapping("/articleList")
    public ResponseEntity<JSONObject> findPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return success(service.findPage(pageNum, pageSize));
    }

    @ApiOperation("根据文章标题查询")
    @PostMapping("/findByName")
    public ResponseEntity<JSONObject> findByName(@RequestBody JSONObject request) {
        return success(service.findByName(request));
    }
}
