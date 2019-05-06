package com.untech.mcrcb.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwBfdetailDao;
import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.untech.mcrcb.web.model.WjwBfdetail;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;

/**
 * WJW_BFDETAIL Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwBfdetailService
      extends BaseService<WjwBfdetail, Long>
    {
      @Autowired
      private WjwBfdetailDao dao;
      
      @Autowired
      private WjwIncomedetailDao incomedao;
      
      public BaseDao<WjwBfdetail, Long> getHibernateBaseDao()
      {
        return this.dao;
      }

	public Pagination<Map<String, Object>> getBfdetailList(Integer start, Integer limit, Long id, String unitNo,
			String unitName, String connNo, String startTime, String endTime) {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());
		return dao.getBfdetailList(start,limit,id,unitNo,unitName,connNo,startTime,endTime,parentId,user.getOrgId());
	}
}

