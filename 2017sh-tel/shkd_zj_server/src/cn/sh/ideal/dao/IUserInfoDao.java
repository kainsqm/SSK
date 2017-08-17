package cn.sh.ideal.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.UserInfoList;

public interface IUserInfoDao {

    void insert(UserInfo record);


    
    public List<UserInfo> getUserInfo(UserInfo user);
    public List<UserInfoList> getUserInfoByparam(UserInfo user);
    
    
    public int countuser(UserInfo user);
    
    List<UserInfoList> getsmailworid(UserInfoList user);
    
    UserInfo userinfoByworkid(String workId);
    
    void releasePasswd(UserInfo user);
    
    void  insertroleuser(UserInfo user);
    
    void deluserInfo(String WorkId);
    void upduserRole(Integer userId);
    
    void updUserInfo(UserInfo user);
    void delsmailWorkid(Integer userId);
    
    UserInfo userinfoByuserid(Integer userId);
   
    void updateuserRolebyuserid(UserInfo user);
    List<UserInfo> rolelist();
   
    /**
     * 根据大工号获取用户信息；数据来源：接口数据
     * @param workId
     * @return
     */
    UserInfo selectByBigWorkid(String workId);
    List<UserInfo> selectSmailWorkId(UserInfo user);
    /**
     * 根据大工号、小工号获取用户userid
     * @param 
     * @return
     */
    Integer selectUserId(UserInfo userInfo);
    
    List<UserInfo> selectgroupName();
    /**
     * 逻辑删除大工号
     * @param userlist
     * @return
     */
    boolean deleteBigWorkid(@Param("userlist")Set<String> userlist);
    /**
     * 
     * 逻辑删除小工号
     * @param userlist
     * @param pid
     * @return
     */
    boolean deleteSmallWorkid(@Param("userlist") Set<String> userlist,@Param("pid")Integer pid);    
    
    /**
     * 根据小工号查询姓名
     * @param smailworkId
     * @return
     */
    UserInfo  selectbysmailworkId(UserInfo user);
    
    /**
     * @author lk 2017/4/17
     * @return
     * 获取所有小工号
     */
    List<UserInfo> getagentWorkid(UserInfo user);
    
    /**
     * @author lk 2017/4/17
     * @return
     * 获取所有小工号数量
     */
    int getagentWorkidsum(UserInfo user);
    
    
    
    /*****
     * 根据工号查询该员工信息
     * @author niewenqiang
     * 2017-4-17
     * *****/
    UserInfo queryUserByworkId(String workId);
    
    
    /*****
     * 修改用户信息
     * @author niewenqiang
     * 2017-4-17
     * ******/
    boolean updateUserInfo(UserInfo userinfo,String roleId);
    
    
    /****
     * 根据用户ID查询用户信息
     * @author niewenqiang
     * 2017-4-17
     * ******/
    UserInfo queryUserById(int userid);
   
    /**
     * @author lk
     * @param userlist
     * @return
     * 通过大工号userid查询小工号
     */
    List<String> getsmailWorkidbyuserid(@Param("userlist")Set<String> userlist);
    
    String userIdbyworkId(@Param("workid")String workid);
    
    
    /*******
     * 查询受理员列表
     * @author niewenqiang
     * @date 2017-6-7
     * ********/
    List<UserInfo> getAcceptorUserList(Map<String, String> map);
    
    
    /*******
     * 查询受理员列表数量
     * @author niewenqiang
     * @date 2017-6-7
     * ********/
    int getAcceptorUserListCount(Map<String, String> map);
}