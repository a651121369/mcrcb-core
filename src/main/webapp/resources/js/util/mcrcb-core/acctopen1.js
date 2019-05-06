Ext.BLANK_IMAGE_URL = "/resources/js/ext/resources/images/default/s.gif";
//需要补充的空格
var BLANKSTR = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';   

var ORG_TREE_LOADER = '/fms/businessdata/queryAcctTable1.json'; //账户机构
var USER_GRID_STORE_URL = '/fms/businessdata/getMulAccts';
var USER_ROLE_RELATION_STORE_URL = '/user/pageQueryRoles4User.json';//用户授予角色时显示的角色关系列表
var PAGESIZE=20;
var SELNODE;//当前节点
/***********************OrgTree组件**************************
 *author        ：zhangyi
 *description   : 多级账户
 *date          : 2013-09-05
******************************************************************/
OrgTree  = Ext.extend(Ext.tree.TreePanel, {
	constructor : function(width, height) {
		this.rightMenu = new Ext.menu.Menu({
			items : [
			{
				text : '添加同级节点',
				iconCls : 'add',
				handler : this.onAddSiblingNode,
				scope : this
			}, '-', {
				text : '添加子节点',
				iconCls : 'add',
				handler : this.onAddChildNode,
				scope : this
			}, '-', {
				text : '修改节点',
				iconCls : 'edit',
				handler : this.onUpdateNode,
				scope : this
			}, '-', {
				text : '删除节点',
				iconCls : 'delete',
				handler : this.onDeleteNode,
				scope : this
			} 
			]
		});
		
		this.constructionInsertWindow = new ConstructionInsertWindow();       
        this.constructionUpdateWindow = new ConstructionUpdateWindow();
		
		OrgTree.superclass.constructor.call(this, {
			title : '账户机构',
			region:'west',
			collapsible :true,
			autoScroll : true,
			enableDD : true,//是否支持拖拽效果
			containerScroll : true,//是否支持滚动条
			width : width,
			height : height,
			border : true,
			frame : true,
			rootVisible : false,//是否显示根节点
			margins : '0 0 0 0',
			loader : new Ext.tree.TreeLoader({
				dataUrl : ORG_TREE_LOADER + "?node=0"
			}),
			root : new Ext.tree.AsyncTreeNode({
				id: "0",
				level: "0",
				hidden: false
			}),
			listeners : {
				'click': {fn: this.onLeftClick, scope: this},
				//'beforedblclick':{fn: this.beforedblClick, scope: this},
				'contextmenu' : {
					fn : this.onRightMenuClick,
					scope : this
				 },				
				'beforeload' : {//在节点加载之前触发，返回false取消操作
					fn : this.onBeforeLoad,
					scope : this
				}
			},
			tbar : new Ext.Toolbar({
				items : [ {
					iconCls : "add",
					text : "创建",
					handler : function() {
						var win = orgTree.constructionInsertWindow;
				    	win.constructionForm.getForm().reset();
				    	//win.constructionForm.idHidden.setValue(0);
				    	win.constructionForm.fatherNode.setValue('0');
				    	win.constructionForm.accProperty.setValue('2');
				    	win.show();
					}
				},{
					iconCls : "refresh",
					text : "刷新",
					handler : function() {
						orgTree.root.reload();
						orgTree.expandAll();
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
	onLeftClick : function(node, e) {
		var fnode = node.attributes.id;
		var fatherNode = node.attributes.fatherNode;
		SETNODE_PARENT = node.parentNode;
		if(node == this.getRootNode()) {
			orgUserGrid.setTitle("帐户列表");
			orgUserGrid.fatherNode = fnode;
			alert(fnode);
			if(fnode != 0){
				orgUserGrid.fatherNodeName = node.attributes.text;
			}
			orgUserGrid.seq = node.attributes.seq;
			orgUserGrid.level = node.attributes.level;
			orgUserGrid.store.baseParams = {fatherNode:fnode}; 
			orgUserGrid.store.load({params:{start:0,limit:PAGESIZE}});
			return;
		}
		var consName = node.text;
		var seq = node.attributes.seq;
		var level = node.attributes.level;
		orgUserGrid.setTitle(consName + "--帐户列表");
		//orgUserGrid.seq = seq;
		orgUserGrid.seq = seq;
		orgUserGrid.level= level;
		orgUserGrid.store.baseParams = {seq:seq}; 
		orgUserGrid.fatherNode = fnode;
		if(fatherNode != 0){
				orgUserGrid.fatherNodeName = consName;
			}
		//orgUserGrid.fatherNodeName = consName;
		orgUserGrid.store.baseParams = {fatherNode:fnode}; 
		orgUserGrid.store.load({params:{start:0,limit:PAGESIZE}});
		//orgUserGrid.fatherNode = fnode;
	},
	onRightMenuClick: function(node, e) {
		selNode = node;
		this.rightMenu.showAt(e.getXY());
	},
	beforedblClick: function(node, e) {
		orgUserGrid.store.baseParams = {orgId:node.id}; 
		orgUserGrid.store.load({
            params:{start:0, limit:PAGESIZE}
        });
		return false;
	},
	onBeforeLoad : function(node, e) {
		var pid = node.attributes.id;
		this.loader.dataUrl = ORG_TREE_LOADER + '?node=' + pid; //定义子节点的Loader
	},
    refreshNode: function(node) {
        this.getLoader().load(node, function() {
        	node.reload();
        });
    },
    onAddSiblingNode : function(node,e) {//右键添加
    	var win = this.constructionInsertWindow;
    	win.constructionForm.getForm().reset();
    	//win.constructionForm.idHidden.setValue(0);
    	win.constructionForm.fatherNode.setValue(selNode.attributes.fatherNode);
    	win.constructionForm.accProperty.setValue('1');
    	win.show();
    },
    onCreatFirstNode : function(node,e) {//创建
    	var win = this.constructionInsertWindow;
    	win.constructionForm.getForm().reset();
    	//win.constructionForm.idHidden.setValue(0);
    	win.constructionForm.fatherNode.setValue('0');
    	win.constructionForm.accProperty.setValue('1');
    	win.show();
    },
    onAddChildNode : function(node,e) {
    	var win = this.constructionInsertWindow;
    	win.constructionForm.getForm().reset();
    	//win.constructionForm.idHidden.setValue(0);
    	win.constructionForm.fatherNode.setValue(selNode.attributes.id);
    	win.constructionForm.accProperty.setValue('1');
    	win.show();
    },
    onUpdateNode : function(node,e) {
    	var win = this.constructionUpdateWindow;
    	win.constructionForm.getForm().reset();
    	//win.constructionForm.idHidden.setValue(0);
    	win.constructionForm.fatherNode.setValue(selNode.attributes.fatherNode);
    	win.constructionForm.idHidden.setValue(selNode.attributes.id);
    	win.constructionForm.acctName.setValue(selNode.attributes.text);
    	win.constructionForm.acctNo.setValue(selNode.attributes.acctNo);
    	win.constructionForm.accBalabce.setValue(selNode.attributes.accBalabce);
    	win.constructionForm.acctNo.setDisabled(true);
    	win.constructionForm.accProperty.setValue('1');
    	win.show();
    },
    onDeleteNode : function(node,e) {
    	var pid = selNode.attributes.id;
    	Ext.Ajax.request({
	        url: "/fms/businessdata/checkHasLaber?fatherNode="+pid,
	        method:"POST",
	        success: function(response,options){
	            var rootJson = Ext.util.JSON.decode(response.responseText);
	            if(rootJson.RESULT == "false"){
	            	Ext.Msg.confirm("提醒信息", "确定要删除 <span style='color:red'>" + selNode.attributes.text + "</span> 吗?",function(btn){
	    				if (btn == 'yes') {
	    			       	Ext.Ajax.request({
	    				       	   url:'/fms/businessdata/deleteAcctTable',
	    				       	   method : 'POST', 
	    				       	   params: { ids: pid},
	    			               success: function(form, action) { 
	    				               Ext.MessageBox.alert("系统提示:", BLANKSTR + "删除成功!" + BLANKSTR);
	    				               selNode.parentNode.reload();
	    			               },
	    			               failure: function(form, action) {
	    			            	   Ext.MessageBox.alert("系统提示:", BLANKSTR + "删除失败!" + BLANKSTR);
	    			               }
	    				       	});					
	    				}
	    	    	});	
	            }else{
	            	Ext.Msg.alert('错误',"<span style='color:red'>" + selNode.attributes.text + "</span> 存在下级机构或专户，请先删除下级机构和专户！");
	            	return;
	            }
	        },
	        failure: function(response,options){
                Ext.Msg.alert('错误',Ext.util.JSON.decode(response.responseText));
	        },
	        scope:this 
	    })
    }
    
});


/***************************************ConstructionForm组件**************************************************/
ConstructionForm = Ext.extend(Ext.ux.Form, {
    constructor: function() {
    	this.idHidden = this.createHidden('acctId','acctId');
    	this.acctName = this.createTextField('<font color="red">*</font>机构名称:', 'acctName', '90%','账户名称不能为空！');
        this.acctNo = this.createHidden('<font color="red">*</font>账号:', 'acctNo', '90%','帐号不能为空！');
        this.acctBalabce = this.createHidden('<font color="red">*</font>年利率:', 'acctBalabce', '90%');
        this.fatherNode = this.createHidden('<font color="red">*</font>上级账户:', 'fatherNode', '90%');
        this.accProperty = this.createHidden('<font color="red">*</font>账户属性:', 'accProperty', '90%');
        this.createTime1 = this.createHidden('<font color="red">*</font>上级账户:', 'createTime1', '90%');
//        this.acctNo.on('blur', function(e){
//			var bank_account = this.getValue();
//			Ext.Ajax.request({
//		        url: "/fms/businessdata/checkAcctNo?accProperty=2&acctNo="+bank_account,
//		        method:"POST",
//		        success: function(response,options){
//		            var rootJson = Ext.util.JSON.decode(response.responseText);
//		            if(rootJson.RESULT == "false"){
//		            }else{
//		            	Ext.Msg.alert('错误',"该帐号<span style='color:red'> "+bank_account+" </span> 已经存在，无需重新添加！");
//		            	this.setValue("");
//		            }
//		        },
//		        failure: function(response,options){
//	                Ext.Msg.alert('错误',Ext.util.JSON.decode(response.responseText));
//		        },
//		        scope:this 
//		    })
//         });
        ConstructionForm.superclass.constructor.call(this, {
        	anchor: '95%',
        	autoHeight:true,
        	layout:"form",
        	labelWidth: 60,
            labelAlign :'right',
            frame:true,
            bodyStyle:"padding: 5px 5px 0",
            width: '100%',
            items: [{xtype:'fieldset', 'title':'账户信息',collapsible : true,anchor:'100%',
				layout: 'tableform',
				layoutConfig: {columns: 2},
				defaults: {
                    columnWidth: .45,
                    anchor: '100%',
                    layout: "form"
                },
				items : [   
						this.acctName,
						this.acctNo,
						this.acctBalabce,
						this.fatherNode,
						this.accProperty,
						this.idHidden,
						this.createTime1
		        ]},
            this.idHidden    
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
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/fms/businessdata/saveMulAcct1', 
                 method: 'POST',
                 success: function(form, action) { 
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加成功!" + BLANKSTR);
                 	orgTree.constructionInsertWindow.hide();
//                 	orgTree.selNode.parentNode.reload();
                 	orgTree.root.reload();
                 	orgTree.expandAll();
                 },
                 failure: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加失败!" + BLANKSTR);
                 }
         	});
         }
     },
     updateFormClick: function() {       //修改
         if(this.getForm().isValid()) {
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/fms/accttable/saveAcctTable', 
                 method: 'POST',
                 success: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改成功!" + BLANKSTR);
                 	orgTree.constructionUpdateWindow.hide();
//                 	orgTree.selNode.parentNode.reload();
                 	orgTree.root.reload();
                 	orgTree.expandAll();
                 },
                 failure: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改失败!" + BLANKSTR);
                 }
         	});
         }
     },
     onCloseClick: function(){ 			//关闭
         this.ownerCt.hide();
     }
});


/***************************************ConstructionInsertWindow组件**************************************************/
ConstructionInsertWindow = Ext.extend(Ext.Window,{
	constructionForm : null,
    constructor: function(grid) {
        this.constructionForm = new ConstructionForm();
        this.constructionForm.buttons[0].show();   //隐藏添加按钮
    	this.constructionForm.buttons[1].hide();   //显示修改按钮
        ConstructionInsertWindow.superclass.constructor.call(this, {
            title: "添加账户信息",
            width: 850,
            anchor: '100%',
            autoHeight:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});

/***************************************ConstructionUpdateWindow组件**************************************************/
ConstructionUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.constructionForm = new ConstructionForm();
    	this.constructionForm.buttons[0].hide();   //隐藏添加按钮
    	this.constructionForm.buttons[1].show();   //显示修改按钮
    	ConstructionUpdateWindow.superclass.constructor.call(this, {
        	title: "修改专户机构",
            width: 800,
            autoHeight:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.constructionForm]
        });
    }
});

/***********************OrgUserGrid组件**************************
 *author        ： zhangyi
 *description   : 多级账户管理--账户列表
 *date          : 2013-09-05
******************************************************************/
ConstructionGrid = Ext.extend(UxGrid, {
	fatherNode:null,
	seq:null,
	level:null,
	pageSizeCombo: null,
	vtbar:null,				//面板顶部的工具条	
	vbbar:null,				//面板底部的工具条
    store:null,
    constructor: function(height, width){
    	this.store = new Ext.data.Store({          //Grid Store
            proxy: new Ext.data.HttpProxy({url: USER_GRID_STORE_URL, method: 'POST'}),
            reader: new Ext.data.JsonReader({totalProperty: 'total', root:'rows'},[
            		{name:'acctId'},{name:'acctName'},{name:'acctNo'},{name:'acctBalabce'},{name:'acctBalance'},{name:'product'},{name:'acctForm'}
            		,{name:'orgNo'},{name:'accProperty'},{name:'accCate'},{name:'currenSymbols'},{name:'balanceFlag'},{name:'balanceCyc'},{name:'balanceType'}
            		,{name:'seq'},{name:'level'},{name:'createUser'},{name:'createTime'},{name:'outState'}
            ])
        });
    	this.vbbar= this.createPagingToolbar(PAGESIZE);
    	this.vtbar = new Ext.Toolbar({
            items:[
                '-',{text:'添加',iconCls: 'add',handler:this.onAddClick,scope:this},
                '-',{text:'修改',iconCls: 'edit',handler:this.onModifyClick,scope:this},
            	'-',{text:'注销',iconCls: 'delete',handler:this.onDeleteClick,scope:this}
            ]
        });
        this.acctTableInsertWindow = new AcctTableInsertWindow();       
        this.acctTableUpdateWindow = new AcctTableUpdateWindow();
        var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true}); 
        ConstructionGrid.superclass.constructor.call(this, {
        	region:'center',
        	title: '专户基本信息',
        	stripeRows: true,
            frame: true,
            height: height,
            viewConfig: {
                forceFit: false
            },
            loadMask: {
                msg : '正在载入数据,请稍候...'
            },
            sm: sm,
            cm: new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
                                          sm,
                {header:'编号',dataIndex:'acctId',width:80,sortable: true, hidden:true},
                {header:'上级账户',dataIndex:'fatherNode',width:80,sortable: true, hidden:true},
                {header:'账户属性',dataIndex:'accProperty',width:80,sortable: true, hidden:true},
            	{header:'专户名称',dataIndex:'acctName',width:200,sortable: true},
            	{header:'银行账号',dataIndex:'acctNo',width:200,sortable: true},
            	{header:'年利率',dataIndex:'acctBalabce',width:100,sortable: true},
            	{header:'余额',dataIndex:'acctBalance',width:100,sortable: true},
            	{header:'累计积数',dataIndex:'product',width:100,sortable: true},
            	{header:'账户类型',dataIndex:'acctForm',width:100,sortable: true,renderer:function(data, metadata, record){
            		if(data ==1){
            			return '定期';
            		}else if(data ==2){
            			return '活期';
            		}else if(data ==3){
            			return '协议账户';
            		}else{
            			return data;
            		}
            		
            	}}
            ]),
            tbar: this.vtbar,
            bbar: this.vbbar,
            ds: this.store,
            listeners: {
                //"dblclick": { fn: this.onModifyClick, scope: this}, 		//响应双击事件
                //"rowcontextmenu": {fn: this.onRightMenuClick, scope: this}  //响应右击事件
            }
        });
    },
