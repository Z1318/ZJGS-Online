package com.cod.service;


import com.cod.common.page.PagingResult;
import com.cod.common.page.Search;
import com.cod.dao.UserDao;
import com.cod.dto.UserDto;
import com.cod.entity.manage.GroupDto;
import com.cod.entity.manage.User;
import com.cod.entity.manage.User_Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MycountService {
    @Autowired
    UserDao mycountDao;

    //插入mycount数据
    public void insertMycount(User mycount){
        mycountDao.insertData(mycount);
    }


    //依据学号和密码查询出mycount数据
    public User getMycount(String username, String password){
        Map<String,Object> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        return mycountDao.getMycount(map);
    }

    /**
     * 获取我的账户依据用户名
     * @return
     */
    public User getMycountByUsername(String username){
        return mycountDao.getMycountByUsername(username);
    }

    //查询出mycount数据
    public PagingResult<UserDto> getAllMycount(int page,int size,String url){
        //定义查询的参数
        Search search=new Search();
        search.setPage(page);
        search.setRows(size);
        search.setUrl(url);

        PagingResult<User> pagelist=mycountDao.getCounts(search);

        List<UserDto> userDtos=new ArrayList<>();

        for(User user:pagelist.getRows()){
            //依据用户id获取角色id
            List<Integer> roleids=mycountDao.getRoleByUser(user.getId());
            List<String>  groups=mycountDao.getGroupsByUser(user.getId());
            String roleidst="";
            for(int roleid:roleids){
                roleidst=roleidst+String.valueOf(roleid)+",";
            }
            String groupdst="";
            for (String group:groups) {
                groupdst=groupdst+group+",";
            }

            UserDto userDto=new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setPhone(user.getPhone());
            userDto.setBlgroup(user.getBlgroup());
            userDto.setStatus(user.getStatus());
            userDto.setIp(user.getIp());
            userDto.setRoles(roleidst);
            userDto.setGroupNames(groupdst);
            userDtos.add(userDto);
        }
        PagingResult<UserDto> userDtoPagingResult=new PagingResult<>();
        userDtoPagingResult.setTotal(pagelist.getTotal());
        userDtoPagingResult.setRows(userDtos);
        userDtoPagingResult.setPage(pagelist.getPage());
        userDtoPagingResult.setPageSize(pagelist.getPageSize());
        userDtoPagingResult.setHtmlst(pagelist.getHtmlst());
        userDtoPagingResult.setMaxpage(pagelist.getMaxpage());
        return userDtoPagingResult;
    }



    //更新我的账户
    public void update(User user){
        mycountDao.update(user);
    }

    /**
     * 依据用户名获取组名
     * @return
     */
    public String getTeamByUserName(String username){
        return mycountDao.getTeamByUserName(username);
    }



    //查出所有的组
    public List<GroupDto> getAllGroup(){
        List<String> list = mycountDao.getAllGroup();
        List<GroupDto> groupDtos=new ArrayList<>();
        for(String groupname:list){
            GroupDto groupDto = new GroupDto();
            groupDto.setGroupName(groupname);
            groupDtos.add(groupDto);
        }

        return groupDtos;

    }

    /**
     * 依据用户id获取组集合
     * @param id
     * @return
     */
    public List<String>getGroupsByUser(Long id){
        return mycountDao.getGroupsByUser(id);
    }
    //根据用户id删除数据权限
    public void deleteUserGroup(int id){
        mycountDao.deleteUserGroup(id);
    }
    /*
     *插入用户与数据关系
     */
    public void insertUserGroup(List<User_Group> user_groups){
        mycountDao.insertUserGroup(user_groups);
    }

    public int updatePassById(Long userid,String password){
        return mycountDao.updateByuserId(userid,password);
    }

    public List<User> getUserByGroupName(String blgroup){
        return mycountDao.getUserByGroupName(blgroup);
    }

    /**
     * 获取所有的账户
     * @return
     */
    public List<User> getAllMycount(){
       return mycountDao.getAllMycount();
    }
}
