Ext.BLANK_IMAGE_URL = "/resources/js/ext/resources/images/default/s.gif";
//需要补充的空格
var BLANKSTR = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';   
var TREE_ROOT_URL = '/hmb/ll/treeRootData';
var ORG_TREE_LOADER = '/hmb/ll/queryLlzl'; //机构树
var USER_GRID_STORE_URL = '/hmb/ll/llPageData';//根据用户信息查询该用户所属角色
var PAGESIZE=20;
var selNode;//当前节点
var RATE_TYPE_DATA = [{'id' : '0', 'name' : '靠档'}, {'id' : '1', 'name' : '分段'}];
var STATUS_DATA = [{'id' : '0', 'name' : '正常'}, {'id' : '1', 'name' : '历史'}];
var BOOLEAN_DATA = [{'id' : '0', 'name' : '是'}, {'id' : '1', 'name' : '否'}];
var PRO_CODE = '';
var PRO_NAME = '';
var RATE_CODE = '';
var RATE_NAME = '';

/***************************************ProductForm组件**************************************************/
ProductForm = Ext.extend(Ext.ux.Form, {
	
    constructor: function() {
    	this.proCode = this.createTextField('<font color="red">*</font>产品代码:', 'proCode', '95%','','',20,'最大长度20');
        this.proName = this.createTextField('<font color="red">*</font>产品名称:', 'proName', '95%','','',32,'最大长度32');
        this.rateType = this.createMemoryCombo('<font color="red">*</font>计息方式', 'id', 'name', '95%', RATE_TYPE_DATA, 'rateType');
        this.ordered = this.createNumberField('<font color="red">*</font>显示顺序:','ordered','95%',0);
        this.startTime = this.createDateField('<font color="red">*</font>起始日期', 'startTime', 'Ymd', '95%');
        this.endTime = this.createDateField('<font color="red">*</font>到期日期', 'endTime', 'Ymd', '95%');
        this.status = this.createMemoryCombo('<font color="red">*</font>状态', 'id', 'name', '95%', STATUS_DATA, 'status');
        
        this.parCode = this.createHidden('parCode', 'parCode', '95%');
        this.createNo = this.createHidden('createNo', 'createNo', '95%');
        this.createDate = this.createHidden('createDate', 'createDate', '95%');
		this.note1 = this.createHidden('note1', 'note1', '95%');
		this.note2 = this.createHidden('note2', 'note2', '95%');
		this.flag = this.createHidden('flag', 'flag', '95%');
		this.rateType.store.load();
		this.status.store.load();
        
        this.ordered.maxLength = 10;
        this.ordered.maxLengthText = '最大长度10';
        
        this.rateType.setValue('0');
        this.status.setValue('0');
        

        OrgNodeForm.superclass.constructor.call(this, {
        	anchor: '100%',
        	autoHeight:true,
        	layout:"tableform",
        	layoutConfig: {columns: 1},
        	labelWidth: 100,
            labelAlign :'right',
            frame:true,
            bodyStyle:"padding: 5px 5px 0",
            width: '100%',
            items: [
                this.proCode,
            	this.proName,
            	this.rateType,
            	this.ordered,
            	this.startTime,
            	this.endTime,
            	this.status,
            	this.parCode,
            	this.createNo,
            	this.createDate,
            	this.note1,
            	this.note2,
            	this.flag
            ],
            buttonAlign :'center',
            buttons: [
                      {text: '保存', width: 20,iconCls: 'save', hidden: false, handler: this.addFormClick, scope: this},  
                      {text:'修改',iconCls: 'edit',handler:this.updateFormClick,scope:this},
                      {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
              ]
        });
     },
     addFormClick: function() {
         if(this.getForm().isValid()) {
        	var startDate		= this.startTime.getValue();
     		var endDate			= this.endTime.getValue();
     		startDate = parseInt(startDate.format("Ymd"));
     		endDate = parseInt(endDate.format("Ymd"));
     		/*if(startDate == endDate) {
      			Ext.Msg.confirm("提示", "确定起始日期与到期日期同一天？", function(btn, text) {
      	            if (btn == 'no') {
      	            	return;
      	            }
      	        }, this);
      		}*/
     		if(startDate >= endDate) {
     			Ext.MessageBox.alert("系统提示:", BLANKSTR + "到期日期必须晚于起始日期!" + BLANKSTR);
     			return;
     		}
     		
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/hmb/ll/saveProduct', 
                 method: 'POST',
                 success: function(form, action) { 
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加成功!" + BLANKSTR);
                 	orgTree.productInsertWindow.hide();
                 	orgTree.root.reload();
                 },
                 failure: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加失败!" + BLANKSTR);
                 },
                 scope:this
         	});
         }
     },
     updateFormClick: function() {       //修改
         if(this.getForm().isValid()) {
        	var startDate		= this.startTime.getValue();
      		var endDate			= this.endTime.getValue();
      		startDate = parseInt(startDate.format("Ymd"));
      		endDate = parseInt(endDate.format("Ymd"));
      		/*if(startDate == endDate) {
      			Ext.Msg.confirm("提示", "确定起始日期与到期日期同一天？", function(btn, text) {
      	            if (btn == 'no') {
      	            	return;
      	            }
      	        }, this);
      		}*/
      		if(startDate >= endDate) {
      			Ext.MessageBox.alert("系统提示:", BLANKSTR + "到期日期必须晚于起始日期!" + BLANKSTR);
      			return;
      		}
      		this.proCode.enable();
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/hmb/ll/saveProduct', 
                 method: 'POST',
                 success: function(form, action) { 
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改成功!" + BLANKSTR);
                 	orgTree.productUpdateWindow.hide();
                 	orgTree.root.reload();
                 },
                 failure: function(form, action) {
                	this.proCode.disable();
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改失败!" + BLANKSTR);
                 },
                 scope:this
         	});
         }
     },
     onCloseClick: function(){ 			//关闭
         this.ownerCt.hide();
     }
});
/***************************************ProductInsertWindow组件**************************************************/
ProductInsertWindow = Ext.extend(Ext.Window,{
	constructionForm : null,
    constructor: function(a) {
        this.constructionForm = new ProductForm();
        this.constructionForm.buttons[0].show();   //隐藏添加按钮
    	this.constructionForm.buttons[1].hide();   //显示修改按钮
        OrgNodeInsertWindow.superclass.constructor.call(this, {
            title: "添加产品",
            width: 400,
            anchor: '100%',
            autoHeight:true,
            constrainHeader:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});

/***************************************ProductUpdateWindow组件**************************************************/
ProductUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.constructionForm = new ProductForm();
    	this.constructionForm.proCode.disable();
    	this.constructionForm.buttons[0].hide();   //隐藏添加按钮
    	this.constructionForm.buttons[1].show();   //显示修改按钮
    	OrgNodeUpdateWindow.superclass.constructor.call(this, {
        	title: "修改产品",
            width: 400,
            autoHeight:true,
            constrainHeader:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});

/***************************************OrgNodeForm组件**************************************************/
OrgNodeForm = Ext.extend(Ext.ux.Form, {
	
    constructor: function() {
    	this.ID = this.createHidden('ID', 'id', '95%');
    	this.proCode = this.createDisplayField('<font color="red">*</font>产品代码:', 'proCode', '95%','','',20,'最大长度20');
    	this.proName = this.createDisplayField('<font color="red">*</font>产品名称:', 'proName', '95%','','',32,'最大长度32');
        this.rateCode = this.createTextField('<font color="red">*</font>利率代码:', 'rateCode', '95%','','',20,'最大长度20');
        this.rateName = this.createTextField('<font color="red">*</font>利率名称:', 'rateName', '95%','','',32,'最大长度32');
        this.limitAmt = this.createNumberField('<font color="red">*</font>起存金额:','limitAmt','95%',2);
        this.orderNum = this.createNumberField('<font color="red">*</font>显示顺序:','orderNum','95%',0);
        this.isPer = this.createMemoryCombo('<font color="red">*</font>支持签约', 'id', 'name', '95%', BOOLEAN_DATA, 'isPer');
        this.isNear = this.createMemoryCombo('<font color="red">*</font>支持靠档', 'id', 'name', '95%', BOOLEAN_DATA, 'isNear');
        this.status = this.createMemoryCombo('<font color="red">*</font>状态', 'id', 'name', '95%', STATUS_DATA, 'status');
		this.note1 = this.createHidden('note1', 'note1', '95%');
		this.note2 = this.createHidden('note2', 'note2', '95%');
		this.parentCode = this.createHidden('父级代码', 'parentCode');
		this.isPer.store.load();
		this.isNear.store.load();
		this.status.store.load();
        
        this.orderNum.maxLength = 10;
        this.orderNum.maxLengthText = '最大长度10';
        
        this.isPer.setValue('0');
        this.isNear.setValue('0');
        this.status.setValue('0');
        

        OrgNodeForm.superclass.constructor.call(this, {
        	anchor: '100%',
        	autoHeight:true,
        	layout:"tableform",
        	layoutConfig: {columns: 1},
        	labelWidth: 100,
            labelAlign :'right',
            frame:true,
            bodyStyle:"padding: 5px 5px 0",
            width: '100%',
            items: [
            	this.ID,
            	this.proCode,
            	this.proName,
            	this.rateCode,
            	this.rateName,
            	this.limitAmt,
            	this.orderNum,
            	this.isPer,
            	this.isNear,
            	this.status,
            	this.note1,
            	this.note2,
            	this.parentCode
            ],
            buttonAlign :'center',
            buttons: [
                      {text: '保存', width: 20,iconCls: 'save', hidden: false, handler: this.addFormClick, scope: this},  
                      {text:'修改',iconCls: 'edit',handler:this.updateFormClick,scope:this},
                      {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
              ]
        });
     },
     createDisplayField: function(fieldLabel, name, anchor, blankText, vtype, maxLength, maxLengthText,inputType, colspan) {    //生成一个通用的TextField
         var tf = new Ext.form.TextField({
             fieldLabel: fieldLabel,
             name: name,
             xtype: 'textfield',
             readOnly: true,
             style : 'color:gray',
             allowBlank: false,
             anchor: anchor,
             blankText: '该选项为必填项,请输入内容...',
             vtype: vtype,
             maxLength: maxLength,
             maxLengthText: maxLengthText,
             inputType:inputType,
             colspan: colspan
         });
         return tf;
     },
     addFormClick: function() {
         if(this.getForm().isValid()) {
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/hmb/ll/saveLlzlb', 
                 method: 'POST',
                 success: function(form, action) { 
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加成功!" + BLANKSTR);
                 	orgTree.constructionInsertWindow.hide();
                 	orgTree.root.reload();
                 },
                 failure: function(form, action) {
                	if(action.result && action.result.message) {
  						Ext.MessageBox.alert("系统提示：",action.result.message);
  					} else {
  						Ext.MessageBox.alert("系统提示：","添加失败！");
  					}
                 },
                 scope:this
         	});
         }
     },
     updateFormClick: function() {       //修改
         if(this.getForm().isValid()) {
        	this.rateCode.enable();
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/hmb/ll/saveLlzlb', 
                 method: 'POST',
                 success: function(form, action) { 
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改成功!" + BLANKSTR);
                 	orgTree.constructionUpdateWindow.hide();
                 	orgTree.root.reload();
                 },
                 failure: function(form, action) {
                	this.rateCode.disable();
                	if(action.result && action.result.message) {
   						Ext.MessageBox.alert("系统提示：",action.result.message);
   					} else {
   						Ext.MessageBox.alert("系统提示：","修改失败！");
   					}
                 },
                 scope:this
         	});
         }
     },
     onCloseClick: function(){ 			//关闭
         this.ownerCt.hide();
     }
});
/***************************************OrgNodeInsertWindow组件**************************************************/
OrgNodeInsertWindow = Ext.extend(Ext.Window,{
	constructionForm : null,
    constructor: function(a) {
        this.constructionForm = new OrgNodeForm();
        this.constructionForm.buttons[0].show();   //隐藏添加按钮
    	this.constructionForm.buttons[1].hide();   //显示修改按钮
        OrgNodeInsertWindow.superclass.constructor.call(this, {
            title: "添加利率种类",
            width: 400,
            anchor: '100%',
            autoHeight:true,
            constrainHeader:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});

/***************************************OrgNodeUpdateWindow组件**************************************************/
OrgNodeUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.constructionForm = new OrgNodeForm();
    	this.constructionForm.rateCode.disable();
    	this.constructionForm.buttons[0].hide();   //隐藏添加按钮
    	this.constructionForm.buttons[1].show();   //显示修改按钮
    	OrgNodeUpdateWindow.superclass.constructor.call(this, {
        	title: "修改利率种类",
            width: 400,
            autoHeight:true,
            constrainHeader:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});

/***************************************UserForm组件**************************************************/
UserForm = Ext.extend(Ext.ux.Form, {
	
    constructor: function() {
        this.ID = this.createHidden('ID','id');
        this.proCode = this.createHidden('proCode','proCode');
        this.rateCode = this.createHidden('rateCode','rateCode');
        this.note1 = this.createHidden('note1','note1');
        this.note2 = this.createHidden('note2','note2');
        this.rateDate = this.createDateField('<font color="red">*</font>利率生效日期', 'rateDate', 'Ymd', '95%');
        this.rate = this.createNumberField('<font color="red">*</font>基准利率(%):','rate','95%', 2);
        this.upRate = this.createNumberField('<font color="red">*</font>上浮比例(%):','upRate','95%', 2);
        this.status = this.createMemoryCombo('<font color="red">*</font>状态', 'id', 'name', '95%', STATUS_DATA, 'status');
        
		this.status.store.load();
        this.status.setValue('0');
        
        UserForm.superclass.constructor.call(this, {
        	anchor: '100%',
        	autoHeight:true,
        	layout:"tableform",
        	layoutConfig: {columns: 1},
        	labelWidth: 100,
            labelAlign :'right',
            frame:true,
            bodyStyle:"padding: 5px 5px 0",
            width: '100%',
            items: [
            	this.ID,this.proCode,this.rateCode,this.note1,
                this.note2,this.rateDate,this.rate,this.upRate,this.status
            ],
            buttonAlign :'center',
            buttons: [
                      {text: '保存', width: 20,iconCls: 'save', hidden: false, handler: this.addFormClick, scope: this},  
                      {text:'修改',iconCls: 'edit',handler:this.updateFormClick,scope:this},
                      {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
              ]
        });
     },
     addFormClick: function() {
    	 if(!this.getForm().isValid()){
    		 return;
    	 }
    	 var thisForm = this.getForm();
    	 var rateCode = this.rateCode.getValue();
    	 var proCode = this.proCode.getValue();
    	 var rateDate = this.rateDate.getValue();
    	 rateDate = rateDate.format("Ymd");
         Ext.Ajax.request({
         	url: '/hmb/ll/checkLl',
             method: 'POST',
             params:{'rateCode' : rateCode, 'rateDate' : rateDate, 'proCode' : proCode},
             success: function(response, options){
             	 var res = Ext.decode(response.responseText);
                 if(res.success == true){//不重复，表单提交
                  	Ext.Msg.alert('系统提示',BLANKSTR + rateDate+'日利率已存在' + BLANKSTR);
                 	return;              	 
                 } else {
                		 thisForm.submit({
                             waitMsg: '正在提交数据...',
                             url: '/hmb/ll/saveLlb', 
                             method: 'POST',
                             success: function(form, action) { 
                             	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加成功!" + BLANKSTR);
                             	orgTree.addUserWindow.hide();
                             	//alert(PRO_NAME);
                            	//alert(RATE_NAME);
                            	if(PRO_CODE == ""){
                            		PRO_CODE = selNode.attributes.proCode;
                                 	PRO_NAME = selNode.parentNode.text;
                            		RATE_CODE = selNode.attributes.rateCode;
                            		RATE_NAME = selNode.attributes.rateName;
                            	} 
                        		orgUserGrid.setTitle('利率列表（'+PRO_NAME+'-'+RATE_NAME+'）');
                             	orgUserGrid.store.baseParams = {
                            			rateCode 	: RATE_CODE, 
                            			proCode 	: PRO_CODE
                            	}; 
								orgUserGrid.store.load({
						            params:{start:0, limit:PAGESIZE}
						        });
                             },
                             failure: function(form, action) {
                             	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加失败!" + BLANKSTR);
                             }
                     	});
               }
           },
           failure: function(response, options){
         	  Ext.Msg.alert( "提示", "操作失败!,请联系管理员" );
         	  return;
           }
        }); 
     },
     updateFormClick: function() {       //修改
         if(this.getForm().isValid()) {
        	this.rateDate.enable();
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/hmb/ll/saveLlb', 
                 method: 'POST',
                 success: function(form, action) { 
                	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加成功!" + BLANKSTR);
                	orgUserGrid.updateRateWindow.hide();
                  	orgUserGrid.store.baseParams = {
                 			rateCode 	: RATE_CODE, 
                 			proCode 	: PRO_CODE
                 	}; 
					orgUserGrid.store.load({
			            params:{start:0, limit:PAGESIZE}
			        });
                 },
                 failure: function(form,action){
                	this.rateDate.disable();
                	orgUserGrid.updateRateWindow.hide();
 					if(action.result && action.result.message) {
 						Ext.MessageBox.alert("系统提示：",action.result.message);
 					} else {
 						Ext.MessageBox.alert("系统提示：","修改失败！");
 					}
 				 },
 				 scope : this
         	});
         }
     },
     onCloseClick: function(){ 			//关闭
         this.ownerCt.hide();
     }
});
/***************************************UserInsertWindow组件**************************************************/
UserInsertWindow = Ext.extend(Ext.Window,{
	constructionForm : null,
    constructor: function(a) {
        this.constructionForm = new UserForm();
        this.constructionForm.buttons[0].show();   //隐藏添加按钮
    	this.constructionForm.buttons[1].hide();   //显示修改按钮
        UserInsertWindow.superclass.constructor.call(this, {
            title: "添加利率",
            width: 400,
            anchor: '100%',
            autoHeight:true,
            constrainHeader:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});
UserUpdateWindow = Ext.extend(Ext.Window,{
	constructionForm : null,
	constructor: function(a) {
		this.constructionForm = new UserForm();
		this.constructionForm.rateDate.disable();
		this.constructionForm.buttons[0].hide();   //隐藏添加按钮
    	this.constructionForm.buttons[1].show();   //显示修改按钮
    	UserUpdateWindow.superclass.constructor.call(this, {
			title: "修改利率",
			width: 400,
			anchor: '100%',
			autoHeight:true,
			constrainHeader:true,
			resizable : false,
			plain: true,
			modal: true,
			autoScroll: true,
			closeAction: 'hide',
			items: [this.constructionForm]
		});
	}
});
/***********************OrgTree组件**************************
 *author        ：zhuzengpeng
 *description   : 机构管理--机构树
 *date          : 2013-08-12
******************************************************************/
OrgTree  = Ext.extend(Ext.tree.TreePanel, {
	constructor : function(width, height) {
		this.proRightMenu = new Ext.menu.Menu({
			items : [{
				text : '修改产品',
				iconCls : 'edit',
				handler : this.onModifyProduct,
				scope : this
			} , {
				text : '删除产品',
				iconCls : 'delete',
				handler : this.onDeleteProduct,
				scope : this
			} , '-', {
				text : '添加利率种类',
				iconCls : 'add',
				handler : this.onAddRateType,
				scope : this
			}]
		});
		this.llzlRightMenu = new Ext.menu.Menu({
			items : [{
				text : '修改利率种类',
				iconCls : 'edit',
				handler : this.onModifyRateType,
				scope : this
			} , {
				text : '删除利率种类',
				iconCls : 'delete',
				handler : this.onDeleteRateType,
				scope : this
			} , '-', {
				text : '添加利率',
				iconCls : 'add',
				handler : this.onAddRate,
				scope : this
			}]
		});
		
		this.productInsertWindow = new ProductInsertWindow();
		this.productUpdateWindow = new ProductUpdateWindow();
		this.constructionInsertWindow = new OrgNodeInsertWindow();       
        this.constructionUpdateWindow = new OrgNodeUpdateWindow();
        this.addUserWindow = new UserInsertWindow();
		
		OrgTree.superclass.constructor.call(this, {
			title : '机构列表',
			region:'west',
			collapsible :true,
			autoScroll : true,
			enableDD : true,//是否支持拖拽效果
			containerScroll : true,//是否支持滚动条
			width : width,
			height : height,
			border : true,
			frame : false,
			rootVisible : false,//是否显示根节点
			margins : '0 0 0 0',
			loader : new Ext.tree.TreeLoader({
				dataUrl : TREE_ROOT_URL + "?pid=0"
			}),
			root : new Ext.tree.AsyncTreeNode({
				id: "0",
				level: "0",
				hidden: false
			}),
			listeners : {
				'beforedblclick':{fn: this.beforedblClick, scope: this},
				'contextmenu' : {
					fn : this.onRightMenuClick,
					scope : this
				 },				
				'beforeload' : {//在节点加载之前触发，返回false取消操作
					fn : this.onBeforeLoad,
					scope : this
				},
				'load' : {
					fn : this.onLoadSuccess,
					scope : this
				}
			},
			tbar : new Ext.Toolbar({
				items : [ {
					iconCls : "add",
					text : "添加产品",
					handler : function() {
						orgTree.onAddProduct();
					}
				}, {
					iconCls : "refresh",
					text : "刷新",
					handler : function() {
						orgTree.root.reload();
					}
				}, {
					text : "展开",
					iconCls : "expand",
					handler : function() {
						orgTree.expandAll();
					}
				}, {
					text : "收起",
					iconCls : "collapse",
					handler : function() {
						orgTree.collapseAll();
					}
				} ]
			})
		});
	},
	onLoadSuccess : function(node) {
		orgTree.expandAll();
	},
	onRightMenuClick: function(node, e) {
		selNode = node;
		node.select();
		if(1 == selNode.attributes.level) {
			this.proRightMenu.showAt(e.getXY());
		} else {
			this.llzlRightMenu.showAt(e.getXY());
		}
	},
	onAddProduct : function() {
		var win = this.productInsertWindow;
		win.show();
		win.constructionForm.getForm().reset();
	},
	onModifyProduct : function() {
		var win = this.productUpdateWindow;
		win.show();
		win.constructionForm.getForm().reset();
		win.constructionForm.proCode.setValue(selNode.attributes.proCode);
		win.constructionForm.proName.setValue(selNode.attributes.proName);
		win.constructionForm.rateType.setValue(selNode.attributes.rateType);
		win.constructionForm.ordered.setValue(selNode.attributes.ordered);
		win.constructionForm.startTime.setValue(selNode.attributes.startTime);
		win.constructionForm.endTime.setValue(selNode.attributes.endTime);
		win.constructionForm.status.setValue(selNode.attributes.status);
		win.constructionForm.parCode.setValue(selNode.attributes.parCode);
		win.constructionForm.createNo.setValue(selNode.attributes.createNo);
		win.constructionForm.createDate.setValue(selNode.attributes.createDate);
		win.constructionForm.note1.setValue(selNode.attributes.note1);
		win.constructionForm.note2.setValue(selNode.attributes.note2);
		win.constructionForm.flag.setValue('1');
	},
	onDeleteProduct : function() {
		if(selNode.childNodes.length > 0) {
    		Ext.Msg.alert('提示', "该产品拥有利率种类，不允许删除！");
    		return
    	}
		Ext.Msg.confirm("提示", "确定删除该产品？", function(btn, text) {
            if (btn == 'yes') {                  
                this.body.mask('提交数据，请稍候...', 'x-mask-loading');
                Ext.Ajax.request({
		            url     : '/hmb/ll/deleteProduct',
		            params  : {proCode : selNode.attributes.proCode},
		            success : function() {
		                this.body.unmask();
		                Ext.MessageBox.alert('提示', '操作成功！');
		                orgTree.root.reload();
		            },
		            failure: function(response, options) {
		            	this.body.unmask();
		            	var res = Ext.decode(response.responseText);
		            	if(res.message) {
							Ext.MessageBox.alert("系统提示：", res.message);
						} else {
							Ext.MessageBox.alert("系统提示：","添加失败！");
						}
					},
		            scope   : this
		        });
            }
        }, this);
	},
	onAddRateType : function(){
		var win = this.constructionInsertWindow;
		win.show();
		win.constructionForm.getForm().reset();
		win.constructionForm.proCode.setValue(selNode.attributes.proCode);
		win.constructionForm.proName.setValue(selNode.attributes.proName);
	},
	onModifyRateType : function(){
		var win = this.constructionUpdateWindow;
		win.show();
		win.constructionForm.getForm().reset();
		var node = selNode.parentNode;
		win.constructionForm.proCode.setValue(node.attributes.proCode);
		win.constructionForm.proName.setValue(node.attributes.proName);
		win.constructionForm.ID.setValue(selNode.attributes.id);
		win.constructionForm.rateCode.setValue(selNode.attributes.rateCode);
		win.constructionForm.rateName.setValue(selNode.attributes.rateName);
		win.constructionForm.parentCode.setValue(selNode.attributes.parentCode);
		win.constructionForm.orderNum.setValue(selNode.attributes.orderNum);
		win.constructionForm.status.setValue(selNode.attributes.status);
		win.constructionForm.limitAmt.setValue(selNode.attributes.limitAmt);
		win.constructionForm.isPer.setValue(selNode.attributes.isPer);
		win.constructionForm.isNear.setValue(selNode.attributes.isNear);
		win.constructionForm.note1.setValue(selNode.attributes.note1);
		win.constructionForm.note2.setValue(selNode.attributes.note2);
	},
	onDeleteRateType : function(){
        Ext.Msg.confirm("提示", "确定删除该利率种类？", function(btn, text) {
            if (btn == 'yes') {                  
                this.body.mask('提交数据，请稍候...', 'x-mask-loading');
                Ext.Ajax.request({
		            url     : '/hmb/ll/deleteLlzlb',
		            params  : {id : selNode.id, proCode : selNode.attributes.proCode, rateCode : selNode.attributes.rateCode},
		            success : function(response, options) {
		            	this.body.unmask();
		            	var res = Ext.decode(response.responseText);
		            	if(res.success) {
		            		Ext.MessageBox.alert('提示', '操作成功！');
		            		orgTree.root.reload();
		            	} else {
		            		if(res.message) {
								Ext.MessageBox.alert("系统提示：", res.message);
							} else {
								Ext.MessageBox.alert("系统提示：","添加失败！");
							}
		            	}
		            },
		            failure: function(response, options) {
		            	this.body.unmask();
		            	var res = Ext.decode(response.responseText);
		            	if(res.message) {
							Ext.MessageBox.alert("系统提示：", res.message);
						} else {
							Ext.MessageBox.alert("系统提示：","添加失败！");
						}
					},
		            scope   : this
		        });
            }
        }, this);
	},
	onExpandNode:function(){
        if (selNode == null) {
            this.getRootNode().eachChild(function(n) {
                n.expand(false, true);
            });
        } else {
            selNode.expand(false, true);
        }
	},
	onAddRate : function(){
		var win = this.addUserWindow;
		win.show();
		win.constructionForm.getForm().reset();
		win.constructionForm.proCode.setValue(selNode.attributes.proCode);
		win.constructionForm.rateCode.setValue(selNode.attributes.rateCode);
//		win.constructionForm.status.setValue('0');
	},
	onAddRate2 : function(RATE_CODE,PRO_CODE){
		var win = this.addUserWindow;
		win.show();
		win.constructionForm.getForm().reset();
		win.constructionForm.proCode.setValue(PRO_CODE);
		win.constructionForm.rateCode.setValue(RATE_CODE);
//		win.constructionForm.status.setValue('0');
	},
	beforedblClick: function(node, e) {
		PRO_CODE = node.attributes.proCode;
		PRO_NAME = node.parentNode.text;
		RATE_CODE = node.attributes.rateCode;
		RATE_NAME = node.attributes.rateName;
		orgUserGrid.setTitle('利率列表（'+PRO_NAME+'-'+RATE_NAME+'）');
		orgUserGrid.store.baseParams = {
			rateCode 	: node.attributes.rateCode, 
			proCode 	: node.attributes.proCode
		}; 
		orgUserGrid.store.load({
            params:{start:0, limit:PAGESIZE}
        });
		return false;
	},
	onBeforeLoad : function(node, e) {
		var pid = node.attributes.id;
		this.loader.dataUrl = TREE_ROOT_URL + '?pid=' + pid; //定义子节点的Loader
	}
});

/***********************OrgUserGrid组件**************************
 *author        ： zhuzengpeng
 *description   : 机构管理--用户列表
 *date          : 2013-08-12
******************************************************************/
OrgUserGrid = Ext.extend(UxGrid, {
    pageSizeCombo: null,
    vtbar:null,             //面板顶部的工具条  
    vbbar:null,             //面板底部的工具条
    store:null,
    constructor: function(height, width){
        this.store = new Ext.data.Store({          //Grid Store
            proxy: new Ext.data.HttpProxy({url: USER_GRID_STORE_URL, method: 'POST'}),
            reader: new Ext.data.JsonReader({totalProperty: 'total', root:'rows'},[
                    {name:'id'},{name:'rateDate'},{name:'proCode'},{name:'rateCode'},
                    {name:'rate'},{name:'upRate'},{name:'status'},{name:'note1'},{name:'note2'}
            ])
        });
        this.vtbar = new Ext.Toolbar({
			items: [
			    '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
			    '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			    '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this}
			]
		});
        this.vbbar= this.createPagingToolbar(PAGESIZE);
        this.vsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
        
        this.updateRateWindow = new UserUpdateWindow();
        
        OrgUserGrid.superclass.constructor.call(this, {
            region:'center',
            title: '利率列表',
            stripeRows: true,
            frame: true,
            height: height,
            viewConfig: {
                forceFit: false
            },
            loadMask: {
                msg : '正在载入数据,请稍候...'
            },
            sm: this.vsm,
            cm: new Ext.grid.ColumnModel([
                new Ext.grid.RowNumberer(),
                this.vsm,
                {header: 'id', width: 40, dataIndex: 'id',hidden:true},
                {header:'利率生效日期',dataIndex:'rateDate',width:100,align:'center',sortable: true},
                {header:'产品代码',dataIndex:'proCode',width:100,align:'center',sortable: true},
                {header:'利率种类代码',dataIndex:'rateCode',width:100,align:'center',sortable: true},
                {header:'基准利率(%)',dataIndex:'rate',width:100,align:'center',sortable: true},
                {header:'上浮比例(%)',dataIndex:'upRate',width:100,align:'center',sortable: true},
                {header: '状态', width: 50,align:'center', sortable: true, dataIndex: 'status',renderer:this.statusRenderer},
	            {header: 'note1', width: 40, dataIndex: 'note1',hidden:true},
	            {header: 'note2', width: 40, dataIndex: 'note2',hidden:true}
            ]),
            tbar: this.vtbar,
            bbar: this.vbbar,
            ds: this.store,
            listeners: {
                "dblclick": { fn: this.onModifyClick, scope: this} 		//响应双击事件
            }
        });
    },
    statusRenderer : function(value, metaData, record, rowIndex, colIndex, store) {
		if('0' == value) {
			return '<font color="green">正常</font>';
		} else {
			return '<font color="gray">历史</font>';
		}
	},
    selectedRecord: function() {
        var record = this.getSelectionModel().getSelected();
        return record;
    },
    refresh: function(){
        this.getView().refresh();
    },
    remove:function(record){
        this.getStore().remove(record);
    },
    onAddClick : function() {
    	
    	if(!RATE_CODE) {
    		Ext.Msg.alert('系统提示','请先双击选择左边一条利率种类数据！');
    		return;
    	}
    	orgTree.onAddRate2(RATE_CODE,PRO_CODE);
    },
    onModifyClick: function() {
    	var record = this.getSelectionModel().getSelected();
    	if(!record) {
    		Ext.Msg.alert('系统提示','请选择一条利率数据！');
    		return;
    	}
    	var curDate = new Date();
    	var rateDate = record.data.rateDate;
    	curDate = parseInt(curDate.format("Ymd"));
    	rateDate = parseInt(rateDate);
  		if(rateDate < curDate) {
  			Ext.MessageBox.alert("系统提示:", BLANKSTR + "不允许修改过往利率！" + BLANKSTR);
  			return;
  		}
    	var win = this.updateRateWindow;
    	win.show();
    	win.constructionForm.getForm().reset();
    	win.constructionForm.getForm().loadRecord(record);
    },
    onDeleteClick: function(){
    	var record = this.getSelectionModel().getSelected();
    	if(!record) {
    		Ext.Msg.alert('系统提示','请选择一条利率数据！');
    		return;
    	}
    	var curDate = new Date();
    	var rateDate = record.data.rateDate;
    	curDate = parseInt(curDate.format("Ymd"));
    	rateDate = parseInt(rateDate);
  		if(rateDate < curDate) {
  			Ext.MessageBox.alert("系统提示:", BLANKSTR + "不允许删除过往利率！" + BLANKSTR);
  			return;
  		}
    	Ext.Msg.confirm("提示", "确定删除该利率？", function(btn, text) {
            if (btn == 'yes') {
                this.body.mask('提交数据，请稍候...', 'x-mask-loading');
                Ext.Ajax.request({
		            url     : '/hmb/ll/deleteRate',
		            params  : {id : record.data.id, rateDate : record.data.rateDate},
		            success : function(response, options) {
		                this.body.unmask();
		                var res = Ext.decode(response.responseText);
		            	if(res.success) {
		            		Ext.MessageBox.alert('提示', '操作成功！');
		            		orgUserGrid.store.baseParams = {
		                 			rateCode 	: RATE_CODE, 
		                 			proCode 	: PRO_CODE
		                 	}; 
							orgUserGrid.store.load({
					            params:{start:0, limit:PAGESIZE}
					        });
		            	} else {
		            		if(res.message) {
								Ext.MessageBox.alert("系统提示：", res.message);
							} else {
								Ext.MessageBox.alert("系统提示：","删除失败！");
							}
		            	}
		                
		            },
		            failure: function(response, options) {
		            	this.body.unmask();
		            	var res = Ext.decode(response.responseText);
		            	if(res.message) {
							Ext.MessageBox.alert("系统提示：", res.message);
						} else {
							Ext.MessageBox.alert("系统提示：","操作失败！");
						}
					},
		            scope   : this
		        });
            }
        }, this);
    }
});
/*********************onReady 组件渲染及处理*************************************************/
Ext.onReady(function() {
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    Ext.override(Ext.Component, {
        stateful: false
    });    
    orgTree = new OrgTree(280, "");
    orgUserGrid = new OrgUserGrid();
    new Ext.Viewport({
    	layout: 'border',
    	items:[
		orgTree,
		orgUserGrid
    	]
    });
   
});