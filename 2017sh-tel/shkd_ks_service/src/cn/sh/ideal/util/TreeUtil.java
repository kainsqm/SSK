package cn.sh.ideal.util;

import java.util.ArrayList;
import java.util.List;

import cn.sh.ideal.model.SysRole;

/**
 * Tree解析工具类
 * 
 * @author chendi
 * @version 1.0
 */
public class TreeUtil {

    private List<SysRole> treeNodeList = new ArrayList<SysRole>();

    public TreeUtil(List<SysRole> list) {
        treeNodeList = list;
    }

    /**
     * 
     * @param roleId
     * @return
     */
    public SysRole getNodeById(int roleId) {
        SysRole treeNode = new SysRole();
        for (SysRole item : treeNodeList) {
            if (item.getRoleId() == roleId) {
                treeNode = item;
                break;
            }
        }
        return treeNode;
    }

    /**
     * 
     * @param roleId
     * @return
     */
    public List<SysRole> getChildrenNodeById(int roleId) {
        List<SysRole> childrenSysRole = new ArrayList<SysRole>();
        for (SysRole item : treeNodeList) {
            if (Integer.parseInt(item.getPid()) == roleId) {
                childrenSysRole.add(item);
            }
        }
        return childrenSysRole;
    }

    /**
     * 递归生成Tree结构数据
     * 
     * @param roleId
     * @return
     */
    public SysRole generateSysRole(int roleId) {
        SysRole role = this.getNodeById(roleId);
        List<SysRole> childrenSysRole = this.getChildrenNodeById(roleId);
        for (SysRole item : childrenSysRole) {
            SysRole node = this.generateSysRole(item.getRoleId());
            role.getChildren().add(node);
        }
        return role;
    }
}
