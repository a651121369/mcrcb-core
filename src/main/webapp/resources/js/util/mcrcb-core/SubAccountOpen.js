		var ENTITY_URL_LIST = "/AccountManager/subpageropen";
var ENTITY_URL_SAVE = "/AccountManager/subsaveopen";
var ENTITY_URL_UPDATE = "/AccountManager/subupdateopen";
var ENTITY_URL_DELETE = "/AccountManager/subdeleteopen";
var ENTITY_URL_OPEN = "/AccountManager/open";
var STATUS_DATA = [{'id' : '1', 'name' : '正常'}];
var ACCTYPE_DATA = [{'id' : '1', 'name' : '对公'}, {'id' : '2', 'name' : '对私'}];
var CURRENCY_DATA = [{'id' : '156', 'name' : '人民币'}];
//var ACCFLD_DATA = [{'id' : '1', 'name' : '真实账户'}, {'id' : '2', 'name' : '虚拟账户'}];
var ACCFLD_DATA = [{'id' : '2', 'name' : '虚拟账户'}];
var ACCPRO_DATA = [{'id' : '1', 'name' : '活期'}, {'id' : '2', 'name' : '定期'}, {'id' : '3', 'name' : '协议'}];
var INTFLAG_DATA = [{'id' : '1', 'name' : '计息'}, {'id' : '2', 'name' : '不计息'}];
var INTTYPE_DATA = [{'id' : '0', 'name' : '不计息'}, {'id' : '1', 'name' : '按月计息'}, {'id' : '2', 'name' : '按季计息'}, {'id' : '3', 'name' : '按年计息'}];
var INOROUT_DATA = [{'id' : '1', 'name' : '收入'}, {'id' : '2', 'name' : '支出'}];



var PAGESIZE=50;
/*
 * WJW_ACCOUNT 
 * @author chenyong
 * @date 2015-11-04
 */
