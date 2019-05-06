package com.untech.mcrcb.web.vo;

import java.io.Serializable;

/**
 * TODO: 收款人vo
 * @Auther: Mr.lx-root
 * @Date: 2019/4/3 0003
 */
public class PayeeVo implements Serializable {

    private  String[] inAccno;
    private   String[] inName;
    private  String[] amount;
    private  String[] inBank;
    private  String[] zjFld;
    private  String[] payWay;
    private  String[] topYsdw;
    private  String[] footYsdw;
    private  String[] itmeYs;
    private  String[] funcFl;
    private  String[] ecnoFl;
    private  String[] yt;
    private  String[] zbDetail;
    private  String[] item;
    private  String payType;
    private  String remark ;

    public String[] getInAccno() {
        return inAccno;
    }

    public void setInAccno(String[] inAccno) {
        this.inAccno = inAccno;
    }

    public String[] getInName() {
        return inName;
    }

    public void setInName(String[] inName) {
        this.inName = inName;
    }

    public String[] getAmount() {
        return amount;
    }

    public void setAmount(String[] amount) {
        this.amount = amount;
    }

    public String[] getInBank() {
        return inBank;
    }

    public void setInBank(String[] inBank) {
        this.inBank = inBank;
    }

    public String[] getZjFld() {
        return zjFld;
    }

    public void setZjFld(String[] zjFld) {
        this.zjFld = zjFld;
    }

    public String[] getPayWay() {
        return payWay;
    }

    public void setPayWay(String[] payWay) {
        this.payWay = payWay;
    }

    public String[] getTopYsdw() {
        return topYsdw;
    }

    public void setTopYsdw(String[] topYsdw) {
        this.topYsdw = topYsdw;
    }

    public String[] getFootYsdw() {
        return footYsdw;
    }

    public void setFootYsdw(String[] footYsdw) {
        this.footYsdw = footYsdw;
    }

    public String[] getItmeYs() {
        return itmeYs;
    }

    public void setItmeYs(String[] itmeYs) {
        this.itmeYs = itmeYs;
    }

    public String[] getFuncFl() {
        return funcFl;
    }

    public void setFuncFl(String[] funcFl) {
        this.funcFl = funcFl;
    }

    public String[] getEcnoFl() {
        return ecnoFl;
    }

    public void setEcnoFl(String[] ecnoFl) {
        this.ecnoFl = ecnoFl;
    }

    public String[] getYt() {
        return yt;
    }

    public void setYt(String[] yt) {
        this.yt = yt;
    }

    public String[] getZbDetail() {
        return zbDetail;
    }

    public void setZbDetail(String[] zbDetail) {
        this.zbDetail = zbDetail;
    }

    public String[] getItem() {
        return item;
    }

    public void setItem(String[] item) {
        this.item = item;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
