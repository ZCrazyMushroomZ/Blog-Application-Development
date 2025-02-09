package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Long id;
    //网站名
    private String name;
    //logo
    private String logo;
    //描述
    private String description;
    //网站地址
    private String address;
}
