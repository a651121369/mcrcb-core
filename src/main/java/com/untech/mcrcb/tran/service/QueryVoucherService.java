package com.untech.mcrcb.tran.service;

import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;








import com.untech.mcrcb.web.dao.WjwAccchangeDao;
import com.untech.mcrcb.web.dao.WjwBfhzbDao;
import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.untech.mcrcb.web.dao.WjwInterestDao;
import com.untech.mcrcb.web.dao.WjwPaydetailDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
@Service
public class QueryVoucherService extends BaseService<T,Long>{
	@Autowired
	private WjwIncomedetailDao inDao;
	@Autowired
	private WjwPaydetailDao  payDao;
	@Autowired
	private WjwBfhzbDao bfDao;
	@Autowired
	private WjwAccchangeDao changeDao;
	@Autowired
	private WjwInterestDao interestDao;
	@Override
	public BaseDao<T,Long> getHibernateBaseDao() {
		
		return this.getHibernateBaseDao();
	}
	
	public Map<String,Object> queryIncomeByVoucher(String voucher){
		return inDao.queryIncomeByVoucher(voucher);
	}

	public Map<String,Object> queryPayByVoucher(String voucher) {
		return payDao.queryPayByVoucher(voucher);
	}

	public Map<String,Object> queryAppropriateByVoucher(String voucher) {
		
		return bfDao.queryAppropriateByVoucher(voucher);
	}

	public Map<String,Object> queryInterestByVoucher(String voucher) {
		
		return changeDao.queryInterestByVoucher(voucher);
		
	}

	public Map<String, Object> queryUnknownIncomeByVoucher(String voucher) {
		
		return changeDao.queryUnknownIncomeByVoucher(voucher);
	}

	public Map<String, Object> queryInterestOutByVoucher(String voucher) {
		return interestDao.queryInterestOutByVoucher(voucher);
	}
	
}
