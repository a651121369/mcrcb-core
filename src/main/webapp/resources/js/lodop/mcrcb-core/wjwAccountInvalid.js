var ENTITY_URL_LIST = "/mcrcb-core/getAllCertNoInfo";
var ENTITY_URL_INVALID = "/mcrcb-core/invalidCertNoInfo";
var PAGESIZE=50;



/********************WjwAccountUpdateWindow组件*************************/
WjwPaymainUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.WjwPaymain = new WjwPaymain();
    	this.WjwPaymain.buttons[0].show();   //隐藏添加按钮
    	
    	WjwPaymainUpdateWindow.superclass.constructor.call(this, {
			title: '作废',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.WjwPaymain]
        });
    }
});

WjwPaymain = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.appTime = this.createHidden('申请日期','appTime');
		this.connNo = this.createTextField('凭证编号','connNo','95%','',null,60,'长度超过不能60');
		this.UNIT_NAME = this.createTextField('机构名称','UNIT_NAME','95%','',null,200,'长度超过不能200');
		this.UNIT_NO = this.createTextField('机构编号','UNIT_NO','95%','',null,30,'长度超过不能30');
		this.OUT_ACCNAME = this.createTextField('付款人名称','OUT_ACCNAME','95%','',null,200,'长度超过不能200');
		this.OUT_ACCNO = this.createTextField('付款人账号','OUT_ACCNO','95%','',null,32,'长度超过不能32');
		this.IN_NAME = this.createTextField('收款人姓名','IN_NAME','95%','',null,200,'长度超过不能200');
		this.IN_ACCNO = this.createTextField('收款人账号','IN_ACCNO','95%','',null,32,'长度超过不能32');
		this.AMOUNT = this.createTextField('金额','AMOUNT','95%','',null,20,'长度超过不能20');
		this.DRUG_AMT = this.createTextField('药品金额','DRUG_AMT','95%','',null,30,'长度超过不能30');
		this.MEDICAL_AMT = this.createTextField('医疗金额','MEDICAL_AMT','95%','',null,20,'长度超过不能20');
		this.TIME = this.createDateField('<font color="red">*</font>记账日期', 'TIME', 'Ymd', '95%');
		this.STATE = this.createTextField('缴费状态','STATE','95%','',null,32,'长度超过不能32');
		
		
		this.connNo.allowBlank = true;
		this.UNIT_NAME.allowBlank = true;
		this.UNIT_NO.allowBlank = true;
		this.OUT_ACCNAME.allowBlank = true;
		this.OUT_ACCNO.allowBlank = true;
		this.IN_NAME.allowBlank = true;
		this.IN_ACCNO.allowBlank = true;
		this.AMOUNT.allowBlank = true;
		this.DRUG_AMT.allowBlank = true;
		this.MEDICAL_AMT.allowBlank = true;
		this.STATE.allowBlank = true;
		this.TIME.allowBlank = false;
		
        WjwPaymain.superclass.constructor.call(this, {
	            anchor: '100%',
	            autoHeight:true,
	            labelWidth: 90,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'tableform',
	            layoutConfig: {columns: 2},
	            items:[
	                this.connNo,
					this.UNIT_NAME,
					this.UNIT_NO,
					this.OUT_ACCNAME,
					this.OUT_ACCNO,
					this.IN_NAME,
					this.IN_ACCNO,
					this.AMOUNT,
					this.DRUG_AMT,
					this.MEDICAL_AMT,
					this.STATE,
					this.TIME,
					this.appTime
	            ],
	            buttonAlign :'center',
	            buttons: [
	               {text: '确定', width: 20,iconCls:'edit', hidden: true, handler: this.updateFormClick, scope: this}
	            ]
	        });
	},
	updateFormClick: function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交，请稍后...',
				url: "/mcrcb-core/updateAccInfoByCertNo",
				method: 'POST',
				success: function(form,action){
					Ext.MessageBox.alert("系统提示：","作废成功！");
					wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwPaymainGrid.WjwPaymainUpdateWindow.hide();
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
					wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwPaymainGrid.WjwPaymainUpdateWindow.hide();
				}
			});
		}
	}
});



