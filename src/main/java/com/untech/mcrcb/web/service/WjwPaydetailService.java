package com.untech.mcrcb.web.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.untech.mcrcb.web.common.Constants;
import com.untech.mcrcb.web.vo.PayeeVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.WjwAccchangeDao;
import com.untech.mcrcb.web.dao.WjwAccountDao;
import com.untech.mcrcb.web.dao.WjwCertNumDao;
import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.untech.mcrcb.web.dao.WjwPaydetailDao;
import com.untech.mcrcb.web.dao.WjwPaymainDao;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.model.WjwCertNum;
import com.untech.mcrcb.web.model.WjwPaydetail;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.util.Utils;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;

/**
 * WJW_PAYDETAIL Service
 *
 * @author chenyong
 * @since 2015-11-04
 */

@Service
public class WjwPaydetailService  extends BaseService<WjwPaydetail, Long> {
    @Autowired
    private WjwPaydetailDao dao;
    @Autowired
    private WjwCertNumDao certNumDao;
    @Autowired
    private WjwAccountDao accountDao;
    @Autowired
    private WjwPaymainDao paymainDao;
    @Autowired
    private WjwAccchangeDao accChangeDao;
    @Autowired
    private WjwIncomedetailDao incomedetailDao;
    @Autowired
    private PublicService publicService;

    @Override
    public BaseDao<WjwPaydetail, Long> getHibernateBaseDao() {
        return this.dao;
    }

