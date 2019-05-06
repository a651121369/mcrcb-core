var ENTITY_URL_LIST = "/WjwPaydetail/modifyPaydetail";
var ENTITY_URL_UPDATE = "/WjwPaydetail/appUpdate";
var ENTITY_URL_DELETE = "/WjwPaydetail/appDelete";
var URL_GET_ZBDETAILS = "/WjwPaydetail/findZbDetails";
var URL_GET_ECONOMIC = "/WjwPaydetail/findEconomic";
var PAGESIZE=50;
/*
 * WJW_PAYDETAIL 
 * @author liuyong
 * @date 2015-12-16
 */

var dataStore = new Ext.data.JsonStore({  
    autoLoad : true,  
    fields:['itemId', 'itemName' ],  
    data: [  
        {itemId:'1',itemName:'医疗'},  
        {itemId:'2',itemName:'药品'}          
    ]  
});  
var dataStore2 = new Ext.data.JsonStore({  
    autoLoad : true,  
    fields:['payId', 'payName' ],  
    data: [  
        {payId:'1',payName:'现金'},  
        {payId:'2',payName:'转账'}          
    ]  
});  
WjwPaydetail = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.id_ = this.createHidden('ID','id');
		this.amount = new Ext.form.NumberField({
          fieldLabel: '<font color="red">*</font>金额',
          name: 'amount',
          allowBlank: false,
          allowNegative :false,
          maxLength:21,
          maxLengthText:'长度超过不能21', 
          anchor: '95%',
          cls:'forbiddenZH',
          blankText: '该选项为必填项,请输入内容...'
      });
		this.unitName = this.createTextField('机构名称','unitName','95%','',null,200,'长度超过不能200');
		this.inAccno = this.createTextField('<font color="red">*</font>收款人账号','inAccno','95%','',null,32,'长度超过不能32');
		this.inName = this.createTextField('<font color="red">*</font>收款人名称','inName','95%','',null,200,'长度超过不能200');
		this.inBank = this.createTextField('<font color="red">*</font>收款人开户行','inBank','95%','',null,200,'长度超过不能200');
		this.payTime = this.createTextField('支付申请日期','payTime','95%','',null,30,'长度超过不能30');
		this.zjFld = this.createTextField('资金性质','zjFld','95%','',null,60,'长度超过不能60');
		this.payWay = new Ext.form.ComboBox({
			fieldLabel:'<font color="red">*</font>结算方式',
			name:"payWay",
			mode:'local',
			allowBlank:true,
			anchor:'95%',
			hiddenName:'payWay',
			valueField : "payId",
			displayField : "payName",
			editable : false,
			frame : true,
			layout : 'form',
			forceSelection : true,
			isFormField: true,
			triggerAction : 'all',
			store:dataStore2
		});
		this.topYsdw = this.createTextField('一级预算单位','topYsdw','95%','',null,30,'长度超过不能30');
		this.footYsdw = this.createTextField('基层预算单位','footYsdw','95%','',null,30,'长度超过不能30');
		this.itmeYs = this.createTextField('预算科目','itmeYs','95%','',null,30,'长度超过不能30');
		this.funcFl = this.createTextField('功能分类','funcFl','95%','',null,60,'长度超过不能60');
//		this.ecnoFl = this.createTextField('经济分类','ecnoFl','95%','',null,60,'长度超过不能60');
		this.ecnoFl = new Ext.form.ComboBox({
				   fieldLabel:'经济分类',
				   anchor:'95%',
              	   autoLoad: true,
	               triggerAction: 'all',
	               mode: 'remote',
	               name:'ecnoFl',
	               hiddenName :'ecnoFl',
	               allowBlank: true,
	               displayField:'ecName',
	               valueField:'ecName',
	               forceSelection:false,
	               store: new Ext.data.Store({
	                   proxy: new Ext.data.HttpProxy({url:URL_GET_ECONOMIC, method: 'POST'}),
	                   reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'ecCode'},{name:'ecName'}]))
	               }),
	               editable : true,
	               listeners : {
	               	'beforequery':function(e){  
	               		var combo = e.combo; 
	               		combo.collapse();
	                   	if(!e.forceAll){  
	                   		var value = e.query;  
	                   		combo.store.filterBy(function(record,id){  
	                   			var text = record.get(combo.displayField);  
	                   			//用自己的过滤规则,如写正则式  
	                   			return (text.indexOf(value)!=-1);    //实现的核心
	                   		}); 
	                   		combo.onLoad(); 
	                   		combo.expand();  
	                   		return false;  
	                   	}
	               	},
	               	'focus':function(combo){  
	                   	combo.store.load();
	               	} 
	               }  
	           });