//    onRightMenuClick: function(grid, rowIndex, e) {//右键菜单
//        this.getSelectionModel().selectRow(rowIndex);
//        e.preventDefault();
//        this.rightMenu.showAt(e.getXY());
//    },
    onAddClick: function() {
    	if(this.fatherNode == null || this.fatherNode ==" "){
    		Ext.Msg.alert('系统提示', '请先选择右边机构！');
    		return;
    	}
    	Ext.Ajax.request({
	        url: "/fms/businessdata/checkBalanceFlag?fatherNode="+this.fatherNode,
	        method:"POST",
	        success: function(response,options){
	            var rootJson = Ext.util.JSON.decode(response.responseText);
	            if(rootJson.RESULT == "true"){
	            	Ext.Msg.alert('错误',"<span style='color:red'>" + this.fatherNodeName + "</span> 当前状态为计息状态，若要为其添加子账户，应先设为不计息！");
	            	return;
	            }else{
			    	var win = this.acctTableInsertWindow;
			    	win.acctTableForm.getForm().reset();
			    	win.acctTableForm.fatherNode.setValue(this.fatherNode);
			    	win.acctTableForm.seq.setValue(this.seq);
			    	win.acctTableForm.level.setValue(this.level);
			    	win.acctTableForm.fatherNodeName.setValue(this.fatherNodeName);
			    	win.acctTableForm.fatherNode.setdisp
			    	win.acctTableForm.accProperty.setValue(2);
			    	win.acctTableForm.accCate.setValue(1);
			    	win.acctTableForm.currenSymbols.setValue(1);
			    	win.acctTableForm.acctForm.setValue(2);
			    	win.acctTableForm.balanceType.setValue(1);
			    	win.acctTableForm.balanceFlag.setValue(0);
			    	win.acctTableForm.outState.setValue(1);
			    	win.show();
	            }
	        },
	        failure: function(response,options){
                Ext.Msg.alert('错误',Ext.util.JSON.decode(response.responseText));
	        },
	        scope:this 
	    })
    },
    onModifyClick: function() {
    	var records=this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   		    	var win = this.acctTableUpdateWindow;
   		    	win.acctTableForm.getForm().reset();
   		    	win.acctTableForm.createTime1.setValue(vrecord.get("createTime"));
   		    	win.acctTableForm.fatherNode.setValue(this.fatherNode);
   		    	win.acctTableForm.fatherNodeName.setValue(this.fatherNodeName);
             	win.acctTableForm.product.setReadOnly(true);
             	win.acctTableForm.acctNo.setReadOnly(true);
   		    	win.show();
   		    	win.acctTableForm.getForm().loadRecord(vrecord);
   		    	
   			}else{
   				Ext.Msg.alert('系统提示', '不能修改多条记录..');
   			}
   		}else{
   			Ext.Msg.alert('系统提示', BLANKSTR + '请选择一条记录' + BLANKSTR);
   		}    	
    },
    onDeleteClick: function() {
    	var records=this.getSelectionModel().getSelections();
    	var acctName = records[0].get("acctName");
    	var acctId = records[0].get("acctId");
   		if(records.length > 0) {
	    	Ext.Msg.confirm("提醒信息", "确定要注销 <span style='color:red'>"+acctName+"</span> 这条账户吗?",function(btn){
				if (btn == 'yes') {
					Ext.Ajax.request({
				        url: "/fms/businessdata/checkHasLaber?acctId="+acctId,
				        method:"POST",
				        success: function(response,options){
				            var rootJson = Ext.util.JSON.decode(response.responseText);
				            if(rootJson.RESULT == "false"){
				    			       		Ext.Ajax.request({
								       		   url:'/fms/businessdata/deleteRealAcctTable',
									       	   method : 'POST', 
									       	   params: { id:acctId},
								               success: function(form, action) { 
									               Ext.MessageBox.alert("系统提示:", BLANKSTR + "注销成功!" + BLANKSTR);
									               orgUserGrid.store.reload();
								               },
								               failure: function(form, action) {
								            	   Ext.MessageBox.alert("系统提示:", BLANKSTR + "注销失败!" + BLANKSTR);
								               }
									       	});			
				    	    	
				            }else{
				            	Ext.Msg.alert('错误',"<span style='color:red'>" +acctName+ "</span> 存在下级机构或专户，请先注销下级机构和专户！");
				            	return;
				            }
				        },
				        failure: function(response,options){
			                Ext.Msg.alert('错误',Ext.util.JSON.decode(response.responseText));
				        },
				        scope:this 
	    	});
				}
	    	});	
    	}else{
    		 Ext.Msg.alert('系统提示', BLANKSTR + '请选择一条记录' + BLANKSTR);
         	return;
    	}
    },
    sheetNoChange: function(value) {
    	return '<a href="javascript:void(0)" onclick=javascript:clickSheetNo('+value+')><b><font color=red>'+ value + '</font></b></a>';
    },
    clickSheetNo: function() {
    	//alert(11);
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
    formatTime : function(val) {
    		var valStr = val.constructor == Date ? Ext.util.Format.date(val, 'Y-m-d H:i:s') : formatDate(new Date(val["time"]),"yyyy-MM-dd HH:mm:ss");    		
    		var valArr = valStr.split('-');
    		var valDate = new Date(valArr[0], valArr[1] - 1, valArr[2]);
    		return valStr;
    }
});

