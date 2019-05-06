var ENTITY_URL_LIST = "/WjwBfhzb/pager";
var ENTITY_URL_LIST2 = "/WjwBfhzb/getBfHzbList";
var ENTITY_URL_SAVE = "/WjwBfhzb/save";
//拨付
var ENTITY_URL_BF = "/WjwBfhzb/appropriate";
var ENTITY_URL_UPDATE = "/WjwBfhzb/update";
var ENTITY_URL_DELETE = "/WjwBfhzb/delete";
var ENTITY_URL_ZF = "/WjwBfhzb/zf";

var GET_ALL_ACCOUNT_URL = "/WjwBfhzb/getAllIncomeAccount"
var	GET_ALL_ACCOUNT_URL2= "/WjwBfhzb/getWjwIncomeAccountList"
var	GET_ALL_ACCOUNT_URL3= "/WjwBfhzb/getJlyIncomeAccountList"
var PAGESIZE=50;
var accounts=null;
/*
 * WJW_BFHZB 
 * @author chenyong
 * @date 2015-11-04
 */
WjwBfhzb = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.id_ = new Ext.form.NumberField({
            fieldLabel: '<font color="red">*</font>ID',
            name: 'id',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.unitNo = this.createTextField('UNIT_NO','unitNo','95%','',null,30,'长度超过不能30');
		this.unitName = this.createTextField('UNIT_NAME','unitName','95%','',null,60,'长度超过不能60');
		this.bfAmt = this.createTextField('BF_AMT','bfAmt','95%','',null,21,'长度超过不能21');
		this.bfDrugAmt = this.createTextField('BF_DRUG_AMT','bfDrugAmt','95%','',null,21,'长度超过不能21');
		this.bfMedcAmt = this.createTextField('BF_MEDC_AMT','bfMedcAmt','95%','',null,21,'长度超过不能21');
		this.bfTim = this.createTextField('BF_TIM','bfTim','95%','',null,30,'长度超过不能30');
		this.operNo = this.createTextField('OPER_NO','operNo','95%','',null,20,'长度超过不能20');
		this.operTime = this.createTextField('OPER_TIME','operTime','95%','',null,30,'长度超过不能30');
		this.detail = this.createTextField('DETAIL','detail','95%','',null,100,'长度超过不能100');
		this.connNo = this.createTextField('CONN_NO','connNo','95%','',null,32,'长度超过不能32');
		this.remark = this.createTextField('REMARK','remark','95%','',null,80,'长度超过不能80');
		this.note1 = this.createTextField('NOTE1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('NOTE2','note2','95%','',null,100,'长度超过不能100');
		//======
		this.note3 = this.createTextField('NOTE2','note3','95%','',null,100,'长度超过不能100');
		this.note4 = this.createTextField('NOTE2','note4','95%','',null,100,'长度超过不能100');
		this.note5 = this.createTextField('NOTE2','note5','95%','',null,100,'长度超过不能100');
		this.note6 = this.createTextField('NOTE2','note6','95%','',null,100,'长度超过不能100');
		this.note7 = this.createTextField('NOTE2','note7','95%','',null,100,'长度超过不能100');
		this.note8 = this.createTextField('NOTE2','note8','95%','',null,100,'长度超过不能100');
		this.note9 = this.createTextField('NOTE2','note9','95%','',null,100,'长度超过不能100');
		this.note10 = this.createTextField('NOTE2','note10','95%','',null,100,'长度超过不能100');
		this.note11 = this.createTextField('NOTE2','note11','95%','',null,100,'长度超过不能100');
		this.note12 = this.createTextField('NOTE2','note12','95%','',null,100,'长度超过不能100');
		//======
		this.unitNo.allowBlank = true;
		this.unitName.allowBlank = true;
		this.bfAmt.allowBlank = true;
		this.bfDrugAmt.allowBlank = true;
		this.bfMedcAmt.allowBlank = true;
		this.bfTim.allowBlank = true;
		this.operNo.allowBlank = true;
		this.operTime.allowBlank = true;
		this.detail.allowBlank = true;
		this.connNo.allowBlank = true;
		this.remark.allowBlank = true;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
        WjwBfhzb.superclass.constructor.call(this, {
	            anchor: '100%',
	            autoHeight:true,
	            labelWidth: 90,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'tableform',
	            layoutConfig: {columns: 2},
	            items:[
					this.id_,
					this.unitNo,
					this.unitName,
					this.bfAmt,
					this.bfDrugAmt,
					this.bfMedcAmt,
					this.bfTim,
					this.operNo,
					this.operTime,
					this.detail,
					this.connNo,
					this.remark,
					this.note1,
					//============
					this.note3,
					this.note4,
					this.note5,
					this.note6,
					this.note7,
					this.note8,
					this.note9,
					this.note10,
					this.note11,
					this.note12,
					//================
					this.note2
	            ],
	            buttonAlign :'center',
	            buttons: [
	               {text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
	               {text: '修改', width: 20,iconCls:'edit', hidden: true, handler: this.updateFormClick, scope: this},
	               {text: '重置', width: 20,iconCls:'redo', hidden: true, handler: this.onResumeClick, scope: this},               
	               {text: '清空', width: 20, iconCls:'redo',  handler: this.resetFormClick, scope: this},
	               {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
	            ]
	        });
	},
	addFormClick: function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交数据...',
				url: ENTITY_URL_SAVE,
				method: 'POST',
				success: function(form,action){
					Ext.Msg.alert("系统提示：","添加成功！");
					wjwBfhzbGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwBfhzbGrid.wjwBfhzbWindow.hide();
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	updateFormClick: function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交，请稍后...',
				url: ENTITY_URL_UPDATE,
				method: 'POST',
				success: function(form,action){
					Ext.MessageBox.alert("系统提示：","修改成功！");
					wjwBfhzbGrid.wjwBfhzbUpdateWindow.hide();
					wjwBfhzbGrid.vbbar.doLoad(wjwBfhzbGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwBfhzbGrid.wjwBfhzbUpdateWindow)
    	wjwBfhzbGrid.wjwBfhzbUpdateWindow.wjwBfhzb.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwBfhzbGrid.onModifyClick();
    }
	
});

/**************WjwBfhzbWindow*********************/
WjwBfhzbWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwBfhzb = new WjwBfhzb();
		WjwBfhzbWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			 autoScroll:true,
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwBfhzb]
		});
	}
});

