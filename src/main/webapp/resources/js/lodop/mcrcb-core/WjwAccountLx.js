var ENTITY_URL_LIST = "/WjwAccchange/findLxDetails";
var LX_URL_RZ = "/WjwAccount/LxRz"
var	ENTITY_URL_BACK = "/WjwAccchange/lxRollback";
var PAGESIZE=10;

/**********************************************InterestOutForm***********************************************/
InterestOutForm = Ext.extend(Ext.ux.Form,{
	
	constructor:function(){
//		this.accNo = this.createSearchCombo("请选择:",'accNo','custName','accNo','95%',"/WjwAccount/findMain");
		this.accNo = new Ext.form.ComboBox({
//  		enableKeyEvents:true,
			fieldLabel:'请选择账户',
			anchor:'95%',
            autoLoad: true,
            triggerAction: 'all',
            mode: 'remote',
            name:'accNo',
            hiddenName :'accNo',
            allowBlank: false,
            displayField:'custName',
            valueField:'accNo',
            forceSelection:false,
            store: new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:'/WjwAccount/findMain', method: 'POST'}),
                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'accNo'},{name:'custName'},{name:'intCome'}]))
            }),
            editable : true,
            listeners : {
         	 'select':function(combo,record,index){
         		var intCome = record.get('intCome');
         		this.ownerCt.amount.setValue(intCome);
         		this.ownerCt.amount.setReadOnly(true);
         	
         	 }
  
            }  
        })
		this.date =  this.createDateField('<font color="red">*</font>支出日期','date','Y-m-d','95%');
		this.amount = this.createTextField('利息余额','amount','95%','',null,21,'长度超过不能21');
		this.payAmt = new Ext.form.NumberField({
            fieldLabel: '<font color="red">*</font>支出金额',
            name: 'payAmt',
            allowBlank: false,
            allowNegative :false,
            maxLength:21,
            maxLengthText:'长度超过不能21', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		
		this.date.allowBlank = false;
		
		InterestOutForm.superclass.constructor.call(this,{
			anchor: '100%',
        	title: '利息出账',
        	collapsible: true,
            layout: 'tableform',
	        layoutConfig: {columns: 2},
	        autoHeight:true,
            labelWidth: 60,
            frame: true,
            bodyStyle:"padding: 5px 5px 0",
            items:[                  
                   this.accNo,
                   this.amount,
                   this.date,
                   this.payAmt
 			],
 			buttonAlign:'center',
 			buttons:[
				{text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
				{text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
 			]
		});
	},
	addFormClick: function(){
		var accNo = this.accNo.getValue();
		var rzAmt = this.payAmt.getValue();
		var date = this.date.getValue().format('Ymd');
		if(this.getForm().isValid()){
		
			Ext.Msg.confirm('系统提示：',"支出金额 为 ："+this.payAmt.getValue(),function(btn){
				if(btn == 'yes'){	
					Ext.Ajax.request({
						waitMsg: '正在提交数据...',
						url: LX_URL_RZ,
						method: 'POST',
						params:{accNo:accNo,payAmt:payAmt,date:date},
						success: function(form,action){
							Ext.Msg.alert("系统提示：","利息支出成功！");
							wjwAccchangeGrid.interestOutWindow.hide();
							wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
							
							
						},
						failure: function(form,action){
							Ext.MessageBox.alert("系统提示：",action.result.message);
						}
						});
					}
				})
		}
	},
	
	//关闭
    onCloseClick: function(){
    	if(wjwAccchangeGrid.interestOutWindow)
    		wjwAccchangeGrid.interestOutWindow.interestOutForm.getForm().reset();
        this.ownerCt.hide();
//    	this.ownerCt.close();
    }
	
});


/***************************************InterestForm组件***************************************************/
InterestForm = Ext.extend(Ext.ux.Form,{
	
	constructor:function(){
//		this.id_ = this.createHidden('ID','id','95%');
		this.accNo = this.createSearchCombo("请选择:",'accNo','custName','accNo','95%',"/WjwAccount/findMain");
		this.date =  this.createDateField('<font color="red">*</font>入账日期','date','Y-m-d','95%');
//		this.custName = this.createTextField('账户名','custName','95%','',null,60,'长度超过不能60');
//		this.amount = this.createTextField('账户余额','amount','95%','',null,21,'长度超过不能21');
		this.rzAmt = new Ext.form.NumberField({
            fieldLabel: '<font color="red">*</font>入账金额',
            name: 'rzAmt',
            allowBlank: false,
            allowNegative :false,
            maxLength:21,
            maxLengthText:'长度超过不能21', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		
		this.date.allowBlank = false;
		
		InterestForm.superclass.constructor.call(this,{
			anchor: '100%',
        	title: '利息入账',
        	collapsible: true,
            layout: 'tableform',
	        layoutConfig: {columns: 2},
	        autoHeight:true,
            labelWidth: 60,
            frame: true,
            bodyStyle:"padding: 5px 5px 0",
            items:[                  
                   this.accNo,
//                   this.custName,
//                   this.amount,
                   this.date,
                   this.rzAmt
//                   this.id_
 			],
 			buttonAlign:'center',
 			buttons:[
				{text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
				{text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
 			]
		});
	},
	addFormClick: function(){
		var accNo = this.accNo.getValue();
		var rzAmt = this.rzAmt.getValue();
		var date = this.date.getValue().format('Ymd');
		if(this.getForm().isValid()){
		
			Ext.Msg.confirm('系统提示：',"入账 金额 为 ："+this.rzAmt.getValue(),function(btn){
				if(btn == 'yes'){	
					Ext.Ajax.request({
						waitMsg: '正在提交数据...',
						url: LX_URL_RZ,
						method: 'POST',
						params:{accNo:accNo,rzAmt:rzAmt,date:date},
						success: function(form,action){
							Ext.Msg.alert("系统提示：","利息入账成功！");
							wjwAccchangeGrid.interestAddWindow.hide();
							wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
							
							
						},
						failure: function(form,action){
							Ext.MessageBox.alert("系统提示：",action.result.message);
						}
						});
					}
				})
		}
	},
	
	//关闭
    onCloseClick: function(){
    	if(wjwAccchangeGrid.interestAddWindow)
    		wjwAccchangeGrid.interestAddWindow.interestForm.getForm().reset();
        this.ownerCt.hide();
//    	this.ownerCt.close();
    }
	
});

/**************InterestAddWindow*********************/
InterestAddWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.interestForm = new InterestForm();
    	this.interestForm.buttons[0].show();   //隐藏添加按钮
    	this.interestForm.buttons[1].show();   //显示修改按钮
    	InterestAddWindow.superclass.constructor.call(this,{
			title: '利息入账',
			 width: 1000,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.interestForm]
		});
	}
});

/**************InterestOutWindow*********************/
InterestOutWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.interestOutForm = new InterestOutForm();
    	this.interestOutForm.buttons[0].show();   //隐藏添加按钮
    	this.interestOutForm.buttons[1].show();   //显示修改按钮
    	InterestOutWindow.superclass.constructor.call(this,{
			title: '利息支出',
			 width: 1000,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.interestOutForm]
		});
	}
});



