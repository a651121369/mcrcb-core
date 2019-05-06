var ENTITY_URL_LIST = "/WjwBfdetail/pager";
var ENTITY_URL_LIST2 = "/WjwBfdetail/getBfdetailList";
var ENTITY_URL_SAVE = "/WjwBfdetail/save";
var ENTITY_URL_UPDATE = "/WjwBfdetail/update";
var ENTITY_URL_DELETE = "/WjwBfdetail/delete";
var PAGESIZE=50;
//var store2 = new Ext.data.Store({
//	autoLoad: true,
//	proxy : new Ext.data.HttpProxy({
//		url : '/AccountManager/getOrgInfo',
//		method : 'POST'
//	}),
//	reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'orgNo'}, {name:'orgName'}]))
//});
/*
 * WJW_BFDETAIL 
 * @author chenyong
 * @date 2015-11-04
 */
WjwBfdetail = Ext.extend(Ext.ux.Form,{
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
		this.amount = this.createTextField('AMOUNT','amount','95%','',null,21,'长度超过不能21');
		this.unitName = this.createTextField('UNIT_NAME','unitName','95%','',null,60,'长度超过不能60');
		this.bfTime = this.createTextField('BF_TIME','bfTime','95%','',null,32,'长度超过不能32');
		this.bfItem = new Ext.form.NumberField({
            fieldLabel: '1-药品，2-医疗，3-其他',
            name: 'bfItem',
            allowBlank: false,
            allowNegative :false,
            maxLength:10,
            maxLengthText:'长度超过不能10', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.inBfqAmt = this.createTextField('IN_BFQ_AMT','inBfqAmt','95%','',null,21,'长度超过不能21');
		this.inBfhAmt = this.createTextField('IN_BFH_AMT','inBfhAmt','95%','',null,21,'长度超过不能21');
		this.outBfqAmt = this.createTextField('OUT_BFQ_AMT','outBfqAmt','95%','',null,21,'长度超过不能21');
		this.outBfhAmt = this.createTextField('OUT_BFH_AMT','outBfhAmt','95%','',null,21,'长度超过不能21');
		this.connNo = this.createTextField('CONN_NO','connNo','95%','',null,32,'长度超过不能32');
		this.drugBfAmt = this.createTextField('DRUG_BF_AMT','drugBfAmt','95%','',null,21,'长度超过不能21');
		this.medcBfAmt = this.createTextField('MEDC_BF_AMT','medcBfAmt','95%','',null,21,'长度超过不能21');
		this.note1 = this.createTextField('NOTE1','note1','95%','',null,100,'长度超过不能100');
		this.note2 = this.createTextField('NOTE2','note2','95%','',null,100,'长度超过不能100');
		
		this.unitNo.allowBlank = true;
		this.amount.allowBlank = true;
		this.unitName.allowBlank = true;
		this.bfTime.allowBlank = true;
		this.bfItem.allowBlank = true;
		this.inBfqAmt.allowBlank = true;
		this.inBfhAmt.allowBlank = true;
		this.outBfqAmt.allowBlank = true;
		this.outBfhAmt.allowBlank = true;
		this.connNo.allowBlank = true;
		this.drugBfAmt.allowBlank = true;
		this.medcBfAmt.allowBlank = true;
		this.note1.allowBlank = true;
		this.note2.allowBlank = true;
        
        WjwBfdetail.superclass.constructor.call(this, {
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
					this.amount,
					this.unitName,
					this.bfTime,
					this.bfItem,
					this.inBfqAmt,
					this.inBfhAmt,
					this.outBfqAmt,
					this.outBfhAmt,
					this.connNo,
					this.drugBfAmt,
					this.medcBfAmt,
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
					wjwBfdetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
					wjwBfdetailGrid.wjwBfdetailWindow.hide();
					
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
					wjwBfdetailGrid.wjwBfdetailUpdateWindow.hide();
					wjwBfdetailGrid.vbbar.doLoad(wjwBfdetailGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwBfdetailGrid.wjwBfdetailUpdateWindow)
    	wjwBfdetailGrid.wjwBfdetailUpdateWindow.wjwBfdetail.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwBfdetailGrid.onModifyClick();
    }
	
});

/**************WjwBfdetailWindow*********************/
WjwBfdetailWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwBfdetail = new WjwBfdetail();
		WjwBfdetailWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 700,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwBfdetail]
		});
	}
});

