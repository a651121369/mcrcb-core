var ENTITY_URL_LIST = "/WjwPaydetail/toBankPayBack";
var BANK_PAY_BACK = "/WjwPaydetail/backPay";
var ROLL_BANK = "/WjwPaydetail/rollback";
var PAGESIZE=50;
/* 
 * @author liuyong
 * @date 2015-11-04
 */
/****************************WjwPaydetailForm*********************************/
WjwPaydetailForm = Ext.extend(Ext.ux.Form,{
	
	constructor:function(){
		this.voucher = this.createTextField('<font color="red">*</font>退汇凭证号','voucher','95%','',null,13,'长度不超过13位');
		this.backTime = this.createDateField('<font color="red">*</font>退汇日期','backTime','Ymd','95%');
		this.voucher.allowBlank = false;
		this.backTime.allowBlank = false;
		WjwPaydetailForm.superclass.constructor.call(this,{
			anchor:'100%',
			title:'银行退汇处理',
//			collapsible:true,
			layout:'tableform',
			layoutConfig:{columns:2},
			autoHeight:true,
			labelWidth:90,
			frame:true,
			bodyStyle:'padding: 5px 5px 0',
			items:[
			       this.voucher,
			       this.backTime
			],
			buttonAlign:'center',
			buttons:[	         
				{text: '退汇', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
				{text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}           
			]
			
		});
	},
	
	addFormClick:function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交数据...',
				url: BANK_PAY_BACK,
				method: 'POST',
				success: function(form,action){
					Ext.Msg.alert("系统提示：","退汇成功！");
					wjwPaydetailGrid.wjwPaydetailWindow.hide();
					wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE}});	
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwPaydetailGrid.wjwPaydetailWindow)
    		wjwPaydetailGrid.wjwPaydetailWindow.wjwPaydetailForm.getForm().reset();
        this.ownerCt.hide();
    }
	
});

/****************WjwPaydetailWindow***********************/
WjwPaydetailWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwPaydetailForm = new WjwPaydetailForm();
    	this.wjwPaydetailForm.buttons[0].show();   //隐藏添加按钮
    	this.wjwPaydetailForm.buttons[1].show();   //显示修改按钮
    	WjwPaydetailWindow.superclass.constructor.call(this,{
			title: '退汇',
			 width: 700,
//			 anchor: '100%',
			autoHeight:true,
			 resizable :false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwPaydetailForm]
		});
	}
});

/****************WjwPaydetailGrid***********************/
WjwPaydetailGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
					{name:'id'},          
					{name:'unitNo'},      
					{name:'amount'},      
					{name:'unitName'},    
					{name:'outAccno'},    
					{name:'outAccname'},  
					{name:'outBank'},     
					{name:'inAccno'},     
					{name:'inName'},      
					{name:'inBank'},      
					{name:'payTime'},     
					{name:'zjFld'},       
					{name:'payWay'},      
					{name:'topYsdw'},     
					{name:'footYsdw'},    
					{name:'itmeYs'},      
					{name:'funcFl'},      
					{name:'ecnoFl'},      
					{name:'zbDetail'},    
					{name:'currency'},    
					{name:'status'},      
					{name:'operNo'},      
					{name:'yt'},          
					{name:'item'},        
					{name:'connNo'},      
					{name:'note1'},       
					{name:'note2'},       
					{name:'xnAcctno'},    
					{name:'xnAcctName'},  
					{name:'zcAcctno'},    
					{name:'zcAcctname'},  
					{name:'fhUser'},      
					{name:'fhTime'},	   
					{name:'backFlg'},	
					{name:'note1'},
					{name:'backVoucher'} 
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			        '-',{xtype:'button',text:'退汇',iconCls:'add',handler:this.bankPayBackClick,scope:this},
			        '-',{xtype:'button',text:'退汇撤销',iconCls:'redo',handler:this.rollBackClick,scope:this},
				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,
				    	value:new Date().getFirstDateOfMonth ().format('Ymd')},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,
				    	value:new Date().format('Ymd')},
			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			wjwPaydetailGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
		    	   			var startTime = Ext.getCmp('startTime').getValue();
		    	   			var endTime = Ext.getCmp('endTime').getValue();
		    	   			if(startTime){
		    	   				startTime = startTime.format('Ymd');
		    	   			}
		    	   			if(endTime){
		    	   				endTime = endTime.format('Ymd');
		    	   			}
		    	   			if(startTime!=null&&startTime!=''&&endTime!=null&&endTime!=''){
			    	   			if(startTime>endTime){
					       			Ext.Msg.alert('系统提示','截止日期应大于等于开始日期。');
					       			return false;
			    	   			}
		    	   			}
		    	   			
		    	   			params= {startTime:startTime,endTime:endTime};
	    	   				wjwPaydetailGrid.store.baseParams= params;
	    	   				wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			}
