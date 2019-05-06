var ENTITY_URL_LIST = "/taskList/getTaskwant";
var ENTITY_URL_SAVE = "/WjwPaymain/save";
var ENTITY_URL_UPDATE = "/taskList/update";
var ENTITY_URL_UPDATEBACK = "/taskList/updateback";
var ENTITY_URL_BATCH = "/taskList/batchUpdate";
var PAGESIZE=50;
var store2 = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : '/AccountManager/getOrgInfo',
		method : 'POST'
	}),
	reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'orgNo'}, {name:'orgName'}]))

	//{},new Ext.data.Record.create([{name:'ZBDH'},{name:'ZBMC'}]
	//reader: new Ext.data.JsonReader({},[ {name:'userCode'}, {name:'userName'} ])
});
store2.load();
/*
 * WJW_PAYMAIN 
 * @author chenyong
 * @date 2015-11-04
 */
WjwPaymain = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		
		this.unitName = this.createTextField('机构名称','unitName','95%','',null,60,'长度超过不能60');
		this.unitNo = this.createTextField('机构编号','unitNo','95%','',null,30,'长度超过不能30');
		this.payType = this.createTextField('支出类型','payType','95%','',null,60,'长度超过不能60');
		this.sqTime = this.createTextField('申请日期','sqTime','95%','',null,32,'长度超过不能32');
		this.sqUserno = this.createTextField('申请人编号','sqUserno','95%','',null,20,'长度超过不能20');
		this.sqUsername = this.createTextField('申请人姓名','sqUsername','95%','',null,30,'长度超过不能30');
		this.dspUserno = this.createSearchCombo('<font color="red">*</font>复审人：','userCode','userName','dspUserno','95%','/WjwChecker/getFsUser');
		//this.createTextField('复审人','dspUserno','95%','',null,20,'长度超过不能20');
		//this.dspUsername = this.createTextField('待审批人姓名','dspUsername','95%','',null,30,'长度超过不能30');
		this.csUserno = this.createTextField('初审人编号','csUserno','95%','',null,20,'长度超过不能20');
		this.csUsername = this.createTextField('初审人姓名','csUsername','95%','',null,30,'长度超过不能30');
		this.csTime = this.createTextField('初审人操作时间','csTime','95%','',null,30,'长度超过不能30');
		this.fsUserno = this.createTextField('复审人编号','fsUserno','95%','',null,20,'长度超过不能20');
		this.fsUsername = this.createTextField('复审人姓名','fsUsername','95%','',null,30,'长度超过不能30');
		this.fsTime = this.createTextField('复审人操作时间','fsTime','95%','',null,30,'长度超过不能30');
		this.connNo = this.createTextField('申请单编号','connNo','95%','',null,32,'长度超过不能32');
		this.note1 = this.createTextField('初审人意见','note1','95%','',null,200,'长度超过不能200');
		this.note2 = this.createTextField('复审人意见','note2','95%','',null,200,'长度超过不能200');
		this.remark = this.createTextField('<font color="red">*</font>审批意见','remark','95%','',null,200,'长度超过不能200');
		this.status = new Ext.form.NumberField({
            fieldLabel: '状态',
            name: 'status',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.id_ = new Ext.form.NumberField({
            fieldLabel: '<font color="red">*</font>编号',
            name: 'id',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		
		this.unitName.allowBlank = true;
		this.unitNo.allowBlank = true;
		this.payType.allowBlank = true;
		this.sqTime.allowBlank = true;
		this.status.allowBlank = true;
		this.dspUserno.allowBlank = true;
		this.csUserno.allowBlank = true;
		this.fsUserno.allowBlank = true;
		this.csTime.allowBlank = true;
		this.fsTime.allowBlank = true;
		this.connNo.allowBlank = true;
		//this.dspUsername.allowBlank = true;
		this.csUsername.allowBlank = true;
		this.fsUsername.allowBlank = true;
		this.sqUserno.allowBlank = true;
		this.sqUsername.allowBlank = true;
		this.remark.allowBlank = true;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
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
					
					this.unitName,
					this.unitNo,
					this.sqUserno,
					this.sqUsername,
					this.connNo,
					this.payType,
					this.sqTime,
					
					//this.dspUsername,
					this.csUserno,
					this.csUsername,
					this.csTime,
					this.note1,
					this.fsUserno,
					this.fsUsername,
					this.fsTime,
					this.note2,
					this.remark,
					this.dspUserno,
					this.status,
					this.id_
					
	            ],
	            buttonAlign :'center',
	            buttons: [
	               {text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
	               {text: '通过', width: 20,iconCls:'edit', hidden: true, handler: this.updateFormClick, scope: this},
	               {text: '驳回', width: 20,iconCls:'edit', hidden: true, handler: this.updatebackFormClick, scope: this},
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
					wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwPaymainGrid.wjwPaymainWindow.hide();
					
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
					Ext.MessageBox.alert("系统提示：","审核成功！");
					wjwPaymainGrid.wjwPaymainUpdateWindow.hide();
					wjwPaymainGrid.vbbar.doLoad(wjwPaymainGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	updatebackFormClick: function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交，请稍后...',
				url: ENTITY_URL_UPDATEBACK,
				method: 'POST',
				success: function(form,action){
					Ext.MessageBox.alert("系统提示：","驳回成功！");
					wjwPaymainGrid.wjwPaymainUpdateWindow.hide();
					wjwPaymainGrid.vbbar.doLoad(wjwPaymainGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwPaymainGrid.wjwPaymainUpdateWindow)
    	wjwPaymainGrid.wjwPaymainUpdateWindow.wjwPaymain.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwPaymainGrid.onModifyClick();
    }
	
});

/**************WjwPaymainWindow*********************/
WjwPaymainWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwPaymain = new WjwPaymain();
		WjwPaymainWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwPaymain]
		});
	}
});

