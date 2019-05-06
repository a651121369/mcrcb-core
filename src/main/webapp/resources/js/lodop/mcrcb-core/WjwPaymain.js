var ENTITY_URL_LIST = "/WjwPaymain/list";
var URL_GET_USERS = "/WjwPaymain/getUsers";
var ENTITY_URL_UPDATE = "/WjwPaymain/update";
var PAGESIZE=50;
/*
 * WJW_PAYMAIN 
 * @author chenyong
 * @date 2015-11-04
 */

var relafunctypeDictStore = new Ext.data.Store({
	proxy: new Ext.data.HttpProxy({url:URL_GET_USERS, method: 'POST'}),
	reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'userId'},{name:'userName'}]))
});

WjwPaymain = Ext.extend(Ext.ux.Form,{
	constructor: function(){

		this.id_ = this.createHidden('ID','id','90%');
		this.unitName = this.createTextField('机构名称','unitName','95%','',null,60,'长度超过不能60');
		this.unitNo = this.createTextField('机构号','unitNo','95%','',null,30,'长度超过不能30');
//		this.payType = this.createTextField('支付类型','payType','95%','',null,60,'长度超过不能60');
		this.sqTime = this.createTextField('申请时间','sqTime','95%','',null,32,'长度超过不能32');
		this.status = this.createTextField('状态','status','95%');
//		this.dspUserno = this.createTextField('待审批人编号','dspUserno','95%','',null,20,'长度超过不能20');
//		this.csUserno = this.createTextField('初审人编号','csUserno','95%','',null,20,'长度超过不能20');
//		this.fsUserno = this.createTextField('复审人编号','fsUserno','95%','',null,20,'长度超过不能20');
		this.csTime = this.createTextField('初审时间','csTime','95%','',null,30,'长度超过不能30');
		this.fsTime = this.createTextField('复审时间','fsTime','95%','',null,30,'长度超过不能30');
//		this.connNo = this.createTextField('关联号','connNo','95%','',null,32,'长度超过不能32');
	//	this.dspUsername = this.createTextField('待审批人','dspUsername','95%','',null,30,'长度超过不能30');
		this.dspUsername = new Ext.form.ComboBox({
			valueField : "userId",
			displayField : "userName",
			mode : 'local',
//			mode : 'remote',
			forceSelection : true,
			blankText : '请选择...',
			emptyText : '请选择...',
			hiddenName:"dspUserno",
//			lastquery: '',
			editable : false,
			frame : true,
			layout : 'form',
			triggerAction : 'all',
			allowBlank : false,
			fieldLabel : '<font color="red">*</font>待审批人',
			name : 'dspUserno',
			anchor : '95%',
//			store : datastore,
			store : relafunctypeDictStore,
	        listeners: {
	            // delete the previous query in the beforequery event or set
	            // combo.lastquery = null (this will reload the store the next time it expands)
	            beforequery: function(qe){
	                delete qe.combo.lastquery;
	                
	            }
	        }

			
		});
//		this.dspUsername = 
//			this.createSearchCombo('<font color="red">*</font>待审批人','userId','userName','userName','95%',URL_GET_DSP_USERS);

		this.csUsername = this.createTextField('初审人','csUsername','95%','',null,30,'长度超过不能30');
		this.fsUsername = this.createTextField('复审人','fsUsername','95%','',null,30,'长度超过不能30');
//		this.sqUserno = this.createTextField('申请人编号','sqUserno','95%','',null,20,'长度超过不能20');
		this.sqUsername = this.createTextField('申请人','sqUsername','95%','',null,30,'长度超过不能30');
//		this.remark = this.createTextField('备注','remark','95%','',null,60,'长度超过不能60');

		
		this.unitName.allowBlank = true;
		this.unitNo.allowBlank = true;
//		this.payType.allowBlank = true;
		this.sqTime.allowBlank = true;
		this.status.allowBlank = true;
//		this.dspUserno.allowBlank = true;
//		this.csUserno.allowBlank = true;
//		this.fsUserno.allowBlank = true;
		this.csTime.allowBlank = true;
		this.fsTime.allowBlank = true;
//		this.connNo.allowBlank = true;
//		this.dspUsername.allowBlank = true;
		this.csUsername.allowBlank = true;
		this.fsUsername.allowBlank = true;
//		this.sqUserno.allowBlank = true;
		this.sqUsername.allowBlank = true;
//		this.remark.allowBlank = true;

        
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
	                   
	                this.unitNo,
					this.unitName,					
//					this.payType,
					this.status,
//					this.dspUserno,
//					this.csUserno,
//					this.fsUserno,
					this.csTime,
					this.fsTime,
//					this.connNo,					
					this.csUsername,
					this.fsUsername,
//					this.sqUserno,
					this.sqUsername,
//					this.remark
					this.dspUsername,
					this.sqTime,
					this.id_
					

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
					wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwPaymainGrid.wjwPaymainWindow.hide();
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：","添加失败！");
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
					wjwPaymainGrid.wjwPaymainUpdateWindow.hide();
					wjwPaymainGrid.vbbar.doLoad(wjwPaymainGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：","修改失败");
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwPaymainGrid.wjwPaymainUpdateWindow)
    	wjwPaymainGrid.wjwPaymainUpdateWindow.wjwPaymain.getForm().reset();
        this.ownerCt.hide();
//    	this.ownerCt.close();
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
    	this.wjwPaymain.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwPaymainUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwPaymain],
            
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
//			       '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:100,sortable:true,hidden:true},
		          
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:100,sortable:true,hidden:false},
		            {header:'支付类型',dataIndex:'payType',width:100,sortable:true,hidden:true},
		            {header:'状态',dataIndex:'status',width:100,sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
		            		if(data ==1){
		            			return '待初审';
		            		}else if(data ==2){
		            			return '待复审';
		            		}else{
		            			return data;
		            		}
		            		
		            	}},
		            {header:'待审批人编号',dataIndex:'dspUserno',width:100,sortable:true,hidden:false},
		            {header:'待审批人',dataIndex:'dspUsername',width:100,sortable:true,hidden:false},
		            {header:'申请人编号',dataIndex:'sqUserno',width:100,sortable:true,hidden:false},
		            {header:'申请人',dataIndex:'sqUsername',width:100,sortable:true,hidden:false},
		            {header:'申请时间',dataIndex:'sqTime',width:120,sortable:true,hidden:false},
		            {header:'初审人编号',dataIndex:'csUserno',width:100,sortable:true,hidden:false},
		            {header:'初审人',dataIndex:'csUsername',width:100,sortable:true,hidden:false},
		            {header:'初审时间',dataIndex:'csTime',width:120,sortable:true,hidden:false},
		            {header:'复审人编号',dataIndex:'fsUserno',width:100,sortable:true,hidden:false},
		            {header:'复审人',dataIndex:'fsUsername',width:100,sortable:true,hidden:false},
		            {header:'复审时间',dataIndex:'fsTime',width:120,sortable:true,hidden:false},
		            {header:'关联号',dataIndex:'connNo',width:100,sortable:true,hidden:true},  
		            {header:'备注',dataIndex:'remark',width:100,sortable:true,hidden:true}
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
//    onAddClick: function(){
//    	if(!this.wjwPaymainWindow)
//    		this.wjwPaymainWindow = new WjwPaymainWindow();
//    	var win = this.wjwPaymainWindow;
//		win.show();
//		win.wjwPaymain.getForm().reset();
//    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				

   				var unitId = vrecord.data.unitNo;