/***************************************ConstructionInsertWindow组件**************************************************/
AcctTableInsertWindow = Ext.extend(Ext.Window,{
	acctTableForm : null,
    constructor: function(grid) {
        this.acctTableForm = new AcctTableForm();
        this.acctTableForm.buttons[0].show();   
    	this.acctTableForm.buttons[1].hide();   
    	AcctTableInsertWindow.superclass.constructor.call(this, {
            title: "添加账户信息",
            width: 800,
            anchor: '100%',
            autoHeight:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.acctTableForm]
        });
    }
});

/***************************************ConstructionUpdateWindow组件**************************************************/
AcctTableUpdateWindow = Ext.extend(Ext.Window, {
	acctTableForm : null,
    constructor: function() {
    	this.acctTableForm = new AcctTableForm();
    	this.acctTableForm.buttons[0].hide();   //隐藏添加按钮
    	this.acctTableForm.buttons[1].show();   //显示修改按钮
    	//this.acctTableForm.acctBalance.setReadOnly(true);
    	
    	AcctTableUpdateWindow.superclass.constructor.call(this, {
        	title: "修改专户信息",
            width: 800,
            autoHeight:true,
            resizable : false,
            plain: true,
            modal: true,
            autoScroll: true,
            closeAction: 'hide',
            items: [this.acctTableForm]
        });
    }
});



		
/***************************************ConstructionForm组件**************************************************/
AcctTableForm = Ext.extend(Ext.ux.Form, {
	
		
	
    constructor: function() {
    	//账户属性
    	this.accPropertyMode = this.createMemoryCombo('账户属性:','code','name','90%',ACC_PROPERTY,'accProperty');
    	this.accPropertyMode.allowBlank = true;
    	this.accPropertyMode.store.load(); 
    	//账户类别
    	this.accCateMode = this.createMemoryCombo('账户类别:','code','name','90%',ACC_CATE,'accCate');
    	this.accCateMode.allowBlank = true;
    	this.accCateMode.store.load();
    	//货币代号
    	this.currenSymbolsMode = this.createMemoryCombo('货币代号:','code','name','90%',CURREN_SYMBOLS,'currenSymbols');
    	this.currenSymbolsMode.allowBlank = true;
    	this.currenSymbolsMode.store.load();
    	//账户类型
    	this.acctFormMode = this.createMemoryCombo('账户类型:','code','name','90%',ACCT_FORM,'acctForm');
    	this.acctFormMode.allowBlank = true;
    	this.acctFormMode.store.load();
    	//计息类型
    	this.balanceTypeMode = this.createMemoryCombo('计息类型:','code','name','90%',BALANCE_TYPE,'balanceType');
    	this.balanceTypeMode.allowBlank = true;
    	this.balanceTypeMode.store.load();
    	//计息标志
    	this.balanceFlagMode = this.createMemoryCombo('计息标志:','code','name','90%',BALANCE_FLAG,'balanceFlag');
    	this.balanceFlagMode.allowBlank = true;
    	this.balanceFlagMode.store.load();
    	
    	
    	
    	this.idHidden = this.createHidden('acctId','acctId');
    	this.outState = this.createHidden('outState','outState');
        this.acctNo = this.createTextField('<font color="red">*</font>账户号:', 'acctNo', '90%','银行卡号不能为空！');
        this.acctName = this.createTextField('<font color="red">*</font>账户名称:', 'acctName', '90%','账户名称不能为空！');
        this.orgNo = this.createTextField('开户机构号:', 'orgNo', '90%');
        this.accProperty =this.accPropertyMode;
        this.accCate = this.accCateMode;
        this.currenSymbols = this.currenSymbolsMode;
        this.fatherNodeName = this.createTextField1('上级账户:', 'fatherNodeName', '90%');
        this.fatherNode = this.createHidden('<font color="red">*</font>上级账户:', 'fatherNode', '90%');
        //序列
    	this.seq = this.createHidden('<font color="red">*</font>上级账户:', 'seq', '90%');
    	//层级
    	this.level = this.createHidden('<font color="red">*</font>上级账户:', 'level', '90%');
    	this.createUser = this.createHidden('<font color="red">*</font>上级账户:', 'createUser', '90%');
    	this.createTime1 = this.createHidden('<font color="red">*</font>上级账户:', 'createTime1', '90%');
        this.acctForm = this.acctFormMode;
        this.acctBalance = this.createTextField('<font color="red">*</font>账户余额:', 'acctBalance', '90%');
        this.balanceFlag = this.balanceFlagMode;
        this.balanceCyc = this.createTextField('<font color="red">*</font>3个月利率:', 'balanceCyc', '90%','计息周期不能为空！');
        this.acctBalabce = this.createTextField('<font color="red">*</font>年利率:', 'acctBalabce', '90%');
        this.product = this.createTextField('积数:', 'product', '90%');
        this.balanceType = this.balanceTypeMode;
        
        this.acctNo.on('blur', function(e){
			var bank_account = this.getValue();
			Ext.Ajax.request({
				url: "/fms/businessdata/checkAcctNo?accProperty=2&acctNo="+bank_account,
		        method:"POST",
		        success: function(response,options){
		            var rootJson = Ext.util.JSON.decode(response.responseText);
		            if(rootJson.RESULT == "false"){
		            	
		            }else{
		            	Ext.Msg.alert('错误',"该专户帐号为 <span style='color:red'> "+bank_account+" </span> 已经存在，无需重新添加！");
		            	this.setValue("");
		            }
		        },
		        failure: function(response,options){
	                Ext.Msg.alert('错误',Ext.util.JSON.decode(response.responseText));
		        },
		        scope:this 
		    })
         });
        AcctTableForm.superclass.constructor.call(this, {
        	anchor: '95%',
        	autoHeight:true,
        	layout:"form",
        	labelWidth: 70,
            labelAlign :'right',
            frame:true,
            bodyStyle:"padding: 5px 5px 0",
            width: '100%',
            items: [{xtype:'fieldset', 'title':'账户信息',collapsible : true,anchor:'100%',
				layout: 'tableform',
				layoutConfig: {columns: 2},
				defaults: {
                    columnWidth: .45,
                    anchor: '100%',
                    layout: "form"
                },
				items : [   
				        this.acctNo,
				        this.accCate,
				        this.acctName,
				        this.accProperty,
				        this.orgNo,
				        this.currenSymbols,
				        this.fatherNodeName,
				        this.acctForm,
				        this.acctBalance,
				        this.balanceFlag,
				        this.acctBalabce,
				        this.balanceType,
				        this.product,
				        this.balanceCyc
		        ]},
		    this.outState,
            this.idHidden,
            this.fatherNode,
            this.seq,
    		this.level,
    		this.createUser,
    		this.createTime1
            ],
            buttonAlign :'center',
            buttons: [
                      {text: '保存', width: 20,iconCls: 'save', hidden: false, handler: this.addFormClick, scope: this},  
                      {text: '修改', iconCls: 'edit',handler:this.updateFormClick,scope:this},
                      {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
              ]
        });
     },
     addFormClick: function() {
         if(this.getForm().isValid()) {
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/fms/businessdata/saveMulAcct', 
                 method: 'POST',
                 params:{
                 },
                 success: function(form, action) { 
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加成功!" + BLANKSTR);
                 	orgUserGrid.acctTableInsertWindow.hide();
                 	orgUserGrid.vbbar.doLoad(orgUserGrid.vbbar.cursor);
                 },
                 failure: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "添加失败!" + BLANKSTR);
                 }
         	});
         }
     },
     updateFormClick: function() {       //修改
         if(this.getForm().isValid()) {
         	var createTime1 = this.createTime1.getValue();
         	this.getForm().submit({
                 waitMsg: '正在提交数据...',
                 url: '/fms/businessdata/saveMulAcct', 
                 method: 'POST',
                 params:{
                 	createTime1:createTime1
                 },
                 success: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改成功!" + BLANKSTR);
                 	orgUserGrid.acctTableUpdateWindow.hide();
                 	orgUserGrid.vbbar.doLoad(orgUserGrid.vbbar.cursor);
                 },
                 failure: function(form, action) {
                 	Ext.MessageBox.alert("系统提示:", BLANKSTR + "修改失败!" + BLANKSTR);
                 }
         	});
         }
     },
     onCloseClick: function(){ 			//关闭
         this.ownerCt.hide();
     },
     
     
    createTextField1: function(fieldLabel, name, anchor, blankText, vtype, maxLength, maxLengthText,inputType, colspan) {    //生成一个通用的TextField
        var tf = new Ext.form.TextField({
            fieldLabel: fieldLabel,
            name: name,
            xtype: 'textfield',
            readOnly: true,
            allowBlank: true,
            anchor: anchor,
            blankText: '该选项为必填项,请输入内容...',
            vtype: vtype,
            maxLength: maxLength,
            maxLengthText: maxLengthText,
            inputType:inputType,
            colspan: colspan
        });
        return tf;
    }
    
    
    
});

/*********************onReady 组件渲染及处理*************************************************/
Ext.onReady(function() {
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    orgTree = new OrgTree(260, "");
    orgUserGrid = new ConstructionGrid();
    new Ext.Viewport({
    	layout: 'border',
    	items:[
		orgTree,
		orgUserGrid
    	]
    });
   
});