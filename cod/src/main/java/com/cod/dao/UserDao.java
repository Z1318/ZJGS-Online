package com.cod.dao;


import com.cod.common.page.PagingResult;
import com.cod.common.page.Search;
import com.cod.dao.common.BaseMybatisDAO;
import com.cod.dto.UserDto;
import com.cod.entity.manage.User;
import com.cod.entity.manage.User_Group;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author wjn
 */
@Repository
public class UserDao extends BaseMybatisDAO {
    private final String MAPPER_NAMESPACE = "com.cod.dao.mapper.MycountMapper";
    @Autowired
    SqlSessionTemplate template;
    /**
     * 插入我的账户
     */
    public void insertData(User mycount){
        String sqlId=MAPPER_NAMESPACE + ".insert";
        super.save(sqlId,mycount);
    }

    /**
     * 依据用户名和密码获取我的账户
     */
    public User getMycount(Map <String,Object>map){
        String sqlId=MAPPER_NAMESPACE + ".getMycount";
        return super.findOne(sqlId,map);
    }


    /**
     * 依据用户名和密码获取我的账户
     */
    public User getMycountByUsername(String username){
        String sqlId=MAPPER_NAMESPACE + ".getMycountByUsername";
        return super.findOne(sqlId,username);
    }

    /**
     * 获取所有的账户
     */
    public List<User> getAllMycount(){
        String sqlId=MAPPER_NAMESPACE + ".getAllMycount";
        return super.findAll(sqlId);
    }

    /*
     *依据用户获取组
     */
    public List<String> getGroupByUser(long userid){
        String sqlId=MAPPER_NAMESPACE + ".getGroupByUserid";
        return template.selectList(sqlId,userid);

    }

    /*
     *依据用户id获取所属的数据权限
     */
    public List<String> getGroupsByUser(long userid){
        String sqlId=MAPPER_NAMESPACE + ".getGroupsByUserid";
        return template.selectList(sqlId,userid);
    }


    /*
     *根据用户id删除数据权限
     */
    public  void deleteUserGroup(int id){
        String sqlId = MAPPER_NAMESPACE + ".deleteUserGroup";
        super.delete(sqlId,id);
    }

    /**
     * 插入所有的用户数据权限
     */
    public void insertUserGroup(List<User_Group> user_groups){
        String sqlId=MAPPER_NAMESPACE + ".insertUserGroup";
        super.saveBatch(sqlId,user_groups);
    }

    /**
     *依据组名获取用户
     * @return
     */
    public List<User> getUserByGroupName(String blgroup){
        String sqlId=MAPPER_NAMESPACE + ".getUserByGroupName";
        return super.findAll(sqlId,blgroup);
    }

    /*
     *获取所有的组
     */
    public List<String> getAllGroup(){
        String sqlId=MAPPER_NAMESPACE + ".getAllGroup";
        return template.selectList(sqlId);

    }

    /**
     * 依据用户名获取组名
     * @return
     */
    public String getTeamByUserName(String username){
        String sqlId=MAPPER_NAMESPACE + ".getTeamByUserName";
        return template.selectOne(sqlId,username);
    }

    /**
     * 分页查询出账户
     * @param search
     * @return
     */
    public PagingResult<User> getCounts(Search search) {
        return super.findForPage(MAPPER_NAMESPACE + ".countUser", MAPPER_NAMESPACE
                + ".getAllMycount", search);
    }

    //依据用户获取角色
    public List<Integer> getRoleByUser(long userid){
        String sqlId=MAPPER_NAMESPACE + ".getRoleByUserid";
        return template.selectList(sqlId,userid);
    }


    //更新我的账户
    public void update(User user){
        String sqlId=MAPPER_NAMESPACE + ".updateMycount";
        super.update(sqlId,user);
    }

    public int updateByuserId(Long userid,String password){
        User user = new User();
        user.setId(userid);
        user.setPassword(password);
        String sqlId=MAPPER_NAMESPACE + ".updatePassByid";
        return template.update(sqlId,user);
    }

}
