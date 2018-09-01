package com.cod.dao;

import com.cod.dao.common.BaseMybatisDAO;
import com.cod.entity.manage.Role;
import com.cod.entity.manage.Role_Func;
import com.cod.entity.manage.User_Role;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author  董磊
 */
@Repository
public class RoleDao extends BaseMybatisDAO {
    @Autowired
    SqlSessionTemplate template;

    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.RoleMapper";

    /**
     * 插入所有的角色
     */
    public void insertData(Role role){
        String sqlId=MAPPER_NAMESPACE + ".insert";
        super.save(sqlId,role);
    }
    /**
     * 插入所有的用户角色
     */
    public void insertUserRole(List<User_Role> user_roles){
        String sqlId=MAPPER_NAMESPACE + ".insertUserrole";
        super.saveBatch(sqlId,user_roles);
    }
    /**
     * 插入所有的角色菜单
     */
    public void insertRoleFunc(List<Role_Func> role_funcs){
        String sqlId=MAPPER_NAMESPACE + ".insertRolefunc";
        super.saveBatch(sqlId,role_funcs);
    }

    /**
     * 获取所有的角色
     */
    public List<Role> getRoles(){
        String sqlId=MAPPER_NAMESPACE + ".getAllRoles";
        return super.findAll(sqlId);
    }

    /**
     *依据角色id获取菜单id
     * @return
     */
    public List<Long> findByRoleIds(List<Integer> roleids){
        String sqlId=MAPPER_NAMESPACE + ".findByRoleIds";
        Map<String, Object> params = new HashMap<>();
        params.put("roleids", roleids);
        return template.selectList(sqlId,params);
    }

    /**
     * 依据用户id获取角色id
     * @return
     */
    public List<Integer> getRoleidByUserid(long user_id) {
        String sqlId=MAPPER_NAMESPACE + ".getRoleidByUserid";
        return template.selectList(sqlId,user_id);
    }

    /**
     * 依据单个角色id获取菜单id
     * @param role_id
     * @return
     */
    public List<Integer> getFuncidByroleid(long role_id){
        String sqlId=MAPPER_NAMESPACE + ".getFuncidByroleid";
        return template.selectList(sqlId,role_id);
    }
    /**
     * 删除角色
     * @param id
     */
    public void deleteRoleById(int id){
        String sqlId=MAPPER_NAMESPACE + ".deleteRoleById";
        super.delete(sqlId,id);
    }


    /**
     * 删除用户角色
     * @param id
     */
    public void deleteUserRole(int id){
        String sqlId=MAPPER_NAMESPACE + ".deleteUserRole";
        super.delete(sqlId,id);
    }
    /**
     * 删除角色菜单依据角色id
     * @param id
     */
    public void deleteRoleFunc(int id){
        String sqlId=MAPPER_NAMESPACE + ".deleteRoleFunc";
        super.delete(sqlId,id);
    }

    /**
     * 删除角色菜单依据菜单id
     * @param id
     */
    public void deleteRoleFuncByFuncid(int id){
        String sqlId=MAPPER_NAMESPACE + ".deleteRoleFuncByfuncid";
        super.delete(sqlId,id);
    }

    /**
     * 更新角色
     * @param role
     */
    public void updateRole(Role role){
        String sqlId=MAPPER_NAMESPACE + ".updateRole";
        super.update(sqlId,role);
    }


}
