var ENTITY_URL_LIST = "/WjwPayeracct/pager";
var ENTITY_URL_SAVE = "/WjwPayeracct/save";
var ENTITY_URL_UPDATE = "/WjwPayeracct/update";
var ENTITY_URL_DELETE = "/WjwPayeracct/delete";
var PAGESIZE=50;
/*
 * WJW_PAYERACCT 
 * @author liuyong
 * @date 2015-11-16
 */
WjwPayeracct = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.id_ = this.createHidden('ID','id','90%');
		this.acctNo = this.createTextField('收款人账号','acctNo','95%','',null,32,'长度超过不能32');
		this.acctName = this.createTextField('收款人姓名','acctName','95%','',null,200,'长度超过不能200');
		this.acctBank = this.createTextField('收款人开户行','acctBank','95%','',null,200,'长度超过不能200');
		this.note1 = this.createTextField('备注1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('备注2','note2','95%','',null,100,'长度超过不能100');
		this.acctNo.allowBlank = false;
		this.acctName.allowBlank = false;
		this.acctBank.allowBlank = false;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
        WjwPayeracct.superclass.constructor.call(this, {
	            anchor: '100%',
	            autoHeight:true,
	            labelWidth: 90,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'tableform',
	            layoutConfig: {columns: 2},
	            items:[
					this.acctNo,
					this.acctName,
					this.acctBank,
					this.note1,
					this.note2,
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
					wjwPayeracctGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwPayeracctGrid.wjwPayeracctWindow.hide();
					
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
					wjwPayeracctGrid.wjwPayeracctUpdateWindow.hide();
					wjwPayeracctGrid.vbbar.doLoad(wjwPayeracctGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwPayeracctGrid.wjwPayeracctUpdateWindow)
    	wjwPayeracctGrid.wjwPayeracctUpdateWindow.wjwPayeracct.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwPayeracctGrid.onModifyClick();
    }
	
});

/**************WjwPayeracctWindow*********************/
WjwPayeracctWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwPayeracct = new WjwPayeracct();
		WjwPayeracctWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwPayeracct]
		});
	}
});

/********************WjwPayeracctUpdateWindow组件*************************/
WjwPayeracctUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwPayeracct = new WjwPayeracct();
    	this.wjwPayeracct.buttons[0].hide();   //隐藏添加按钮
    	this.wjwPayeracct.buttons[1].show();   //显示修改按钮
    	this.wjwPayeracct.buttons[2].show();   //显示重置按钮
    	this.wjwPayeracct.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwPayeracctUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwPayeracct]
        });
    }
});

/****************WjwPayeracctGrid***********************/
WjwPayeracctGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
			        {name:'id'},                                                              
		            {name:'acctNo'},
		            {name:'acctName'},
		            {name:'acctBank'},
		            {name:'note1'},
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this},
					'-',{xtype:'label',text:'收款人账号'},{xtype:'textfield',id:'acctNox'},
					'-',{xtype:'label',text:'收款人姓名'},{xtype:'textfield',id:'acctNamex'},
					'-',{xtype:'label',text:'收款人开户行'},{xtype:'textfield',id:'acctBankx'},
//					'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			wjwPayeracctGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwPayeracctGrid.store.baseParams= params;
	    	   				wjwPayeracctGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwPayeracctGrid.vtbar.items.each(function(item,index,length){   
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
		            {header:'ID',dataIndex:'id',width:240,sortable:true,hidden:true},
		            {header:'收款人账号',dataIndex:'acctNo',width:240,sortable:true,hidden:false},
		            {header:'收款人姓名',dataIndex:'acctName',width:300,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'acctBank',width:300,sortable:true,hidden:false},
		            {header:'备注1',dataIndex:'note1',width:100,sortable:true,hidden:false},
		            {header:'备注2',dataIndex:'note2',width:100,sortable:true,hidden:false}
		           ]);
		WjwPayeracctGrid.superclass.constructor.call(this,{
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
    	if(!this.wjwPayeracctWindow)
    		this.wjwPayeracctWindow = new WjwPayeracctWindow();
    	var win = this.wjwPayeracctWindow;
		win.show();
		win.wjwPayeracct.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwPayeracctUpdateWindow)
					this.wjwPayeracctUpdateWindow = new WjwPayeracctUpdateWindow();
   				
   		    	var win = this.wjwPayeracctUpdateWindow;
				var winForm = win.wjwPayeracct;
				
				win.show();
				
				winForm.id_.setValue(vrecord.data['id']);
   		    	winForm.acctNo.setValue(vrecord.data['acctNo']);
				winForm.acctName.setValue(vrecord.data.acctName);
				winForm.acctBank.setValue(vrecord.data.acctBank);
				winForm.note1.setValue(vrecord.data.note1);
				winForm.note2.setValue(vrecord.data.note2);
   		    	
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
    						wjwPayeracctGrid.vbbar.doLoad(wjwPayeracctGrid.vbbar.cursor);
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
    
    wjwPayeracctGrid = new WjwPayeracctGrid(Ext.getBody().getViewSize().height);
    wjwPayeracctGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPayeracctGrid   
		]
	});
});

