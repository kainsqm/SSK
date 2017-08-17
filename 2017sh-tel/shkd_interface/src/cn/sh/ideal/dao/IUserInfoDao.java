package cn.sh.ideal.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;

import cn.sh.ideal.model.UserInfo;

public interface IUserInfoDao {

    void insert(UserInfo record);


    
    public List<UserInfo> getUserInfo(UserInfo user);
    
    public int countuser(UserInfo user);
    
    List<UserInfo> getsmailworid(UserInfo user);
    
    UserInfo userinfoByworkid(String workId);
    
    void resetPasswd(UserInfo user);
    
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
     * 根据小工号匹配员工信息  用于 录音同步接口
     * */
    UserInfo getUserBySmallworkid(UserInfo userinfo);
}