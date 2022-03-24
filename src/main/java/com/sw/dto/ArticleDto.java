package com.sw.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Wang Hao
 * @date 2022/3/24 10:58
 */
@Data
public class ArticleDto implements Serializable {

    @ApiModelProperty("ID")
    @ExcelProperty(value = "id")
    private Integer id;

    @ApiModelProperty("对应文章类型的ID")
    @ExcelProperty(value = "对应文章类型的ID")
    private String type;

    @ApiModelProperty("标题")
    @ExcelProperty(value = "标题")
    private String title;

    @ApiModelProperty("作者")
    @ExcelProperty(value = "作者")
    private String author;

    @ApiModelProperty("内容")
    @ExcelProperty(value = "内容")
    private String content;
}