/********************WjwBfhzbUpdateWindow组件*************************/
WjwBfhzbUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwBfhzb = new WjwBfhzb();
    	this.wjwBfhzb.buttons[0].hide();   //隐藏添加按钮
    	this.wjwBfhzb.buttons[1].show();   //显示修改按钮
    	this.wjwBfhzb.buttons[2].show();   //显示重置按钮
    	this.wjwBfhzb.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwBfhzbUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwBfhzb]
        });
    }
});

/****************WjwBfhzbGrid***********************/
WjwBfhzbGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST2,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'unitNo'},
		            {name:'unitName'},
		            {name:'bfAmt'},
		            {name:'bfDrugAmt'},
		            {name:'bfMedcAmt'},
		            {name:'bfTim'},
		            {name:'operNo'},
		            {name:'operTime'},
		            {name:'detail'},
		            {name:'connNo'},
		            {name:'remark'},
		            {name:'status'},
		            {name:'note1'},
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
				'-',{xtype:'button',text:'卫生院拨付',iconCls:'add',handler:this.onAddWjwClick,scope:this},
				// '-',{xtype:'button',text:'敬老院拨付',iconCls:'add',handler:this.onAddJlyClick,scope:this},
			       '-',{xtype:'button',text:'拨付打印',iconCls:'add',handler:this.onPrintClick,scope:this},
			       '-',{xtype:'button',text:'作废',iconCls:'delete',handler:this.onDeleteClick,scope:this},
			     '-',{xtype:'button',text:'清单',iconCls:'edit',handler:this.onDetailClick,scope:this}, 
//					'-',{xtype:'label',text:'操作人编号'},{xtype:'textfield',id:'operNo'},
					'-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,value:''},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,value:''},
