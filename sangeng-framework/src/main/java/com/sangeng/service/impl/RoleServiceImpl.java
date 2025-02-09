package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constans.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Role;
import com.sangeng.domain.entity.RoleMenu;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.RoleMapper;
import com.sangeng.service.RoleMenuService;
import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-01-13 21:59:42
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否为管理员 userId = 1 如果是 只返回集合中有admin的
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
        lambdaQueryWrapper.eq(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());
        lambdaQueryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
        page(page,lambdaQueryWrapper);

        //转换成VO
        List<Role> roles = page.getRecords();

        PageVo pageVo = new PageVo(roles, page.getTotal());
//        pageVo.setTotal(page.getTotal());
//        pageVo.setRows(roles);
        return ResponseResult.okResult(pageVo);
    }

    //-------------------------------新增角色----------------------------------------

    @Override
    @Transactional
    public ResponseResult insertRole(Role role) {
        save(role);
        System.out.println(role.getId());
        if(role.getMenuIds() != null && role.getMenuIds().length > 0){
            insertRoleMenu(role);
        }
        return ResponseResult.okResult();
    }

    //-----------------------新增用户-①查询角色列表接口----------------------------

    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstants.NORMAL));
    }

    //-----------------------修改用户-①根据id查询用户信息----------------------------
    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);
    }


    private void insertRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }
}