WjwAccount = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.id_ = new Ext.form.NumberField({
            fieldLabel: '编号',
            name: 'id',
            allowBlank: true,
            allowNegative :true,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.accNo = this.createTextField('<font color="red">*</font>账号','accNo','95%','',null,32,'长度超过不能32');
		this.custName = this.createTextField('<font color="red">*</font>账户名称1','custName','95%','',null,60,'长度超过不能60');
		this.accType = this.createMemoryCombo('<font color="red">*</font>账户种类', 'id', 'name', '95%', ACCTYPE_DATA, 'accType');
		/*this.accType = new Ext.form.NumberField({
            fieldLabel: '账户种类',
            name: 'accType',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		this.unitNo = this.createSearchCombo('<font color="red">*</font>机构号：','orgNo','orgName','unitNo','95%','/AccountManager/getSubOrgInfo');
			//this.createTextField('机构号','unitNo','95%','',null,20,'长度超过不能20');
		//this.unitName = this.createTextField('机构名称','unitName','95%','',null,60,'长度超过不能60');
		this.amount = this.createTextField('<font color="red">*</font>账户余额','amount','95%','',null,21,'长度超过不能21');
		this.accNum = this.createTextField('<font color="red">*</font>账户积数','accNum','95%','',null,21,'长度超过不能21');
		this.accStatus = this.createMemoryCombo('<font color="red">*</font>账户状态', 'id', 'name', '95%', STATUS_DATA, 'accStatus');
		/*this.accStatus = new Ext.form.NumberField({
            fieldLabel: '账户状态',
            name: 'accStatus',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		//this.accParent = this.createTextField('上一级账户号','accParent','95%','',null,32,'长度超过不能32');
		//this.createTime = this.createTextField('CREATE_TIME','createTime','95%','',null,30,'长度超过不能30');
		//this.createUser = this.createTextField('CREATE_USER','createUser','95%','',null,20,'长度超过不能20');
		//this.updateTime = this.createTextField('UPDATE_TIME','updateTime','95%','',null,30,'长度超过不能30');
		//this.updateUser = this.createTextField('UPDATE_USER','updateUser','95%','',null,20,'长度超过不能20');
		/*this.level = new Ext.form.NumberField({
            fieldLabel: '账户级别',
            name: 'level',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		this.rate = this.createTextField('<font color="red">*</font>利率','rate','95%','',null,9,'长度超过不能9');
		//this.bankAmount = this.createTextField('<font color="red">*</font>银行余额','bankAmount','95%','',null,21,'长度超过不能21');
		//this.bankUnit = this.createTextField('<font color="red">*</font>银行机构号','bankUnit','95%','',null,20,'长度超过不能20');
		//this.bankName = this.createTextField('<font color="red">*</font>银行机构名称','bankName','95%','',null,60,'长度超过不能60');
		
		//this.unkCome = this.createTextField('不明来账收入','unkCome','95%','',null,21,'长度超过不能21');
		//this.intCome = this.createTextField('利息收入','intCome','95%','',null,21,'长度超过不能21');
		//this.currency = this.createTextField('币种','currency','95%','',null,16,'长度超过不能16');
		this.currency = this.createMemoryCombo('<font color="red">*</font>币种', 'id', 'name', '95%', CURRENCY_DATA, 'currency');
		this.accFld = this.createMemoryCombo('<font color="red">*</font>账户属性', 'id', 'name', '95%', ACCFLD_DATA, 'accFld');

		/*this.accFld = new Ext.form.NumberField({
            fieldLabel: '账户属性',
            name: 'accFld',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		
		this.accPro = this.createMemoryCombo('<font color="red">*</font>账户类型', 'id', 'name', '95%', ACCPRO_DATA, 'accPro');
/*
		this.accPro = new Ext.form.NumberField({
            fieldLabel: '账户类型',
            name: 'accPro',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		
		this.intFlag = this.createMemoryCombo('<font color="red">*</font>计息标志', 'id', 'name', '95%', INTFLAG_DATA, 'intFlag');

		/*this.intFlag = new Ext.form.NumberField({
            fieldLabel: '计息标志',
            name: 'intFlag',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		
		this.intType = this.createMemoryCombo('<font color="red">*</font>计息类型', 'id', 'name', '95%', INTTYPE_DATA, 'intType');

		/*this.intType = new Ext.form.NumberField({
            fieldLabel: '计息类型',
            name: 'intType',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		
		this.inOrOut = this.createMemoryCombo('<font color="red">*</font>收入支出标志', 'id', 'name', '95%', INOROUT_DATA, 'inOrOut');

		/*this.inOrOut = new Ext.form.NumberField({
            fieldLabel: '账户收入支出标志',
            name: 'inOrOut',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });*/
		this.drugAmt = this.createTextField('药品金额','drugAmt','95%','',null,21,'长度超过不能21');
		this.medicalAmt = this.createTextField('医疗金额','medicalAmt','95%','',null,21,'长度超过不能21');
		//this.otherAmt = this.createTextField('其他金额','otherAmt','95%','',null,21,'长度超过不能21');
		this.note1 = this.createTextField('备注1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('备注2','note2','95%','',null,100,'长度超过不能100');
		
		this.accType.store.load();
		this.accStatus.store.load();
		this.currency.store.load();
		this.accFld.store.load();
		this.accPro.store.load();
		this.intFlag.store.load();
		this.intType.store.load();
		this.inOrOut.store.load();
		
		this.accNo.allowBlank = false;
		this.custName.allowBlank = false;
		this.accType.allowBlank = false;
        this.unitNo.allowBlank = true;
		//this.unitName.allowBlank = true;
		this.amount.allowBlank = false;
		this.accNum.allowBlank = false;
		this.accStatus.allowBlank = false;
		//this.accParent.allowBlank = true;
		//this.createTime.allowBlank = true;
		//this.createUser.allowBlank = true;
		//this.updateTime.allowBlank = true;
		//this.updateUser.allowBlank = true;
		//this.level.allowBlank = true;
		this.rate.allowBlank = false;
		//this.bankAmount.allowBlank = false;
		//this.bankUnit.allowBlank = false;
		//this.bankName.allowBlank = false;
		
		//this.unkCome.allowBlank = true;
		//this.intCome.allowBlank = true;
		this.currency.allowBlank = false;
		this.accFld.allowBlank = false;
		this.accPro.allowBlank = false;
		this.intFlag.allowBlank = false;
		this.intType.allowBlank = false;
		this.inOrOut.allowBlank = false;
		this.drugAmt.allowBlank = true;
		this.medicalAmt.allowBlank = true;
		//this.otherAmt.allowBlank = true;
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
					
					this.accNo,
					this.custName,
					this.accType,
					this.unitNo,
					//this.unitName,
					this.amount,
					this.accNum,
					this.accStatus,
					//this.accParent,
					//this.createTime,
					//this.createUser,
					//this.updateTime,
					//this.updateUser,
					//this.level,
					this.rate,
					//this.bankAmount,
					//this.bankUnit,
					//this.bankName,
					
					//this.unkCome,
					//this.intCome,
					this.currency,
					this.accFld,
					this.accPro,
					this.intFlag,
					this.intType,
					this.inOrOut,
					this.drugAmt,
					this.medicalAmt,
					//this.otherAmt,
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
		//alert("1");
		//var unitNo =this.unitNo.getValue();
		//alert(unitNo);
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
		}else{
			//alert("2");
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
                   //'-',{xtype:'button',text:'开通',iconCls:'edit',handler:this.onOpenClick,scope:this},
			       '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this},
					//'-',{xtype:'label',text:'ID'},{xtype:'textfield',id:'Q_id_N_EQ'},
					'-',{xtype:'label',text:'账号'},{xtype:'textfield',id:'accNo'},
					'-',{xtype:'label',text:'账户名称2'},{xtype:'textfield',id:'custName'},
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
			       			/*wjwAccountGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwAccountGrid.store.baseParams= params;
	    	   				wjwAccountGrid.store.load({params:{start:0,limit:PAGESIZE}});*/
			       			var accNo =  Ext.getCmp('accNo').getValue();
			       			var custName =  Ext.getCmp('custName').getValue();
			       			wjwAccountGrid.store.baseParams= {
		    	   					//unitNo:unitNo,
			       					accNo:accNo,custName:custName 
		    	   			};
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
		            {header:'编号',dataIndex:'id',width:60,sortable:true,hidden:false},
		            {header:'账号',dataIndex:'accNo',width:100,sortable:true,hidden:false},
		            {header:'账户名称3',dataIndex:'custName',width:200,sortable:true,hidden:false},
		            {header:'账户种类',dataIndex:'accType',width:70,sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data ==1){
		            			return '对公';
		            		}else if(data ==2){
		            			return '对私';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:200,sortable:true,hidden:false},
		            {header:'账户余额',dataIndex:'amount',width:120,sortable:true,hidden:false,align: 'right',
			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
			        {header:'药品金额',dataIndex:'drugAmt',width:120,sortable:true,hidden:false,align: 'right',
				            
				            renderer: function(value){
				            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
				            }},
		            {header:'医疗金额',dataIndex:'medicalAmt',width:120,sortable:true,hidden:false,align: 'right',
					            
					            renderer: function(value){
					            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
					            }},
		            {header:'其他金额',dataIndex:'otherAmt',width:120,sortable:true,hidden:false,align: 'right',
						            
						            renderer: function(value){
						            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
						            }},
		            {header:'账户积数',dataIndex:'accNum',width:180,sortable:true,hidden:false,align: 'right',
				            
				            renderer: function(value){
				            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
				            }},
		            {header:'账户状态',dataIndex:'accStatus',width:60,sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data ==1){
		            			return '正常';
		            		}else if(data ==2){
		            			return '注销';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'上一级账户号1',dataIndex:'accParent',width:180,sortable:true,hidden:false},
		            
		            {header:'账户级别',dataIndex:'level',width:60,sortable:true,hidden:false},
		            {header:'利率',dataIndex:'rate',width:80,sortable:true,hidden:false,align: 'right',
			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00000')+'';
			            }},
		            //{header:'银行开户机构号',dataIndex:'bankUnit',width:100,sortable:true,hidden:false},
		            //{header:'银行开户机构名称',dataIndex:'bankName',width:160,sortable:true,hidden:false},
		            /*{header:'银行余额',dataIndex:'bankAmount',width:120,sortable:true,hidden:false,align: 'right',
			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'不明来账收入',dataIndex:'unkCome',width:160,sortable:true,hidden:false,align: 'right',
				            
				            renderer: function(value){
				            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
				            }},
		            {header:'利息收入',dataIndex:'intCome',width:160,sortable:true,hidden:false,align: 'right',
					            
					            renderer: function(value){
					            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
					            }},*/
		            {header:'币种',dataIndex:'currency',width:70,sortable:true,hidden:false,
					            	renderer:function(data, metadata, record){
				                        if(data ==156){
					            			return '人民币';
					            		}else{
					            			return data;
					            		}
					            	}
					  },
		            {header:'账户属性',dataIndex:'accFld',width:80,sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data ==1){
		            			return '真实账户';
		            		}else if(data ==2){
		            			return '虚拟账户';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'账户类型',dataIndex:'accPro',width:60,sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data ==1){
		            			return '活期';
		            		}else if(data ==2){
		            			return '定期';
		            		}else if(data ==3){
		            			return '协议';
		            		}else{
		            			return data;
		            		}
	            	      }
		            },
		            {header:'计息标志',dataIndex:'intFlag',width:70,sortable:true,hidden:false,
	            		renderer:function(data, metadata, record){
	                        
	                        if(data ==1){
		            			return '计息';
		            		}else if(data ==2){
		            			return '不计息';
		            		}else{
		            			return data;
		            		}
	            		}
	                },
		            {header:'计息类型',dataIndex:'intType',width:100,sortable:true,hidden:false,
	                	   renderer:function(data, metadata, record){
		                        if(data ==1){
			            			return '按月计息';
			            		}else if(data ==0){
			            			return '不计息';
			            		}else if(data ==2){
			            			return '按季度计息';
			            		}else if(data ==3){
			            			return '按年计息';
			            		}else{
			            			return data;
			            		}
		            		}
	                 },
		            {header:'账户收入支出标志',dataIndex:'inOrOut',width:120,sortable:true,hidden:false,renderer:function(data, metadata, record){
                        if(data ==1){
	            			return '收入';
	            		}else if(data ==2){
	            			return '支出';
	            		}else{
	            			return data;
	            		}
            		    }
	                 },
		            {header:'创建人',dataIndex:'createUser',width:100,sortable:true,hidden:false},
		            {header:'创建时间',dataIndex:'createTime',width:120,sortable:true,hidden:false}
		            //{header:'修改时间',dataIndex:'updateTime',width:100,sortable:true,hidden:false},
		            //{header:'修改人',dataIndex:'updateUser',width:100,sortable:true,hidden:false},
		           // {header:'备注1',dataIndex:'note1',width:100,sortable:true,hidden:false},
		            //{header:'备注2',dataIndex:'note2',width:100,sortable:true,hidden:false}
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
		var winForm = win.wjwAccount;
		//winForm.inOrOut.setValue(1);
		winForm.accType.setValue(1);
		winForm.accNum.setValue(0.00);
		winForm.accStatus.setValue(1);
		winForm.rate.setValue(0.00);
		winForm.currency.setValue(156);
		winForm.accFld.setValue(2);
		winForm.accPro.setValue(1);
		winForm.intFlag.setValue(1);
		winForm.intType.setValue(0);
		winForm.drugAmt.setValue(0.00);
		winForm.medicalAmt.setValue(0.00);
		var winForm = win.wjwAccount;
		winForm.id_.hide();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				//if(vrecord.data.accFld == 1 ||vrecord.data.accFld=='1'){
   					if(!this.wjwAccountUpdateWindow)
   						this.wjwAccountUpdateWindow = new WjwAccountUpdateWindow();
   	   				
   	   		    	var win = this.wjwAccountUpdateWindow;
   					var winForm = win.wjwAccount;
   					
   					win.show();
   								
   	   		    	winForm.id_.setValue(vrecord.data['id']);
   	   		    	winForm.id_.hide();
   					winForm.accNo.setValue(vrecord.data.accNo);
   					winForm.custName.setValue(vrecord.data.custName);
   					winForm.accType.setValue(vrecord.data.accType);
   					winForm.unitNo.setValue(vrecord.data.unitNo);
   					//winForm.unitName.setValue(vrecord.data.unitName);
   					winForm.amount.setValue(vrecord.data.amount);
   					winForm.accNum.setValue(vrecord.data.accNum);
   					winForm.accStatus.setValue(vrecord.data.accStatus);
   					//winForm.accParent.setValue(vrecord.data.accParent);
   					//winForm.createTime.setValue(vrecord.data.createTime);
   					//winForm.createUser.setValue(vrecord.data.createUser);
   					//winForm.updateTime.setValue(vrecord.data.updateTime);
   					//winForm.updateUser.setValue(vrecord.data.updateUser);
   					//winForm.level.setValue(vrecord.data.level);
   					winForm.rate.setValue(vrecord.data.rate);
   					//winForm.bankUnit.setValue(vrecord.data.bankUnit);
   					//winForm.bankName.setValue(vrecord.data.bankName);
   					//winForm.bankAmount.setValue(vrecord.data.bankAmount);
   					//winForm.unkCome.setValue(vrecord.data.unkCome);
   					//winForm.intCome.setValue(vrecord.data.intCome);
   					winForm.currency.setValue(vrecord.data.currency);
   					winForm.accFld.setValue(vrecord.data.accFld);
   					
   					winForm.accPro.setValue(vrecord.data.accPro);
   					winForm.intFlag.setValue(vrecord.data.intFlag);
   					winForm.intType.setValue(vrecord.data.intType);
   					winForm.inOrOut.setValue(vrecord.data.inOrOut);
   					winForm.drugAmt.setValue(vrecord.data.drugAmt);
   					winForm.medicalAmt.setValue(vrecord.data.medicalAmt);
   					//winForm.otherAmt.setValue(vrecord.data.otherAmt);
   					winForm.note1.setValue(vrecord.data.note1);
   					winForm.note2.setValue(vrecord.data.note2);
   				/*}else{
   					Ext.Msg.alert('系统提示','只能修改真实账号！');
   				}*/
   		    	
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
    },
    onOpenClick: function(){
    	var records = this.getSelectionModel().getSelections();
    	var ids = {};
		var valueStr = [];
		if(records.length > 0) {
   			if(records.length == 1){
   				
   				for(var i=0;i<records.length;i++){
   		    		valueStr.push(records[i].get('id'));
   		    	}
   				ids['ids'] = valueStr;
   				Ext.Msg.confirm('系统提示：',"确定开通这"+records.length+"条信息吗？",function(btn){
   	    			if(btn == 'yes'){
   	    				Ext.Ajax.request({
   	    					url: ENTITY_URL_OPEN,
   	    					method: 'POST',
   	    					params: ids,
   	    					/*success: function(form,action){
   	    						Ext.Msg.alert('系统提示','开通成功！');
   	    						wjwAccountGrid.vbbar.doLoad(wjwAccountGrid.vbbar.cursor);
   	    					},*/
   	    					success: function(response){
	            				var obj=Ext.decode(response.responseText);
	            				if(obj.success=='false' || obj.success==false){
	            					Ext.Msg.alert('系统提示',obj.message);
	            				}else{
	            					Ext.Msg.alert('系统提示','开通成功！');
	   	    						wjwAccountGrid.vbbar.doLoad(wjwAccountGrid.vbbar.cursor);
	            				}
	            			},
   	    					failure: function(form,action){
   								Ext.MessageBox.alert("系统提示：",action.result.message);
   							}
   	    				});
   	    			}
   	    		});
   			}else{
   				Ext.Msg.alert('系统提示','请选择一条记录进行开通！');
   			}
   		}else{
     		Ext.Msg.alert('系统提示','请选择一条记录进行开通！');
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

