//-------------------------------------------------------------------------
package com.untech.mcrcb.web.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.untech.mcrcb.web.common.Constants;
import com.unteck.tpc.framework.web.model.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.PublicDao;
import com.unteck.common.dao.support.Pagination;

/**
 *
 * @author            fanhua
 * @version           1.0
 * @since             2015年11月5日
 */
@Service
public class PublicService {

	@Autowired
	private PublicDao publicDao;

	public List<Map<String, Object>> getAccCode(){
		return publicDao.getAccCode();
	}

	public List<Map<String, Object>> getOrganization(String orgId){
		return publicDao.getOrganization(orgId);
	}

	public List<Map<String, Object>> getOrganization(String orgId,int accCode){
		return publicDao.getOrganizationByAccCode(orgId,accCode);
	}
	public List<Map<String, Object>> getInAcc(){
		return publicDao.getInAcc();
	}

	public Pagination<Map<String,Object>> findInAccInfoByInName(Integer start, Integer limit,String unitNo,
			String inName) {
		return publicDao.findInAccInfoByInName(start, limit,unitNo,inName);
	}
	/** TODO: ---判断用户的类型，默认是卫生院，
	 * @Author Mr.lx
	 * @Date 2019/4/3 0003
	* @param user
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	public int judgeAccountType(User user) {
		int accCode= Constants.ACC_CODE.WSY;
		Set<GrantedAuthority> authSets= user.getAuthorities();
		for (GrantedAuthority auth:authSets) {
			//有医养中心审核，医养中心复审，都算是医养中心体系
			if (Constants.USER_ROLE.ROLE_YLY.equals(auth.getAuthority())
					||Constants.USER_ROLE.ROLE_JLY_CHECK.equals(auth.getAuthority())
					||Constants.USER_ROLE.ROLE_JLY_RECHECK.equals(auth.getAuthority())){
				accCode=Constants.ACC_CODE.JLY;
				break;
			}
		}
		return accCode;
	}
	/** TODO: ---特殊情况，如果是卫计委用户，权限比较大，什么都能查看
	 * @author Mr.lx
	 * @Date 2019/4/26 0026
	* @param user
	 * @return int
	 **/
	public int judgeUserForm(User user) {
		int userCode= Constants.ACC_CODE.DEFAULT;
		Set<GrantedAuthority> authSets= user.getAuthorities();
		for (GrantedAuthority auth:authSets) {
			//有医养中心审核，医养中心复审，都算是医养中心体系
			if (Constants.USER_ROLE.ROLE_WJW.equals(auth.getAuthority())){
				userCode=Constants.ACC_CODE.WJW;
				break;
			}
		}
		return userCode;
	}
//	public int judgeAccountTypeBy(User user) {
//		int accCode= Constants.ACC_CODE.WSY;
//		Set<GrantedAuthority> authSets= user.getAuthorities();
//		for (GrantedAuthority auth:authSets) {
//			if (Constants.USER_ROLE.ROLE_YLY.equals(auth.getAuthority())){
//				accCode=Constants.ACC_CODE.YLY;
//				break;
//			}
//		}
//		return accCode;
//	}

}