//		this.zbDetail = this.createTextField('<font color="red">*</font>指标摘要','zbDetail','95%','',null,60,'长度超过不能60');
		this.zbDetail = new Ext.form.ComboBox({
			  fieldLabel:'<font color="red">*</font>指标摘要',
			  anchor:'95%',
			  autoLoad: true,
              triggerAction: 'all',
              mode: 'remote',
              name:'zbDetail',
              hiddenName :'zbDetail',
              allowBlank: false,
              displayField:'zbName',
              valueField:'zbName',
              forceSelection:true,
              store: new Ext.data.Store({
                  proxy: new Ext.data.HttpProxy({url:URL_GET_ZBDETAILS, method: 'POST'}),
                  reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'zbCode'},{name:'zbName'}]))
              }),
              editable : true,
              listeners : {
              	'beforequery':function(e){  
              		var combo = e.combo; 
              		combo.collapse();
                  	if(!e.forceAll){  
                  		var value = e.query;  
                  		combo.store.filterBy(function(record,id){  
                  			var text = record.get(combo.displayField);  
                  			//用自己的过滤规则,如写正则式  
                  			return (text.indexOf(value)!=-1);    //实现的核心
                  		}); 
                  		combo.onLoad(); 
                  		combo.expand();  
                  		return false;  
                  	}
              	},
              	'focus':function(combo){  
                  	combo.store.load();
              	} 
              }  
          });
		this.yt = this.createTextField('<font color="red">*</font>用途','yt','95%','',null,30,'长度超过不能15');
		this.item = new Ext.form.ComboBox({
			fieldLabel:'<font color="red">*</font>科目',
			name:"item",
			mode:'local',
			allowBlank:true,
			anchor:'95%',
			hiddenName:'item',
			valueField : "itemId",
			displayField : "itemName",
			editable : false,
			frame : true,
			layout : 'form',
			forceSelection : true,
			isFormField: true,
			triggerAction : 'all',
			store:dataStore
		});
		this.note1 = this.createTextField('备注','note1','95%','',null,100,'长度超过不能200');

		this.amount.allowBlank = false;
		this.unitName.allowBlank = false;
		this.inAccno.allowBlank = false;
		this.inName.allowBlank = false;
		this.inBank.allowBlank = false;
		this.payTime.allowBlank = false;
		this.zjFld.allowBlank = true;
		this.payWay.allowBlank = false;
		this.topYsdw.allowBlank = true;
		this.footYsdw.allowBlank = true;
		this.itmeYs.allowBlank = true;
		this.funcFl.allowBlank = true;
		this.ecnoFl.allowBlank = true;
		this.zbDetail.allowBlank = false;
		this.yt.allowBlank = true;
		this.item.allowBlank = false;
		this.note1.allowBlank = true;

        
        WjwPaydetail.superclass.constructor.call(this, {
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
					this.amount,
					this.inAccno,
					this.item,
					this.inName,
					this.payWay,
					this.inBank,
					this.payTime,
					this.zbDetail,
					this.yt,									
					this.ecnoFl,
					this.zjFld,					
					this.topYsdw,
					this.footYsdw,
					this.itmeYs,
					this.funcFl,								
					this.note1,
					this.id_,

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

	updateFormClick: function(){
		var connNo = document.getElementById('conn').value;
		
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交，请稍后...',
				url: ENTITY_URL_UPDATE,
				method: 'POST',
				success: function(form,action){
					Ext.MessageBox.alert("系统提示：","修改成功！");
					wjwPaydetailGrid.wjwPaydetailUpdateWindow.hide();
					wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE,connNo:connNo}});
//					wjwPaydetailGrid.vbbar.doLoad(wjwPaydetailGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwPaydetailGrid.wjwPaydetailUpdateWindow)
    	wjwPaydetailGrid.wjwPaydetailUpdateWindow.wjwPaydetail.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwPaydetailGrid.onModifyClick();
    }
	
});



/********************WjwPaydetailUpdateWindow组件*************************/
WjwPaydetailUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwPaydetail = new WjwPaydetail();
    	this.wjwPaydetail.buttons[0].hide();   //隐藏添加按钮
    	this.wjwPaydetail.buttons[1].show();   //显示修改按钮
    	this.wjwPaydetail.buttons[2].show();   //显示重置按钮
    	this.wjwPaydetail.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwPaydetailUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwPaydetail]
        });
    }
});

/****************WjwPaydetailGrid***********************/
WjwPaydetailGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},{name:'amount'},{name:'unitName'},
		            {name:'inAccno'},{name:'inName'},{name:'inBank'},
		            {name:'payTime'},{name:'zjFld'},{name:'payWay'},
		            {name:'topYsdw'},{name:'footYsdw'},{name:'itmeYs'},
		            {name:'funcFl'},{name:'ecnoFl'},{name:'zbDetail'},
		            {name:'status'},{name:'yt'},{name:'item'},
		            {name:'connNo'},{name:'note1'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this}
//
//			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
//			       			var params = {};
//			       			wjwPaydetailGrid.vtbar.items.each(function(item,index,length){ 
//		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
//		       						if (item.getXType() == 'datefield') {
//		       							params[item.getId()] = item.getValue().format(item.format);
//		       						} else {
//		       							params[item.getId()] = item.getValue();
//		       						}
//		       					}
//							});
//	    	   				wjwPaydetailGrid.store.baseParams= params;
//	    	   				wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
//	    	   			}
//	       			},
//					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
//						wjwPaydetailGrid.vtbar.items.each(function(item,index,length){   
//							if((""+item.getXType()).indexOf("field") != -1) {
//								item.setValue('');
//							}
//						  });  
//    	   			}}
			]
		});
