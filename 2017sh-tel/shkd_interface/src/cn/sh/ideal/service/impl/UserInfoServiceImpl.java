/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sh.ideal.dao.IUserInfoDao;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.service.IUserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
	private static Logger log = Logger.getLogger(UserInfoServiceImpl.class);
	@Resource
	private IUserInfoDao userInfoDao;

	

	public boolean insert(UserInfo record) {
		boolean blag=true;
		try {
			userInfoDao.insert(record);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	public List<UserInfo> getUserInfo(UserInfo user) {
		return userInfoDao.getUserInfo(user);
	}

	
	public int countuser(UserInfo user) {
		return userInfoDao.countuser(user);
	}

	
	public List<UserInfo> getsmailworid(UserInfo user) {
		return userInfoDao.getsmailworid(user);
	}

	
	public UserInfo userinfoByworkid(String workId) {
		return userInfoDao.userinfoByworkid(workId);
	}

	
	public boolean resetPasswd(UserInfo user) {
		boolean blag=true;
		try {
			userInfoDao.resetPasswd(user);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	public boolean insertroleuser(UserInfo user) {
		boolean blag=true;
		try {
			userInfoDao.insertroleuser(user);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	public boolean deluserInfo(String workId,int userId) {
		boolean blag=true;
		UserInfo userinfo=new UserInfo();
		userinfo.setUserId(userId);
		try {
			userInfoDao.deluserInfo(workId);
			userInfoDao.upduserRole(userId);
			userInfoDao.delsmailWorkid(userId);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	public boolean updUserInfo(UserInfo user) {
		boolean blag=true;
		try {
			userInfoDao.updUserInfo(user);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	public UserInfo userinfoByuserid(Integer userId) {
		return userInfoDao.userinfoByuserid(userId);
	}

	
	public boolean updateuserRolebyuserid(UserInfo user) {
		boolean blag=true;
		try {
			userInfoDao.updateuserRolebyuserid(user);
		} catch (Exception e) {
			blag=false;
			log.error(e);
		}
		return blag;
	}

	
	public List<UserInfo> rolelist() {
		return userInfoDao.rolelist();
	}
	
	
	
	
	protected static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	
	public UserInfo selectByBigWorkid(String workId) {
		return userInfoDao.selectByBigWorkid(workId);
	}

	
	public Integer selectUserId(UserInfo userInfo) {
		return userInfoDao.selectUserId(userInfo);
	}

	
	public List<UserInfo> selectgroupName() {
		return userInfoDao.selectgroupName();
	}

	
	public boolean deleteBigWorkid(Set<String> userlist) {
		return userInfoDao.deleteBigWorkid(userlist);
	}

	
	public boolean deleteSmallWorkid(Set<String> userlist, Integer pid) {
		return userInfoDao.deleteSmallWorkid(userlist, pid);
	}

	
	public List<UserInfo> selectSmailWorkId(UserInfo user) {
		return userInfoDao.selectSmailWorkId(user);
	}
	
	 /**
     * 根据小工号匹配员工信息  用于 录音同步接口
     * */
    public UserInfo getUserBySmallworkid(UserInfo userinfo){
    	return userInfoDao.getUserBySmallworkid(userinfo);
    }

	}
	