/********************WjwBfdetailUpdateWindow组件*************************/
WjwBfdetailUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwBfdetail = new WjwBfdetail();
    	this.wjwBfdetail.buttons[0].hide();   //隐藏添加按钮
    	this.wjwBfdetail.buttons[1].show();   //显示修改按钮
    	this.wjwBfdetail.buttons[2].show();   //显示重置按钮
    	this.wjwBfdetail.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwBfdetailUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwBfdetail]
        });
    }
});

/****************WjwBfdetailGrid***********************/
WjwBfdetailGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST2,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'unitNo'},
		            {name:'amount'},
		            {name:'unitName'},
		            {name:'bfTime'},
		            {name:'bfItem'},
		            {name:'inBfqAmt'},
		            {name:'inBfhAmt'},
		            {name:'outBfqAmt'},
		            {name:'outBfhAmt'},
		            {name:'connNo'},
		            {name:'drugBfAmt'},
		            {name:'medcBfAmt'},
		            {name:'note1'},
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
//			       '-',{xtype:'button',text:'添加',iconCls:'add',handler:this.onAddClick,scope:this}, 
//			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this}, 
//			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onDeleteClick,scope:this},
				//	'-',{xtype:'label',text:'ID'},{xtype:'textfield',id:'id'},
//					'-',{xtype:'label',text:'机构号'},{xtype:'textfield',id:'unitNo'},
//					'-',{xtype:'label',text:'机构名称'},{xtype:'textfield',id:'unitName'},
//					'-',{xtype:'label',text:'关联号'},{xtype:'textfield',id:'connNo'},
					 '-',{xtype:'label',text:'机构编号'},