//				URL_GET_DSP_USERS = "/WjwPaymain/getUsers?status="+vrecord.data.status+"&unitId="+unitId;
			
   				if(!this.wjwPaymainUpdateWindow)
					this.wjwPaymainUpdateWindow = new WjwPaymainUpdateWindow();
   				
   		    	var win = this.wjwPaymainUpdateWindow;
   		    	
				var winForm = win.wjwPaymain;
				
				relafunctypeDictStore.load({params: {status:vrecord.data.status,unitId:unitId}});

   		    	winForm.id_.setValue(vrecord.data['id']);
   		    	winForm.id_.setReadOnly(true);
   		    	winForm.dspUsername.setValue('');
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.unitName.setDisabled(true);
				winForm.unitNo.setValue(vrecord.data.unitNo);
				winForm.unitNo.setDisabled(true);
//				winForm.payType.setValue(vrecord.data.payType);
//				winForm.payType.readOnly = true;

				
				
				winForm.sqTime.setValue(vrecord.data.sqTime);
				winForm.sqTime.setDisabled(true);
				winForm.status.setValue(vrecord.data.status);
				winForm.status.setDisabled(true);
//				winForm.csUserno.setValue(vrecord.data.csUserno);
//				winForm.csUserno.setDisabled(true);
				winForm.csTime.setValue(vrecord.data.csTime);
				winForm.csTime.setDisabled(true);
				winForm.fsTime.setValue(vrecord.data.fsTime);
				winForm.fsTime.setDisabled(true);
//				winForm.connNo.setValue(vrecord.data.connNo);
//				winForm.connNo.readOnly = true;
				winForm.csUsername.setValue(vrecord.data.csUsername);
				winForm.csUsername.setDisabled(true);
//				winForm.fsUserno.setValue(vrecord.data.fsUserno);
//				winForm.fsUserno.setDisabled(true);
				winForm.fsUsername.setValue(vrecord.data.fsUsername);
				winForm.fsUsername.setDisabled(true);
//				winForm.sqUserno.setValue(vrecord.data.sqUserno);
//				winForm.sqUserno.setDisabled(true);
				winForm.sqUsername.setValue(vrecord.data.sqUsername);
				winForm.sqUsername.setDisabled(true);
//				winForm.remark.setValue(vrecord.data.remark);
//				winForm.remark.readOnly = true;
				win.show();
   		    	
   			}else{
   				Ext.Msg.alert('系统提示','不能修改多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    }
//    onDeleteClick: function(){
//    	var records = this.getSelectionModel().getSelections();
//    	var ids = {};
//		var valueStr = [];
//    	for(var i=0;i<records.length;i++){
//    		valueStr.push(records[i].get('id'));
//    	}
//		ids['ids'] = valueStr;
//		
//    	if(records.length>0){
//    		Ext.Msg.confirm('系统提示：',"确定删除这"+records.length+"条信息吗？",function(btn){
//    			if(btn == 'yes'){
//    				Ext.Ajax.request({
//    					url: ENTITY_URL_DELETE,
//    					method: 'POST',
//    					params: ids,
//    					success: function(form,action){
//    						Ext.Msg.alert('系统提示','删除成功！');
//    						wjwPaymainGrid.vbbar.doLoad(wjwPaymainGrid.vbbar.cursor);
//    					},
//    					failure: function(form,action){
//							Ext.MessageBox.alert("系统提示：",action.result.message);
//						}
//    				});
//    			}
//    		});
//    	}else{
//    		Ext.Msg.alert('系统提示','请选择一条记录！');
//    	}
//    }
});

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

