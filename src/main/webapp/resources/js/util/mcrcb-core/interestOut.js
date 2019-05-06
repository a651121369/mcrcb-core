var ENTITY_URL_LIST = "/WjwInterest/findInterestOut";
var LX_URL_OUT = "/WjwInterest/interestPay";
var INTEREST_URL_DELETE = "/WjwInterest/del";
var PAGESIZE=20;

/**********************************************InterestOutForm***********************************************/
InterestOutForm = Ext.extend(Ext.ux.Form,{
	
	constructor:function(){

		this.accNo = new Ext.form.ComboBox({
//			id:'accName',
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
            forceSelection:true,
            store: new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:'/WjwAccount/findMainForInterest', method: 'POST'}),
                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'accNo'},{name:'custName'},{name:'intCome'}]))
            }),
//            editable : true,
            listeners : {
         	 'select':function(combo,record,index){
         		var intCome = record.get('intCome');
         		this.ownerCt.amount.setValue(intCome);
         		this.ownerCt.amount.setReadOnly(true);
         	
         	 }
  
            }  
        })
		this.date =  this.createDateField('<font color="red">*</font>支出日期','date','Ymd','95%');
		this.amount = this.createTextField('利息余额','amount','95%','',null,21,'长度超过不能21');
		this.inAccno = this.createTextField('收款人账户','inAccno','95%','',null,32,'长度超过不能32');
		this.inAccname = this.createTextField('收款人名称','inAccname','95%','',null,200,'长度超过不能200');
		this.inBank = this.createTextField('收款人开户行','inBank','95%','',null,200,'长度超过不能200');
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
		this.payWay = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    lazyRender:true,
		    name : 'payWay',
		    anchor: '95%',
		    fieldLabel : '<font color="red">*</font>支出类型',
		    mode: 'local',
		    store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'myId',
		            'displayText'
		        ],
		    	data: [[1, '现金'], [2, '转账']]
		    }),
		    valueField: 'myId',
		    displayField: 'displayText',
		    value:2//默认选中转账

		});
		this.inAccno.allowBlank = false;
		this.inAccname.allowBlank = false;
		this.inBank.allowBlank = false;
		this.date.allowBlank = false;
		
		InterestOutForm.superclass.constructor.call(this,{
			anchor: '100%',
        	title: '利息出账',
        	collapsible: true,
            layout: 'tableform',
	        layoutConfig: {columns: 2},
	        autoHeight:true,
            labelWidth: 80,
            frame: true,
            bodyStyle:"padding: 5px 5px 0",
            items:[                  
                   this.accNo,
                   this.amount,
                   this.inAccno,this.payAmt,
                   this.inAccname,this.payWay,
                   this.inBank,this.date
                   
                   
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
		var accName = this.accNo.getRawValue ();
		var payAmt = this.payAmt.getValue();
		var inAccno = this.inAccno.getValue();
		var inAccname = this.inAccname.getValue();
		var inBank = this.inBank.getValue();
		var payWay = this.payWay.getValue();
		var date = this.date.getValue().format('Ymd');
		if(this.getForm().isValid()){
		
			Ext.Msg.confirm('系统提示：',"支出金额 为 ："+this.payAmt.getValue(),function(btn){
				if(btn == 'yes'){	
					Ext.Ajax.request({
						waitMsg: '正在提交数据...',
						url: LX_URL_OUT,
						method: 'POST',
						params:{accNo:accNo,accName:accName,payAmt:payAmt,inAccno:inAccno,inAccname:inAccname,
							inBank:inBank,payWay:payWay,date:date},
						success: function(form,action){
							Ext.Msg.alert("系统提示：","利息支出成功！");
							wjwInterestGrid.interestOutWindow.hide();
							wjwInterestGrid.store.load({params:{start:0,limit:PAGESIZE}});
							
							
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
    	if(wjwInterestGrid.interestOutWindow)
    		wjwInterestGrid.interestOutWindow.interestOutForm.getForm().reset();
        this.ownerCt.hide();
//    	this.ownerCt.close();
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
WjwInterestGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'unitNo'},
		            {name:'unitName'},
		            {name:'inAccno'},
		            {name:'inAccname'},
		            {name:'inBank'},
		            {name:'outAccno'},
		            {name:'outAccname'},
		            {name:'outBank'},
		            {name:'voucher'},
		            {name:'amount'},
		            {name:'payWay'},
		            {name:'status'},
		            {name:'payTime'},
		            {name:'operator'},
		            {name:'fhUser'},
		            {name:'fhTime'}
		            
//		            {name:'accNo'},
//		            {name:'accName'},
//		            {name:'dfAccno'},
//		            {name:'dfAccname'},
//		            {name:'amount'},
//		            {name:'drugAmt'},
//		            {name:'medcAmt'},
//		            {name:'tranAmt'},
//		            {name:'tranTime'},
//		            {name:'inOrOut'},
//		            {name:'otherAmt'},
//		            {name:'note1'},
//		            {name:'note2'},
//		            {name:'flag'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [

				    '-',{xtype:'button',text:'利息支出',iconCls:'add',handler:this.onInterestOutClick,scope:this},
				    '-',{xtype:'button',text:'利息支出打印',iconCls:'add',handler:this.onPrintClick,scope:this},
				    '-',{xtype:'button',text:'作废',iconCls:'delete',handler:this.onDeleteClick,scope:this}
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
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:180,sortable:true,hidden:false},
		            {header:'收款人账号',dataIndex:'inAccno',width:100,sortable:true,hidden:false},
		            {header:'收款人名称',dataIndex:'inAccname',width:180,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'inBank',width:180,sortable:true,hidden:false},
		            {header:'付款人账号',dataIndex:'outAccno',width:180,sortable:true,hidden:false},
		            {header:'付款人名称',dataIndex:'outAccname',width:180,sortable:true,hidden:false},		            
		            {header:'付款人开户行',dataIndex:'outBank',width:180,sortable:true,hidden:false},
		            {header:'凭证编号',dataIndex:'voucher',width:100,sortable:true,hidden:false},
		            {header:'支出金额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'支付方式',dataIndex:'payWay',width:100,sortable:true,hidden:false,align: 'right',			            
			             	renderer:function(data){
			            		if(data ==1){
			            			return '现金';
			            		}else if(data ==2){
			            			return '转账';
			            		}else{
			            			return data;
			            		}		            		
			            	}
		            	},
			        {header:'状态',dataIndex:'status',width:100,sortable:true,hidden:false,
			            	renderer:function(data){
			            		if(data ==4){
			            			return '<font color="red">已作废</font>';
			            		}
			            		else if(data ==5){
			            			return '<font color="blue">未记账</font>';
			            		}else if(data ==6){
			            			return '<font color="blue">记账完成</font>';
			            		}else{
			            			return data;
			            		}		            		
			         }},
		            {header:'支付时间',dataIndex:'payTime',width:100,sortable:true,hidden:false},
		            {header:'操作人',dataIndex:'operator',width:100,sortable:true,hidden:true},
		            {header:'核销人',dataIndex:'fhUser',width:100,sortable:true,hidden:false},
		            {header:'核销时间',dataIndex:'fhTime',width:100,sortable:true,hidden:false}
		           ]);
		WjwInterestGrid.superclass.constructor.call(this,{
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

    onInterestOutClick: function(){			
	//	if(!this.interestOutWindow)
		this.interestOutWindow = new InterestOutWindow();			
	    var win = this.interestOutWindow;
		var winForm = win.interestOutForm;

		win.show();
				
    },
	
	onPrintClick:function(){
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				if(vrecord.data.status==5){
   					window.open("/WjwInterest/toPrint?id="+vrecord.data.id);
   				}else{
   					Ext.Msg.alert('系统提示','该笔记录已作废或者已记账，不可打印');
   				}
   				
   			}else{
   				Ext.Msg.alert('系统提示','不能同时打印多条记录！');
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
    		if(records[i].data.status == 6){
					Ext.Msg.alert('系统提示','已记账完成的记录不能作废！');
					return;
				}
    		if(records[i].data.status == 4){
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
    					url: INTEREST_URL_DELETE,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','作废成功！');
    						wjwInterestGrid.vbbar.doLoad(wjwInterestGrid.vbbar.cursor);
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
    
    wjwInterestGrid = new WjwInterestGrid(Ext.getBody().getViewSize().height);
    wjwInterestGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwInterestGrid   
		]
	});
});
