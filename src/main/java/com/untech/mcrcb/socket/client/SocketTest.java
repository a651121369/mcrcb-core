/**
 * Copyright (C), 2010-2016, 赛捷软件（上海有限公司）.
 * Project Name:fcrcb-core
 * File Name:SocketTest.java
 * Package Name:com.untech.mcrcb.socket.client
 * Date:2016年3月21日下午8:13:34
 * Description: //模块目的、功能描述      
 * History: //修改记录
*/

package com.untech.mcrcb.socket.client;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.untech.mcrcb.web.util.Utils;

/**
 * ClassName:SocketTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2016年3月21日 下午8:13:34 <br/>
 * scrmVersion 1.0
 * @author   simon
 * @version  jdk1.7
 * @see 	 
 */
public class SocketTest {
	private String wjwCode;
	private String tranCode;
	private String request;



	public SocketTest(){
		try {
			ClassPathResource resource = new ClassPathResource("META-INF/res/socket.properties");
			Properties pp = PropertiesLoaderUtils.loadProperties(resource);
			this.wjwCode = pp.getProperty("wjw.code");
			this.tranCode = pp.getProperty("tran.code");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dispatch(){
		/** TODO:缴款测试
		 * 	this.wjwCode -卫计委编号
		 * 	0002 -暂时不知道啥意思
		 * 	1-缴款
		 * 	2019040401001-缴款单的编号
		 **/
//		this.request = this.wjwCode+"|0002|1|2019042601001|";
		/** TODO:缴款测试
		 * 	this.wjwCode -卫计委编号
		 * 	0001 -暂时不知道啥意思
		 * 	1-缴款
		 * 	2019040401001-缴款单的编号
		 * 	10000.00-金额
		 *  721502-银行柜员用户
		 *  3417827261-银行机构号
		 *  20190404 -日期
		 **/
//		this.request = this.wjwCode+"|0001|1||2019042601002|2000.00||721502|3417827261|20190405|";

		/** TODO:拨付测试
		 * 	this.wjwCode -卫计委编号
		 * 	0002 -暂时不知道啥意思
		 * 	3-拨付-缴款
		 * 	2019040401001-缴款单的编号
		 **/
//        this.request = this.wjwCode+"|0002|3|2019042602001|";
		/** TODO:拨付测试
		 * 	this.wjwCode -卫计委编号
		 * 	0001 -暂时不知道啥意思
		 * 	1-缴款
		 * 	2019040401001-缴款单的编号
		 * 	10200.00-金额
		 *  718329-银行柜员用户
		 *  3417827183-银行机构号
		 *  20190404 -日期
		 **/
//        this.request = this.wjwCode+"|0001|3||2019042603001|2000.00||718329|3417827183|20190404||";

		/** TODO:支付测试---该支付申请已经过审批
		 * 	this.wjwCode -卫计委编号
		 * 	0002 -暂时不知道啥意思
		 * 	2-支付
		 * 	2019040302004-缴款单的编号
		 **/
//		this.request = this.wjwCode+"|0002|2|2019040302004|";
//		this.request = this.wjwCode+"|0002|2|2019041702002|";

		/** TODO:支付返回结果为参数当做报文转入
		 * 	this.wjwCode -卫计委编号
		 * 	0001 -暂时不知道啥意思
		 * 	2-支付
		 * 	2019040302004-缴款单的编号
		 * 	200.00-金额
		 *  718329-银行柜员用户
		 *  3417827183-银行机构号
		 *  2019-04-04 -日期
		 **/
//		this.request = this.wjwCode+"|0001|2||2019042602001|200.00||718329|3417827183|2019-04-04|付个人税";
//		this.request = this.wjwCode+"|0001|2||2019042602002|300.00||718329|3417827183|2019-04-04|住房公积金";



		/** TODO:待清分入账
		 * this.wjwCode-卫计委编号
		 * 0001- 暂时不清楚啥意思
		 * 6-待清分入账
		 * 08890000凭证号
		 * 4-医养中心专用（实际是1-支出，-2收入）
		 * 30313.00-金额
		 *718329-柜员编号
		 * 3417827183-银行机构号
		 * 20190225--日期
		 **/
//		this.request = this.wjwCode+"|0001|6|4|08890010|400.00|1|718329|3417827183|20190417|YLY";

		/** TODO: ---退汇测试
		 * @Author Mr.lx
		 * @Date 2019/4/9 0009
		 **/
//		this.request = this.wjwCode+"|0002|7|2019040902006|";
//		this.request = this.wjwCode+"|0002|7|2019041702002|";

//		this.request = this.wjwCode+"|0001|7||2019041002005|200.00||718332|3417827183|20190409|收款账户查询错误|";
//		this.request = this.wjwCode+"|0001|7||2019040602002|1.00||718332|3417827183|20190405|收款账户查询错误|";
//		<requestStr:            mcwjw|0001|7||2019022602057|1500.00||718332|3417827183|20190305|收款账户查询错误|>


//		<requestStr:mcwjw|0004|20010001805366600000018|蒙城测试1|5000.00|20010001806166600000011|蒙城测试2|49800.00|20190423|>
//		this.request = this.wjwCode+"|0004|20010001805366600000018|蒙城测试1|5000.00|20010001806166600000011|蒙城测试2|49800.00|20190423|";


		ClientTest client = new ClientTest(request);
		client.start();
	}

	public static void main(String[] args) {

		SocketTest st = new SocketTest();
		st.dispatch();

	}

}