    /***
     * 获取付款人信息（账户属性是真实账户：1，账户收入支出标志为支出：2）
     * @author fanhua
     * @since 2015年11月6日 下午4:35:32
     * @param orgId
     * @return
     */
    public Map<String, Object> getOutAccList(Long orgId) {
        List<Map<String, Object>> list = dao.getOutAccList(orgId, 2, 1, false);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("custName", "");
            map.put("accNo", "");
            map.put("unitName", "");
            return map;
        }
    }
    public Map<String, Object> getOutAccList(Long orgId,int accCode) {
        List<Map<String, Object>> list = dao.getOutAccList(orgId, 2, 1, false,accCode);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("custName", "");
            map.put("accNo", "");
            map.put("unitName", "");
            return map;
        }
    }

    /***
     * 获取虚拟账户信息（账户属性是虚拟账户：2，账户收入支出标志为支出：2）
     * @author fanhua
     * @since 2015年11月6日 下午4:38:08
     * @param orgId
     * @return
     */
    public Map<String, Object> getVirtual(Long orgId) {
        List<Map<String, Object>> list = dao.getOutAccList(orgId, 2, 2, false);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("custName", "");
            map.put("accNo", "");
            return map;
        }
    }
    public Map<String, Object> getVirtual(Long orgId,int accCode) {
        List<Map<String, Object>> list = dao.getOutAccList(orgId, 2, 2, false,accCode);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("custName", "");
            map.put("accNo", "");
            return map;
        }
    }

    /****
     * 获取虚拟账户信息（账户属性是虚拟账户：2，账户收入支出标志为支出：2）,ture:表示查出主账户
     * @author fanhua
     * @since 2015年11月6日 下午8:35:47
     * @param orgId
     * @return
     */
    public Map<String, Object> getZcAccount(Long orgId) {
        List<Map<String, Object>> list = dao.getOutAccList(orgId, 2, 2, true);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("accNo", "");
            map.put("custName", "");
            return map;
        }
    }

    public Map<String, Object> getZcAccount(Long orgId,int accCode) {
        List<Map<String, Object>> list = dao.getOutAccList(orgId, 2, 2, true,accCode);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("accNo", "");
            map.put("custName", "");
            return map;
        }
    }


    /***
     * 根据关联号获取支付明细
     * @author fanhua
     * @since 2015年11月7日 上午10:43:01
     * @param connNo
     * @return
     */
    public List<Map<String, Object>> getPayDetailAll(String connNo) {
        return dao.getPayDetailAll(connNo);
    }

    /****
     * 获取审批人信息
     * @author fanhua
     * @since 2015年11月6日 下午5:37:18
     * @param orgId
     * @return
     */
    public Map<String, Object> getdspUser(Long orgId) {
        List<Map<String, Object>> list = dao.getdspUser(orgId);
        if (list.size() > 0 && !list.isEmpty()) {
            return list.get(0);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", "");
            map.put("userName", "");
            return map;
        }
    }
    /** TODO: --- 支出申请单
     * @Author Mr.lx
     * @Date 2019/4/3 0003
     **/
    @Transactional
    public void saveSQ(HttpServletRequest request) {
        logger.info("saveSQ");
        User user = SecurityContextUtil.getCurrentUser();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
        String connNo = user.getUserCode() + sim.format(new Date());
        int accCode=publicService.judgeAccountType(user);
        //数据转换，避免代码过于雍杂
        PayeeVo payeeVo=this.dataPayeeVoChange(request);
        for (int i = 0; i < payeeVo.getInAccno().length; i++) {
            //支出详情实体
            WjwPaydetail wjwPaydetail =dataPaydetailChange(payeeVo,i,connNo,user,accCode);
            this.insertAll(wjwPaydetail);
            //中心记账
            certNumDao.insert(dataCentNumChange(wjwPaydetail.getOperNo(),user));
        }
        //主账户
        paymainDao.insertPayMain(this.dataPaymainChange(user,payeeVo.getPayType(),connNo,payeeVo.getRemark()));
    }

    /** TODO: ---支付时，进行数据转换 ，实在看不下去这么雍杂的代码了。。
     * @Author Mr.lx
     * @Date 2019/4/3 0003
    * @param request
     * @return com.untech.mcrcb.web.vo.PayeeVo
     **/
    private PayeeVo dataPayeeVoChange(HttpServletRequest request){
        PayeeVo payeeVo= new PayeeVo();
        payeeVo.setInAccno(request.getParameterValues("inAccno"));
        payeeVo.setInName(request.getParameterValues("inName"));
        payeeVo.setAmount(request.getParameterValues("amount"));
        payeeVo.setInBank(request.getParameterValues("inBank"));
        payeeVo.setZjFld(request.getParameterValues("zjFld"));
        payeeVo.setPayWay(request.getParameterValues("payWay"));
        payeeVo.setTopYsdw(request.getParameterValues("topYsdw"));
        payeeVo.setFootYsdw(request.getParameterValues("footYsdw"));
        payeeVo.setItmeYs(request.getParameterValues("itmeYs"));
        payeeVo.setFuncFl(request.getParameterValues("funcFl"));
        payeeVo.setEcnoFl(request.getParameterValues("ecnoFl"));
        payeeVo.setYt(request.getParameterValues("yt"));
        payeeVo.setZbDetail(request.getParameterValues("zbDetail"));
        payeeVo.setItem(request.getParameterValues("item"));
        payeeVo.setPayType(request.getParameter("payType"));
        payeeVo.setRemark(request.getParameter("remark"));
        return payeeVo;
    }
    /** TODO: ---支付详情的数据转换
     * @Author Mr.lx
     * @Date 2019/4/3 0003
     * @return com.untech.mcrcb.web.model.WjwPaydetail
     **/
    private WjwPaydetail dataPaydetailChange(PayeeVo payeeVo,int i,String connNo,User user,int accCode){
        WjwPaydetail wjwPaydetail=new WjwPaydetail();
        wjwPaydetail.setAmount(new BigDecimal(payeeVo.getAmount()[i]));
        wjwPaydetail.setConnNo(connNo);
        wjwPaydetail.setCurrency("人民币");
        wjwPaydetail.setEcnoFl(payeeVo.getEcnoFl()[i]);
        wjwPaydetail.setFootYsdw(payeeVo.getFootYsdw()[i]);
        wjwPaydetail.setFuncFl(payeeVo.getFuncFl()[i]);
        wjwPaydetail.setInAccno(payeeVo.getInAccno()[i]);
        wjwPaydetail.setInBank(payeeVo.getInBank()[i]);
        wjwPaydetail.setInName(payeeVo.getInName()[i]);
        wjwPaydetail.setItmeYs(payeeVo.getItmeYs()[i]);
        //医疗使用
        if (StringUtils.isNotBlank(payeeVo.getItem()[i])
                && Constants.OUT_USE.MEDICAL.equals(payeeVo.getItem()[i].trim())) {
            wjwPaydetail.setItem(1);
            //药品使用
        } else if (StringUtils.isNotBlank(payeeVo.getItem()[i])
                    && Constants.OUT_USE.DRUG.equals(payeeVo.getItem()[i].trim())) {
            wjwPaydetail.setItem(2);
        }
        wjwPaydetail.setOperNo(createCode());
        wjwPaydetail.setPayWay(payeeVo.getPayWay()[i] == "现金" ? 1 : 2);
        wjwPaydetail.setPayTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        wjwPaydetail.setStatus(1);
        wjwPaydetail.setTopYsdw(payeeVo.getTopYsdw()[i]);
        wjwPaydetail.setUnitName(user.getOrgName());
        wjwPaydetail.setUnitNo(user.getOrgId().toString());
        wjwPaydetail.setYt(payeeVo.getYt()[i]);
        wjwPaydetail.setZbDetail(payeeVo.getZbDetail()[i]);
        wjwPaydetail.setZjFld(payeeVo.getZjFld()[i]);
        //获取付款人信息（账户属性是真实账户：1，账户收入支出标志为支出：2）
        Map<String, Object> outAccMap = this.getOutAccList(user.getOrgId(),accCode);
        wjwPaydetail.setOutAccname((String) outAccMap.get("custName"));
        wjwPaydetail.setOutAccno((String) outAccMap.get("accNo"));
        wjwPaydetail.setOutBank((String) outAccMap.get("unitName"));
        //获取虚拟账户信息（账户属性是虚拟账户：2，账户收入支出标志为支出：2）
        Map<String, Object> virMap = this.getVirtual(user.getOrgId(),accCode);
        wjwPaydetail.setXnAcctName((String) virMap.get("custName"));
        wjwPaydetail.setXnAcctno((String) virMap.get("accNo"));
        //获取虚拟账户信息（账户属性是虚拟账户：2，账户收入支出标志为支出：2）,ture:表示查出主账户
        Map<String, Object> accMap = this.getZcAccount(user.getOrgId(),accCode);
        wjwPaydetail.setZcAcctno((String) accMap.get("accNo"));
        wjwPaydetail.setZcAcctname((String) accMap.get("custName"));
        return wjwPaydetail;
    }
    /** TODO: ---应该是中心记账
     * @Author Mr.lx
     * @Date 2019/4/3 0003
    * @param certNo 银行凭证
    * @param user 用户信息
     * @return com.untech.mcrcb.web.model.WjwCertNum
     **/
    private WjwCertNum dataCentNumChange(String certNo,User user){
        WjwCertNum certNum=new WjwCertNum();
        certNum.setCertNo(certNo);
        certNum.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        certNum.setUserNo(user.getUserCode());
        certNum.setUserName(user.getUserName());
        certNum.setUnitNo(user.getOrgId().toString());
        certNum.setUnitName(user.getOrgName());
        Integer no = certNumDao.getMaxNo() + 1;
        certNum.setConnNo(no);
        certNum.setNote("申请产生的凭证号");
        return certNum;
    }
    /** TODO:
     * @Author Mr.lx
     * @Date 2019/4/3 0003
    * @param user
    * @param payType
    * @param connNo
    * @param remark
     * @return com.untech.mcrcb.web.model.WjwPaymain
     **/
    private WjwPaymain dataPaymainChange(User user,String payType,String connNo,String remark){
        WjwPaymain paymain = new WjwPaymain();
        paymain.setUnitName(user.getOrgName());
        paymain.setUnitNo(user.getOrgId().toString());
        paymain.setPayType(payType);
        paymain.setSqTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        paymain.setStatus(1);
        Map<String, Object> dspUserMap = this.getdspUser(user.getOrgId());
        paymain.setDspUserno((String) dspUserMap.get("userId"));
        paymain.setDspUsername((String) dspUserMap.get("userName"));
        paymain.setConnNo(connNo);
        paymain.setSqUserno(user.getUserCode());
        paymain.setSqUsername(user.getUserName());
        paymain.setRemark(remark);
        return paymain;
    }

    @Transactional
    public void insertAll(WjwPaydetail wjwPaydetail) {
                wjwPaydetail.getOperNo();
        dao.insert(wjwPaydetail);
    }

    @Transactional
    public void updatePayDetail(WjwPaydetail paydetail) {
        dao.updatePayDetail(paydetail);
    }

    /***
     * 获取凭证号 （卫生院简拼+年度号+缴款类型（01 缴款 02 支出 03 拨付）+5位序列号）
     * @author fanhua
     * @since 2015年11月6日 下午3:40:42
     * @param id
     * @return
     */
    public String getOperNo(Long id, Integer seq) {

        return Utils.createCode(incomedetailDao.getWSYJianpin(id), "02", seq);
    }


    /**
     * 生成支付凭证
     *
     * @return
     */
    public String createCode() {
        int num = dao.getMaxCode();
        String jianPin = incomedetailDao.getWSYJianpin(SecurityContextUtil.getCurrentUser().getOrgId());
        return Utils.createCode(jianPin, "02", num);
    }

    /**
     * 指标摘要
     *
     * @return
     */
    public List<Map<String, Object>> findZbDetails() {

        return dao.findZbDetails();
    }

    public Map<String, Object> getPayAccInfo(Long orgId) {

        return dao.getPayAccInfoByAcc(orgId, 2, 2).get(0);
//		return dao.getPayAccInfo(orgId, 2, 2).get(0);

    }

    public String ampulate(Long[] ids) {
        String flag = "";
        for (Long id : ids) {
            //根据id获取支付主表关联号
            String connNo = dao.getConno(id);
            //根据关联号删除支付明细表相关记录
            dao.ampulate(connNo);
            //根据id删除支付主表相关记录
            dao.ampulateById(id);
            flag = "1";
        }
        return flag;
    }

    /**
     * 经济分类
     *
     * @return
     */
    public List<Map<String, Object>> findEconomic() {

        return dao.findEconomic();
    }

    public Pagination<Map<String, Object>> modifyPaydetail(Integer start, Integer limit, String connNo) {

        return dao.modifyPaydetail(start, limit, connNo);
    }

    public Map<String, Object> findPaydetailById(long id) {

        return dao.findPaydetailById(id);
    }

    public Pagination<Map<String, Object>> getBankPayBack(Integer start,
                                                          Integer limit, String startTime, String endTime) {
        return dao.getBankPayBack(start, limit, startTime, endTime);
    }

    public Pagination<Map<String, Object>> getBackPaydetail(Integer start,
                                                            Integer limit, String startTime, String endTime, String unitNo, String status) {
        return dao.getBackPaydetail(start, limit, startTime, endTime, unitNo, status);
    }

    /**
     * 退汇
     *
     * @param voucher
     * @param backTime
     * @return
     */
    @Transactional
    public int backPay(String voucher, String backTime) {
        int flag = 0;
        try {
            Map<String, Object> pay = dao.findPaydetailByVoucher(voucher);
            if (null == pay) {
                return flag;
            }
            Integer status = (Integer) pay.get("STATUS");
            Integer backFlg = (Integer) pay.get("BACK_FLG");
            if (status != 6) {
                logger.info("该笔记录是非记账状态");
                return 2;//非记账状态不允许退汇
            }
            if (backFlg != null && backFlg == 3) {
                logger.info("该笔记录已经退汇");
                return 3;
            }
            WjwPaydetail entity = this.MaptoEntity(pay);
            String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String operNo = createCode();
            entity.setPayTime(time);
            entity.setBackFlg(1);//退汇标志：0---正常 1--退汇未处理  2--退汇已处理   3--退汇
            entity.setBackVoucher(voucher);
            entity.setOperNo(operNo);//生成新的凭证号
            entity.setStatus(5);//新生成的记录处于未记账状态
            dao.addPayDetail(entity);

            //凭证号改变，凭证号表增加一条记录
            Integer connNo = certNumDao.getNo(voucher);
            WjwCertNum entity1 = new WjwCertNum();
            entity1.setCertNo(operNo);
            entity1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            entity1.setUserNo(SecurityContextUtil.getCurrentUser().getUserCode());
            entity1.setUserName(SecurityContextUtil.getCurrentUser().getUsername());
            entity1.setUnitNo(SecurityContextUtil.getCurrentUser().getOrgId().toString());
            entity1.setUnitName(SecurityContextUtil.getCurrentUser().getOrgName());
            entity1.setConnNo(connNo);
            entity1.setNote("退汇时，产生新的凭证号");
            certNumDao.insert(entity1);

            logger.info("退汇---新增支付明细表记录成功");
            //将原始记录退汇标志变更为3--退汇状态
            dao.updatePayBackFlag(3, voucher);
            logger.info("退汇---变更原始记录，更改退汇标志成功");
            //3、将退汇的支付款入待清分账户，同时更新待清分账户各项余额、主账户各项余额
            pay = dao.findPayByVoucher(voucher);
            BigDecimal payAmount = (BigDecimal) pay.get("AMOUNT");
            BigDecimal payMedcAmt = (BigDecimal) pay.get("MEDICAL_AMT");
            BigDecimal payDrugAmt = (BigDecimal) pay.get("DRUG_AMT");
            Map<String, Object> dqf = accountDao.findDqfAccount("2");
            String dqfAccNo = dqf.get("ACC_NO").toString();
            String dqfAccName = dqf.get("CUST_NAME").toString();
            String dqfUnitNo = dqf.get("UNIT_NO").toString();
            String dqfUnitName = dqf.get("UNIT_NAME").toString();
            BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");
            BigDecimal dqfMedcAmt = (BigDecimal) dqf.get("MEDICAL_AMT");
            BigDecimal dqfDrugAmt = (BigDecimal) dqf.get("DRUG_AMT");
            accountDao.updateAccount(dqfAmount.add(payAmount), dqfDrugAmt.add(payDrugAmt),
                    dqfMedcAmt.add(payMedcAmt), dqfAccNo);
            logger.info("退汇---更新待清分账户余额，各项余额增加");
            Map<String, Object> main = accountDao.findMainAccount("2");
            String mainAccNo = main.get("ACC_NO").toString();
            String mainAccName = main.get("CUST_NAME").toString();
            String mainUnitNo = main.get("UNIT_NO").toString();
            String mainUnitName = main.get("UNIT_NAME").toString();
            BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
            BigDecimal mainMedcAmt = (BigDecimal) main.get("MEDICAL_AMT");
            BigDecimal mainDrugAmt = (BigDecimal) main.get("DRUG_AMT");
            accountDao.updateAccount(mainAmount.add(payAmount), mainDrugAmt.add(payDrugAmt),
                    mainMedcAmt.add(payMedcAmt), mainAccNo);
            logger.info("退汇---更新主账户余额，各项余额增加");
            //4、新增账户余额变更表2条记录

            WjwAccchange parent = new WjwAccchange();
            parent.setUnitNo(mainUnitNo);
            parent.setUnitName(mainUnitName);
            parent.setAccNo(mainAccNo);
            parent.setAccName(mainAccName);
            parent.setDfAccno(dqfAccNo);
            parent.setDfAccname(dqfAccName);
            parent.setAmount(mainAmount.add(payAmount));
            parent.setTranAmt(payAmount);
            parent.setTranTime(time);
            parent.setInOrOut(1);
            parent.setMedcAmt(payMedcAmt);
            parent.setDrugAmt(payDrugAmt);
            parent.setOtherAmt(new BigDecimal(0.00));
            parent.setFlag(5);//**1 入账 2 清分 3 利息*   5--退汇 6--退汇记账完成/
            parent.setNote2(voucher);//退汇的凭证号记入note2而不失note1
            accChangeDao.insertEntity(parent);
            logger.info("退汇---新增账户余额变更表记录1次");

            WjwAccchange child = new WjwAccchange();
            child.setUnitNo(dqfUnitNo);
            child.setUnitName(dqfUnitName);
            child.setAccNo(dqfAccNo);
            child.setAccName(dqfAccName);
            child.setDfAccno(mainAccNo);
            child.setDfAccname(mainAccName);
            child.setAmount(dqfAmount.add(payAmount));
            child.setTranAmt(payAmount);
            child.setTranTime(time);
            child.setInOrOut(1);
            child.setMedcAmt(payMedcAmt);
            child.setDrugAmt(payDrugAmt);
            child.setOtherAmt(new BigDecimal(0.00));
            child.setFlag(5);//**1 入账 2 清分 3 利息*  5--退汇 6--退汇记账完成/
            child.setNote2(voucher);
            accChangeDao.insertEntity(child);
            logger.info("退汇---新增账户余额变更表记录2次");
            flag = 1;
        } catch (Exception e) {
            e.printStackTrace();
            flag = 5;
        }
        return flag;
    }

    private WjwPaydetail MaptoEntity(Map<String, Object> pay) {
        WjwPaydetail entity = new WjwPaydetail();
        entity.setAmount((BigDecimal) pay.get("AMOUNT"));
        entity.setZjFld(dataConvert(pay.get("ZJ_FLD")));
        entity.setZcAcctno(dataConvert(pay.get("ZC_ACCTNO")));
        entity.setZcAcctname(dataConvert(pay.get("ZC_ACCTNAME")));
        entity.setZbDetail(dataConvert(pay.get("ZB_DETAIL")));
        entity.setYt(dataConvert(pay.get("YT")));
        entity.setXnAcctno(dataConvert(pay.get("XN_ACCTNO")));
        entity.setXnAcctName(dataConvert(pay.get("XN_ACCTNAME")));
        entity.setUnitNo(dataConvert(pay.get("UNIT_NO")));
        entity.setUnitName(dataConvert(pay.get("UNIT_NAME")));
        entity.setTopYsdw(dataConvert(pay.get("TOP_YSDW")));
        entity.setStatus((Integer) pay.get("STATUS"));
        entity.setPayWay((Integer) pay.get("PAY_WAY"));
        entity.setPayTime(dataConvert(pay.get("PAY_TIME")));
        entity.setOutBank(dataConvert(pay.get("OUT_BANK")));
        entity.setOutAccno(dataConvert(pay.get("OUT_ACCNO")));
        entity.setOutAccname(dataConvert(pay.get("OUT_ACCNAME")));
        entity.setOperNo(dataConvert(pay.get("OPER_NO")));
        entity.setNote2(dataConvert(pay.get("NOTE2")));
        entity.setNote1(dataConvert(pay.get("NOTE1")));
        entity.setItmeYs(dataConvert(pay.get("ITME_YS")));
        entity.setItem((Integer) pay.get("ITEM"));
        entity.setInName(dataConvert(pay.get("IN_NAME")));
        entity.setInBank(dataConvert(pay.get("IN_BANK")));
        entity.setInAccno(dataConvert(pay.get("IN_ACCNO")));
        entity.setFuncFl(dataConvert(pay.get("FUNC_FL")));
        entity.setFootYsdw(dataConvert(pay.get("FOOT_YSDW")));
        entity.setFhUser(dataConvert(pay.get("FH_USER")));
        entity.setFhTime(dataConvert(pay.get("FH_TIME")));
        entity.setEcnoFl(dataConvert(pay.get("ECNO_FL")));
        entity.setCurrency(dataConvert(pay.get("CURRENCY")));
        entity.setConnNo(dataConvert(pay.get("CONN_NO")));
        entity.setBackFlg((Integer) pay.get("BACK_FLG"));
        entity.setBackVoucher(dataConvert(pay.get("BACK_VOUCHER")));
        return entity;
    }

    private String dataConvert(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return (String) obj;
        }
    }

    /**
     * 撤销退汇
     * @return
     */
    @Transactional
    public int rollback(String voucher) {
        int flag = 0;
        try {

            //1、通过原始凭证号查找退汇生成的新记录
            Map<String, Object> newPay = dao.findNewPayByBackVoucher(voucher);
            //2、判断新记录是否已经记账，如果记账，该笔退汇不能撤销
            Integer status = (Integer) newPay.get("STATUS");
            logger.info("已退汇生产的新纪录的记账状态：" + status);
            if (status == 6) {
                return 2;
            }
            //3、更新原始退汇记录的退汇标志--更新back_flg 为0
            dao.updatePayBackFlag(0, voucher);
            //4、通过原始凭证号，删除退汇生成的新记录
            dao.deleteBankPayByBackVoucher(voucher);
            //在凭证号记录表中通过退汇生成的新凭证号删除记录
            certNumDao.deleteVoucher(newPay.get("OPER_NO").toString());
            Map<String, Object> map = dao.findPayByVoucher(voucher);
            //5、待清分账户、主账户各项余额减少
            BigDecimal payAmount = (BigDecimal) map.get("AMOUNT");
            BigDecimal payMedcAmt = (BigDecimal) map.get("MEDICAL_AMT");
            BigDecimal payDrugAmt = (BigDecimal) map.get("DRUG_AMT");
            Map<String, Object> dqf = accountDao.findDqfAccount("2");
            String dqfAccNo = dqf.get("ACC_NO").toString();
            BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");
            BigDecimal dqfMedcAmt = (BigDecimal) dqf.get("MEDICAL_AMT");
            BigDecimal dqfDrugAmt = (BigDecimal) dqf.get("DRUG_AMT");
            accountDao.updateAccount(dqfAmount.subtract(payAmount), dqfDrugAmt.subtract(payDrugAmt),
                    dqfMedcAmt.subtract(payMedcAmt), dqfAccNo);
            logger.info("退汇撤销---更新待清分账户余额，各项余额减少");
            Map<String, Object> main = accountDao.findMainAccount("2");
            String mainAccNo = main.get("ACC_NO").toString();
            BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
            BigDecimal mainMedcAmt = (BigDecimal) main.get("MEDICAL_AMT");
            BigDecimal mainDrugAmt = (BigDecimal) main.get("DRUG_AMT");
            accountDao.updateAccount(mainAmount.subtract(payAmount), mainDrugAmt.subtract(payDrugAmt),
                    mainMedcAmt.subtract(payMedcAmt), mainAccNo);
            logger.info("退汇---更新主账户余额，各项余额减少");
            //6、删除账户余额变更表
            dao.deleteByVoucher(voucher, true);
            flag = 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
        return flag;
    }

    public InputStream downloadBackPay(String startTime, String endTime,
                                       String status, String unitNo) throws IOException {
        List<Map<String, Object>> list = dao.downloadBackPay(startTime, endTime, status, unitNo);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("机构名称");

        cell = row.createCell(1);
        cell.setCellValue("收款人账号");

        cell = row.createCell(2);
        cell.setCellValue("收款人姓名");

        cell = row.createCell(3);
        cell.setCellValue("收款人开户行");

        cell = row.createCell(4);
        cell.setCellValue("申请时间");

        cell = row.createCell(5);
        cell.setCellValue("凭证编号");

        cell = row.createCell(6);
        cell.setCellValue("退汇标志");

        cell = row.createCell(7);
        cell.setCellValue("退汇凭证号");

        cell = row.createCell(8);
        cell.setCellValue("支付金额");

        cell = row.createCell(9);
        cell.setCellValue("状态");

        cell = row.createCell(10);
        cell.setCellValue("科目");

        cell = row.createCell(11);
        cell.setCellValue("用途");

        cell = row.createCell(12);
        cell.setCellValue("指标摘要");

        cell = row.createCell(13);
        cell.setCellValue("经济分类");

        cell = row.createCell(14);
        cell.setCellValue("资金性质");

        cell = row.createCell(15);
        cell.setCellValue("一级预算单位");

        cell = row.createCell(16);
        cell.setCellValue("基层预算单位");

        cell = row.createCell(17);
        cell.setCellValue("预算科目");

        cell = row.createCell(18);
        cell.setCellValue("功能分类");

        cell = row.createCell(19);
        cell.setCellValue("退汇原因");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            row = sheet.createRow(i + 1);

            cell = row.createCell(0);
            cell.setCellValue(map.get("unitName").toString());

            cell = row.createCell(1);
            cell.setCellValue(map.get("inAccno").toString());

            cell = row.createCell(2);
            cell.setCellValue(map.get("inName").toString());

            cell = row.createCell(3);
            cell.setCellValue(map.get("inBank").toString());

            cell = row.createCell(4);
            cell.setCellValue(map.get("payTime").toString());

            cell = row.createCell(5);
            cell.setCellValue(map.get("operNo").toString());

            cell = row.createCell(6);
            cell.setCellValue((Integer) map.get("backFlg") == 1 ? "退汇未处理" : "退汇已记账");

            cell = row.createCell(7);
            cell.setCellValue(map.get("backVoucher").toString());

            cell = row.createCell(8);
            cell.setCellValue(map.get("amount").toString());

            cell = row.createCell(9);
            cell.setCellValue((Integer) map.get("status") == 5 ? "未记账" : "记账完成");

            cell = row.createCell(10);
            cell.setCellValue((Integer) map.get("item") == 1 ? "医疗" : "药品");

            cell = row.createCell(11);
            cell.setCellValue(map.get("yt") == null ? "" : map.get("yt").toString());

            cell = row.createCell(12);
            cell.setCellValue(map.get("zbDetail").toString());

            cell = row.createCell(13);
            cell.setCellValue(map.get("ecnoFl") == null ? "" : map.get("ecnoFl").toString());

            cell = row.createCell(14);
            cell.setCellValue(map.get("zjFld") == null ? "" : map.get("zjFld").toString());

            cell = row.createCell(15);
            cell.setCellValue(map.get("topYsdw") == null ? "" : map.get("topYsdw").toString());

            cell = row.createCell(16);
            cell.setCellValue(map.get("footYsdw") == null ? "" : map.get("footYsdw").toString());

            cell = row.createCell(17);
            cell.setCellValue(map.get("itmeYs") == null ? "" : map.get("itmeYs").toString());

            cell = row.createCell(18);
            cell.setCellValue(map.get("funcFl") == null ? "" : map.get("funcFl").toString());

            cell = row.createCell(19);
            cell.setCellValue(map.get("note1") == null ? "" : map.get("note1").toString());
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            wb.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = bos.toByteArray();
        bos.close();
        ByteArrayInputStream bis = new ByteArrayInputStream(content);
        return bis;
    }

    @Transactional
    public void updateOperNo(Long[] ids) {
        for (Long id : ids) {
            Map<String, Object> map = dao.findPaydetailById(id);
//			  String certNo = map.get("operNo").toString();
//			  Integer connNo = certNumDao.getNo(certNo);

            String operNo = createCode();
            WjwPaydetail paydetail = new WjwPaydetail();
            paydetail.setId(id);
            paydetail.setOperNo(operNo);
            dao.updateOperNo(paydetail);

//	    	  WjwCertNum entity1 = new WjwCertNum();
//	    	  entity1.setCertNo(operNo);
//	    	  entity1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//	    	  entity1.setUserNo(SecurityContextUtil.getCurrentUser().getUserCode());
//	    	  entity1.setUserName(SecurityContextUtil.getCurrentUser().getUsername());
//	    	  entity1.setUnitNo(SecurityContextUtil.getCurrentUser().getOrgId().toString());
//	    	  entity1.setUnitName(SecurityContextUtil.getCurrentUser().getOrgName());
//	    	  entity1.setConnNo(connNo);
//	    	  entity1.setNote("支付凭证时，修改凭证号");
//	    	  certNumDao.insert(entity1);
        }
    }

    public void selectById(Long id) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        WjwPaydetail paydetail = new WjwPaydetail();
        paydetail.setId(id);
        paydetail.setPrintTime(sim.format(new Date()));
        dao.updateByid(paydetail);
    }
}

