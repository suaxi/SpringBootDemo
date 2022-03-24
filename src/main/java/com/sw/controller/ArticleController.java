package com.sw.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.sw.dto.ArticleDto;
import com.sw.pojo.Article;
import com.sw.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author suaxi
 * @date 2021/4/7 10:16
 * @Description
 */
@Slf4j
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

    @ApiOperation(value = "批量删除", notes = "删除单条记录和批量删除都使用该接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleIds", value = "文章id", required = true, paramType = "body", dataType = "String")
    })
    @DeleteMapping("/{articleIds}")
    public ResponseEntity<JSONObject> deleteBatch(@PathVariable("articleIds") String articleIds) {
        return success(service.deleteBatch(articleIds));
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

    @ApiOperation("导出Excel")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //内容格式及编码
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            List<ArticleDto> resultList = new ArrayList<>();
            service.list().forEach(item -> {
                ArticleDto articleDto = new ArticleDto();
                BeanUtils.copyProperties(item, articleDto);
                resultList.add(articleDto);
            });
            String fileName = URLEncoder.encode("文章", "UTF-8");
            //导出文件名
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //sheet名称
            EasyExcel.write(response.getOutputStream(), ArticleDto.class).sheet("文章").doWrite(resultList);
        } catch (IOException e) {
            log.error("Excel导出失败！");
            e.printStackTrace();
        }
    }
}
