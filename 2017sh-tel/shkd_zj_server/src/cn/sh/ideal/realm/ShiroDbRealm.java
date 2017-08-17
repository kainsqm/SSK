package cn.sh.ideal.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sh.ideal.model.Function;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.CipherUtil;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.HttpUtil;

public class ShiroDbRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	private static final String ALGORITHM = "MD5";
	
	private IUserInfoService userInfoService;

	public ShiroDbRealm() {
		super();
	}
	
	public IUserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(IUserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		if (token != null) {
			return new SimpleAuthenticationInfo(token.getUsername(),token.getPassword(), getName());
		}else{
			throw new AuthenticationException();
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 这里编写授权代码 
			    Set<String> permissions = new HashSet<String>();
				//加载用户权限
				Subject subject = SecurityUtils.getSubject();
				
				String token=subject.getPrincipal().toString();
				String [] param=token.split(",");
				String systemUrl="";
				if(param[1]=="true"){ //内网地址
					systemUrl=ConfigProperties.getProperty("eniSystemUrl");
				}else{
					systemUrl=ConfigProperties.getProperty("SystemUrl");
				}
				String json=HttpUtil.doGet(systemUrl+ ConfigProperties.getProperty("SSO_BASE_URL")
						+ ConfigProperties.getProperty("SSO_USER_TOKEN_SERVICE")+param[0]);
				JSONObject jsonobj=JSONObject.fromObject(json);
				String functionName=jsonobj.getString("listFun");
				String[] functionList=functionName.split(",");	
			  //  List<Function> functionList=functionService.getPermissionByUser(subject.getPrincipal().toString());
			    for (int i = 0; i < functionList.length; i++) {
			    	permissions.add(functionList[i]);
				}		    
			    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			    info.setStringPermissions(permissions);
				return info;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

//	@PostConstruct
//	public void initCredentialsMatcher() {//MD5加密
//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
//		setCredentialsMatcher(matcher);
//	}
}
