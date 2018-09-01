package com.cod.service;


import com.cod.dao.RoleDao;
import com.cod.dto.RoleDto;
import com.cod.entity.manage.Role;
import com.cod.entity.manage.Role_Func;
import com.cod.entity.manage.User_Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleDao roleDao;
    /**
     * 插入所有的角色
     */
    public void insertData(Role role){
        roleDao.insertData(role);
    }

    /**
     *
     * @param user_roles
     */
    public void insertUserRole(List<User_Role> user_roles){
        roleDao.insertUserRole(user_roles);
    }

    /**
     * 插入所有的角色菜单
     */
    public void insertRoleFunc(List<Role_Func> role_funcs){
        roleDao.insertRoleFunc(role_funcs);
    }

    /**
     * 获取所有的角色
     */
    public List<RoleDto> getRoles(){

       List<Role> list=roleDao.getRoles();
       List<RoleDto> roleDtos=new ArrayList<>();
       for(Role role:list){
           //依据roleid，获取funcid
           List<Integer> funcids=roleDao.getFuncidByroleid(role.getId());
           String funcidst="";
           for(int funcid:funcids){
               funcidst=funcidst+String.valueOf(funcid)+",";
           }
           RoleDto roleDto=new RoleDto();
           roleDto.setId(role.getId());
           roleDto.setCreate_time(role.getCreate_time());
           roleDto.setName(role.getName());
           roleDto.setFuncid(funcidst);
           roleDtos.add(roleDto);
       }
       return roleDtos;
    }

    /**
     * 依据角色id获取菜单id
     * @return
     */
    public List<Long> findByRoleIds(List<Integer> roleids){
        return roleDao.findByRoleIds(roleids);
    }


    /**
     * 依据用户id获取角色id
     * @param user_id
     * @return
     */
    public List<Integer> getRoleidByUserid(long user_id) {
        return roleDao.getRoleidByUserid(user_id);
    }
    /**
     * 删除角色
     * @param id
     */
    public void deleteRoleById(int id){
        roleDao.deleteRoleById(id);
    }

    /**
     * 删除用户角色
     * @param id
     */
    public void deleteUserRole(int id){
        roleDao.deleteUserRole(id);
    }

    /**
     * 删除角色菜单
     * @param id
     */
    public void deleteRoleFunc(int id){
        roleDao.deleteRoleFunc(id);
    }

    /**
     * 删除角色菜单
     * @param id
     */
    public void deleteRoleFuncByFuncid(int id){
        roleDao.deleteRoleFuncByFuncid(id);
    }



    public void updateRole(Role role){
        roleDao.updateRole(role);
    }

    /**
     *
     * @param roleid
     * @return
     */
    public List<Integer> getFuncidByroleid(long roleid){
        return roleDao.getFuncidByroleid(roleid);
    }
}
