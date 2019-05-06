package com.untech.mcrcb.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.AccountManagerDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwAccount;
import com.unteck.common.dao.support.Pagination;

/**
 * TODO:   账号业务层
 *
 * @Date 2019/3/27 0027
 **/
@Service
public class AccountManagerService extends BaseService<WjwAccount, Long> {
    @Autowired
    private AccountManagerDao dao;

    @Override
    public BaseDao<WjwAccount, Long> getHibernateBaseDao() {
        return this.dao;
    }

    public Pagination<Map<String, Object>> fetchPager(String acctNo,
                                                      String custName, Integer start, Integer limit) {
        return this.dao.fetchPager(acctNo, custName, start, limit);
    }

    public Pagination<Map<String, Object>> subfetchPager(String acctNo,
                                                         String custName, Integer start, Integer limit) {
        return this.dao.subfetchPager(acctNo, custName, start, limit);
    }
    /** TODO:  查询子账户列表
     * @Author Mr.lx
     * @Date 2019/3/28 0028
    * @param acctNo 账号
    * @param custName 账号名称
    * @param accCode 账号体系
    * @param start 分页
    * @param limit 分页
     * @return com.unteck.common.dao.support.Pagination<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public Pagination<Map<String, Object>> subfetchPagerByaccCode(String acctNo,
                                                                  String custName, String accCode, Integer start, Integer limit) {
        return this.dao.subfetchPagerByaccCode(acctNo, custName,accCode, start, limit);
    }

    public List<Map<String, Object>> gettopOrg(String id) {
        return this.dao.gettopOrg(id);
    }

    public List<Map<String, Object>> check(String id) {
        return dao.check(id);
    }

    public List<Map<String, Object>> getMainAcct(String type) {
        return dao.getMainAcct(type);
    }

    /**
     * TODO: 根据账户体系，去查主账号
     *
     * @param code
     * @param type
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author Mr.lx
     * @Date 2019/3/27 0027
     **/
    public List<Map<String, Object>> getMainAccoutByCode(int code, int type) {
        return dao.getMainAccoutByCode(code, type);
    }

    public List<Map<String, Object>> getOrgInfo(String type) {
        return dao.getOrgInfo(type);
    }

    public List<Map<String, Object>> getMainAcctNo() {
        return dao.getMainAcctNo();
    }

    public List<Map<String, Object>> getSubAcctNo(String acctNo) {
        return dao.getSubAcctNo(acctNo);
    }

    public List<Map<String, Object>> getAcctNoByOrg(String orgNo) {
        return dao.getAcctNoByOrg(orgNo);
    }

    public List<Map<String, Object>> getSubOrg() {
        return dao.getSubOrg();
    }

}
