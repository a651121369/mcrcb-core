package com.untech.mcrcb.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.enhance.QueryFilter;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.service.TaskService;
import com.untech.mcrcb.web.service.WjwPaydetailService;
import com.untech.mcrcb.web.util.AmountTrans;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_PAYMAIN Controller
 *
 * @author chenyong
 * @since 2015-11-04
 */
@Controller
@RequestMapping(value = "/taskList")
public class TaskController extends BaseController {
    @Autowired
    private TaskService service;

    //代办任务列表
    @RequestMapping(value = "/taskwant_index")
    public String taskwant_index() {
        return "/mcrcb-core/taskwant";
    }

    //已办任务列表
    @RequestMapping(value = "/taskfinish_index")
    public String taskfinish_index() {
        return "/mcrcb-core/taskfinish";
    }

    //我的任务列表
    @RequestMapping(value = "/taskself_index")
    public String taskslef_index() {
        return "/mcrcb-core/taskself";
    }

    @RequestMapping(value = "/getTaskwant")
    @ResponseBody
    public Pagination<Map<String, Object>> getTaskwant(Integer start, Integer limit, String connNo, String unitNo, String startTime, String endTime) {
        String dspUserno = SecurityContextUtil.getCurrentUser().getUserCode();
        return service.fetchPager(connNo, unitNo, startTime, endTime, start, limit, dspUserno);
    }

    @RequestMapping(value = "/getTaskfinish")
    @ResponseBody
    public Pagination<Map<String, Object>> getTaskfinish(Integer start, Integer limit, String connNo, String unitNo, String startTime, String endTime) {
        String dspUserno = SecurityContextUtil.getCurrentUser().getUserCode();
        return service.fetchPagerfinish(connNo, unitNo, startTime, endTime, start, limit, dspUserno);
    }

    @RequestMapping(value = "/getTaskself")
    @ResponseBody
    public Pagination<Map<String, Object>> getTaskfinish(Integer start, Integer limit, String connNo, String startTime, String endTime) {
        String dspUserno = SecurityContextUtil.getCurrentUser().getUserCode();
        return service.fetchPagerself(connNo, startTime, endTime, start, limit, dspUserno);
    }