//					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
//						wjwPaydetailGrid.vtbar.items.each(function(item,index,length){   
//							if((""+item.getXType()).indexOf("field") != -1) {
//								item.setValue('');
//							}
//						  });  
//    	   			}},
//    	   	        '-',{xtype:'button',text:'导出Excel文件',iconCls:'excel',handler:function(){
//		       			
//		       			Ext.Msg.confirm("系统提示：", "确定下载查询结果吗？", function(btn){
//
//		       				if(btn=="yes"){
//				    	   		var startTime = Ext.getCmp('startTime').getValue().format('Ymd');
//				    	   		var endTime = Ext.getCmp('endTime').getValue().format('Ymd');
//				    	   		var accNo = Ext.getCmp('accNo').getValue();
//				    	   		var url="/WjwAccchange/downloadTradeDetail?startTime="+startTime+"&endTime="+endTime+"&accNo="+accNo ;
//			    	   			var w  = window.open(url,'_blank');
//					    	    w.location.href = url;
//			    	   	 
//		       				}
//		       				
//		       			});
//		       		},scope:this}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:80,sortable:true,hidden:true},
//		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:true},
		            {header:'机构名称',dataIndex:'unitName',width:150,sortable:true,hidden:false},
		            {header:'付款人账号',dataIndex:'outAccno',width:100,sortable:true,hidden:false},
		            {header:'付款人姓名',dataIndex:'outAccname',width:180,sortable:true,hidden:false},
		            {header:'付款人开户行',dataIndex:'outBank',width:100,sortable:true,hidden:true},
		            {header:'收款人账号',dataIndex:'inAccno',width:180,sortable:true,hidden:false},
		            {header:'收款人姓名',dataIndex:'inName',width:180,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'inBank',width:180,sortable:true,hidden:false},		            
		            {header:'申请时间',dataIndex:'payTime',width:180,sortable:true,hidden:false},
		            {header:'凭证编号',dataIndex:'operNo',width:180,sortable:true,hidden:false},
		            {header:'退汇标志',dataIndex:'backFlg',width:180,sortable:true,hidden:false,
		             	renderer:function(data){
		            		if(data ==1){
		            			return "<font color='red'>退汇未处理</font>";
		            		}else if(data ==2){
		            			return "<font color='#0E54EA'>退汇已记账</font>";
		            		}else if(data==3){
		            			return "<font color='blue'>退汇</font>";
		            		}else{
		            			return data;
		            		}		            		
		            	}
		            },
		            {header:'退汇凭证号',dataIndex:'backVoucher',width:180,sortable:true,hidden:false},		            
		            {header:'支付金额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
			        {header:'状态',dataIndex:'status',width:180,sortable:true,hidden:false,
			            	renderer:function(value){
			            		if(value==1){
			            			return "<font color='#1974C3'>申请</font>";
			            		}else if(value==2){
			            			return "<font color='#468847'>初审</font>";
			            		}else if(value==3){
			            			return "<font color='#B94A48'>复审</font>";
			            		}else if(value==4){
			            			return "<font color='red'>作废</font>";
			            		}else if(value==5){
			            			return "<font color='blue'>未记账</font>";
			            		}else if(value==6){
			            			return "<font color='#0E54EA'>记账完成</font>";
			            		}
			            	}    	
			        },		          
			        {header:'用途',dataIndex:'yt',width:180,sortable:true,hidden:false},
			        {header:'科目',dataIndex:'item',width:180,sortable:true,hidden:false,
			              	renderer:function(data){
			            		if(data ==1){
			            			return '医疗';
			            		}else if(data ==2){
			            			return '药品';
			            		}else{
			            			return data;
			            		}		            		
			         }},
			        {header:'指标摘要',dataIndex:'zbDetail',width:180,sortable:true,hidden:false},
		            {header:'经济分类',dataIndex:'ecnoFl',width:180,sortable:true,hidden:false},		            
		            {header:'币种',dataIndex:'currency',width:180,sortable:true,hidden:false},
		            {header:'资金性质',dataIndex:'zjFld',width:180,sortable:true,hidden:false},
		            {header:'一级预算单位',dataIndex:'topYsdw',width:180,sortable:true,hidden:false},
		            {header:'基层预算单位',dataIndex:'footYsdw',width:180,sortable:true,hidden:false},
		            {header:'预算项目',dataIndex:'itmeYs',width:180,sortable:true,hidden:false},
		            {header:'功能分类',dataIndex:'funcFl',width:180,sortable:true,hidden:false},
		            {header:'退汇原因',dataIndex:'note1',width:180,sortable:true,hidden:false}
//		            
//		            {header:'关联号',dataIndex:'connNo',width:180,sortable:true,hidden:false},
//		            {header:'备注1',dataIndex:'note1',width:180,sortable:true,hidden:false},
//		            {header:'备注2',dataIndex:'note2',width:180,sortable:true,hidden:false},
//		            {header:'支出子账号',dataIndex:'xnAcctno',width:180,sortable:true,hidden:false},
//		            {header:'支出子账户名',dataIndex:'xnAcctName',width:180,sortable:true,hidden:false},
//		            
//		            {header:'支出主账号',dataIndex:'zcAcctno',width:180,sortable:true,hidden:false},
//		            {header:'支出主账户名',dataIndex:'zcAcctname',width:180,sortable:true,hidden:false},
//		            
//		            {header:'完成操作人',dataIndex:'fhUser',width:180,sortable:true,hidden:false},
//		            {header:'完成时间',dataIndex:'fhTime',width:180,sortable:true,hidden:false}
		     
		           ]);
		
		WjwPaydetailGrid.superclass.constructor.call(this,{
			region: 'center',
			frame: true,
			height: height,
            viewConfig: {
                forceFit: false
            },
            loadMask: new Ext.LoadMask(document.body,{ 
				msg: '正在载入数据，请稍后...',
				store   : this.store
			}),
			sm: this.vsm,
			cm: this.vcm,
			tbar: this.vtbar,
			bbar: this.vbbar,
			ds: this.store
		});
		
	},
	bankPayBackClick:function(){
		
		if(!this.wjwPaydetailWindow)
		this.wjwPaydetailWindow = new WjwPaydetailWindow();
			
	    var win = this.wjwPaydetailWindow;
		var winForm = win.wjwPaydetailForm;		
		win.show();
	},
	rollBackClick:function(){
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){  	
   				vrecord = records[0];
   	    		Ext.Msg.confirm('系统提示：',"确定撤销该笔退汇记录吗？",function(btn){
   	    			if(btn == 'yes'){
   	    				Ext.Ajax.request({
   	    					url: ROLL_BANK,
   	    					method: 'POST',
   	    					params: {voucher:vrecord.data.operNo},
   	    					success: function(form,action){
   	    						var result = Ext.util.JSON.decode(form.responseText);
   	    						if(result.success==true){
   	    							Ext.Msg.alert('系统提示','撤销成功！');
   	    						}else{
   	    							Ext.Msg.alert('系统提示',result.message);
   	    						}
   	    						
   	    						wjwPaydetailGrid.vbbar.doLoad(wjwPaydetailGrid.vbbar.cursor);
   	    					},
   	    					failure: function(form,action){
   								Ext.MessageBox.alert("系统提示：","返回异常，撤销失败");
   							}
   	    				});
   	    			}
   	    		});
   						
   			}else{
   				Ext.Msg.alert('系统提示','不能同时撤销多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		} 
	}
	
});


/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"  
    var startTime = new Date().getFirstDateOfMonth ().format('Ymd')
    var endTime = new Date().format('Ymd');
    wjwPaydetailGrid = new WjwPaydetailGrid(Ext.getBody().getViewSize().height);
    wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE,startTime:startTime,endTime:endTime}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaydetailGrid   
		]
	});
});