/********************WjwPaymainUpdateWindow组件*************************/
WjwPaymainUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwPaymain = new WjwPaymain();
    	this.wjwPaymain.buttons[0].hide();   //隐藏添加按钮
    	this.wjwPaymain.buttons[1].show();   //显示修改按钮
    	this.wjwPaymain.buttons[2].show();   //显示重置按钮
    	this.wjwPaymain.buttons[3].hide();   //显示重置按钮
    	this.wjwPaymain.buttons[4].hide();   //隐藏清空按钮
    	
    	//WjwPaymain.unitName.setReadOnly(true);

    	
    	WjwPaymainUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwPaymain]
        });
    }
});

/****************WjwPaymainGrid***********************/
WjwPaymainGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'unitName'},
		            {name:'unitNo'},
		            {name:'payType'},
		            {name:'sqTime'},
		            {name:'status'},
		            {name:'dspUserno'},
		            {name:'csUserno'},
		            {name:'fsUserno'},
		            {name:'csTime'},
		            {name:'fsTime'},
		            {name:'connNo'},
		            {name:'dspUsername'},
		            {name:'csUsername'},
		            {name:'fsUsername'},
		            {name:'sqUserno'},
		            {name:'sqUsername'},
		            {name:'remark'},
		            {name:'note1'},
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       //'-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'单笔审批',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			       '-',{xtype:'button',text:'批量审批',iconCls:'edit',handler:this.onBatchModifyClick,scope:this}, 
			       //'-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this},
					//'-',{xtype:'label',text:'ID'},{xtype:'textfield',id:'Q_id_N_EQ'},
					//'-',{xtype:'label',text:'UNIT_NAME'},{xtype:'textfield',id:'Q_unitName_S_LK'},
			       //'-',{xtype:'label',text:'申请单编号'},{xtype:'textfield',id:'connNo'},
					'-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,value:''},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,value:''},
			       '-',{xtype:'label',text:'机构编号'},new Ext.form.ComboBox({
						valueField : "orgNo",
						displayField : "orgName",
						mode : 'remote',
						resizable:false,
						listWidth : 260,
						forceSelection : true,
						blankText : '请选择...',
						emptyText : '请选择...',
						lastQuery: '',
						id:'orgNo',
						editable : false,
						submitValue : true,
						triggerAction : 'all',
						allowBlank : true,
						anchor : '95%',
						//pageSize:100,
						store : store2,
						listeners : {  
				            'beforequery':function(e){  
				                var combo = e.combo;    
				                if(!e.forceAll){    
				                    var input = e.query;    
				                    // 检索的正则   
				                    var regExp = new RegExp(".*" + input + ".*");  
				                    // 执行检索   
				                    combo.store.filterBy(function(record,id){    
				                        // 得到每个record的项目名称值   
				                        var text = record.get(combo.displayField);    
				                        return regExp.test(text);   
				                    });  
				                    combo.expand();    
				                    return false;  
				                }  
				            }
				        }
						
					}),
					//{xtype:'textfield',id:'unitNo'},
					//'-',{xtype:'label',text:'PAY_TYPE'},{xtype:'textfield',id:'Q_payType_S_LK'},
					//'-',{xtype:'label',text:'SQ_TIME'},{xtype:'textfield',id:'Q_sqTime_S_LK'},
					//'-',{xtype:'label',text:'1-申请，2-初审，3-复审，4-作废，5-完成'},{xtype:'textfield',id:'Q_status_N_EQ'},
					//'-',{xtype:'label',text:'DSP_USERNO'},{xtype:'textfield',id:'Q_dspUserno_S_LK'},
					//'-',{xtype:'label',text:'CS_USERNO'},{xtype:'textfield',id:'Q_csUserno_S_LK'},
					//'-',{xtype:'label',text:'FS_USERNO'},{xtype:'textfield',id:'Q_fsUserno_S_LK'},
					//'-',{xtype:'label',text:'CS_TIME'},{xtype:'textfield',id:'Q_csTime_S_LK'},
					//'-',{xtype:'label',text:'FS_TIME'},{xtype:'textfield',id:'Q_fsTime_S_LK'},
					//'-',{xtype:'label',text:'CONN_NO'},{xtype:'textfield',id:'Q_connNo_S_LK'},
					//'-',{xtype:'label',text:'DSP_USERNAME'},{xtype:'textfield',id:'Q_dspUsername_S_LK'},
					//'-',{xtype:'label',text:'CS_USERNAME'},{xtype:'textfield',id:'Q_csUsername_S_LK'},
					//'-',{xtype:'label',text:'FS_USERNAME'},{xtype:'textfield',id:'Q_fsUsername_S_LK'},
					//'-',{xtype:'label',text:'SQ_USERNO'},{xtype:'textfield',id:'Q_sqUserno_S_LK'},
					//'-',{xtype:'label',text:'SQ_USERNAME'},{xtype:'textfield',id:'Q_sqUsername_S_LK'},
					//'-',{xtype:'label',text:'REMARK'},{xtype:'textfield',id:'Q_remark_S_LK'},
					//'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			/*wjwPaymainGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwPaymainGrid.store.baseParams= params;
	    	   				wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});*/
			       			var startTime = Ext.getCmp('startTime').getValue();
					    	var endTime = Ext.getCmp('endTime').getValue();
		    	   			if(startTime != null &&  startTime != ''){
		    	   				startTime = Ext.getCmp('startTime').getValue().format('Ymd');
			       			}else{
			       				startTime = "";
			       			}
		    	   			if(endTime != null &&  endTime != ''){
		    	   				endTime = Ext.getCmp('endTime').getValue().format('Ymd');
			       			}else{
			       				endTime = "";
			       			}
		    	   		    var unitNo =  Ext.getCmp('orgNo').getValue();
		    	   		    var connNo = "";
		    	   		    	//Ext.getCmp('connNo').getValue();
		    	   		    wjwPaymainGrid.store.baseParams= {
		    	   					unitNo:unitNo,connNo:connNo,startTime:startTime,endTime:endTime
		    	   			};
		    	   		    wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwPaymainGrid.vtbar.items.each(function(item,index,length){   
							if((""+item.getXType()).indexOf("field") != -1) {
								item.setValue('');
							}
							Ext.getCmp('orgNo').setValue('');
						  });  
    	   			}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		           // {header:'编号',dataIndex:'id',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:100,sortable:true,hidden:false,
			          	renderer: function(value,metadata){
			          		metadata.attr = 'ext:qtip="点击修改支付申请信息！"';
			            	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+value+'</a>';	
						}
		            },
		            {header:'机构编号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'申请人编号',dataIndex:'sqUserno',width:100,sortable:true,hidden:false},
		            {header:'申请人名称',dataIndex:'sqUsername',width:100,sortable:true,hidden:false},
		            {header:'支付类型',dataIndex:'payType',width:100,sortable:true,hidden:false},
		            {header:'申请日期',dataIndex:'sqTime',width:100,sortable:true,hidden:false},
		            {header:'状态',dataIndex:'status',width:80,sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
		            		if(data ==1){
		            			return '申请';
		            		}else if(data ==2){
		            			return '初审';
		            		}else if(data ==3){
		            			return '复审';
		            		}else if(data ==4){
		            			return '作废';
		            		}else if(data ==5){
		            			return '未记账';
		            		}else if(data ==6){
		            			return '记账完成';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'待审批人编号',dataIndex:'dspUserno',width:100,sortable:true,hidden:false},
		            {header:'待审批人姓名',dataIndex:'dspUsername',width:100,sortable:true,hidden:false},
		            //{header:'申请单编号',dataIndex:'connNo',width:100,sortable:true,hidden:false},
		            {header:'操作',dataIndex:'connNo',width:60,sortable:true,hidden:false,
		            	 renderer: function(value){
			            		/*if(value=="0"){
			            			return '<a onclick="document.onClickckhz();" style="color:blue;cursor: pointer;">打印</a>';
			            		}else{
			            			return '<a onclick="document.onClickckhz();" style="color:blue;cursor: pointer;">重打印</a>';

			            		}*/
		            			return '<a onclick="document.onClickckhz();" style="color:blue;cursor: pointer;">明细</a>';
//			            		return '<a onclick="document.onClickckhz();" style="color:blue;cursor: pointer;">'+Ext.util.Format.number(value,'0,000.00')+'</a>';
							} 
		            }
		            //{header:'CS_USERNO',dataIndex:'csUserno',width:100,sortable:true,hidden:false},
		            //{header:'FS_USERNO',dataIndex:'fsUserno',width:100,sortable:true,hidden:false},
		            //{header:'CS_TIME',dataIndex:'csTime',width:100,sortable:true,hidden:false},
		            //{header:'FS_TIME',dataIndex:'fsTime',width:100,sortable:true,hidden:false},
		            
		            
		            //{header:'CS_USERNAME',dataIndex:'csUsername',width:100,sortable:true,hidden:false},
		            //{header:'FS_USERNAME',dataIndex:'fsUsername',width:100,sortable:true,hidden:false},
		           
		            //{header:'REMARK',dataIndex:'remark',width:100,sortable:true,hidden:false},
		           // {header:'NOTE1',dataIndex:'note1',width:100,sortable:true,hidden:false},
		           // {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:false}
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
    onAddClick: function(){
    	if(!this.wjwPaymainWindow)
    		this.wjwPaymainWindow = new WjwPaymainWindow();
    	var win = this.wjwPaymainWindow;
		win.show();
		win.wjwPaymain.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwPaymainUpdateWindow)
					this.wjwPaymainUpdateWindow = new WjwPaymainUpdateWindow();
   				
   		    	var win = this.wjwPaymainUpdateWindow;
				var winForm = win.wjwPaymain;
				
				win.show();
							
   		    	
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.unitName.setReadOnly(true);
				winForm.unitNo.setValue(vrecord.data.unitNo);
				winForm.unitNo.setReadOnly(true);
				winForm.payType.setValue(vrecord.data.payType);
				winForm.payType.setReadOnly(true);
				winForm.sqTime.setValue(vrecord.data.sqTime);
				winForm.sqTime.setReadOnly(true);
				winForm.status.setValue(vrecord.data.status);
				//winForm.status.setReadOnly(true);
				winForm.status.hide();
				//winForm.dspUserno.setValue(vrecord.data.dspUserno);
                if(vrecord.data.status == 1){
                	winForm.dspUserno.setValue("");
				}else if(vrecord.data.status == 2){
					winForm.dspUserno.setValue("");
					winForm.dspUserno.hide();
				}
				//winForm.dspUserno.setReadOnly(true);
				winForm.csUserno.setValue(vrecord.data.csUserno);
				winForm.csUserno.setReadOnly(true);
				winForm.fsUserno.setValue(vrecord.data.fsUserno);
				winForm.fsUserno.setReadOnly(true);
				winForm.csTime.setValue(vrecord.data.csTime);
				winForm.csTime.setReadOnly(true);
				winForm.fsTime.setValue(vrecord.data.fsTime);
				winForm.fsTime.setReadOnly(true);
				winForm.connNo.setValue(vrecord.data.connNo);
				winForm.connNo.setReadOnly(true);
				//winForm.dspUsername.setValue(vrecord.data.dspUsername);
				//winForm.dspUsername.setReadOnly(true);
				winForm.csUsername.setValue(vrecord.data.csUsername);
				winForm.csUsername.setReadOnly(true);
				winForm.fsUsername.setValue(vrecord.data.fsUsername);
				winForm.fsUsername.setReadOnly(true);
				winForm.sqUserno.setValue(vrecord.data.sqUserno);
				winForm.sqUserno.setReadOnly(true);
				winForm.sqUsername.setValue(vrecord.data.sqUsername);
				winForm.sqUsername.setReadOnly(true);
				winForm.remark.setValue("");
				winForm.note1.setValue(vrecord.data.note1);
				winForm.note1.setReadOnly(true);
				winForm.note2.setValue(vrecord.data.note2);
				winForm.note2.setReadOnly(true);
				winForm.id_.setValue(vrecord.data['id']);
   		    	winForm.id_.hide();
   			}else{
   				Ext.Msg.alert('系统提示','不能修改多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
    onBatchModifyClick: function(){
    	var records = this.getSelectionModel().getSelections();
    	var ids = {};
		var valueStr = [];
    	for(var i=0;i<records.length;i++){
    		valueStr.push(records[i].get('id'));
    	}
		ids['ids'] = valueStr;
		
    	if(records.length>0){
    		Ext.Msg.confirm('系统提示：',"确定审批这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_BATCH,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','批量审批成功！');
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

document.onClickckhz = function(){
	var connNo = wjwPaymainGrid.getSelectionModel().getSelections()[0].data.connNo;
	//alert(connNo);
	var url = '/taskList/payMxDetail?connNo='+connNo;
	window.open(url, "newWindow");
};


/************************************************************/
document.onAClick = function(value){
	var connNo = wjwPaymainGrid.getSelectionModel().getSelections()[0].data.connNo;
	var time = new Date();
	parent.Home.AddTab(connNo+time,"修改支付明细", '/WjwPaydetail/toModifyPaydetail?connNo='+connNo);
	
};

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwPaymainGrid = new WjwPaymainGrid(Ext.getBody().getViewSize().height);
    wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaymainGrid   
		]
	});
});

