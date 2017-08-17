package cn.sh.ideal.service;

import java.util.List;

import cn.sh.ideal.model.SysCode;

public interface ISysCodeService {
   
   List<SysCode> selectSyscode(SysCode record);
   SysCode selectcodebyId(SysCode record);
   
   /*****
    * 根据传入的父节点值查询该节点下的所有的子节点
    * @author niewenqiang
    * @date 2017-4-24
    * ******/
   public List<SysCode> queryChildrenByValue(String flag);
   
   
   /*****
    * 根据传入的父节点标识查询该节点下的所有的子节点
    * @author niewenqiang
    * @date 2017-5-10
    * ******/
   public List<SysCode> queryChildrenByFlag(String flag);
}