    @RequestMapping("/payMxDetail")
    public String payTypeDetailList(String connNo, ModelMap modelMap) throws Exception {
        //查询支付主表信息
        List<Map<String, Object>> mainList = this.service.getPayMainByConnno(connNo);
        WjwPaymain main = new WjwPaymain();
        if (mainList != null && mainList.size() > 0) {
            Map<String, Object> map = mainList.get(0);
            String connNos = map.get("connNo").toString();
            main.setConnNo(connNos);
            main.setUnitName(map.get("unitName").toString());
            String sqTime = map.get("sqTime").toString();
            main.setSqTime(sqTime.substring(0, 4) + "年" + sqTime.substring(4, 6) + "月" + sqTime.substring(6, 8) + "日");
            main.setUnitNo(map.get("unitNo").toString());
            main.setPayType(map.get("payType").toString());
        }
        //查询支付明细表信息
        List<Map<String, Object>> mainmxList = this.service.getPayMxByConnno(connNo);
        modelMap.put("main", main);
        modelMap.put("mainmxList", mainmxList);
        BigDecimal amount = new BigDecimal("0.00");
        if (mainmxList != null && mainmxList.size() > 0) {
            for (int i = 0; i < mainmxList.size(); i++) {
                amount = amount.add(new BigDecimal(mainmxList.get(i).get("amount").toString()));
            }
        }
        modelMap.put("amount", amount.setScale(2, BigDecimal.ROUND_HALF_UP));
        modelMap.put("amountUpper", AmountTrans.hangeToBig(amount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        return "mcrcb-core/payDetailList";
    }


    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwPaymain> pager(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData save(WjwPaymain entity) {
        if (service.getEntity(entity.getId()) != null) {
            return new ResponseData(false, "记录已存在");
        }
        service.insertEntity(entity);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/updatetest")
    @ResponseBody
    public ResponseData updatetest(WjwPaymain entity) {
        WjwPaymain oldEntity = (WjwPaymain) service.getEntity(entity.getId());
        if (oldEntity == null) {
            return new ResponseData(false, "记录不存在");
        }
        oldEntity.setUnitName(entity.getUnitName());
        oldEntity.setUnitNo(entity.getUnitNo());
        oldEntity.setPayType(entity.getPayType());
        oldEntity.setSqTime(entity.getSqTime());
        oldEntity.setStatus(entity.getStatus());
        oldEntity.setDspUserno(entity.getDspUserno());
        oldEntity.setCsUserno(entity.getCsUserno());
        oldEntity.setFsUserno(entity.getFsUserno());
        oldEntity.setCsTime(entity.getCsTime());
        oldEntity.setFsTime(entity.getFsTime());
        oldEntity.setConnNo(entity.getConnNo());
        oldEntity.setDspUsername(entity.getDspUsername());
        oldEntity.setCsUsername(entity.getCsUsername());
        oldEntity.setFsUsername(entity.getFsUsername());
        oldEntity.setSqUserno(entity.getSqUserno());
        oldEntity.setSqUsername(entity.getSqUsername());
        oldEntity.setRemark(entity.getRemark());
        oldEntity.setNote1(entity.getNote1());
        oldEntity.setNote2(entity.getNote2());
        service.updateEntity(oldEntity);
        return ResponseData.SUCCESS_NO_DATA;
    }
    //批量审批

    @RequestMapping(value = "/batchUpdate")
    @ResponseBody
    public ResponseData batchUpdate(Long[] ids) {
        System.out.println("ids size:" + ids.length);

        for (Long id : ids) {
            System.out.println("=========id :" + id);
            WjwPaymain oldEntity = (WjwPaymain) service.getEntity(id);
            if (null == oldEntity) {
                return new ResponseData(false, "批量审批失败！");
            }
            Map<String, Object> role = service.getRechecker("ROLE_RECHECK");
            String userCode = (String) role.get("userCode");
            String userName = (String) role.get("userName");
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            String time = format.format(new Date());
            if (1 == oldEntity.getStatus()) {
                oldEntity.setStatus(2);
                oldEntity.setCsUserno(SecurityContextUtil.getCurrentUser().getUserCode());
                oldEntity.setCsUsername(SecurityContextUtil.getCurrentUser().getUsername());
                oldEntity.setCsTime(time);
                oldEntity.setDspUserno(userCode);
                oldEntity.setDspUsername(userName);
            } else if (2 == oldEntity.getStatus()) {
                oldEntity.setStatus(5);
                oldEntity.setFsUserno(SecurityContextUtil.getCurrentUser().getUserCode());
                oldEntity.setFsUsername(SecurityContextUtil.getCurrentUser().getUsername());
                oldEntity.setFsTime(time);
                oldEntity.setDspUserno(oldEntity.getCsUserno());
                oldEntity.setDspUsername(oldEntity.getCsUsername());
            }
            service.updatePayAcctNo(oldEntity);
        }
        return ResponseData.SUCCESS_NO_DATA;
    }

    //审批通过
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(WjwPaymain entity) {
        System.out.println(entity.getId() + "---------------");
        System.out.println(entity.getUnitNo() + "---------------");
        WjwPaymain oldEntity = (WjwPaymain) service.getEntity(entity.getId());
        if (oldEntity == null) {
            return new ResponseData(false, "记录不存在");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String time = format.format(new Date());
        //待初审
        if (1 == oldEntity.getStatus()) {
            oldEntity.setStatus(2);
            oldEntity.setNote1(entity.getRemark());
            oldEntity.setCsUserno(SecurityContextUtil.getCurrentUser().getUserCode());
            oldEntity.setCsUsername(SecurityContextUtil.getCurrentUser().getUsername());
            oldEntity.setCsTime(time);
            System.out.println(entity.getDspUserno() + "----------------");
            if ("".equals(entity.getDspUserno()) || "null".equals(entity.getDspUserno()) || entity.getDspUserno() == null) {
                return new ResponseData(false, "未选择复审人");
            }
            //
            List<Map<String, Object>> list = service.getUserName(entity.getDspUserno());
            if (list != null && list.size() > 0) {
                String userName = list.get(0).get("userName").toString();
                oldEntity.setDspUserno(entity.getDspUserno());
                oldEntity.setDspUsername(userName);
            } else {
                oldEntity.setDspUserno("");
                oldEntity.setDspUsername("");
            }
        } else if (2 == oldEntity.getStatus()) {
            oldEntity.setStatus(5);
            oldEntity.setNote2(entity.getRemark());
            oldEntity.setFsUserno(SecurityContextUtil.getCurrentUser().getUserCode());
            oldEntity.setFsUsername(SecurityContextUtil.getCurrentUser().getUsername());
            oldEntity.setFsTime(time);
            oldEntity.setDspUserno(oldEntity.getCsUserno());
            oldEntity.setDspUsername(oldEntity.getCsUsername());

        }

        service.updatePayAcctNo(oldEntity);
        return ResponseData.SUCCESS_NO_DATA;
    }

    //审批驳回
    @RequestMapping(value = "/updateback")
    @ResponseBody
    public ResponseData updateback(WjwPaymain entity) {
        System.out.println(entity.getId() + "---------------");
        System.out.println(entity.getUnitNo() + "---------------");
        WjwPaymain oldEntity = (WjwPaymain) service.getEntity(entity.getId());
        if (oldEntity == null) {
            return new ResponseData(false, "记录不存在");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String time = format.format(new Date());
        //待初审
        if (1 == oldEntity.getStatus()) {
            oldEntity.setStatus(4);
            oldEntity.setNote1(entity.getRemark());
            oldEntity.setCsUserno(SecurityContextUtil.getCurrentUser().getUserCode());
            oldEntity.setCsUsername(SecurityContextUtil.getCurrentUser().getUserName());
            oldEntity.setCsTime(time);
            oldEntity.setDspUserno("");
            oldEntity.setDspUsername("");
        } else if (2 == oldEntity.getStatus()) {
            oldEntity.setStatus(4);
            oldEntity.setNote2(entity.getRemark());
            oldEntity.setFsUserno(SecurityContextUtil.getCurrentUser().getUserCode());
            oldEntity.setFsUsername(SecurityContextUtil.getCurrentUser().getUserName());
            oldEntity.setFsTime(time);
            oldEntity.setDspUserno("");
            oldEntity.setDspUsername("");
        }
      /*oldEntity.setUnitName(entity.getUnitName());
      oldEntity.setUnitNo(entity.getUnitNo());
      oldEntity.setPayType(entity.getPayType());
      oldEntity.setSqTime(entity.getSqTime());
      oldEntity.setStatus(entity.getStatus());
      oldEntity.setDspUserno(entity.getDspUserno());
      oldEntity.setCsUserno(entity.getCsUserno());
      oldEntity.setFsUserno(entity.getFsUserno());
      oldEntity.setCsTime(entity.getCsTime());
      oldEntity.setFsTime(entity.getFsTime());
      oldEntity.setConnNo(entity.getConnNo());
      oldEntity.setDspUsername(entity.getDspUsername());
      oldEntity.setCsUsername(entity.getCsUsername());
      oldEntity.setFsUsername(entity.getFsUsername());
      oldEntity.setSqUserno(entity.getSqUserno());
      oldEntity.setSqUsername(entity.getSqUsername());
      oldEntity.setRemark(entity.getRemark());
      oldEntity.setNote1(entity.getNote1());
      oldEntity.setNote2(entity.getNote2());*/
        //service.updateEntity(oldEntity);
        service.updateStatus(oldEntity);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(Long[] ids) {
        for (Long id : ids) {
            service.deleteEntity(id);
        }
        return ResponseData.SUCCESS_NO_DATA;
    }


}

