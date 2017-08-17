package cn.sh.ideal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sh.ideal.dao.ItSysCodeDao;
import cn.sh.ideal.dao.IvSysuserDao;
import cn.sh.ideal.model.SysRole;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ISysCodeService;
import cn.sh.ideal.service.VSysuserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("sysuserService")
public class SysuserServiceImpl implements VSysuserService {
	@Resource
	private IvSysuserDao sysuserDao;
	
	 public List<vSysuser> selectSysusers(vSysuser vo){
	     return sysuserDao.selectSysusers(vo);
	 }
	 
	 public List<vSysuser> getGroups(){
	     return sysuserDao.getGroups();
	 }
	 
	 public List<SysRole> getRoles(){
	     return sysuserDao.getRoles();
	 }
}
