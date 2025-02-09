package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ChangeRoleStatusDto;
import com.sangeng.domain.entity.Role;
import com.sangeng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //------------------------------查询角色列表---------------------------------------
    @GetMapping("/list")
    public ResponseResult list(Role role, Integer pageNum, Integer pageSize) {
        return roleService.selectRolePage(role,pageNum,pageSize);
    }

    //-----------------------------修改角色的状态--------------------------------------

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));
    }

    //-------------------------------新增角色-----------------------------------------

    @PostMapping
    public ResponseResult add( @RequestBody Role role) {
        return roleService.insertRole(role);
    }

    //--------------------------------删除角色---------------------------------------

    @DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable(name = "ids") List<Long> ids) {
        roleService.removeByIds(ids);
        return ResponseResult.okResult();
    }

    //--------------------------------新增用户---------------------------------------

    @GetMapping("/listAllRole")
    //①查询角色列表接口
    public ResponseResult listAllRole(){
        List<Role> roles = roleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }
}
