package com.sangeng.domain.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2023-09-19 22:36:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sg_link")
@SuppressWarnings("serial")
public class Link {

    private Long id;
    //网站名
    private String name;
    //logo
    private String logo;
    //描述
    private String description;
    //网站地址
    private String address;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
    //创建人的用户id
    private Long createBy;
    //发布时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //最后编辑时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}

