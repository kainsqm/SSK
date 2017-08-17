package cn.sh.ideal.service;

import java.util.List;
import java.util.Map;

import cn.sh.ideal.model.SysCode;
import cn.sh.ideal.model.SysRole;

public interface ISysRoleService {

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);
    
    List<SysRole> selectList(SysRole record);
    
    int selectCount(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    SysRole selectRoleByUserId(Integer userId);
    
    
    /*****
     * 查询角色父类信息
     * 2017-4-13
     * @author niewenqiang
     * *******/
    public List<SysRole> queryParentRole();
    
    
    /*****
     * 根据父类ID 查询子类信息
     * 2017-4-13
     * @author niewenqiang
     * *******/
    public List<SysRole> queryChildRole(int parentId);
    
    
    /*****
     * 根据用户ID查询该用户拥有的角色
     * @author niewenqiang
     * 2017-4-14
     * ****/
    public List<SysRole> queryRoleByUserId(int userId);
    
    

    /******
     * 根据系统角色Code查询对应的系统子角色
     * @author niewenqiang
     * 2017-4-18
     * ******/
    public List<SysRole> queryRoleByCode(String code);
    
    
    /******
     * 角色信息列表查询
     * @author niewenqiang
     * 2017-4-24
     * ******/
    public List<SysRole> queryRoleList(SysRole role);
    
    
    
    /******
     * 角色信息列表查询数量
     * @author niewenqiang
     * 2017-4-24
     * ******/
    public int queryRoleListCount(SysRole role);
    
    
    /*****
     * 根据code查询该条记录的ID
     * @author niewenqiang
     * @date 2017-4-24
     * *****/
    public int queryRoleIdByCode(String code);
    
    
    /******
     * 验证角色名称是否已存在
     * @author niewenqiang
     * @date  2017-4-24
     * ******/
    public int checkRoleName(Map<String, String> map);
    
    
    
    
    /******
     * 新增角色
     * @author niewenqiang
     * @date  2017-4-24
     * *****/
    public void addRole(SysRole role,String treeList);
    
    
    /******
     * 修改角色
     * @author niewenqiang
     * @date  2017-4-24
     * *****/
    public void updateRole(SysRole role,String treeList);
    
    
    
    
    /******
     * 查询该角色是否已经被使用中
     * *******/
    public int queryRoleIsUsed(int roleId);
}