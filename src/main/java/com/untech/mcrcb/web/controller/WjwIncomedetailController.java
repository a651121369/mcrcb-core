package com.untech.mcrcb.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.enhance.QueryFilter;
import com.untech.mcrcb.web.model.WjwIncomedetail;
import com.untech.mcrcb.web.service.WjwIncomedetailService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_INCOMEDETAIL Controller
 *
 * @author chenyong
 * @since 2015-11-04
 */
@Controller
@RequestMapping(value = "/WjwIncomedetail")
public class WjwIncomedetailController extends BaseController {
    @Autowired
    private WjwIncomedetailService service;


    @RequestMapping(value = "/index")
    public String index() {
        return "/mcrcb-core/WjwIncomedetail";
    }

    @RequestMapping(value = "/index2")
    public String index2(Integer id, ModelMap modelMap) {
        modelMap.put("id", id);
        //modelMap.put("location",12345);
        return "/mcrcb-core/WjwIncomedetailLodop";
        //return new ModelAndView("/mcrcb-core/WjwPaydetailMx");
    }

    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwIncomedetail> pager(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }

    @RequestMapping(value = "/getWjwIncomedetailList")
    @ResponseBody
    public Pagination<Map<String, Object>> getWjwIncomedetailList(Integer start, Integer limit,
                                                                  String unitNo, String certNo, String startTime, String endTime) {
        return service.getWjwIncomedetailList(start, limit, unitNo, certNo, startTime, endTime);
    }

    @RequestMapping(value = "/getWjwIncomedetailById")
    @ResponseBody
    public Map<String, Object> getWjwIncomedetailById(Integer id) {
        return service.getWjwIncomedetailById(id);
    }

//    @RequestMapping(value="/save")
//    @ResponseBody
//    public ResponseData save(WjwIncomedetail detail){
////		if (service.getEntity(detail.getId()) != null) {
////           return new ResponseData(false, "记录已存在");
////       }	
// //     service.insertEntity(detail);
//		return service.save(detail);
//    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData save(HttpServletRequest request) {

        String flag = service.save(request);
        if ("0".equals(flag)) {
            return ResponseData.SUCCESS_NO_DATA;
        }
        return new ResponseData(false, "卫计委角色不可以缴款");
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(WjwIncomedetail entity) {
        if (!service.update(entity)) {
            return new ResponseData(false, "记录不存在");
        }
        return ResponseData.SUCCESS_NO_DATA;
    }

    /**
     * 作废选择的缴款信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(Long[] ids) {
//      for (Long id : ids){
//        service.deleteEntity(id);
//      }
        service.deleteIncome(ids);
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "/wjwInAccount")
    @ResponseBody
    public Map<String, Object> getWjwInAccount() {
        return service.getWjwYlyInAccount(SecurityContextUtil.getCurrentUser());
//        return service.getWjwInAccount();
    }

//	@RequestMapping(value="/toDisplayIncomedetail")
//	public ModelAndView toDisplayIncomedetail(HttpServletRequest request){
//		request.setAttribute("voucher", request.getAttribute("voucher"));
//		return new ModelAndView ("/mcrcb-core/displayIncomedetail");
//	}

}