//		this.vbbar = this.createPagingToolbar(PAGESIZE);
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:100,sortable:true,hidden:true},
		            {header:'机构名称',dataIndex:'unitName',width:200,sortable:true,hidden:false},
		            {header:'收款人账号',dataIndex:'inAccno',width:200,sortable:true,hidden:false},
		            {header:'收款人名称',dataIndex:'inName',width:300,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'inBank',width:300,sortable:true,hidden:false},
		            {header:'金额',dataIndex:'amount',width:100,sortable:true,hidden:false},
		            {header:'科目',dataIndex:'item',width:100,sortable:true,hidden:false,
		            	renderer:function(data){
		            		 if(data ==1){
			            		return '医疗';
			            	}else if(data ==2){
			            		return '药品';
			            	}
		            	}
		            },
		            {header:'支付方式',dataIndex:'payWay',width:100,sortable:true,hidden:false,
		               	renderer:function(data){
		            		 if(data ==1){
			            		return '现金';
			            	}else if(data ==2){
			            		return '转账';
			            	}
		            	}	
		            },
		            {header:'支付申请日期',dataIndex:'payTime',width:150,sortable:true,hidden:false},
		            {header:'指标摘要',dataIndex:'zbDetail',width:150,sortable:true,hidden:false},
		            {header:'用途',dataIndex:'yt',width:200,sortable:true,hidden:false},
		            {header:'状态',dataIndex:'status',width:100,sortable:true,hidden:false,
		            	renderer:function(data){
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
		            {header:'经济分类',dataIndex:'ecnoFl',width:100,sortable:true,hidden:false},
		            {header:'资金性质',dataIndex:'zjFld',width:100,sortable:true,hidden:false},
		            {header:'一级预算单位',dataIndex:'topYsdw',width:100,sortable:true,hidden:false},
		            {header:'基层预算单位',dataIndex:'footYsdw',width:100,sortable:true,hidden:false},
		            {header:'预算科目',dataIndex:'itmeYs',width:100,sortable:true,hidden:false},
		            {header:'功能分类',dataIndex:'funcFl',width:100,sortable:true,hidden:false},
		            {header:'关联号',dataIndex:'connNo',width:100,sortable:true,hidden:true},
		            {header:'备注',dataIndex:'note1',width:100,sortable:true,hidden:false}
		           ]);
		WjwPaydetailGrid.superclass.constructor.call(this,{
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
    	if(!this.wjwPaydetailWindow)
    		this.wjwPaydetailWindow = new WjwPaydetailWindow();
    	var win = this.wjwPaydetailWindow;
		win.show();
		win.wjwPaydetail.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwPaydetailUpdateWindow)
					this.wjwPaydetailUpdateWindow = new WjwPaydetailUpdateWindow();
   				
   		    	var win = this.wjwPaydetailUpdateWindow;
				var winForm = win.wjwPaydetail;
				
				win.show();			
   		    	winForm.id_.setValue(vrecord.data['id']);
				winForm.amount.setValue(vrecord.data.amount);
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.unitName.setReadOnly(true);
				winForm.inAccno.setValue(vrecord.data.inAccno);
				winForm.inName.setValue(vrecord.data.inName);
				winForm.inBank.setValue(vrecord.data.inBank);
				winForm.payTime.setValue(vrecord.data.payTime);
				winForm.payTime.setReadOnly(true);
				winForm.zjFld.setValue(vrecord.data.zjFld);
				winForm.payWay.setValue(vrecord.data.payWay);
				winForm.topYsdw.setValue(vrecord.data.topYsdw);
				winForm.footYsdw.setValue(vrecord.data.footYsdw);
				winForm.itmeYs.setValue(vrecord.data.itmeYs);
				winForm.funcFl.setValue(vrecord.data.funcFl);
				winForm.ecnoFl.setValue(vrecord.data.ecnoFl);
				winForm.zbDetail.setValue(vrecord.data.zbDetail);
				winForm.yt.setValue(vrecord.data.yt);
				winForm.item.setValue(vrecord.data.item);
				winForm.note1.setValue(vrecord.data.note1);
   		    	
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
    		var connNo = document.getElementById('conn').value;
    		Ext.Msg.confirm('系统提示：',"确定删除这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_DELETE,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','删除成功！');
    						wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE,connNo:connNo}});
//    						wjwPaydetailGrid.vbbar.doLoad(wjwPaydetailGrid.vbbar.cursor);
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
    var connNo = document.getElementById('conn').value;
    wjwPaydetailGrid = new WjwPaydetailGrid(Ext.getBody().getViewSize().height);
    wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE,connNo:connNo}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaydetailGrid   
		]
	});
});

