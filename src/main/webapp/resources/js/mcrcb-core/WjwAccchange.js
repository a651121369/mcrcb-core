var ENTITY_URL_LIST = "//WjwAccchange/pager";
var ENTITY_URL_SAVE = "//WjwAccchange/save";
var ENTITY_URL_UPDATE = "//WjwAccchange/update";
var ENTITY_URL_DELETE = "//WjwAccchange/delete";
var PAGESIZE=50;
/*
 * WJW_ACCCHANGE 
 * @author chenyong
 * @date 2015-11-04
 */
WjwAccchange = Ext.extend(Ext.ux.Form,{
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
		this.unitNo = this.createTextField('UNIT_NO','unitNo','95%','',null,20,'长度超过不能20');
		this.unitName = this.createTextField('UNIT_NAME','unitName','95%','',null,60,'长度超过不能60');
		this.accNo = this.createTextField('ACC_NO','accNo','95%','',null,32,'长度超过不能32');
		this.accName = this.createTextField('ACC_NAME','accName','95%','',null,60,'长度超过不能60');
		this.dfAccno = this.createTextField('DF_ACCNO','dfAccno','95%','',null,32,'长度超过不能32');
		this.dfAccname = this.createTextField('DF_ACCNAME','dfAccname','95%','',null,60,'长度超过不能60');
		this.amount = this.createTextField('AMOUNT','amount','95%','',null,21,'长度超过不能21');
		this.drugAmt = this.createTextField('DRUG_AMT','drugAmt','95%','',null,21,'长度超过不能21');
		this.medcAmt = this.createTextField('MEDC_AMT','medcAmt','95%','',null,21,'长度超过不能21');
		this.tranAmt = this.createTextField('TRAN_AMT','tranAmt','95%','',null,21,'长度超过不能21');
		this.tranTime = this.createTextField('TRAN_TIME','tranTime','95%','',null,30,'长度超过不能30');
		this.inOrOut = new Ext.form.NumberField({
            fieldLabel: '1-收入，2-支出',
            name: 'inOrOut',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.otherAmt = this.createTextField('OTHER_AMT','otherAmt','95%','',null,21,'长度超过不能21');
		this.note1 = this.createTextField('NOTE1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('NOTE2','note2','95%','',null,100,'长度超过不能100');
		
		this.unitNo.allowBlank = true;
		this.unitName.allowBlank = true;
		this.accNo.allowBlank = true;
		this.accName.allowBlank = true;
		this.dfAccno.allowBlank = true;
		this.dfAccname.allowBlank = true;
		this.amount.allowBlank = true;
		this.drugAmt.allowBlank = true;
		this.medcAmt.allowBlank = true;
		this.tranAmt.allowBlank = true;
		this.tranTime.allowBlank = true;
		this.inOrOut.allowBlank = true;
		this.otherAmt.allowBlank = true;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
        WjwAccchange.superclass.constructor.call(this, {
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
					this.accNo,
					this.accName,
					this.dfAccno,
					this.dfAccname,
					this.amount,
					this.drugAmt,
					this.medcAmt,
					this.tranAmt,
					this.tranTime,
					this.inOrOut,
					this.otherAmt,
					this.note1,
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
					wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwAccchangeGrid.wjwAccchangeWindow.hide();
					
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
					wjwAccchangeGrid.wjwAccchangeUpdateWindow.hide();
					wjwAccchangeGrid.vbbar.doLoad(wjwAccchangeGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwAccchangeGrid.wjwAccchangeUpdateWindow)
    	wjwAccchangeGrid.wjwAccchangeUpdateWindow.wjwAccchange.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwAccchangeGrid.onModifyClick();
    }
	
});

/**************WjwAccchangeWindow*********************/
WjwAccchangeWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwAccchange = new WjwAccchange();
		WjwAccchangeWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwAccchange]
		});
	}
});

