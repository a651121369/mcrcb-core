package com.untech.mcrcb.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.service.PublicService;
import com.untech.mcrcb.web.service.WjwPaydetailService;
import com.untech.mcrcb.web.service.WjwPaymainService;
import com.untech.mcrcb.web.model.WjwPaydetail;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;
import com.unteck.tpc.framework.web.model.admin.User;

import freemarker.template.utility.SecurityUtilities;

/**
 * WJW_PAYDETAIL Controller
 *
 * @author chenyong
 * @since 2015-11-04
 */
@Controller
@RequestMapping(value = "/WjwPaydetail")
public class WjwPaydetailController extends BaseController {
    @Autowired
    private WjwPaydetailService service;
    @Autowired
    private WjwPaymainService paymainService;
    @Autowired
    private PublicService publicService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        User user = SecurityContextUtil.getCurrentUser();
        request.setAttribute("orgName", user.getOrgName());
        request.setAttribute("orgId", user.getOrgId());
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        String parentId = ((Integer) map.get("PARENTID")).toString();
        request.setAttribute("parentId", parentId);
        Map<String, Object> accInfo = service.getPayAccInfo(user.getOrgId());
        String accAmount = accInfo.get("accAmount") + "";
        request.setAttribute("accAmount", accAmount);
        String drugAmt = accInfo.get("drugAmt") + "";
        String medcAmt = accInfo.get("medcAmt") + "";
        request.setAttribute("drugAmt", drugAmt);
        request.setAttribute("medcAmt", medcAmt);
        return "/mcrcb-core/WjwPaydetail";
    }

    @RequestMapping(value = "/getPayAccInfo")
    @ResponseBody
    public Map<String, Object> getPayAccInfo() {
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
//    	String parentId = ((Integer)map.get("PARENTID")).toString();
        return service.getPayAccInfo(user.getOrgId());

    }

    /***
     * 打开申请书打印页面
     * @author fanhua
     * @since 2015年11月7日 下午7:29:45
     * @param request
     * @return
     */
    @RequestMapping(value = "/PayDetailMx")
    public ModelAndView openPayDetailMx(HttpServletRequest request) {
        request.setAttribute("cnnNo", request.getParameter("cnnNo"));
        request.setAttribute("sqTime", request.getParameter("sqTime"));
        return new ModelAndView("/mcrcb-core/WjwPaydetailMx");
    }

    /***
     * 代开支付明细页面，进行套打
     * @author fanhua
     * @since 2015年11月7日 下午7:28:52
     * @return
     */
    @RequestMapping(value = "/PayDetailLodop")
    public ModelAndView openPayDetailLodop(HttpServletRequest request) {
        User user = SecurityContextUtil.getCurrentUser();
        request.setAttribute("userCode", user.getUserCode());
        return new ModelAndView("/mcrcb-core/WjwPaydetailLodop");
    }

    /**
     * 打开支付凭证页面
     *
     * @param request
     * @return
     * @author fanhua
     * @since 2015年11月7日 下午7:28:38
     */
    @RequestMapping(value = "/LodopPage")
    public ModelAndView openLodopPage(HttpServletRequest request) {
        request.setAttribute("id", request.getParameter("id"));
        return new ModelAndView("/mcrcb-core/LodopPage");
    }

    @RequestMapping(value = "/listLodopPage")
    public ModelAndView listLodopPage(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        request.setAttribute("ids", ids);
        return new ModelAndView("/mcrcb-core/listLodopPage");
    }

    @RequestMapping(value = "/getLodopPageList")
    @ResponseBody
    public List<Map<String, Object>> getLodopPageList(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        ids = ids.substring(0, ids.length() - 1);
        String[] data = ids.split(",");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < data.length; i++) {
            list.add(service.findPaydetailById(Long.parseLong(data[i])));
        }
        return list;
    }

    @RequestMapping(value = "/toModifyPaydetail")
    public ModelAndView toModifyPaydetail(HttpServletRequest request) {
        request.setAttribute("connNo", request.getParameter("connNo"));
        return new ModelAndView("/mcrcb-core/modifyPaydetail");
    }

    @RequestMapping(value = "/modifyPaydetail")
    @ResponseBody
    public Pagination<Map<String, Object>> modifyPaydetail(Integer start, Integer limit, String connNo) {
        return service.modifyPaydetail(start, limit, connNo);
    }

    @RequestMapping(value = "/findZbDetails")
    @ResponseBody
    public List<Map<String, Object>> findZbDetails() {
        return service.findZbDetails();
    }

    @RequestMapping(value = "/findEconomic")
    @ResponseBody
    public List<Map<String, Object>> findEconomic() {
        return service.findEconomic();
    }

    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwPaydetail> pager(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData save(HttpServletRequest request) {
        service.saveSQ(request);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData updateEntity(WjwPaydetail entity) {
//      WjwPaydetail oldEntity = (WjwPaydetail)service.getEntity(Long.parseLong(request.getParameter("id")));
        WjwPaydetail oldEntity = service.getEntity(entity.getId());
        if (oldEntity == null) {
            return new ResponseData(false, "记录不存在");
        }
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        Integer parentId = (Integer) map.get("PARENTID");
        //卫生院角色只能修改申请状态的记录
        if (parentId != 0) {
            Integer status = oldEntity.getStatus();
            if (status != 1) {
                return new ResponseData(false, "该笔记录已经审核不能修改");
            }
        }
//      oldEntity.setInName(request.getParameter("inName"));
//      oldEntity.setInAccno(request.getParameter("inAccno"));
//      oldEntity.setInBank(request.getParameter("inBank"));
        oldEntity.setInName(entity.getInName());
        oldEntity.setInAccno(entity.getInAccno());
        oldEntity.setInBank(entity.getInBank());
        oldEntity.setYt(entity.getYt());
        service.updatePayDetail(oldEntity);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(Long[] ids) {
        for (Long id : ids) {
            WjwPaymain paymain = paymainService.getEntity(id);
            String connNo = paymain.getConnNo();
            List<Map<String, Object>> list = service.getPayDetailAll(connNo);
            if (list.size() > 0 && !list.isEmpty()) {
                for (Map<String, Object> map : list) {
                    String deId = String.valueOf(map.get("ID"));
                    Long payDetailId = Long.valueOf(deId);
                    WjwPaydetail Paydetail = service.getEntity(payDetailId.longValue());
                    Paydetail.setStatus(4);
                    service.updateEntity(Paydetail);
                }
                paymain.setStatus(4);

                paymainService.updateEntity(paymain);
                return new ResponseData(true, "操作成功！");
            } else {
                return new ResponseData(true, "操作失败！");
            }
        }
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/ampulate")
    @ResponseBody
    public ResponseData ampulate(Long[] ids) {

        System.out.println("作废或已驳回支付申请删除开始......");
        String flag = service.ampulate(ids);
        if ("1".equals(flag)) {
            return new ResponseData(true, "删除成功！");
        } else {
            return new ResponseData(false, "删除失败！");
        }
    }


    @RequestMapping(value = "/appUpdate")
    @ResponseBody
    public ResponseData update(WjwPaydetail entity) {

        WjwPaydetail oldEntity = (WjwPaydetail) service.getEntity(entity.getId());
        if (oldEntity == null) {
            return new ResponseData(false, "记录不存在");
        }
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        Integer parentId = (Integer) map.get("PARENTID");
        //卫生院角色只能修改申请状态的记录
        if (parentId != 0) {
            Integer status = oldEntity.getStatus();
            if (status != 1) {
                return new ResponseData(false, "该笔记录已经审核不能修改");
            }
        }
        oldEntity.setAmount(entity.getAmount());
        oldEntity.setInAccno(entity.getInAccno());
        oldEntity.setInName(entity.getInName());
        oldEntity.setInBank(entity.getInBank());
        oldEntity.setItem(entity.getItem());
        oldEntity.setPayWay(entity.getPayWay());
        oldEntity.setZbDetail(entity.getZbDetail());
        oldEntity.setYt(entity.getYt());
        oldEntity.setNote1(entity.getNote1());
        oldEntity.setEcnoFl(entity.getEcnoFl());
        oldEntity.setFuncFl(entity.getFuncFl());
        oldEntity.setZjFld(entity.getZjFld());
        oldEntity.setTopYsdw(entity.getTopYsdw());
        oldEntity.setFootYsdw(entity.getFootYsdw());
        oldEntity.setItmeYs(entity.getItmeYs());
        service.updateEntity(oldEntity);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/appDelete")
    @ResponseBody
    public ResponseData delete(String[] ids) {
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        Integer parentId = (Integer) map.get("PARENTID");
        if (parentId != 0) {
            WjwPaydetail oldEntity = (WjwPaydetail) service.getEntity(Long.parseLong(ids[0]));
            Integer status = oldEntity.getStatus();
            if (status != 1) {
                return new ResponseData(false, "该笔记录已经审核不能删除");
            }
        }
        for (String id : ids) {
            service.deleteEntity(Long.parseLong(id));
        }
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "bankPayBack")
    public String toBankPayBack() {
        return "/mcrcb-core/bankPayBack";
    }

    @RequestMapping(value = "backPaydetail")
    public String toBackPaydetail(HttpServletRequest request) {
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        Integer parentId = ((Integer) map.get("PARENTID"));
        request.setAttribute("parentId", parentId);
        return "/mcrcb-core/backPaydetail";
    }

    @RequestMapping(value = "/toBankPayBack")
    @ResponseBody
    public Pagination<Map<String, Object>> bankPayBack(Integer start, Integer limit, String startTime, String endTime) {
        return service.getBankPayBack(start, limit, startTime, endTime);
    }

    @RequestMapping(value = "/toBackPaydetail")
    @ResponseBody
    public Pagination<Map<String, Object>> backPaydetail(Integer start, Integer limit,
                                                         String startTime, String endTime, String unitNo, String status) {
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        Integer parentId = ((Integer) map.get("PARENTID"));

        if (StringUtils.isBlank(unitNo)) {
            if (parentId == 0) {
                unitNo = "";
            } else {
                unitNo = SecurityContextUtil.getCurrentUser().getOrgId() + "";
            }
        }
        return service.getBackPaydetail(start, limit, startTime, endTime, unitNo, status);
    }

    @RequestMapping(value = "/backPay")
    @ResponseBody
    public ResponseData backPay(String voucher, String backTime) {
        int flag = service.backPay(voucher, backTime);
        if (flag == 0) {
            return new ResponseData(false, "凭证号不存在");
        } else if (flag == 1) {
            return new ResponseData(true, "退汇成功");
        } else if (flag == 2) {
            return new ResponseData(false, "非记账状态不可退汇");
        } else if (flag == 3) {
            return new ResponseData(false, "该笔已经退汇");
        }
        return new ResponseData(false, "退汇失败");
    }

    @RequestMapping(value = "/rollback")
    @ResponseBody
    public ResponseData rollback(String voucher) {
        int flag = service.rollback(voucher);
        logger.info("flag:" + flag);
        if (flag == 1) {
            return new ResponseData(true, "退汇撤销成功");
        } else if (flag == 2) {
            return new ResponseData(false, "该笔退汇已经记账,撤销失败");
        }
        return new ResponseData(false, "退汇撤销失败");
    }

    @RequestMapping(value = "/downloadBackPay")
    public void downloadBackPay(HttpServletResponse res, String startTime, String endTime,
                                String unitNo, String status) throws IOException {
        User user = SecurityContextUtil.getCurrentUser();
        Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
        Integer parentId = ((Integer) map.get("PARENTID"));

        if (StringUtils.isBlank(unitNo)) {
            if (parentId == 0) {
                unitNo = "";
            } else {
                unitNo = SecurityContextUtil.getCurrentUser().getOrgId() + "";
            }
        }
        String fileName = "卫计委退汇明细_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        res.setHeader("Content-disposition", "attchment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + ".xls");
        res.setContentType("application/vnd.ms-excel");
        InputStream in = service.downloadBackPay(startTime, endTime, status, unitNo);
        OutputStream out = res.getOutputStream();
        byte[] buf = new byte[2048];
        try {
            while ((in.read(buf)) != -1) {
                out.write(buf);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
            if (null != in) {
                in.close();
            }
        }

    }

    @RequestMapping(value = "/updateOperNo")
    @ResponseBody
    public ResponseData updateOperNo(Long[] ids) {
        service.updateOperNo(ids);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/updateIsPrintPaypz.do")
    @ResponseBody
    public void updateIsPrintPaypz(String id) {
        service.selectById(Long.parseLong(id));
    }
}

