/**
 * 
 */
/**
 * @author Administrator
 *
 */
package cn.sh.ideal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ISysRoleDao;
import cn.sh.ideal.dao.ISysRoleFunctionDao;
import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.SysRoleFunction;
import cn.sh.ideal.service.ISysRoleService;

@Service("sysRoleService")
public class SysRoleServiceImpl implements ISysRoleService {
	@Resource
	private ISysRoleDao sysRoleDao;
    @Resource
    private ISysRoleFunctionDao functionDao;
	
	
	@Override
	public int insert(SysRole record) {
		return sysRoleDao.insert(record);
	}
	

	@Override
	public SysRole selectByPrimaryKey(Integer roleId) {
		return sysRoleDao.selectByPrimaryKey(roleId);
	}

	@Override
	public List<SysRole> selectList(SysRole record) {
		return sysRoleDao.selectList(record);
	}

	@Override
	public int selectCount(SysRole record) {
		return sysRoleDao.selectCount(record);
	}


	@Override
	public int updateByPrimaryKey(SysRole record) {
		return sysRoleDao.updateByPrimaryKey(record);
	}

	@Override
	public SysRole selectRoleByUserId(Integer userId) {
		return sysRoleDao.selectRoleByUserId(userId);
	}
	
	
	  /*****
     * 查询角色父类信息
     * 2017-4-13
     * @author niewenqiang
     * *******/
    public List<SysRole> queryParentRole(){
    	return sysRoleDao.queryParentRole();
    }
    
    
    /*****
     * 根据父类ID 查询子类信息
     * 2017-4-13
     * @author niewenqiang
     * *******/
    public List<SysRole> queryChildRole(int parentId){
    	return sysRoleDao.queryChildRole(parentId);
    }
    
    
    /*****
     * 根据用户ID查询该用户拥有的角色
     * @author niewenqiang
     * 2017-4-14
     * ****/
    public List<SysRole> queryRoleByUserId(int userId){
    	return sysRoleDao.queryRoleByUserId(userId);
    }
    
    

    /******
     * 根据系统角色Code查询对应的系统子角色
     * @author niewenqiang
     * 2017-4-18
     * ******/
    public List<SysRole> queryRoleByCode(String code){
    	return sysRoleDao.queryRoleByCode(code);
    }
    
    
    
    /******
     * 角色信息列表查询
     * @author niewenqiang
     * 2017-4-24
     * ******/
    public List<SysRole> queryRoleList(SysRole role){
    	return sysRoleDao.queryRoleList(role);
    }
	
    
    
    /******
     * 角色信息列表查询数量
     * @author niewenqiang
     * 2017-4-24
     * ******/
    public int queryRoleListCount(SysRole role){
    	return sysRoleDao.queryRoleListCount(role);
    }
    
    
    
    /*****
     * 根据code查询该条记录的ID
     * @author niewenqiang
     * @date 2017-4-24
     * *****/
    public int queryRoleIdByCode(String code){
    	return sysRoleDao.queryRoleIdByCode(code);
    }
    
    
    
    /******
     * 验证角色名称是否已存在
     * @author niewenqiang
     * @date  2017-4-24
     * ******/
    public int checkRoleName(Map<String, String> map){
    	return sysRoleDao.checkRoleName(map);
    }
    
    /******
     * 新增角色
     * @author niewenqiang
     * @date  2017-4-24
     * *****/
    public void addRole(SysRole role,String treedata){
    	sysRoleDao.insert(role); //新增
		if (treedata != null && !treedata.equals("")) {  //循环新增权限角色中间表
			treedata = treedata.substring(0, treedata.length() - 1);
			String[] rfname = treedata.split(",");
			for (String string : rfname) {
				SysRoleFunction rfs = new SysRoleFunction();
				rfs.setFunctionId(Integer.parseInt(string));
				rfs.setRoleId(role.getRoleId());
				functionDao.insert(rfs);
			}
		}
    }
    
    
    /******
     * 修改角色
     * @author niewenqiang
     * @date  2017-4-24
     * *****/
    public void updateRole(SysRole role,String treedata){
    	sysRoleDao.updateByPrimaryKey(role); //修改
    	SysRoleFunction function=new SysRoleFunction();
    	function.setEnabled("0");
    	function.setUpdateUserId(role.getUpdateUserId());
    	function.setRoleId(role.getRoleId());
    	//把之前的权限角色中间表数据置为无效
    	functionDao.updateByRoleId(function);
		if (treedata != null && !treedata.equals("")) {  //循环新增权限角色中间表
			treedata = treedata.substring(0, treedata.length() - 1);
			String[] rfname = treedata.split(",");
			for (String string : rfname) {
				SysRoleFunction rfs = new SysRoleFunction();
				rfs.setFunctionId(Integer.parseInt(string));
				rfs.setRoleId(role.getRoleId());
				functionDao.insert(rfs);
			}
		}
    }
    
    
    /******
     * 查询该角色是否已经被使用中
     * *******/
    public int queryRoleIsUsed(int roleId){
    	return sysRoleDao.queryRoleIsUsed(roleId);
    }
}