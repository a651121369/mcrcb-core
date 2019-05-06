var ENTITY_URL_LIST = "//WjwAccount/pager";
var ENTITY_URL_SAVE = "//WjwAccount/save";
var ENTITY_URL_UPDATE = "//WjwAccount/update";
var ENTITY_URL_DELETE = "//WjwAccount/delete";
var PAGESIZE=50;
/*
 * WJW_ACCOUNT 
 * @author chenyong
 * @date 2015-11-04
 */
WjwAccount = Ext.extend(Ext.ux.Form,{
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
		this.accNo = this.createTextField('ACC_NO','accNo','95%','',null,32,'长度超过不能32');
		this.custName = this.createTextField('CUST_NAME','custName','95%','',null,60,'长度超过不能60');
		this.accType = new Ext.form.NumberField({
            fieldLabel: '1对公，2对私',
            name: 'accType',
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
		this.amount = this.createTextField('AMOUNT','amount','95%','',null,21,'长度超过不能21');
		this.accNum = this.createTextField('ACC_NUM','accNum','95%','',null,21,'长度超过不能21');
		this.accStatus = new Ext.form.NumberField({
            fieldLabel: '1-正常，2-注销',
            name: 'accStatus',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.accParent = this.createTextField('ACC_PARENT','accParent','95%','',null,32,'长度超过不能32');
		this.createTime = this.createTextField('CREATE_TIME','createTime','95%','',null,30,'长度超过不能30');
		this.createUser = this.createTextField('CREATE_USER','createUser','95%','',null,20,'长度超过不能20');
		this.updateTime = this.createTextField('UPDATE_TIME','updateTime','95%','',null,30,'长度超过不能30');
		this.updateUser = this.createTextField('UPDATE_USER','updateUser','95%','',null,20,'长度超过不能20');
		this.level = new Ext.form.NumberField({
            fieldLabel: 'LEVEL',
            name: 'level',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.rate = this.createTextField('RATE','rate','95%','',null,9,'长度超过不能9');
		this.bankUnit = this.createTextField('BANK_UNIT','bankUnit','95%','',null,20,'长度超过不能20');
		this.bankName = this.createTextField('BANK_NAME','bankName','95%','',null,60,'长度超过不能60');
		this.bankAmount = this.createTextField('BANK_AMOUNT','bankAmount','95%','',null,21,'长度超过不能21');
		this.unkCome = this.createTextField('UNK_COME','unkCome','95%','',null,21,'长度超过不能21');
		this.intCome = this.createTextField('INT_COME','intCome','95%','',null,21,'长度超过不能21');
		this.currency = this.createTextField('156-人名币','currency','95%','',null,16,'长度超过不能16');
		this.accFld = new Ext.form.NumberField({
            fieldLabel: '1-真实帐户，2-虚拟账户',
            name: 'accFld',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.accPro = new Ext.form.NumberField({
            fieldLabel: '1-活期，2-定期，3-协议',
            name: 'accPro',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.intFlag = new Ext.form.NumberField({
            fieldLabel: '1-计息，2-不计息',
            name: 'intFlag',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.intType = new Ext.form.NumberField({
            fieldLabel: '1-按月计息，2-按季度计息，3-按年计息',
            name: 'intType',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
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
		this.drugAmt = this.createTextField('DRUG_AMT','drugAmt','95%','',null,21,'长度超过不能21');
		this.medicalAmt = this.createTextField('MEDICAL_AMT','medicalAmt','95%','',null,21,'长度超过不能21');
		this.otherAmt = this.createTextField('OTHER_AMT','otherAmt','95%','',null,21,'长度超过不能21');
		this.note1 = this.createTextField('NOTE1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('NOTE2','note2','95%','',null,100,'长度超过不能100');
		
		this.accNo.allowBlank = true;
		this.custName.allowBlank = true;
		this.accType.allowBlank = true;
		this.unitNo.allowBlank = true;
		this.unitName.allowBlank = true;
		this.amount.allowBlank = true;
		this.accNum.allowBlank = true;
		this.accStatus.allowBlank = true;
		this.accParent.allowBlank = true;
		this.createTime.allowBlank = true;
		this.createUser.allowBlank = true;
		this.updateTime.allowBlank = true;
		this.updateUser.allowBlank = true;
		this.level.allowBlank = true;
		this.rate.allowBlank = true;
		this.bankUnit.allowBlank = true;
		this.bankName.allowBlank = true;
		this.bankAmount.allowBlank = true;
		this.unkCome.allowBlank = true;
		this.intCome.allowBlank = true;
		this.currency.allowBlank = true;
		this.accFld.allowBlank = true;
		this.accPro.allowBlank = true;
		this.intFlag.allowBlank = true;
		this.intType.allowBlank = true;
		this.inOrOut.allowBlank = true;
		this.drugAmt.allowBlank = true;
		this.medicalAmt.allowBlank = true;
		this.otherAmt.allowBlank = true;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
        WjwAccount.superclass.constructor.call(this, {
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
					this.accNo,
					this.custName,
					this.accType,
					this.unitNo,
					this.unitName,
					this.amount,
					this.accNum,
					this.accStatus,
					this.accParent,
					this.createTime,
					this.createUser,
					this.updateTime,
					this.updateUser,
					this.level,
					this.rate,
					this.bankUnit,
					this.bankName,
					this.bankAmount,
					this.unkCome,
					this.intCome,
					this.currency,
					this.accFld,
					this.accPro,
					this.intFlag,
					this.intType,
					this.inOrOut,
					this.drugAmt,
					this.medicalAmt,
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
					wjwAccountGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwAccountGrid.wjwAccountWindow.hide();
					
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
					wjwAccountGrid.wjwAccountUpdateWindow.hide();
					wjwAccountGrid.vbbar.doLoad(wjwAccountGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwAccountGrid.wjwAccountUpdateWindow)
    	wjwAccountGrid.wjwAccountUpdateWindow.wjwAccount.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwAccountGrid.onModifyClick();
    }
	
});

/**************WjwAccountWindow*********************/
WjwAccountWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwAccount = new WjwAccount();
		WjwAccountWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwAccount]
		});
	}
});

/********************WjwAccountUpdateWindow组件*************************/
WjwAccountUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwAccount = new WjwAccount();
    	this.wjwAccount.buttons[0].hide();   //隐藏添加按钮
    	this.wjwAccount.buttons[1].show();   //显示修改按钮
    	this.wjwAccount.buttons[2].show();   //显示重置按钮
    	this.wjwAccount.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwAccountUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwAccount]
        });
    }
});

/****************WjwAccountGrid***********************/
WjwAccountGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'accNo'},
		            {name:'custName'},
		            {name:'accType'},
		            {name:'unitNo'},
		            {name:'unitName'},
		            {name:'amount'},
		            {name:'accNum'},
		            {name:'accStatus'},
		            {name:'accParent'},
		            {name:'createTime'},
		            {name:'createUser'},
		            {name:'updateTime'},
		            {name:'updateUser'},
		            {name:'level'},
		            {name:'rate'},
		            {name:'bankUnit'},
		            {name:'bankName'},
		            {name:'bankAmount'},
		            {name:'unkCome'},
		            {name:'intCome'},
		            {name:'currency'},
		            {name:'accFld'},
		            {name:'accPro'},
		            {name:'intFlag'},
		            {name:'intType'},
		            {name:'inOrOut'},
		            {name:'drugAmt'},
		            {name:'medicalAmt'},
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
					'-',{xtype:'label',text:'ACC_NO'},{xtype:'textfield',id:'Q_accNo_S_LK'},
					'-',{xtype:'label',text:'CUST_NAME'},{xtype:'textfield',id:'Q_custName_S_LK'},
					'-',{xtype:'label',text:'1对公，2对私'},{xtype:'textfield',id:'Q_accType_N_EQ'},
					//'-',{xtype:'label',text:'UNIT_NO'},{xtype:'textfield',id:'Q_unitNo_S_LK'},
					//'-',{xtype:'label',text:'UNIT_NAME'},{xtype:'textfield',id:'Q_unitName_S_LK'},
					//'-',{xtype:'label',text:'AMOUNT'},{xtype:'textfield',id:'Q_amount_S_EQ'},
					//'-',{xtype:'label',text:'ACC_NUM'},{xtype:'textfield',id:'Q_accNum_S_EQ'},
					//'-',{xtype:'label',text:'1-正常，2-注销'},{xtype:'textfield',id:'Q_accStatus_N_EQ'},
					//'-',{xtype:'label',text:'ACC_PARENT'},{xtype:'textfield',id:'Q_accParent_S_LK'},
					//'-',{xtype:'label',text:'CREATE_TIME'},{xtype:'textfield',id:'Q_createTime_S_LK'},
					//'-',{xtype:'label',text:'CREATE_USER'},{xtype:'textfield',id:'Q_createUser_S_LK'},
					//'-',{xtype:'label',text:'UPDATE_TIME'},{xtype:'textfield',id:'Q_updateTime_S_LK'},
					//'-',{xtype:'label',text:'UPDATE_USER'},{xtype:'textfield',id:'Q_updateUser_S_LK'},
					//'-',{xtype:'label',text:'LEVEL'},{xtype:'textfield',id:'Q_level_N_EQ'},
					//'-',{xtype:'label',text:'RATE'},{xtype:'textfield',id:'Q_rate_S_EQ'},
					//'-',{xtype:'label',text:'BANK_UNIT'},{xtype:'textfield',id:'Q_bankUnit_S_LK'},
					//'-',{xtype:'label',text:'BANK_NAME'},{xtype:'textfield',id:'Q_bankName_S_LK'},
					//'-',{xtype:'label',text:'BANK_AMOUNT'},{xtype:'textfield',id:'Q_bankAmount_S_EQ'},
					//'-',{xtype:'label',text:'UNK_COME'},{xtype:'textfield',id:'Q_unkCome_S_EQ'},
					//'-',{xtype:'label',text:'INT_COME'},{xtype:'textfield',id:'Q_intCome_S_EQ'},
					//'-',{xtype:'label',text:'156-人名币'},{xtype:'textfield',id:'Q_currency_S_LK'},
					//'-',{xtype:'label',text:'1-真实帐户，2-虚拟账户'},{xtype:'textfield',id:'Q_accFld_N_EQ'},
					//'-',{xtype:'label',text:'1-活期，2-定期，3-协议'},{xtype:'textfield',id:'Q_accPro_N_EQ'},
					//'-',{xtype:'label',text:'1-计息，2-不计息'},{xtype:'textfield',id:'Q_intFlag_N_EQ'},
					//'-',{xtype:'label',text:'1-按月计息，2-按季度计息，3-按年计息'},{xtype:'textfield',id:'Q_intType_N_EQ'},
					//'-',{xtype:'label',text:'1-收入，2-支出'},{xtype:'textfield',id:'Q_inOrOut_N_EQ'},
					//'-',{xtype:'label',text:'DRUG_AMT'},{xtype:'textfield',id:'Q_drugAmt_S_EQ'},
					//'-',{xtype:'label',text:'MEDICAL_AMT'},{xtype:'textfield',id:'Q_medicalAmt_S_EQ'},
					//'-',{xtype:'label',text:'OTHER_AMT'},{xtype:'textfield',id:'Q_otherAmt_S_EQ'},
					//'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			wjwAccountGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwAccountGrid.store.baseParams= params;
	    	   				wjwAccountGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwAccountGrid.vtbar.items.each(function(item,index,length){   
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
		            {header:'ACC_NO',dataIndex:'accNo',width:100,sortable:true,hidden:false},
		            {header:'CUST_NAME',dataIndex:'custName',width:100,sortable:true,hidden:false},
		            {header:'1对公，2对私',dataIndex:'accType',width:100,sortable:true,hidden:false},
		            {header:'UNIT_NO',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'UNIT_NAME',dataIndex:'unitName',width:100,sortable:true,hidden:false},
		            {header:'AMOUNT',dataIndex:'amount',width:100,sortable:true,hidden:false},
		            {header:'ACC_NUM',dataIndex:'accNum',width:100,sortable:true,hidden:false},
		            {header:'1-正常，2-注销',dataIndex:'accStatus',width:100,sortable:true,hidden:false},
		            {header:'ACC_PARENT',dataIndex:'accParent',width:100,sortable:true,hidden:false},
		            {header:'CREATE_TIME',dataIndex:'createTime',width:100,sortable:true,hidden:false},
		            {header:'CREATE_USER',dataIndex:'createUser',width:100,sortable:true,hidden:false},
		            {header:'UPDATE_TIME',dataIndex:'updateTime',width:100,sortable:true,hidden:false},
		            {header:'UPDATE_USER',dataIndex:'updateUser',width:100,sortable:true,hidden:false},
		            {header:'LEVEL',dataIndex:'level',width:100,sortable:true,hidden:false},
		            {header:'RATE',dataIndex:'rate',width:100,sortable:true,hidden:false},
		            {header:'BANK_UNIT',dataIndex:'bankUnit',width:100,sortable:true,hidden:false},
		            {header:'BANK_NAME',dataIndex:'bankName',width:100,sortable:true,hidden:false},
		            {header:'BANK_AMOUNT',dataIndex:'bankAmount',width:100,sortable:true,hidden:false},
		            {header:'UNK_COME',dataIndex:'unkCome',width:100,sortable:true,hidden:false},
		            {header:'INT_COME',dataIndex:'intCome',width:100,sortable:true,hidden:false},
		            {header:'156-人名币',dataIndex:'currency',width:100,sortable:true,hidden:false},
		            {header:'1-真实帐户，2-虚拟账户',dataIndex:'accFld',width:100,sortable:true,hidden:false},
		            {header:'1-活期，2-定期，3-协议',dataIndex:'accPro',width:100,sortable:true,hidden:false},
		            {header:'1-计息，2-不计息',dataIndex:'intFlag',width:100,sortable:true,hidden:false},
		            {header:'1-按月计息，2-按季度计息，3-按年计息',dataIndex:'intType',width:100,sortable:true,hidden:false},
		            {header:'1-收入，2-支出',dataIndex:'inOrOut',width:100,sortable:true,hidden:false},
		            {header:'DRUG_AMT',dataIndex:'drugAmt',width:100,sortable:true,hidden:false},
		            {header:'MEDICAL_AMT',dataIndex:'medicalAmt',width:100,sortable:true,hidden:false},
		            {header:'OTHER_AMT',dataIndex:'otherAmt',width:100,sortable:true,hidden:false},
		            {header:'NOTE1',dataIndex:'note1',width:100,sortable:true,hidden:false},
		            {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:false}
		           ]);
		WjwAccountGrid.superclass.constructor.call(this,{
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
    	if(!this.wjwAccountWindow)
    		this.wjwAccountWindow = new WjwAccountWindow();
    	var win = this.wjwAccountWindow;
		win.show();
		win.wjwAccount.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwAccountUpdateWindow)
					this.wjwAccountUpdateWindow = new WjwAccountUpdateWindow();
   				
   		    	var win = this.wjwAccountUpdateWindow;
				var winForm = win.wjwAccount;
				
				win.show();
							
   		    	winForm.id_.setValue(vrecord.data['id']);
				winForm.accNo.setValue(vrecord.data.accNo);
				winForm.custName.setValue(vrecord.data.custName);
				winForm.accType.setValue(vrecord.data.accType);
				winForm.unitNo.setValue(vrecord.data.unitNo);
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.amount.setValue(vrecord.data.amount);
				winForm.accNum.setValue(vrecord.data.accNum);
				winForm.accStatus.setValue(vrecord.data.accStatus);
				winForm.accParent.setValue(vrecord.data.accParent);
				winForm.createTime.setValue(vrecord.data.createTime);
				winForm.createUser.setValue(vrecord.data.createUser);
				winForm.updateTime.setValue(vrecord.data.updateTime);
				winForm.updateUser.setValue(vrecord.data.updateUser);
				winForm.level.setValue(vrecord.data.level);
				winForm.rate.setValue(vrecord.data.rate);
				winForm.bankUnit.setValue(vrecord.data.bankUnit);
				winForm.bankName.setValue(vrecord.data.bankName);
				winForm.bankAmount.setValue(vrecord.data.bankAmount);
				winForm.unkCome.setValue(vrecord.data.unkCome);
				winForm.intCome.setValue(vrecord.data.intCome);
				winForm.currency.setValue(vrecord.data.currency);
				winForm.accFld.setValue(vrecord.data.accFld);
				winForm.accPro.setValue(vrecord.data.accPro);
				winForm.intFlag.setValue(vrecord.data.intFlag);
				winForm.intType.setValue(vrecord.data.intType);
				winForm.inOrOut.setValue(vrecord.data.inOrOut);
				winForm.drugAmt.setValue(vrecord.data.drugAmt);
				winForm.medicalAmt.setValue(vrecord.data.medicalAmt);
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
    						wjwAccountGrid.vbbar.doLoad(wjwAccountGrid.vbbar.cursor);
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
    
    wjwAccountGrid = new WjwAccountGrid(Ext.getBody().getViewSize().height);
    wjwAccountGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwAccountGrid   
		]
	});
});