//					 new Ext.form.ComboBox({
//						valueField : "orgNo",
//						displayField : "orgName",
//						mode : 'remote',
//						resizable:false,
//						listWidth : 260,
//						forceSelection : true,
//						blankText : '请选择...',
//						emptyText : '请选择...',
//						lastQuery: '',
//						id:'unitNo',
//						editable : false,
//						submitValue : true,
//						triggerAction : 'all',
//						allowBlank : true,
//						anchor : '95%',
//						//pageSize:100,
//						store : store2,
//						listeners : {  
//				            'beforequery':function(e){  
//				                var combo = e.combo;    
//				                if(!e.forceAll){    
//				                    var input = e.query;    
//				                    // 检索的正则   
//				                    var regExp = new RegExp(".*" + input + ".*");  
//				                    // 执行检索   
//				                    combo.store.filterBy(function(record,id){    
//				                        // 得到每个record的项目名称值   
//				                        var text = record.get(combo.displayField);    
//				                        return regExp.test(text);   
//				                    });  
//				                    combo.expand();    
//				                    return false;  
//				                }  
//				            }
//				        }
//						
//					}),
					 
					 new Ext.form.ComboBox({
				        	autoLoad: true,
				            typeAhead: false,
				            emptyText: '请选择...',
				            triggerAction: 'all',
				            isFormField: true,
//				            mode: 'local',
				            mode: 'remote',
				            hiddenName :'NAME',
				            name:'NAME',
				            allowBlank: true,
				            blankText:'请选择...',
				            forceSelection: false,
				            lastQuery: '',
				            displayField:'NAME',
				            valueField:'CODE',
				            id:'unitNo',
				            selectOnFocus: true,
				            store: new Ext.data.Store({
				                proxy: new Ext.data.HttpProxy({url: '/public/getOrganizationbf', method: 'POST'}),
				                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'CODE'},{name:'NAME'}]))
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
				        }),
					 
					'-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,value:''},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,value:''},
					//'-',{xtype:'label',text:'BF_TIME'},{xtype:'textfield',id:'Q_bfTime_S_LK'},
					//'-',{xtype:'label',text:'1-药品，2-医疗，3-其他'},{xtype:'textfield',id:'Q_bfItem_N_EQ'},
					//'-',{xtype:'label',text:'IN_BFQ_AMT'},{xtype:'textfield',id:'Q_inBfqAmt_S_EQ'},
					//'-',{xtype:'label',text:'IN_BFH_AMT'},{xtype:'textfield',id:'Q_inBfhAmt_S_EQ'},
					//'-',{xtype:'label',text:'OUT_BFQ_AMT'},{xtype:'textfield',id:'Q_outBfqAmt_S_EQ'},
					//'-',{xtype:'label',text:'OUT_BFH_AMT'},{xtype:'textfield',id:'Q_outBfhAmt_S_EQ'},
					//'-',{xtype:'label',text:'CONN_NO'},{xtype:'textfield',id:'Q_connNo_S_LK'},
					//'-',{xtype:'label',text:'DRUG_BF_AMT'},{xtype:'textfield',id:'Q_drugBfAmt_S_EQ'},
					//'-',{xtype:'label',text:'MEDC_BF_AMT'},{xtype:'textfield',id:'Q_medcBfAmt_S_EQ'},
					//'-',{xtype:'label',text:'NOTE1'},{xtype:'textfield',id:'Q_note1_S_LK'},
					//'-',{xtype:'label',text:'NOTE2'},{xtype:'textfield',id:'Q_note2_S_LK'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			    	   if(Ext.getCmp('endTime').getValue()>0 && Ext.getCmp('startTime').getValue() > Ext.getCmp('endTime').getValue()){
	    	   				Ext.Msg.alert('系统提示', '开始日期不能大于结束日期');
	    	   				return;
	    	   			}
			       			var params = {};
			       			wjwBfdetailGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwBfdetailGrid.store.baseParams= params;
	    	   				wjwBfdetailGrid.store.load({params:{start:0,limit:PAGESIZE,unitNo:Ext.getCmp('unitNo').getValue()}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwBfdetailGrid.vtbar.items.each(function(item,index,length){   
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
		     //       {header:'ID',dataIndex:'id',width:30,sortable:true,hidden:false},
		            {header:'机构号',dataIndex:'unitNo',width:80,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:100,sortable:true,hidden:false},
		            {header:'拨付金额',dataIndex:'amount',width:80,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            
		            {header:'药品拨付金额',dataIndex:'drugBfAmt',width:100,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'医疗拨付金额',dataIndex:'medcBfAmt',width:100,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'收入账户拨付前余额',dataIndex:'inBfqAmt',width:140,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'收入账户拨付后余额',dataIndex:'inBfhAmt',width:140,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'支出账户拨付前余额',dataIndex:'outBfqAmt',width:140,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'支出账户拨付后余额',dataIndex:'outBfhAmt',width:140,sortable:true,hidden:false,align: 'right',
            			renderer: function(value){
            				return ''+Ext.util.Format.number(value,'0,000.00')+'';
            		}},
		            {header:'拨付时间',dataIndex:'bfTime',width:75,sortable:true,hidden:false,renderer:
		            	function(value){
	            		return value.substring(0,8);
	            	}
	            }
			           // {header:'1-药品，2-医疗，3-其他',dataIndex:'bfItem',width:100,sortable:true,hidden:false},
//			        {header:'关联号',dataIndex:'connNo',width:120,sortable:true,hidden:false},
//		            {header:'备注1',dataIndex:'note1',width:100,sortable:true,hidden:false},
//		            {header:'备注2',dataIndex:'note2',width:100,sortable:true,hidden:false}
		           ]);
		WjwBfdetailGrid.superclass.constructor.call(this,{
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
    	if(!this.wjwBfdetailWindow)
    		this.wjwBfdetailWindow = new WjwBfdetailWindow();
    	var win = this.wjwBfdetailWindow;
		win.show();
		win.wjwBfdetail.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				
   				if(!this.wjwBfdetailUpdateWindow)
					this.wjwBfdetailUpdateWindow = new WjwBfdetailUpdateWindow();
   				
   		    	var win = this.wjwBfdetailUpdateWindow;
				var winForm = win.wjwBfdetail;
				
				win.show();
							
   		    	winForm.id_.setValue(vrecord.data['id']);
				winForm.unitNo.setValue(vrecord.data.unitNo);
				winForm.amount.setValue(vrecord.data.amount);
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.bfTime.setValue(vrecord.data.bfTime);
				winForm.bfItem.setValue(vrecord.data.bfItem);
				winForm.inBfqAmt.setValue(vrecord.data.inBfqAmt);
				winForm.inBfhAmt.setValue(vrecord.data.inBfhAmt);
				winForm.outBfqAmt.setValue(vrecord.data.outBfqAmt);
				winForm.outBfhAmt.setValue(vrecord.data.outBfhAmt);
				winForm.connNo.setValue(vrecord.data.connNo);
				winForm.drugBfAmt.setValue(vrecord.data.drugBfAmt);
				winForm.medcBfAmt.setValue(vrecord.data.medcBfAmt);
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
    						wjwBfdetailGrid.vbbar.doLoad(wjwBfdetailGrid.vbbar.cursor);
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
    
    wjwBfdetailGrid = new WjwBfdetailGrid(Ext.getBody().getViewSize().height);
    wjwBfdetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwBfdetailGrid   
		]
	});
});