/********************WjwAccchangeUpdateWindow组件*************************/
WjwAccchangeUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwAccchange = new WjwAccchange();
    	this.wjwAccchange.buttons[0].hide();   //隐藏添加按钮
    	this.wjwAccchange.buttons[1].show();   //显示修改按钮
    	this.wjwAccchange.buttons[2].show();   //显示重置按钮
    	this.wjwAccchange.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwAccchangeUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwAccchange]
        });
    }
});

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
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this},
					'-',{xtype:'label',text:'ID'},{xtype:'textfield',id:'Q_id_N_EQ'},
					'-',{xtype:'label',text:'UNIT_NO'},{xtype:'textfield',id:'Q_unitNo_S_LK'},
					'-',{xtype:'label',text:'UNIT_NAME'},{xtype:'textfield',id:'Q_unitName_S_LK'},
					'-',{xtype:'label',text:'ACC_NO'},{xtype:'textfield',id:'Q_accNo_S_LK'},
					//'-',{xtype:'label',text:'ACC_NAME'},{xtype:'textfield',id:'Q_accName_S_LK'},
					//'-',{xtype:'label',text:'DF_ACCNO'},{xtype:'textfield',id:'Q_dfAccno_S_LK'},
					//'-',{xtype:'label',text:'DF_ACCNAME'},{xtype:'textfield',id:'Q_dfAccname_S_LK'},
					//'-',{xtype:'label',text:'AMOUNT'},{xtype:'textfield',id:'Q_amount_S_EQ'},
					//'-',{xtype:'label',text:'DRUG_AMT'},{xtype:'textfield',id:'Q_drugAmt_S_EQ'},
					//'-',{xtype:'label',text:'MEDC_AMT'},{xtype:'textfield',id:'Q_medcAmt_S_EQ'},
					//'-',{xtype:'label',text:'TRAN_AMT'},{xtype:'textfield',id:'Q_tranAmt_S_EQ'},
					//'-',{xtype:'label',text:'TRAN_TIME'},{xtype:'textfield',id:'Q_tranTime_S_LK'},
					//'-',{xtype:'label',text:'1-收入，2-支出'},{xtype:'textfield',id:'Q_inOrOut_N_EQ'},
					//'-',{xtype:'label',text:'OTHER_AMT'},{xtype:'textfield',id:'Q_otherAmt_S_EQ'},
					//'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			wjwAccchangeGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwAccchangeGrid.store.baseParams= params;
	    	   				wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwAccchangeGrid.vtbar.items.each(function(item,index,length){   
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
		            {header:'ID',dataIndex:'id',width:100,sortable:true,hidden:false},
		            {header:'UNIT_NO',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'UNIT_NAME',dataIndex:'unitName',width:100,sortable:true,hidden:false},
		            {header:'ACC_NO',dataIndex:'accNo',width:100,sortable:true,hidden:false},
		            {header:'ACC_NAME',dataIndex:'accName',width:100,sortable:true,hidden:false},
		            {header:'DF_ACCNO',dataIndex:'dfAccno',width:100,sortable:true,hidden:false},
		            {header:'DF_ACCNAME',dataIndex:'dfAccname',width:100,sortable:true,hidden:false},
		            {header:'AMOUNT',dataIndex:'amount',width:100,sortable:true,hidden:false},
		            {header:'DRUG_AMT',dataIndex:'drugAmt',width:100,sortable:true,hidden:false},
		            {header:'MEDC_AMT',dataIndex:'medcAmt',width:100,sortable:true,hidden:false},
		            {header:'TRAN_AMT',dataIndex:'tranAmt',width:100,sortable:true,hidden:false},
		            {header:'TRAN_TIME',dataIndex:'tranTime',width:100,sortable:true,hidden:false},
		            {header:'1-收入，2-支出',dataIndex:'inOrOut',width:100,sortable:true,hidden:false},
		            {header:'OTHER_AMT',dataIndex:'otherAmt',width:100,sortable:true,hidden:false},
		            {header:'NOTE1',dataIndex:'note1',width:100,sortable:true,hidden:false},
		            {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:false}
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
    onAddClick: function(){
    	if(!this.wjwAccchangeWindow)
    		this.wjwAccchangeWindow = new WjwAccchangeWindow();
    	var win = this.wjwAccchangeWindow;
		win.show();
		win.wjwAccchange.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwAccchangeUpdateWindow)
					this.wjwAccchangeUpdateWindow = new WjwAccchangeUpdateWindow();
   				
   		    	var win = this.wjwAccchangeUpdateWindow;
				var winForm = win.wjwAccchange;
				
				win.show();
							
   		    	winForm.id_.setValue(vrecord.data['id']);
				winForm.unitNo.setValue(vrecord.data.unitNo);
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.accNo.setValue(vrecord.data.accNo);
				winForm.accName.setValue(vrecord.data.accName);
				winForm.dfAccno.setValue(vrecord.data.dfAccno);
				winForm.dfAccname.setValue(vrecord.data.dfAccname);
				winForm.amount.setValue(vrecord.data.amount);
				winForm.drugAmt.setValue(vrecord.data.drugAmt);
				winForm.medcAmt.setValue(vrecord.data.medcAmt);
				winForm.tranAmt.setValue(vrecord.data.tranAmt);
				winForm.tranTime.setValue(vrecord.data.tranTime);
				winForm.inOrOut.setValue(vrecord.data.inOrOut);
				winForm.otherAmt.setValue(vrecord.data.otherAmt);
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

