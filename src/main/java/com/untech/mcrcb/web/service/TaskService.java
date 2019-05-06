package com.untech.mcrcb.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.TaskDao;
import com.untech.mcrcb.web.dao.WjwCertNumDao;
import com.untech.mcrcb.web.dao.WjwCheckerDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwCertNum;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import org.springframework.util.CollectionUtils;

/**
 * WJW_PAYMAIN Service
 *
 * @author chenyong
 * @since 2015-11-04
 */

@Service
public class TaskService extends BaseService<WjwPaymain, Long> {
    @Autowired
    private TaskDao dao;
    @Autowired
    private WjwCheckerDao wcDao;
    @Autowired
    private WjwCertNumDao certNumDao;
    @Autowired
    private WjwPaydetailService wjwPaydetailService;

    public BaseDao<WjwPaymain, Long> getHibernateBaseDao() {
        return this.dao;
    }

    public Pagination<Map<String, Object>> fetchPager(String connNo, String unitNo, String startTime, String endTime,
                                                      Integer start, Integer limit, String dspUserno) {
        return this.dao.fetchPager(connNo, unitNo, startTime, endTime, start, limit, dspUserno);
    }

    public Pagination<Map<String, Object>> fetchPagerfinish(String connNo, String unitNo, String startTime, String endTime,
                                                            Integer start, Integer limit, String dspUserno) {
        return this.dao.fetchPagerfinish(connNo, unitNo, startTime, endTime, start, limit, dspUserno);
    }

    public Pagination<Map<String, Object>> fetchPagerself(String connNo, String startTime, String endTime,
                                                          Integer start, Integer limit, String dspUserno) {
        return this.dao.fetchPagerself(connNo, startTime, endTime, start, limit, dspUserno);
    }

    public List<Map<String, Object>> getPayMainByConnno(String connNo) {
        return this.dao.getPayMainByConnno(connNo);
    }

    public List<Map<String, Object>> getPayMainById(String id) {
        return this.dao.getPayMainById(id);
    }

    public List<Map<String, Object>> getPayMxByConnno(String connNo) {
        return this.dao.getPayMxByConnno(connNo);
    }

    public List<Map<String, Object>> getUserName(String userCode) {
        return this.dao.getUserName(userCode);
    }

    @Transactional
    public boolean updateStatus(WjwPaymain entity) {
        this.updateEntity(entity);
        this.dao.updatePayMx(entity.getConnNo(), entity.getStatus());
        return true;
    }

    @Transactional
    public boolean updatePayAcctNo(WjwPaymain entity) {
        //更新结果
        this.updateEntity(entity);
        if (entity.getStatus() == 5) {
            //根据申请人的code进行判断该体系来自哪里
            int accCode=getAccCodeByUserCode(entity.getSqUserno());
            //查询主账号信息
//            List<Map<String, Object>> list = dao.queryMainAcctNo();
            List<Map<String, Object>> list = dao.queryMainAcctNoByaccCode(accCode);
            if (list != null && list.size() > 0) {
                String accNo = list.get(0).get("accNo").toString();
                String custName = list.get(0).get("custName").toString();
                String bankName = list.get(0).get("bankName").toString();
                dao.updatePayMxAcct(entity.getConnNo(), accNo, custName, bankName);
            }

            String ConnNo = entity.getConnNo();
            List<Map<String, Object>> detailList = this.getPayMxByConnno(ConnNo);
            if (detailList != null && detailList.size() > 0) {
                for (int i = 0; i < detailList.size(); i++) {
//                    String operNo = wjwPaydetailService.createCode();
//                    String certNo = detailList.get(i).get("operNo").toString();
//                    Integer connNo = certNumDao.getNo(certNo);

                    //复审通过时凭证号和状态改变
                    String id = detailList.get(i).get("id").toString();
                    dao.upadateStaAndOper(Long.valueOf(id), entity.getStatus());

                    //复审通过时凭证号修改记录插入到凭证号记录表中
//					 WjwCertNum entity1 = new WjwCertNum();
//			    	 entity1.setCertNo(operNo);
//			    	 entity1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//			    	 entity1.setUserNo(SecurityContextUtil.getCurrentUser().getUserCode());
//			    	 entity1.setUserName(SecurityContextUtil.getCurrentUser().getUsername());
//			    	 entity1.setUnitNo(SecurityContextUtil.getCurrentUser().getOrgId().toString());
//			    	 entity1.setUnitName(SecurityContextUtil.getCurrentUser().getOrgName());
//			    	 entity1.setConnNo(connNo);
//			    	 entity1.setNote("复审通过时改变凭证号");
//			    	 certNumDao.insert(entity1);
                }
            }

        }

        this.dao.updatePayAndNo(entity.getConnNo(), entity.getStatus());
        return true;
    }
    /** TODO: --根据申请人的code进行判断该体系来自哪里
     * @author Mr.lx
     * @Date 2019/4/19 0019
    * @param userCode 申请人code
     * @return int 账号体系返回
     **/
    private int getAccCodeByUserCode(String userCode){
        Map<String, Object> userMap=dao.getUserByCode(userCode);
        Integer userId= (Integer) userMap.get(Constants.USER.USER_ID);
        List<Map<String, Object>> roleMap=dao.getUserRole(userId);
        for (Map<String, Object> map:roleMap) {
            if (Constants.USER_ROLE.ROLE_YLY.equals(map.get(Constants.USER.ROLE_CODE))){
                return Constants.ACC_CODE.JLY;
            }
        }
        return Constants.ACC_CODE.WSY;
    }

    public Map<String, Object> getRechecker(String role_id) {
        return wcDao.getRoleUser(role_id).get(0);
    }

    public void saveInAcc(String IN_ACCNO, String IN_NAME, String IN_BANK, String unitNo) {
        dao.saveInAcc(IN_ACCNO, IN_NAME, IN_BANK, unitNo);
    }

    public List<Map<String, Object>> getInAccList(String IN_ACCNO) {
        return dao.getInAccList(IN_ACCNO);
    }
}

