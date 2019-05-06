var ENTITY_URL_LIST = "/WjwChecker/pager";
var ENTITY_URL_SAVE = "/WjwChecker/save";
var ENTITY_URL_UPDATE = "/WjwChecker/update";
var ENTITY_URL_DELETE = "/WjwChecker/delete";
var PAGESIZE=50;

var store = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : '/WjwChecker/getCsUser',
		method : 'POST'
	}),
	reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'userCode'}, {name:'userName'}]))

	//{},new Ext.data.Record.create([{name:'ZBDH'},{name:'ZBMC'}]
	//reader: new Ext.data.JsonReader({},[ {name:'userCode'}, {name:'userName'} ])
});
store.load();

var store2 = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : '/AccountManager/getSubOrgInfo',
		method : 'POST'
	}),
	reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'orgNo'}, {name:'orgName'}]))

	//{},new Ext.data.Record.create([{name:'ZBDH'},{name:'ZBMC'}]
	//reader: new Ext.data.JsonReader({},[ {name:'userCode'}, {name:'userName'} ])
});
store2.load();
/*
 * WJW_CHECKER 
 * @author chenyong
 * @date 2015-11-04
 */
WjwChecker = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.id_ = new Ext.form.NumberField({
            fieldLabel: '<font color="red">*</font>ID',
            name: 'id',
            allowBlank: true,
            allowNegative :true,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		//this.userId = this.createTextField('USER_ID','userId','95%','',null,20,'长度超过不能20');
		//this.unitId = this.createTextField('UNIT_ID','unitId','95%','',null,20,'长度超过不能20');
		this.userId = this.createSearchCombo('<font color="red">*</font>用户编号：','userCode','userName','userId','95%','/WjwChecker/getCsUser');

		this.unitId = this.createSearchCombo('<font color="red">*</font>机构号：','orgNo','orgName','unitId','95%','/AccountManager/getSubOrgInfo');
		
		this.note1 = this.createTextField('备注1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('备注2','note2','95%','',null,100,'长度超过不能100');
		
		this.userId.allowBlank = false;
		this.unitId.allowBlank = false;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
        WjwChecker.superclass.constructor.call(this, {
	            anchor: '100%',
	            autoHeight:true,
	            labelWidth: 90,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'tableform',
	            layoutConfig: {columns: 2},
	            items:[
					
					this.userId,
					this.unitId,
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
					wjwCheckerGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwCheckerGrid.wjwCheckerWindow.hide();
					
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
					wjwCheckerGrid.wjwCheckerUpdateWindow.hide();
					wjwCheckerGrid.vbbar.doLoad(wjwCheckerGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwCheckerGrid.wjwCheckerUpdateWindow)
    	wjwCheckerGrid.wjwCheckerUpdateWindow.wjwChecker.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwCheckerGrid.onModifyClick();
    }
	
});

/**************WjwCheckerWindow*********************/
WjwCheckerWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwChecker = new WjwChecker();
		WjwCheckerWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwChecker]
		});
	}
});

/********************WjwCheckerUpdateWindow组件*************************/
WjwCheckerUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwChecker = new WjwChecker();
    	this.wjwChecker.buttons[0].hide();   //隐藏添加按钮
    	this.wjwChecker.buttons[1].show();   //显示修改按钮
    	this.wjwChecker.buttons[2].show();   //显示重置按钮
    	this.wjwChecker.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwCheckerUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwChecker]
        });
    }
});

/****************WjwCheckerGrid***********************/
WjwCheckerGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'userId'},
		            {name:'userName'},
		            {name:'unitName'},
		            {name:'unitId'},
		            {name:'note1'},
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       //'-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this},
					//'-',{xtype:'label',text:'ID'},{xtype:'textfield',id:'Q_id_N_EQ'},
					'-',{xtype:'label',text:'用户名称'},new Ext.form.ComboBox({
						valueField : "userCode",
						displayField : "userName",
						mode : 'remote',
						resizable:false,
						listWidth : 260,
						forceSelection : true,
						blankText : '请选择...',
						emptyText : '请选择...',
						lastQuery: '',
						id:'userCode',
						editable : false,
						submitValue : true,
						triggerAction : 'all',
						allowBlank : true,
						anchor : '95%',
						//pageSize:100,
						store : store,
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
					//{xtype:'textfield',id:'userId'},
					'-',{xtype:'label',text:'机构名称'}, new Ext.form.ComboBox({
						valueField : "orgNo",
						displayField : "orgName",
						mode : 'remote',
						typeAhead: false,
						isFormField: true,
						resizable:false,
						//listWidth : 260,
						forceSelection : true,
						blankText : '请选择...',
						emptyText : '请选择...',
						//lastQuery: '',
						id:'orgNo',
						editable : true,
						//submitValue : true,
						triggerAction : 'all',
						allowBlank : true,
						anchor : '95%',
						//pageSize:0,
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
					//{xtype:'textfield',id:'unitId'},
					//'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			/*wjwCheckerGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});*/
			       			var userId = Ext.getCmp('userCode').getValue();
					    	var unitId = Ext.getCmp('orgNo').getValue();
					    	wjwCheckerGrid.store.baseParams= {
		    	   		    		userId:userId,unitId:unitId 
		    	   			};
	    	   				//wjwCheckerGrid.store.baseParams= params;
	    	   				wjwCheckerGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwCheckerGrid.vtbar.items.each(function(item,index,length){   
							if((""+item.getXType()).indexOf("field") != -1) {
								item.setValue('');
							}
							Ext.getCmp('userCode').setValue('');
							Ext.getCmp('orgNo').setValue('');
						  });  
    	   			}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'编号',dataIndex:'id',width:100,sortable:true,hidden:false},
		            {header:'用户编号',dataIndex:'userId',width:100,sortable:true,hidden:false},
		            {header:'用户名称',dataIndex:'userName',width:130,sortable:true,hidden:false},
		            {header:'机构编号',dataIndex:'unitId',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:130,sortable:true,hidden:false},
		            {header:'备注1',dataIndex:'note1',width:130,sortable:true,hidden:false},
		            {header:'备注2',dataIndex:'note2',width:130,sortable:true,hidden:false}
		           ]);
		WjwCheckerGrid.superclass.constructor.call(this,{
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
    	if(!this.wjwCheckerWindow)
    		this.wjwCheckerWindow = new WjwCheckerWindow();
    	var win = this.wjwCheckerWindow;
		win.show();
		var winForm = win.wjwChecker;
		winForm.id_.hide();
		win.wjwChecker.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwCheckerUpdateWindow)
					this.wjwCheckerUpdateWindow = new WjwCheckerUpdateWindow();
   				
   		    	var win = this.wjwCheckerUpdateWindow;
				var winForm = win.wjwChecker;
				
				win.show();
							
   		    	winForm.id_.setValue(vrecord.data['id']);
				winForm.userId.setValue(vrecord.data.userId);
				winForm.unitId.setValue(vrecord.data.unitId);
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
    						wjwCheckerGrid.vbbar.doLoad(wjwCheckerGrid.vbbar.cursor);
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
    
    wjwCheckerGrid = new WjwCheckerGrid(Ext.getBody().getViewSize().height);
    wjwCheckerGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwCheckerGrid   
		]
	});
});