//					'-',{xtype:'label',text:'UNIT_NO'},{xtype:'textfield',id:'Q_unitNo_S_LK'},
//					'-',{xtype:'label',text:'UNIT_NAME'},{xtype:'textfield',id:'Q_unitName_S_LK'},
//					'-',{xtype:'label',text:'BF_AMT'},{xtype:'textfield',id:'Q_bfAmt_S_EQ'},
					//'-',{xtype:'label',text:'BF_DRUG_AMT'},{xtype:'textfield',id:'Q_bfDrugAmt_S_EQ'},
					//'-',{xtype:'label',text:'BF_MEDC_AMT'},{xtype:'textfield',id:'Q_bfMedcAmt_S_EQ'},
					//'-',{xtype:'label',text:'BF_TIM'},{xtype:'textfield',id:'Q_bfTim_S_LK'},
					//'-',{xtype:'label',text:'OPER_NO'},{xtype:'textfield',id:'Q_operNo_S_LK'},
					//'-',{xtype:'label',text:'OPER_TIME'},{xtype:'textfield',id:'Q_operTime_S_LK'},
					//'-',{xtype:'label',text:'DETAIL'},{xtype:'textfield',id:'Q_detail_S_LK'},
					//'-',{xtype:'label',text:'CONN_NO'},{xtype:'textfield',id:'Q_connNo_S_LK'},
					//'-',{xtype:'label',text:'REMARK'},{xtype:'textfield',id:'Q_remark_S_LK'},
					//'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			    	   if(Ext.getCmp('endTime').getValue()>0 && Ext.getCmp('startTime').getValue() > Ext.getCmp('endTime').getValue()){
	    	   				Ext.Msg.alert('系统提示', '开始日期不能大于结束日期');
	    	   				return;
	    	   			}
			       			var params = {};
			       			wjwBfhzbGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwBfhzbGrid.store.baseParams= params;
	    	   				wjwBfhzbGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwBfhzbGrid.vtbar.items.each(function(item,index,length){   
							if((""+item.getXType()).indexOf("field") != -1) {
								item.setValue('');
							}
						  });  
    	   			}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:50,sortable:true,hidden:true},
		            {header:'机构号',dataIndex:'unitNo',width:80,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:120,sortable:true,hidden:false,
		                renderer:function(data, metadata, record, rowIndex, columnIndex, store){
	                    	metadata.attr = 'ext:qtip="点击查看拨付明细！"';
	                    	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+data+'</a>';
	                    }	
		            },
		            {header:'拨付药品金额',dataIndex:'bfDrugAmt',width:120,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'拨付医疗金额',dataIndex:'bfMedcAmt',width:120,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'拨付总金额',dataIndex:'bfAmt',width:120,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'拨付时间',dataIndex:'bfTim',width:90,sortable:true,hidden:false,renderer:
		            	function(value){
		            	if(value.length > 8){
		            		return value.substring(0,8);
		            	}
	            		return value;
	            	}
	            },
		            {header:'操作人编号',dataIndex:'operNo',width:100,sortable:true,hidden:false},
		            {header:'操作时间',dataIndex:'operTime',width:90,sortable:true,hidden:false,renderer:
		            	function(value){
	            		return value.substring(0,8);
	            	}
	            },
		            {header:'状态',dataIndex:'status',width:80,sortable:true,renderer: 
		            	function(data){//1-申请，2-初审，3-复审，4-作废，5-完成
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
		            {header:'详细信息',dataIndex:'detail',width:140,sortable:true,hidden:false}
//		            {header:'关联号',dataIndex:'connNo',width:100,sortable:true,hidden:false},
//		            {header:'备注',dataIndex:'remark',width:90,sortable:true,hidden:false},
//		            {header:'备注1',dataIndex:'note1',width:90,sortable:true,hidden:false},
//		            {header:'备注2',dataIndex:'note2',width:90,sortable:true,hidden:false}
		           ]);
		WjwBfhzbGrid.superclass.constructor.call(this,{
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

    onAddWjwClick: function(){
    	for(var i=0,len=this.store.data.length;i<len;i++){
    		var data = this.store.getAt(i).data;//data就是对应record的一个一个的对象
    		if(data.status == 5){
    			Ext.Msg.alert('系统提示',"尚有拨付记录未处理完成！");
    			return;
    		}
    	}
    	this.wjwBfWindow = new WjwBfWindow();
    	var win = this.wjwBfWindow;
    	Ext.Ajax.request({
			url:GET_ALL_ACCOUNT_URL2,
			method: 'POST',
			success: function(resp,opts){
				var result = Ext.util.JSON.decode(resp.responseText); 
		    	var winForm = win.wjwBfForm;
		    	for(var i=0;i<result.length;i++){
		    		var acc = result[i];
					winForm.add(new Ext.form.Label({
						fieldLabel:acc.unitName,
						name:acc.accCode,
						width:120
					}));
					var bda = acc.bfDrugAmt;
					// winForm.add(new Ext.form.Label({
					// 	value:acc.accCode,
					// 	name:'accCode',
					// 	hidden:true
					// }));
					winForm.add(new Ext.form.NumberField({
						allowBlank : false, 
						fieldLabel:'药品金额',
						name:acc.accCode+'bfDrugAmt_'+acc.unitNo,
						maxValue:bda,
						value:acc.bfDrugAmt
					}));
					winForm.add(new Ext.form.NumberField({
						allowBlank : false, 
						fieldLabel:'医疗金额',
						name:acc.accCode+'bfMedcAmt_'+acc.unitNo,
						maxValue:acc.bfMedcAmt,
						value:acc.bfMedcAmt
					}));
				}
				win.show();
			},
			failure: function(action){
				Ext.Msg.alert('系统提示',action);
			}
		});
 
    },

	onAddJlyClick: function(){
		for(var i=0,len=this.store.data.length;i<len;i++){
			var data = this.store.getAt(i).data;//data就是对应record的一个一个的对象
			if(data.status == 5){
				Ext.Msg.alert('系统提示',"尚有拨付记录未处理完成！");
				return;
			}
		}
		this.wjwBfWindow = new WjwBfWindow();
		var win = this.wjwBfWindow;
		Ext.Ajax.request({
			url:GET_ALL_ACCOUNT_URL3,
			method: 'POST',
			success: function(resp,opts){
				var result = Ext.util.JSON.decode(resp.responseText);
				var winForm = win.wjwBfForm;
				for(var i=0;i<result.length;i++){
					var acc = result[i];
					winForm.add(new Ext.form.Label({
						fieldLabel:acc.unitName,
						width:120
					}));
					// winForm.add(new Ext.form.NumberField({
					// 	allowBlank : false,
					// 	hidden:true
					// }));
					var bda = acc.bfDrugAmt;
					winForm.add(new Ext.form.NumberField({
						allowBlank : false,
						fieldLabel:'药品金额',
						name:acc.accCode+'bfDrugAmt_'+acc.unitNo,
						maxValue:bda,
						value:acc.bfDrugAmt
					}));
					winForm.add(new Ext.form.NumberField({
						allowBlank : false,
						fieldLabel:'医疗金额',
						name:acc.accCode+'bfMedcAmt_'+acc.unitNo,
						maxValue:acc.bfMedcAmt,
						value:acc.bfMedcAmt
					}));
				}
				win.show();
			},
			failure: function(action){
				Ext.Msg.alert('系统提示',action);
			}
		});

	},
    
    onPrintClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				if(vrecord.data.status!=5){
   					Ext.Msg.alert('系统提示','非未记账状态，不能打印该笔记录！');
   					return;
   				}
   				window.open("/WjwBfhzb/toAppropriatePrint?id="+vrecord.data.id);
   			}else{
   				Ext.Msg.alert('系统提示','不能同时打印多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
    
    onDetailClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				window.open("/WjwBfhzb/index2?id="+vrecord.data.id);
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
    		valueStr.push(records[i].get('id'));
    		if(records[i].get('status') == 6){
    			Ext.Msg.alert('记账完成的不能作废');
    			return;
    		}
    	}
		ids['ids'] = valueStr;
		
    	if(records.length>0){
    		Ext.Msg.confirm('系统提示：',"确定作废这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_ZF,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','作废成功！');
    						wjwBfhzbGrid.vbbar.doLoad(wjwBfhzbGrid.vbbar.cursor);
    					},
    					failure: function(form,action){
							Ext.MessageBox.alert("系统提示：","操作有误");
						}
    				});
    			}
    		});
    	}else{
    		Ext.Msg.alert('系统提示','请选择一条记录！');
    	}
    }
});
/****************拨付测试   拨付设置**********************/
WjwBfForm = Ext.extend(Ext.ux.Form,{
	constructor: function(){

		WjwBfForm.superclass.constructor.call(this, {
	            anchor: '100%',
	            //autoHeight:true,
	           // height:600,
	          //  autoScroll:true,
	          //  labelWidth: 90,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'tableform',
	            layoutConfig: {columns: 3},
	            items:[
					
	            ],
	            buttonAlign :'center',
	            buttons: [
	               {text: '确定', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
	               {text: '清空', width: 20, iconCls:'redo',  handler: this.resetFormClick, scope: this},
	               {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
	            ]
	        });
	},
	addFormClick: function(){		
		///=====
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交数据...',
				url: ENTITY_URL_BF,
				method: 'POST',
				success: function(form,action){
					Ext.Msg.alert("系统提示：","添加成功！");
					wjwBfhzbGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwBfhzbGrid.wjwBfWindow.hide();
					//wjwBfhzbGrid.wjwBfWindow.close();
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwBfhzbGrid.wjwBfhzbUpdateWindow)
    	wjwBfhzbGrid.wjwBfhzbUpdateWindow.wjwBfhzb.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }
	
});

/**************WjwBfWindow  拨付window*********************/
WjwBfWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwBfForm = new WjwBfForm();
		WjwBfWindow.superclass.constructor.call(this,{
			title: '设置拨付金额',
			 width: 700,
			 anchor: '100%',
			 autoScroll:true,
			//autoHeight:true,
			 height:400,
			 resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwBfForm]
		});
	}
});
/****************拨付测试**********************/
function ontest(result){
	accounts = new Array(result.length);
	for(var i=0;i<result.length;i++){
		accounts[i] = result[i];
	}
	//accounts = result;
	Ext.Msg.alert('数组创建成功，长度为',accounts.length);
}

/************************************************************/
document.onAClick = function(value){
	var id = wjwBfhzbGrid.getSelectionModel().getSelected().data.id;
	var time = new Date();
	parent.Home.AddTab(time+id,"查看拨付明细", '/WjwBfhzb/toAppropriateDetail?id='+id);
	
};

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwBfhzbGrid = new WjwBfhzbGrid(Ext.getBody().getViewSize().height);
    wjwBfhzbGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwBfhzbGrid   
		]
	});
});