/****************WjwPaymainGrid***********************/
WjwPaymainGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
			        {name:'id'},                                                            
		            {name:'DATETIME'},
		            {name:'connNo'},
		            {name:'UNIT_NAME'},
		            {name:'UNIT_NO'},
		            {name:'OUT_ACCNAME'},
		            {name:'OUT_ACCNO'},
		            {name:'IN_NAME'},
		            {name:'IN_ACCNO'},
		            {name:'AMOUNT'},
		            {name:'DRUG_AMT'},
		            {name:'MEDICAL_AMT'},
		            {name:'STATE'},
		            {name:'STATUS'},
		            {name:'FH_TIME'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			        '-',{xtype:'button',text:'作废',iconCls:'delete',handler:this.onInvalidClick,scope:this}, 
				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,
				    	value:new Date().getFirstDateOfMonth ().format('Ymd')},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,
				    	value:new Date().format('Ymd')},
					'-',{xtype:'label',text:'凭证编号'},{xtype:'textfield',id:'connNo'},
					'-',{xtype:'label',text:'交易金额'},{xtype:'textfield',id:'amount'},
			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			var connNo = Ext.getCmp('connNo').getValue();
			       			var amount = Ext.getCmp('amount').getValue();
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
		    	   		    wjwPaymainGrid.store.baseParams= {
		    	   		    		startTime:startTime,endTime:endTime,connNo:connNo,amount:amount
		    	   			};
		    	   		    wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'id',dataIndex:'id',width:130,align:'center',sortable:true,hidden:true},
		            {header:'申请日期',dataIndex:'DATETIME',width:130,align:'center',sortable:true,hidden:false},
		            {header:'凭证编号',dataIndex:'connNo',width:130,align:'center',sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'UNIT_NAME',width:180,align:'center',sortable:true,hidden:false},
		            {header:'机构编号',dataIndex:'UNIT_NO',width:100,align:'center',sortable:true,hidden:false},
		            {header:'缴费类型',dataIndex:'STATE',width:80,align:'center',sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data ==1){
		            			return '缴费';
		            		}else if(data ==2){
		            			return '支付';
		            		}else if(data ==3){
		            			return '拨付';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'金额',dataIndex:'AMOUNT',width:80,align:'right',sortable:true,hidden:false,
		            	 renderer: function(value){
				            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
				            }},
		            {header:'药品金额',dataIndex:'DRUG_AMT',width:80,align:'right',sortable:true,hidden:false,
				            	 renderer: function(value){
						            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
						            }},
		            {header:'医疗金额',dataIndex:'MEDICAL_AMT',width:80,align:'right',sortable:true,hidden:false,
						            	 renderer: function(value){
								            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
								            }},
		            {header:'操作状态',dataIndex:'STATUS',width:80,align:'center',sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data ==1){
		            			return '<font color="blue">申请</font>';
		            		}else if(data ==2){
		            			return '<font color="blue">初审</font>';
		            		}else if(data ==3){
		            			return '<font color="blue">复审</font>';
		            		}else if(data ==4){
		            			return '<font color="blue">作废</font>';
		            		}else if(data ==5){
		            			return '<font color="blue">未记账</font>';
		            		}else if(data ==6){
		            			return '<font color="blue">记账完成</font>';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'记账日期',dataIndex:'FH_TIME',width:180,align:'center',sortable:true,hidden:false},
		            {header:'付款人名称',dataIndex:'OUT_ACCNAME',width:180,align:'center',sortable:true,hidden:false},
		            {header:'付款人账号',dataIndex:'OUT_ACCNO',width:150,align:'center',sortable:true,hidden:false},
		            {header:'收款人名称',dataIndex:'IN_NAME',width:180,align:'center',sortable:true,hidden:false},
		            {header:'收款人账号',dataIndex:'IN_ACCNO',width:150,align:'center',sortable:true,hidden:false}
		           ]);
		WjwPaymainGrid.superclass.constructor.call(this,{
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
	onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
				if(!this.WjwPaymainUpdateWindow)
					this.WjwPaymainUpdateWindow = new WjwPaymainUpdateWindow();
   				
   		    	var win = this.WjwPaymainUpdateWindow;
				var winForm = win.WjwPaymain;
				
				win.show();
				winForm.appTime.setValue(vrecord.data.DATETIME);
   		    	winForm.connNo.setValue(vrecord.data.connNo);
				winForm.UNIT_NAME.setValue(vrecord.data.UNIT_NAME);
				winForm.UNIT_NO.setValue(vrecord.data.UNIT_NO);
				winForm.OUT_ACCNAME.setValue(vrecord.data.OUT_ACCNAME);
				winForm.OUT_ACCNO.setValue(vrecord.data.OUT_ACCNO);
				winForm.IN_NAME.setValue(vrecord.data.IN_NAME);
				winForm.IN_ACCNO.setValue(vrecord.data.IN_ACCNO);
				winForm.AMOUNT.setValue(vrecord.data.AMOUNT);
				winForm.DRUG_AMT.setValue(vrecord.data.DRUG_AMT);
				winForm.MEDICAL_AMT.setValue(vrecord.data.MEDICAL_AMT);
				winForm.STATE.setValue(vrecord.data.STATE);
				
				
				winForm.connNo.setReadOnly(true);
				winForm.UNIT_NAME.setReadOnly(true);
				winForm.UNIT_NO.setReadOnly(true);
				winForm.OUT_ACCNAME.setReadOnly(true);
				winForm.OUT_ACCNO.setReadOnly(true);
				winForm.IN_NAME.setReadOnly(true);
				winForm.IN_ACCNO.setReadOnly(true);
				winForm.AMOUNT.setReadOnly(true);
				winForm.DRUG_AMT.setReadOnly(true);
				winForm.MEDICAL_AMT.setReadOnly(true);
				winForm.STATE.setReadOnly(true);
				winForm.STATE.hide();
				
   			}else{
   				Ext.Msg.alert('系统提示','不能修改多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
	
	onInvalidClick: function(){
    	var records = this.getSelectionModel().getSelections();
    	var ids = {};
		var valueStr = [];
    	for(var i=0;i<records.length;i++){
    		
    		if(records[i].data.STATUS == 6){
					Ext.Msg.alert('系统提示','已记账完成的记录不能作废！');
					return;
				}
    		if(records[i].data.STATUS == 4){
					Ext.Msg.alert('系统提示','记录已作废！');
					return;
				}
    		valueStr.push(records[i].get('id'));
    	}
		ids['ids'] = valueStr;
		
    	if(records.length>0){
    		Ext.Msg.confirm('系统提示：',"确定作废这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_INVALID,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','作废成功！');
    						wjwPaymainGrid.vbbar.doLoad(wjwPaymainGrid.vbbar.cursor);
    					},
    					failure: function(form,action){
							Ext.MessageBox.alert("系统提示：",action.result.message);
						}
    				});
    			}
    		});
    	}else{
    		Ext.Msg.alert('系统提示','请选择一条记录！');
    	}
    }
});

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    var startTime = new Date().getFirstDateOfMonth ().format('Ymd');
    var endTime = new Date().format('Ymd');
    wjwPaymainGrid = new WjwPaymainGrid(Ext.getBody().getViewSize().height);
    wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE,startTime:startTime,endTime:endTime}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaymainGrid   
		]
	});
});

