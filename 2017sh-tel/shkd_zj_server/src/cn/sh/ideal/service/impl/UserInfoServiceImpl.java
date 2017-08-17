/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sh.ideal.dao.ISysRoleDao;
import cn.sh.ideal.dao.IUserInfoDao;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.UserInfo;
import cn.sh.ideal.model.UserInfoList;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.IUserInfoService;
import cn.sh.ideal.util.ConfigProperties;
import cn.sh.ideal.util.DesUtil;
import cn.sh.ideal.util.FileUtil;
import cn.sh.ideal.util.PoiExcelUtil;
import cn.sh.ideal.util.ValidatorUtil;

@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {
	private static Logger log = Logger.getLogger(UserInfoServiceImpl.class);
	@Resource
	private IUserInfoDao userInfoDao;
	@Resource
	private ISysRoleDao sysRoleDao;
	

	@Override
	public boolean insert(UserInfo user, String roleId) {
		boolean blag = true;
		userInfoDao.insert(user);
		// 循环新增用户角色权限表
		String[] roleList = roleId.split(",");
		for (int i = 0; i < roleList.length; i++) {
			user.setRole_id(Integer.parseInt(roleList[i]));
			userInfoDao.insertroleuser(user);
		}

		return blag;
	}

	@Override
	public List<UserInfo> getUserInfo(UserInfo user) {
		return userInfoDao.getUserInfo(user);
	}

	@Override
	public int countuser(UserInfo user) {
		return userInfoDao.countuser(user);
	}

	@Override
	public List<UserInfoList> getsmailworid(UserInfoList user) {
		return userInfoDao.getsmailworid(user);
	}

	@Override
	public UserInfo userinfoByworkid(String workId) {
		return userInfoDao.userinfoByworkid(workId);
	}

	@Override
	public boolean releasePasswd(UserInfo user) {
		boolean blag = true;
		try {
			userInfoDao.releasePasswd(user);
		} catch (Exception e) {
			blag = false;
			log.error(e);
		}
		return blag;
	}

	@Override
	public boolean insertroleuser(UserInfo user) {
		boolean blag = true;
		try {
			userInfoDao.insertroleuser(user);
		} catch (Exception e) {
			blag = false;
			log.error(e);
		}
		return blag;
	}

	@Override
	public boolean deluserInfo(String workId, int userId) {
		boolean blag = true;
		UserInfo userinfo = new UserInfo();
		userinfo.setUserId(userId);
		try {
			userInfoDao.deluserInfo(workId);
			userInfoDao.upduserRole(userId);
			userInfoDao.delsmailWorkid(userId);
		} catch (Exception e) {
			blag = false;
			log.error(e);
		}
		return blag;
	}

	@Override
	public boolean updUserInfo(UserInfo user) {
		boolean blag = true;
		try {
			userInfoDao.updUserInfo(user);
		} catch (Exception e) {
			blag = false;
			log.error(e);
		}
		return blag;
	}

	/*****
	 * 修改用户信息
	 * 
	 * @author niewenqiang 2017-4-17
	 * ******/
	public boolean updateUserInfo(UserInfo userinfo, String roleId) {
		boolean blag = true;
		userInfoDao.updUserInfo(userinfo);
		// 循环新增用户角色权限表
		String[] roleList = roleId.split(",");
		List<SysRole> sysList=sysRoleDao.queryRoleByUserId(userinfo.getUserId());
		//判断修改用户的时候是否修改了角色
		if(roleList.length!=sysList.size()){ //用户修改了角色
			userInfoDao.upduserRole(userinfo.getUserId());
			for (int i = 0; i < roleList.length; i++) {
				userinfo.setRole_id(Integer.parseInt(roleList[i]));
				userInfoDao.insertroleuser(userinfo);
			}
		}else{
			for (int i = 0; i < roleList.length; i++) {
				boolean changeRole=true;
				int roleIdindex=Integer.parseInt(roleList[i]);
				for (int j = 0; j < sysList.size(); j++) {
					SysRole role=sysList.get(j);
					if(role.getRoleId()==roleIdindex){  //已存在
						changeRole=false;
					}
				}
				if(changeRole){ //表示该用户修改了角色
					userInfoDao.upduserRole(userinfo.getUserId());
					for (int k = 0; k < roleList.length; k++) {
						userinfo.setRole_id(Integer.parseInt(roleList[k]));
						userInfoDao.insertroleuser(userinfo);
					}
					break;
				}
			}			
		}
		return blag;

	}

	@Override
	public UserInfo userinfoByuserid(Integer userId) {
		return userInfoDao.userinfoByuserid(userId);
	}

	@Override
	public boolean updateuserRolebyuserid(UserInfo user, String roleStr) {
		boolean blag = true;
		// 先删除记录 在新增
		String[] role = roleStr.split(",");
		userInfoDao.upduserRole(user.getUserId());
		for (int i = 0; i < role.length; i++) {
			user.setRole_id(Integer.parseInt(role[i]));
			userInfoDao.insertroleuser(user);
		}
		return blag;
	}

	@Override
	public List<UserInfo> rolelist() {
		return userInfoDao.rolelist();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> updateimportUser(List<List<Object>> excelList,
			Map<Object, Object> paramMap, List<List<Object>> titlelList) {
		int rowNum = 6; // 从第6行开始读取数据
		String lineErrorMsg = ""; // 行错误信息
		long errorColumnCnt = 0; // 列错误数
		boolean lineError = false; // 行错误标识
		StringBuffer errorRowStr = new StringBuffer(); // 行列错误信息
		Map<String, Object> map = new HashMap<String, Object>();// 返回值，用来存入错误个数及错误信息
		List<List<Object>> errorList = new ArrayList<List<Object>>();// 错误数据集合
		List<List<Object>> correctList = new ArrayList<List<Object>>();// 正确数据集合
		List<String> errormslist = new ArrayList<String>();
		List<Map<String, Integer>> errorRegionList = new ArrayList<Map<String, Integer>>();// 格式错误的单元格（正确数据导入）
		List<Map<String, Integer>> regionList = new ArrayList<Map<String, Integer>>();// 格式错误的单元格（正确数据不导入）
		List<Map<String, Integer>> errorColorList = new ArrayList<Map<String, Integer>>();// 与数据库客户号码重复（正确数据导入）
		try {

			Map<Object, Object> mapPutkeyValueMap = new HashMap<Object, Object>();
			if (excelList.isEmpty()) {
				errorColumnCnt += 1;
				lineErrorMsg = "第7行以后(包括第7行)没有数据源信息数据！";
				map.put("errorCnt", errorColumnCnt);
				map.put("errorRowStr", errorRowStr);
				errormslist.add(lineErrorMsg);
			} else {
				// 获取excel列的数目
				int cellNum = excelList.get(0).size();
				if (cellNum < 6) {
					errorColumnCnt += 1;
					lineErrorMsg = "请检查excel列数与模板列数是否一致！";
					errormslist.add(lineErrorMsg);
				} else {
					List<Object> xtitleList = titlelList.get(0);
					String titlename = (String) xtitleList.get(0);
					String titlework = (String) xtitleList.get(1);
					String titlepsss = (String) xtitleList.get(2);
					String titlezs = (String) xtitleList.get(3);
					String titlegw = (String) xtitleList.get(4);
					String titlerole = (String) xtitleList.get(5);
					String titlecenter = (String) xtitleList.get(6);
					if (!"姓名".equals(titlename)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"工号".equals(titlework)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"密码".equals(titlepsss)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"组室".equals(titlezs)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"岗位".equals(titlegw)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"角色".equals(titlerole)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					}else if (!"所属中心".equals(titlecenter)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					}
					
					
					if ("".equals(lineErrorMsg)) {
						int rowSize = excelList.size();
						for (int i = 0; i < rowSize; i++) {
							lineError=false;
							List<Object> rowObjList = excelList.get(i); // 行数据
							int errorRowNum = errorList.size() + 6;
							rowNum = rowNum + 1;
							String userName = (String) rowObjList.get(0); // 员工姓名
							/* 判断用户名是否符合规则 */
							if (!ValidatorUtil.notEmpty(userName)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:姓名不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 0);
								addElement(regionList, rowNum, 0);
							} else if (!ValidatorUtil
									.isChineseLetterAndNumber(userName)) { // 中文字母数字
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:姓名只能是中文字母数字";

								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 0);
								addElement(regionList, rowNum, 0);
							} else if (!ValidatorUtil.isLength(userName, 1, 20)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:姓名超出长度限制";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 0);
								addElement(regionList, rowNum, 0);
							}

							String workid = (String) rowObjList.get(1); // 工号
							UserInfo userinfo = userInfoDao.queryUserByworkId(workid);
							/* 判断工号是否符合规则 */
							if (!ValidatorUtil.notEmpty(workid)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:工号不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 1);
								addElement(regionList, rowNum, 1);
							} else if (!ValidatorUtil.isLetterAndNumber(workid)) { // 字母数字
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:工号只能是字母数字";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 1);
								addElement(regionList, (rowNum - 6), 1);
							} else if (!ValidatorUtil.isLength(workid, 3, 12)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:工号长度不合规则";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 1);
								addElement(regionList, rowNum, 1);
							} else if (userinfo != null) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:工号已经存在";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 1);
								addElement(regionList, rowNum, 1);
							}

							String passWD = (String) rowObjList.get(2); // 密码
							/* 判断密码是否符合规则 */
							if (!ValidatorUtil.notEmpty(passWD)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:密码不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 2);
								addElement(regionList, rowNum, 2);
							}

							String groupname = (String) rowObjList.get(3); // 组室
							/* 判断组室是否符合规则 */
							if (!ValidatorUtil.notEmpty(groupname)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:组室不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 3);
								addElement(regionList, rowNum, 3);
							}

							String Job = (String) rowObjList.get(4); // 岗位
							/* 判断岗位是否符合规则 */
							if (!ValidatorUtil.notEmpty(Job)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:岗位不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 4);
								addElement(regionList, rowNum, 4);
							}

							String role = (String) rowObjList.get(5);// 角色
							/* 判断角色是否符合规则 */
							if (!ValidatorUtil.notEmpty(role)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:角色不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 5);
								addElement(regionList, rowNum, 5);
							}else{
								String [] str=role.split(",");
								for(int k=0;k<str.length;k++){
									 if(!ValidatorUtil.number(str[k])){
										 lineError = true;
											lineErrorMsg = "第" + (rowNum - 6)
													+ "条信息导入失败,失败原因:角色只能是数字";
											errorColumnCnt += 1;
											addElement(errorRegionList, errorRowNum, 5);
											addElement(regionList, rowNum, 5);
									 }
									//判断系统中是否存在该角色ID
									SysRole sysrole=sysRoleDao.selectByPrimaryKey(Integer.parseInt(str[k]));
									if(null==sysrole){
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 6)
												+ "条信息导入失败,失败原因:系统中不存在该角色ID";
										errorColumnCnt += 1;
										addElement(errorRegionList, errorRowNum, 5);
										addElement(regionList, rowNum, 5);
									}
								}
								if(role.indexOf("，")>-1){
									 lineError = true;
										lineErrorMsg = "第" + (rowNum - 6)
												+ "条信息导入失败,失败原因:角色分隔符,只能为英文输入法下的字符";
										errorColumnCnt += 1;
										addElement(errorRegionList, errorRowNum, 5);
										addElement(regionList, rowNum, 5);
								}
								
								
							} 
							
							for (int j = 0; j < excelList.size(); j++) { // 判断excel中是否存在相同的用户名
								List<Object> obj1 = excelList.get(j);
								String cellUserName = (String) obj1.get(1);// 工号
								if (cellUserName.equals(userName) && i > j) {
									int rowNumj = 7 + j;
									lineError = true;
									lineErrorMsg = "与第" + rowNumj + "行用户工号重复,";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 0);
									addElement(regionList, rowNum - 1, 0);
								}
							}

							String center = (String) rowObjList.get(6); // 所属中心
							/* 判断岗位是否符合规则 */
							if (!ValidatorUtil.notEmpty(center)) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 6)
										+ "条信息导入失败,失败原因:所属中心不能为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 6);
								addElement(regionList, rowNum, 6);
							}else{
								if(center.equals("浦东") || center.equals("新华") || center.equals("逸仙")){
									
								}else{
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 6)
											+ "条信息导入失败,失败原因:所属中心只能为浦东/新华/逸仙";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 6);
									addElement(regionList, rowNum, 6);
								}
							}
							
							
							/*
							 * if (lineError) { userName = (userName == null) ?
							 * "" : userName;
							 * errorRowStr.append("\r\n第").append(rowNum)
							 * .append("行").append(userName).append("出错:")
							 * .append(lineErrorMsg); }
							 */

							if (lineError) {
								errormslist.add(lineErrorMsg);
								errorList.add(rowObjList);
							} else {
								correctList.add(rowObjList);
							}
						}
					}

					// 正确信息导入到数据库
					if (!correctList.isEmpty())
						updateimportUserBeanToDataBase(correctList,
								mapPutkeyValueMap, getRequest());

					// 错误数据写入到excel
					if (!errorList.isEmpty()) {
						FileUtil fileUtil = new FileUtil();
						String errorFileName = (String) paramMap
								.get("errorFileName");// 临时错误文件名
						String errorFileNamePath = (String) paramMap
								.get("errorFileNamePath");// 错误模板文件路径
						String errorNewFilePath = getRequest().getSession()
								.getServletContext().getRealPath("imptemp")
								+ File.separatorChar + errorFileName; // 临时错误文件目录
						fileUtil.copyFile(errorFileNamePath, errorNewFilePath);
						Map<Object, Object> poiMap = new HashMap<Object, Object>();
						poiMap.put("fileName", errorNewFilePath);
						poiMap.put("excelVersion", "2003");
						poiMap.put("propSheetName", "dataSourceSheetName");
						poiMap.put("propTtitle", "dataSourceTitle");
						poiMap.put("propBlackContent", "dataSourceBlackContent");
						poiMap.put("propRedContent", "dataSourceRedContent");
						poiMap.put("propColumnName", "dataSourceColumnName");
						PoiExcelUtil.poiCreateExcel(errorList, poiMap,
								errorRegionList, errorColorList);
					}
				}
			}
		} catch (Exception e) {
			map.put("errorCnt", "1");
			map.put("errorCnt", errorList.size());
			map.put("successCnt", correctList.size());
			map.put("errorRowStr", errormslist);
			log.error("导入员工异常："+e.getMessage(),e);
			return map;
		}
		map.put("successCnt", correctList.size());
		map.put("errorCnt", errorList.size());
		map.put("errorRowStr", errormslist);
		map.put("dataCnt", excelList.size() - 1);
		return map;
	}

	private List<Map<String, Integer>> addElement(
			List<Map<String, Integer>> pramaList, int rowNum, int columnNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("rowNum", rowNum);
		map.put("columnNum", columnNum);
		pramaList.add(map);
		return pramaList;
	}

	@SuppressWarnings("rawtypes")
	private void updateimportUserBeanToDataBase(List<List<Object>> correctList,
			Map<Object, Object> mapPutkeyValueMap, HttpServletRequest request) {
		vSysuser user=(vSysuser) request.getAttribute("User");
		int userid =user.getUserId();
		for (int j = 0; j < correctList.size(); j++) {
			List<Object> rowObjList = correctList.get(j); // 行数据
			UserInfo bean = new UserInfo();
			bean.setUserName((String) rowObjList.get(0));// 员工姓名
			bean.setWorkId((String) rowObjList.get(1));// 工号
			try {
				String pwd=DesUtil.encrypt((String) rowObjList.get(2),ConfigProperties.getProperty("DESKEY"));
				bean.setPasswd(pwd);// 密码
			} catch (Exception e) {
				log.error("导入员工信息加密密码出现异常"+e.getMessage(),e);
			}
			bean.setGroupName((String) rowObjList.get(3));
			bean.setJob((String) rowObjList.get(4));
			bean.setTheirCenter((String) rowObjList.get(6));
			bean.setPid(1);
			bean.setDataFrom("2");
			bean.setCreateUserId(userid);
			userInfoDao.insert(bean);
			String role=(String) rowObjList.get(5);
			String [] strrole=role.split(",");
			for (int i = 0; i < strrole.length; i++) {
				bean.setRole_id(Integer.parseInt(strrole[i]));
				userInfoDao.insertroleuser(bean);
			}
			
		}
	}

	protected static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	@Override
	public UserInfo selectByBigWorkid(String workId) {
		return userInfoDao.selectByBigWorkid(workId);
	}

	@Override
	public Integer selectUserId(UserInfo userInfo) {
		return userInfoDao.selectUserId(userInfo);
	}

	@Override
	public List<UserInfo> selectgroupName() {
		return userInfoDao.selectgroupName();
	}

	@Override
	public boolean deleteBigWorkid(Set<String> userlist) {
		return userInfoDao.deleteBigWorkid(userlist);
	}

	@Override
	public boolean deleteSmallWorkid(Set<String> userlist, Integer pid) {
		return userInfoDao.deleteSmallWorkid(userlist, pid);
	}

	@Override
	public List<UserInfo> selectSmailWorkId(UserInfo user) {
		return userInfoDao.selectSmailWorkId(user);
	}

	@Override
	public List<UserInfoList> getUserInfoByparam(UserInfo user) {
		return userInfoDao.getUserInfoByparam(user);
	}

	@Override
	public UserInfo selectbysmailworkId(UserInfo user) {
		return userInfoDao.selectbysmailworkId(user);
	}

	@Override
	public List<UserInfo> getagentWorkid(UserInfo user) {
		return userInfoDao.getagentWorkid(user);
	}

	@Override
	public int getagentWorkidsum(UserInfo user) {
		return userInfoDao.getagentWorkidsum(user);
	}

	/*****
	 * 根据工号查询该员工信息
	 * 
	 * @author niewenqiang 2017-4-17
	 * *****/
	public UserInfo queryUserByworkId(String workId) {
		return userInfoDao.queryUserByworkId(workId);
	}
	
	
	  /****
     * 根据用户ID查询用户信息
     * @author niewenqiang
     * 2017-4-17
     * ******/
    public UserInfo queryUserById(int userid){
    	return userInfoDao.queryUserById(userid);
    }
    
    
    /*******
     * 查询受理员列表
     * @author niewenqiang
     * @date 2017-6-7
     * ********/
    public List<UserInfo> getAcceptorUserList(Map<String, String> map){
    	return userInfoDao.getAcceptorUserList(map);
    }
    
    /*******
     * 查询受理员列表数量
     * @author niewenqiang
     * @date 2017-6-7
     * ********/
    public int getAcceptorUserListCount(Map<String, String> map){
    	return userInfoDao.getAcceptorUserListCount(map);
    }

}
