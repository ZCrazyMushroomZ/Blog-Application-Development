package com.sangeng.domain.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author makejava
 * @since 2024-01-16 23:18:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@TableName("sys_role_menu")
public class RoleMenu {
    //角色ID
    private Long roleId;
    //菜单ID
    private Long menuId;

}

