package com.sw.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "文章")
public class Article implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 对应文章类型的ID
     */
    @ApiModelProperty("对应文章类型的ID")
    private String type;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 作者
     */
    @ApiModelProperty("作者")
    private String author;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 乐观锁
     */
    @Version
    @ApiModelProperty("乐观锁")
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

}
