var USER_GRID_STORE_URL = '/fms/businessdata/getOperatin';
var PAGESIZE=20;

Ext.reg('rateTypeField', Ext.extend(ST.ux.ExtField.ComboBox, {
	store : new Ext.data.JsonStore({  //填充的数据
    	url : "/dict/queryDictEntries",
    	fields : new Ext.data.Record.create(['code', 'name'])
 	}),
 	showAll:false
}));



/***************************************ConstructionForm组件**************************************************/
ConstructionForm = Ext.extend(Ext.ux.Form, {
	
    constructor: function() {
        this.beginTime = this.createDateField('<font color="red">*</font>开始日期','beginTime','Ymd','100%');
        this.endTime = this.createDateField('<font color="red">*</font>截至日期', 'endTime','Ymd','100%');
        //this.ownAcct = this.createTextField('<font color="red">*</font>账户号', 'ownAcct', '100%','该项为必填项！','',64,'最大字符数64！');
        /*this.acctId1 = new Ext.form.ComboBox({
    	    	   id: 'acctId1',
    	    	   fieldLabel:"主账户",
    	           autoLoad: true,
    	           triggerAction: "all",//是否开启自动查询功能
    	           store: new Ext.data.JsonStore({
    	                        proxy : new Ext.data.HttpProxy({
    	                               url : "/AccountManager/getMainAcctNo" ,
    	                               method : "POST"
    	                        }),
    	                        fields : [ 'acctId', 'acctName' ]
    	                 }), //定义数据源
    	           displayField: "acctName",//关联某一个逻辑列名作为显示值
    	           valueField: "acctId",//关联某一个逻辑列名作为实际值
    	           mode: "remote",//如果数据来自本地用local 如果来自远程用remote默认为remote
    	           emptyText: "请选择账户" ,
    	           readOnly: false,
    	           editable : false,
    	           listeners: {  //为Combo添加select事件
    	                       select : function(combo, record, index) {   // 该事件会返回选中的项对应在 store中的 record值. index参数是排列号.
						 			customerBox.reset();
						 			customerBox.clearValue();
						 			customerBox.store.load({params:{acctId1:combo.value}}); 
						 			customerBox.enable();
    	                       	}
    	           }
    	    });*/
        
    	var customerBox=new Ext.form.ComboBox({
			   		 id:"acctId2",
			   		 fieldLabel:"子账户",
//			   		 width:150,
			   		 anchor: '100%',
			   		// typeAhead : true,
			   		 //title:"子账户",
			   		 autoLoad: true,
				   	 //renderTo:"khjl",
				   	 triggerAction:"all",//是否开启自动查询功能
				   	 store:new Ext.data.JsonStore({
    	                        proxy : new Ext.data.HttpProxy({
    	                               url : "/AccountManager/getAcctNoByOrg" ,
    	                               method : "POST"
    	                        }),
    	                        fields : [ 'acctId', 'acctName' ]
    	                 }),//定义数据源
				   	 displayField:"acctName",//关联某一个逻辑列名作为显示值
				   	 valueField:"acctId",//关联某一个逻辑列名作为实际值
				   	 mode:"remote",//如果数据来自本地用local 如果来自远程用remote默认为remote
				   	 emptyText:"请选择子账户",
				   	 readOnly:false,
				   	 editable : false
    	    });
    	  this.acctId2 =  customerBox;
    	    
        ConstructionForm.superclass.constructor.call(this, {
        	renderTo: Ext.getBody(),
        	anchor: '100%',
        	autoHeight:true,
        	layout:"tableform",
        	layoutConfig: {columns: 2},
        	region:'center' ,
        	labelWidth: 90,
            labelAlign :'right',
            frame:true,
            bodyStyle:"padding: 5px 5px 0",
            width: '100%',
            items: [
                    	this.beginTime,
                    	this.endTime,
                    	this.acctId2
            ],
            buttonAlign :'center',
            buttons: [
                      {text: '查询', width: 20,iconCls: 'query', hidden: false, handler: this.queryFormClick, scope: this}//,'-',
                      //{text: '导出Excel', width: 20,iconCls: 'excel', hidden: false, handler: this.excelFormClick, scope: this}
              ]
        });
     },
     queryFormClick: function() {
     	var thiz = this;
     	//var acctId1 = thiz.acctId1.getValue();
     	var acctId2 = thiz.acctId2.getValue();
     	var beginTime1 = thiz.beginTime.getValue();
     	var endTime1 = thiz.endTime.getValue();
     	if(beginTime1 == "" || endTime1 == "" || acctId2 == ""){
     		alert("查询条件不能为空");
     		return ;
     	}
     	var beginTime = beginTime1.format('Ymd');
     	var endTime = endTime1.format('Ymd');
     	/*if(acctId2 == ""){
     		window.open('/MxQuery/queryMx?accNo='+acctId1+'&beginTime='+beginTime+'&endTime='+endTime);
     	}else{
     		window.open('/MxQuery/queryMx?accNo='+acctId2+'&beginTime='+beginTime+'&endTime='+endTime);
     	}*/
     	window.open('/MxQuery/queryMx?accNo='+acctId2+'&beginTime='+beginTime+'&endTime='+endTime);
     },
     excelFormClick: function() {
     	var thiz = this;
     	var acctId1 = thiz.acctId1.getValue();
     	var acctId2 = thiz.acctId2.getValue();
     	var beginTime1 = thiz.beginTime.getValue();
     	var endTime1 = thiz.endTime.getValue();
     	if(beginTime1 == null && endTime1 == null){
     		alert("导出条件不能为空");
     		return ;
     	}
     	var beginTime = beginTime1.format('Y-m-d');
     	var endTime = endTime1.format('Y-m-d');
     	if(acctId2 == ""){
     		window.open('/fms/businessdata/excelMulOperation?acctId1='+acctId1+'&beginTime='+beginTime+'&endTime='+endTime);
     	}else{
     		window.open('/fms/businessdata/excelSonMulOperation?acctId1='+acctId1+'&acctId2='+acctId2+'&beginTime='+beginTime+'&endTime='+endTime);
     	}
     }
});





/*********************onReady 组件渲染及处理**********************************************/
Ext.onReady(function() {
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';
    conditionForm = new ConstructionForm();
    
    //constructionGrid = new ConstructionGrid(Ext.getBody().getViewSize().height-160, Ext.getBody().getViewSize().width);
    //constructionGrid.store.load({params:{start:0,limit:PAGESIZE}});
    new Ext.Viewport({
    	layout: 'border',
    	items:[
		conditionForm
		//constructionGrid
    	]
    });
    //constructionGrid.constructionUpdateWindow.show();
    //constructionGrid.constructionUpdateWindow.hide();
   
});