/*************************************************************************************/
/****************WjwAccchangeGrid***********************/
WjwAccchangeGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'unitNo'},
		            {name:'unitName'},
		            {name:'accNo'},
		            {name:'accName'},
		            {name:'dfAccno'},
		            {name:'dfAccname'},
		            {name:'amount'},
		            {name:'drugAmt'},
		            {name:'medcAmt'},
		            {name:'tranAmt'},
		            {name:'tranTime'},
		            {name:'inOrOut'},
		            {name:'otherAmt'},
		            {name:'note1'},
		            {name:'note2'},
		            {name:'flag'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
				    '-',{xtype:'button',text:'利息入账',iconCls:'add',handler:this.onInterestInClick,scope:this},
				    '-',{xtype:'button',text:'利息入账撤销',iconCls:'redo',handler:this.onBackClick,scope:this}
//				    '-',{xtype:'button',text:'利息支出',iconCls:'delete',handler:this.onInterestOutClick,scope:this}
//				    '-',{xtype:'button',text:'待清分账务明细分割',iconCls:'edit',handler:this.onModifyClick,scope:this},
//				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Y-m-d',editable: false},
//				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Y-m-d',editable: false},
//			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
//			       			var params = {};
//			       			wjwAccchangeGrid.vtbar.items.each(function(item,index,length){ 
//		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
//		       						if (item.getXType() == 'datefield') {
//		       							params[item.getId()] = item.getValue().format(item.format);
//		       						} else {
//		       							params[item.getId()] = item.getValue();
//		       						}
//		       					}
//							});
//		    	   			var startTime = Ext.getCmp('startTime').getValue();
//		    	   			var endTime = Ext.getCmp('endTime').getValue();
//		    	   			if(startTime){
//		    	   				startTime = startTime.format('Ymd');
//		    	   			}
//		    	   			if(endTime){
//		    	   				endTime = endTime.format('Ymd');
//		    	   			}
//		    	   			if(startTime!=null&&startTime!=''&&endTime!=null&&endTime!=''){
//			    	   			if(startTime>endTime){
//					       			Ext.Msg.alert('系统提示','截止日期应大于等于开始日期。');
//					       			return false;
//			    	   			}
//		    	   			}
//		    	   			params= {startTime:startTime,endTime:endTime};
//	    	   				wjwAccchangeGrid.store.baseParams= params;
//	    	   				wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
//	    	   			}
//	       			},
//					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
//						wjwAccchangeGrid.vtbar.items.each(function(item,index,length){   
//							if((""+item.getXType()).indexOf("field") != -1) {
//								item.setValue('');
//							}
//						  });  
//    	   			}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:100,sortable:true,hidden:true},
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:true},
		            {header:'机构名称',dataIndex:'unitName',width:100,sortable:true,hidden:true},
		            {header:'己方账号',dataIndex:'accNo',width:100,sortable:true,hidden:false},
		            {header:'己方账户名',dataIndex:'accName',width:180,sortable:true,hidden:false
//		              	renderer: function(value){
//			            	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+value+'</a>';	
//						}
		            	},
		            {header:'对方账号',dataIndex:'dfAccno',width:100,sortable:true,hidden:false},
		            {header:'对方账户名',dataIndex:'dfAccname',width:180,sortable:true,hidden:false},
		            {header:'余额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'药品金额',dataIndex:'drugAmt',width:100,sortable:true,hidden:true},
		            {header:'医疗金额',dataIndex:'medcAmt',width:100,sortable:true,hidden:true},
		            {header:'交易金额',dataIndex:'tranAmt',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }
		            	},
		            {header:'交易时间',dataIndex:'tranTime',width:150,sortable:true,hidden:false},
		            {header:'收入/支出',dataIndex:'inOrOut',width:100,sortable:true,hidden:false,
		            	renderer:function(data){
		            		if(data ==1){
		            			return '收入';
		            		}else if(data ==2){
		            			return '支出';
		            		}else{
		            			return data;
		            		}		            		
		            	}},
		            {header:'其他金额',dataIndex:'otherAmt',width:100,sortable:true,hidden:true},
		            {header:'凭证号',dataIndex:'note1',width:100,sortable:true,hidden:true},
		            {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:true},
		            {header:'状态',dataIndex:'flag',width:100,sortable:true,hidden:false,
		            	renderer:function(data){
		            		if(data ==1){
		            			return '<font color="blue">入账</font>';
		            		}else if(data ==2){
		            			return '<font color="blue">清分</font>';
		            		}else if(data==3){
		            			return '<font color="blue">利息收入</font>';
		            		}else if(data==4){
		            			return '<font color="blue">利息支出</font>';
		            		}else{
		            			return data;
		            		}		            		
		            	}}
		           ]);
		WjwAccchangeGrid.superclass.constructor.call(this,{
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
    onInterestInClick: function(){			
		if(!this.interestAddWindow)
		this.interestAddWindow = new InterestAddWindow();			
	    var win = this.interestAddWindow;
		var winForm = win.interestForm;		
		win.show();
					
    },
    onInterestOutClick: function(){			
		if(!this.interestOutWindow)
		this.interestOutWindow = new InterestOutWindow();			
	    var win = this.interestOutWindow;
		var winForm = win.interestOutForm;

		win.show();
		
					
    },
    onBackClick:function(){
    	var records = this.getSelectionModel().getSelections();
    	if(records.length>0){
    		if(records.length==1){
    			vrecord = records[0];
    			var id = vrecord.data.id;
    			var status = vrecord.data.flag;
//    			var accNo = vrecord.data.accNo;
//    			var dfAccno = vrecord.data.dfAccno;
        		Ext.Msg.confirm('系统提示：',"确定撤销这条记录吗？",function(btn){
        			if(btn == 'yes'){
        				Ext.Ajax.request({
        					url: ENTITY_URL_BACK,
        					method: 'POST',
        					params: {id:id,status:status},
        					success: function(form,action){
        						Ext.Msg.alert('系统提示','撤销成功！');
        						wjwAccchangeGrid.vbbar.doLoad(wjwAccchangeGrid.vbbar.cursor);
        					},
        					failure: function(form,action){
    							Ext.MessageBox.alert("系统提示：",action.result.message);
    						}
        				});
        			}
        		});
    			
    		}else{
    			Ext.Msg.alert("系统提示：","不能同时撤销多条记录");
    		}
    	}else{
    		Ext.Msg.alert("系统提示：","请选择一条记录");
    	}
    },
    
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {  			
   			if(records.length == 1){   				
   				vrecord = records[0];
   				if(vrecord.data.flag!=1){
   					Ext.Msg.alert('系统提示','该条记录已清分！');
   					return;
   				}
				this.dqfDetailAddWindow = new DqfDetailAddWindow();
   				
   		    	var win = this.dqfDetailAddWindow;
				var winForm = win.dqfDetailForm;
   				var accNo = vrecord.data.accNo;   				
				var parent = vrecord.data.dfAccno;
				winForm.id_.setValue(vrecord.data.id);
				winForm.note2.setValue(vrecord.data.note2);
				winForm.accNo.setValue(vrecord.data.accNo);
				winForm.custName.setValue(vrecord.data.accName);
				winForm.custName.setReadOnly(true);
				winForm.amount.setValue(vrecord.data.tranAmt);
				winForm.amount.setReadOnly(true);
   		    	Ext.Ajax.request({
   		    		url:DQF_URL_DETAIL,
   		    		params:{accNo:accNo,parent:parent},
   		    		method:'POST', 
   		    		success:function(resp,options){
   		    			var results = Ext.util.JSON.decode(resp.responseText);
   		    			for(var i=0;i<results.length;i++){
   		    				var obj = results[i];

   		    				winForm.add(new Ext.form.TextField({
   		    					anchor:'95%',
   								allowBlank : true, 
   								fieldLabel:obj.custName,
   								name:"accNo"+i,
   								value:obj.accNo,
   								readOnly:true
   							}));
   		    				winForm.add(new Ext.form.TextField({
   		    					anchor:'95%',
   								allowBlank : true, 
   								fieldLabel:'金额：',
   								name:"amount"+i
   							}));
   		    			}
   		    			win.show();
   		    			
   		    		},
   		    		failure:function(action){
   		    			Ext.MessageBox.alert("系统提示：",action);
   		    		}
   		    	});
   		    	
   			}else{
   				Ext.Msg.alert('系统提示','不能修改多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
    onDeleteClick: function(){
    	var records = this.getSelectionModel().getSelections();
    	var ids = {};
		var valueStr = [];
    	for(var i=0;i<records.length;i++){
    		valueStr.push(records[i].get('id'));
    	}
		ids['ids'] = valueStr;
		
    	if(records.length>0){
    		Ext.Msg.confirm('系统提示：',"确定删除这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_DELETE,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','删除成功！');
    						wjwAccchangeGrid.vbbar.doLoad(wjwAccchangeGrid.vbbar.cursor);
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

/************************************************************/
//document.onAClick = function(value){
//	var note2 = wjwAccchangeGrid.getSelectionModel().getSelections()[0].data.note2;
//	var accNo = wjwAccchangeGrid.getSelectionModel().getSelections()[0].data.accNo;
////	var sqTime = wjwAccchangeGrid.getSelectionModel().getSelections()[0].data.sqTime;
//	
//	parent.Home.AddTab(note2+accNo+'1',"待清分账务明细", '/WjwAccchange/qfMx?accNo='+accNo+'&note2='+note2);
////	window.open('/WjwAccchange/qfMx?accNo='+accNo+'&note2='+note2);
//	
//	
//};


/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwAccchangeGrid = new WjwAccchangeGrid(Ext.getBody().getViewSize().height);
    wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwAccchangeGrid   
		]
	});
});
