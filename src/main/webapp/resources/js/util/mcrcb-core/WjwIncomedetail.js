var ENTITY_URL_LIST = "/WjwIncomedetail/getWjwIncomedetailList";//getWjwIncomedetailList //pager
var ENTITY_URL_SAVE = "/WjwIncomedetail/save";
var ENTITY_URL_UPDATE = "/WjwIncomedetail/update";
var ENTITY_URL_DELETE = "/WjwIncomedetail/delete";
var GET_IN_ACCOUNT_URL = "/WjwIncomedetail/wjwInAccount";
var PAGESIZE=50;
var ITEMS_DATA = [{'id' : '1', 'name' : '医疗'},{'id' : '2', 'name' : '药品'} ,{'id' : '3', 'name' : '其他'}];
/*
 * WJW_INCOMEDETAIL 
 * @author chenyong
 * @date 2015-11-04
 */
var RecordsAll=Ext.data.Record.create([  
   {name:'itemCode',mapping:0},{name:'item',mapping:1},{name:'itemDw',mapping:2},
   {name:'itemNum',mapping:3},{name:'itemSt',mapping:4},{name:'itemAmt',mapping:5}
]);


var detailRecords=Ext.data.Record.create([  
                                       {name:'itemCode',mapping:0},{name:'item',mapping:1},{name:'itemDw',mapping:2},
                                       {name:'itemNum',mapping:3},{name:'itemSt',mapping:4},{name:'itemAmt',mapping:5}
                                    ]);

DistributeGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	constructor: function(height,width){
		
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'添加一行',iconCls:'add',handler:function(){
			    	   var row = new Ext.data.Record({ 
			    		   itemCode:'',item:'',itemDw:'',
			    		   itemNum:'',itemSt:'',itemAmt:''
			    		});
			    	   Ext.getCmp('myEditGrid').store.insert(0,row);
			    	   Ext.getCmp('myEditGrid').getSelectionModel().selectRange(0, 0, true);
			    	   Ext.getCmp('myEditGrid').startEditing (0,1);
			       }}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:function(){
			    	   var records = Ext.getCmp('myEditGrid').getSelectionModel().getSelections();
			    	   Ext.getCmp('myEditGrid').store.remove(records);
			       }}
			]
		});
		this.store = new Ext.data.Store({  
            reader:new Ext.data.ArrayReader({id:0},RecordsAll),  
            data:[]  
          }),
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([
		                                     this.vsm,
 		           {header:'项目编码',dataIndex:'itemCode',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		            {header:'<font color="red">*</font>项目名称',dataIndex:'item',width:130,sortable:true,
 		            	editor:new Ext.form.ComboBox({
		                     editable:false,  
		                     displayField:'item',  
		                     mode:'local',  
		                     triggerAction:'all',  
		                     store:new Ext.data.SimpleStore({  
		                       fields:['item'],  
		                       data:[['医疗'],['药品'],['其他']]  
		                     })
		            	})
 		            },
 		           {header:'单位',dataIndex:'itemDw',width:80,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		           {header:'数量',dataIndex:'itemNum',width:80,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		           {header:'收缴标准',dataIndex:'itemSt',width:80,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		           {header:'<font color="red">*</font>金额',dataIndex:'itemAmt',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({
 	 		                  fieldLabel: '',
 	 		                  name: 'itemAmt',
 	 		                  readOnly: false,
 	 		                  allowBlank: false,
 	 		                  allowNegative :false,//值为false时只允许为正数(默认为 true，即默认允许为负数)
 	 		                  cls:'forbiddenZH',//禁用中文输入法
 	 		                  blankText: '该选项为必填项,请输入内容...'
 	 		            	}),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            }

		           ]);
		DistributeGrid.superclass.constructor.call(this,{
			region: 'center',
			frame: true,
			id:'myEditGrid',
			height: height,
			width:width,
			clicksToEdit:'auto',
            viewConfig: {
                forceFit: false
            },
            loadMask: new Ext.LoadMask(document.body,{ 
				msg: '正在载入数据，请稍后...',
				store   : this.store
			}),
			store:this.store,
			tbar:this.vtbar,
			sm: this.vsm,
			cm: this.vcm
		});
	}
});
WjwIncomedetail = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.inAccno = new Ext.form.TextField({
            fieldLabel: '收款人账号',
            name: 'inAccno',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("inAccno").value,
            blankText: '',
            vtype: null,
            maxLength: 32,
            colspan: '长度超过不能32'
        }),
			
		this.inName = new Ext.form.TextField({
            fieldLabel: '收款人姓名',
            name: 'inName',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("inName").value,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
		this.inBank = new Ext.form.TextField({
            fieldLabel: '收款人开户行',
            name: 'inBank',
            xtype: 'textfield',
//          disabled : true,
            readOnly: true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("inBank").value,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
        this.outAccno = new Ext.form.TextField({
            fieldLabel: '付款人账号',
            name: 'outAccno',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:300,
            blankText: '',
            vtype: null,
            maxLength: 32,
            colspan: '长度超过不能32'
        }),
        this.outAccname = new Ext.form.TextField({
            fieldLabel: '付款人姓名',
            name: 'outAccname',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:300,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
        this.outBank = new Ext.form.TextField({
            fieldLabel: '付款人开户行',
            name: 'outBank',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:300,
            blankText:'',
            vtype: null,
            maxLength: 200,
            maxLengthText: '长度超过不能200',
        }),
		
		this.editorGrid = new DistributeGrid(250,900);
		
        
		WjwIncomedetail.superclass.constructor.call(this, {
        		region: 'north',
	            anchor: '30%',
	            autoHeight:true,
	            autoWidth:true,//
	            labelWidth: 10,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'table',
	            layoutConfig: {columns: 6},
	            items:[
	                   {xtype:'label',text:'收款人账号'},{colspan:2,items:[this.inAccno]},{xtype:'label',text:'付款人账号'},{colspan:2,items:[this.outAccno]},
	                   {xtype:'label',text:'收款人姓名'},{colspan:2,items:[this.inName]},{xtype:'label',text:'付款人姓名'},{colspan:2,items:[this.outAccname]},
	                   {xtype:'label',text:'收款人开户行'},{colspan:2,items:[this.inBank]},{xtype:'label',text:'付款人开户行'},{colspan:2,items:[this.outBank]},
	                   {colspan:6,items:[this.editorGrid]}
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
			this.editorGrid.getSelectionModel().selectAll();
			var records = this.editorGrid.getSelectionModel().getSelections();
			if(records.length==0){
				Ext.Msg.alert("系统提示：","请添加一条记录！");
				return;
			}
			if(records.length>3){
				Ext.Msg.alert("系统提示：","缴款申请一次不能超过3条记录！");
				return;
			}
			var itemCode=new Array();var item=new Array();var itemDw=new Array();
			var itemNum=new Array();var itemSt=new Array(); var itemAmt=new Array();
			inAccno = this.inAccno.getValue();
			inName = this.inName.getValue();
			inBank = this.inBank.getValue();
			for(var i=0;i<records.length;i++){
				itemCode.push(records[i].data['itemCode']);
				if(records[i].data['item']==null||records[i].data['item'].trim()==""){
					Ext.Msg.alert("系统提示：","项目名称不能为空！");
					return;
				}
				item.push(records[i].data['item']);
				itemDw.push(records[i].data['itemDw']);
				itemNum.push(records[i].data['itemNum']);
				itemSt.push(records[i].data['itemSt']);
				if(!records[i].data['itemAmt']){
					Ext.Msg.alert("系统提示：","金额不能为空！");
					return;
				}
				itemAmt.push(records[i].data['itemAmt'])
			}
			this.getForm().submit({
				waitMsg: '正在提交数据...',
				url: ENTITY_URL_SAVE,
				method: 'POST',
				params: {
					itemCode: itemCode,item:item,itemDw:itemDw,
					itemNum:itemNum,itemSt:itemSt,itemAmt:itemAmt,
					inAccno:inAccno,inName:inName,inBank:inBank
			    },
				success: function(form,action){
					Ext.Msg.alert("系统提示：","缴款申请成功！");
				     wjwIncomedetailGrid.store.load({params:{
					    	start:0,
					    	limit:PAGESIZE
				    	}});
				     wjwIncomedetailGrid.wjwIncomedetailWindow.hide();
					
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
					wjwIncomedetailGrid.wjwIncomedetailUpdateWindow.hide();
					wjwIncomedetailGrid.vbbar.doLoad(wjwIncomedetailGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwIncomedetailGrid.wjwIncomedetailUpdateWindow)
    	wjwIncomedetailGrid.wjwIncomedetailUpdateWindow.wjwIncomedetail.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwIncomedetailGrid.onModifyClick();
    }
	
});

/**************WjwIncomedetailWindow*********************/
WjwIncomedetailWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwIncomedetail = new WjwIncomedetail();
		WjwIncomedetailWindow.superclass.constructor.call(this,{
			title: '新增',
			 width: 900,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwIncomedetail]
		});
	}
});

/********************WjwIncomedetailUpdateWindow组件*************************/
WjwIncomedetailUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwIncomedetail = new WjwIncomedetail();
    	this.wjwIncomedetail.buttons[0].hide();   //隐藏添加按钮
    	this.wjwIncomedetail.buttons[1].show();   //显示修改按钮
    	this.wjwIncomedetail.buttons[2].show();   //显示重置按钮
    	this.wjwIncomedetail.buttons[3].hide();   //隐藏清空按钮
    	
    	WjwIncomedetailUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwIncomedetail]
        });
    }
});


/****************WjwIncomedetailGrid***********************/
WjwIncomedetailGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},
		            {name:'unitNo'},
		            {name:'amount'},
		            {name:'unitName'},
		            {name:'outAccno'},
		            {name:'outAccname'},
		            {name:'outBank'},
		            {name:'inAccno'},
		            {name:'inName'},
		            {name:'inBank'},
		            {name:'item1'},
		            {name:'item2'},
		            {name:'item3'},
		            {name:'item1Dw'},
		            {name:'item2Dw'},
		            {name:'item3Dw'},
		            {name:'item1Num'},
		            {name:'item2Num'},
		            {name:'item3Num'},
		            {name:'item1St'},
		            {name:'item2St'},
		            {name:'item3St'},
		            {name:'item1Amt'},
		            {name:'item2Amt'},
		            {name:'item3Amt'},
		            {name:'item1Code'},
		            {name:'item2Code'},
		            {name:'item3Code'},
		            {name:'currency'},
		            {name:'status'},
		            {name:'certNo'},
		            {name:'yt'},
		            {name:'incTime'},
		            {name:'operNo'},
		            {name:'operName'},
		            {name:'fhUser'},
		            {name:'fhTime'},
		            {name:'note1'},
		            {name:'note2'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'缴款',iconCls:'add',handler:this.onAddClick,scope:this}, 

//			       '-',{xtype:'button',text:'详细',iconCls:'expand',handler:this.onCheckClick,scope:this},
			       '-',{xtype:'button',text:'详细',iconCls:'expand',handler:this.onDetailClick,scope:this},
			       '-',{xtype:'button',text:'打印',iconCls:'add',handler:this.onPrintClick,scope:this},
			       '-',{xtype:'button',text:'作废',iconCls:'delete',handler:this.onDeleteClick,scope:this},
			       '-',{xtype:'label',text:'机构编号'},
					new Ext.form.ComboBox({
			        	autoLoad: true,
			            typeAhead: false,
			            emptyText: '请选择...',
			            triggerAction: 'all',
			            isFormField: true,
//			            listWidth : 160,
//			            mode: 'local',
			            mode: 'remote',
			            hiddenName :'NAME',
			            name:'NAME',
			            allowBlank: true,
			            blankText:'请选择...',
			            forceSelection: true,
			            lastQuery: '',
			            displayField:'NAME',
			            valueField:'CODE',
			            id:'unitNo',
			            selectOnFocus: true,
			            store: new Ext.data.Store({
			                proxy: new Ext.data.HttpProxy({url: '/public/getOrganization', method: 'POST'}),
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
			       
					'-',{xtype:'label',text:'凭证编号'},{xtype:'textfield',id:'certNo'},
					'-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,value:''},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,value:''},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			    	   if(Ext.getCmp('endTime').getValue()>0 && Ext.getCmp('startTime').getValue() > Ext.getCmp('endTime').getValue()){
	    	   				Ext.Msg.alert('系统提示', '开始日期不能大于结束日期');
	    	   				return;
	    	   			}	
			    	   var params = {};
			       			wjwIncomedetailGrid.vtbar.items.each(function(item,index,length){
			       				//var unitNo =  Ext.getCmp('unitNo').getValue();
			       				//params[unitNo] = Ext.getCmp('unitNo').getValue();
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
	    	   				wjwIncomedetailGrid.store.baseParams= params;
	    	   				wjwIncomedetailGrid.store.load({params:{start:0,limit:PAGESIZE,unitNo:Ext.getCmp('unitNo').getValue()}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						wjwIncomedetailGrid.vtbar.items.each(function(item,index,length){   
							if((""+item.getXType()).indexOf("field") != -1) {
								item.setValue('');
							}
							Ext.getCmp('unitNo').setValue('');
						  });  
    	   			}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'机构号',dataIndex:'unitNo',width:80,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:100,sortable:true,hidden:false
//		              	renderer: function(value,metadata){
//			          		metadata.attr = 'ext:qtip="点击查看缴款申请信息！"';
//			            	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+value+'</a>';	
//						}	
		            },
		            {header:'币种',dataIndex:'currency',width:50,sortable:true,hidden:false,renderer:
		            	function(value){
		            		if(value = '156'){
		            			return "人民币";
		            		}
	            			return value.substring(0,8);
	            		}
		            },
		            {header:'金额',dataIndex:'amount',width:120,sortable:true,hidden:false,align: 'right',
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'缴款时间',dataIndex:'incTime',width:75,sortable:true,hidden:false,renderer:
		            	function(value){
		            		return value.substring(0,8);
		            	}
		            },
		            {header:'凭证编号',dataIndex:'certNo',width:150,sortable:true,hidden:false},
		            {header:'操作人',dataIndex:'operName',width:90,sortable:true,hidden:false},
		            {header:'状态',dataIndex:'status',width:70,sortable:true,renderer: 
		            	function(data){//1-申请，2-初审，3-复审，4-作废，5-完成
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
		            {header:'完成人',dataIndex:'fhUser',width:100,sortable:true,hidden:false},
		            {header:'完成时间',dataIndex:'fhTime',width:100,sortable:true,hidden:false},
		            {header:'收款人姓名',dataIndex:'inName',width:150,sortable:true,hidden:false},
		            {header:'收款人账号',dataIndex:'inAccno',width:150,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'inBank',width:150,sortable:true,hidden:false}
		           ]);
		WjwIncomedetailGrid.superclass.constructor.call(this,{
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
    	this.wjwIncomedetailWindow = new WjwIncomedetailWindow();
    	var win = this.wjwIncomedetailWindow;
    	
    	
    	Ext.Ajax.request({
			url:GET_IN_ACCOUNT_URL,
			method: 'POST',
			success: function(resp,opts){
				var result = Ext.util.JSON.decode(resp.responseText); 
				
				
				var winForm = win.wjwIncomedetail;
				winForm.inAccno.setValue(result.accNo);
				winForm.inName.setValue(result.custName);
				winForm.inBank.setValue(result.bankName);
				
			},
			failure: function(action){
				Ext.Msg.alert('系统提示',action);
			}
		});
    	
		win.show();
		win.wjwIncomedetail.getForm().reset();
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				if(vrecord.data.status == 6){
   					Ext.Msg.alert('系统提示','已记账完成的记录不能修改！');
   					return;
   				}
   				if(vrecord.data.status == 4){
   					Ext.Msg.alert('系统提示','已作废的记录不能修改！');
   					return;
   				}
   				
				this.wjwIncomedetailUpdateWindow = new WjwIncomedetailUpdateWindow();
   				
   		    	var win = this.wjwIncomedetailUpdateWindow;
				var winForm = win.wjwIncomedetail;
				
				win.show();
							
   		    	winForm.id.setValue(vrecord.data['id']);
   		    	winForm.amount.setValue(vrecord.data.amount);
				winForm.outAccno.setValue(vrecord.data.outAccno);
				winForm.outAccname.setValue(vrecord.data.outAccname);
				winForm.outBank.setValue(vrecord.data.outBank);
				winForm.inAccno.setValue(vrecord.data.inAccno);
				winForm.inName.setValue(vrecord.data.inName);
				winForm.inBank.setValue(vrecord.data.inBank);
				winForm.item1.setValue(vrecord.data.item1);
				winForm.item2.setValue(vrecord.data.item2);
				winForm.item3.setValue(vrecord.data.item3);
				winForm.item1Dw.setValue(vrecord.data.item1Dw);
				winForm.item2Dw.setValue(vrecord.data.item2Dw);
				winForm.item3Dw.setValue(vrecord.data.item3Dw);
				winForm.item1Num.setValue(vrecord.data.item1Num);
				winForm.item2Num.setValue(vrecord.data.item2Num);
				winForm.item3Num.setValue(vrecord.data.item3Num);
				winForm.item1St.setValue(vrecord.data.item1St);
				winForm.item2St.setValue(vrecord.data.item2St);
				winForm.item3St.setValue(vrecord.data.item3St);
				winForm.item1Amt.setValue(vrecord.data.item1Amt);
				winForm.item2Amt.setValue(vrecord.data.item2Amt);
				winForm.item3Amt.setValue(vrecord.data.item3Amt);
				winForm.item1Code.setValue(vrecord.data.item1Code);
				winForm.item2Code.setValue(vrecord.data.item2Code);
				winForm.item3Code.setValue(vrecord.data.item3Code);
				winForm.currency.setValue(vrecord.data.currency);
				winForm.certNo.setValue(vrecord.data.certNo);
				winForm.yt.setValue(vrecord.data.yt);
				winForm.incTime.setValue(vrecord.data.incTime);
   		    	
   			}else{
   				Ext.Msg.alert('系统提示','不能修改多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
    

    onPrintClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   			
   				window.open("/WjwIncomedetail/index2?id="+vrecord.data.id);
   			}else{
   				Ext.Msg.alert('系统提示','不能同时打印多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
    
    onDetailClick:function(){
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
//   				if(!this.wjwIncomedetailDisplayWindow){
//   					this.wjwIncomedetailDisplayWindow = new WjwIncomedetailDisplayWindow();
//   				}
//   				var win = this.wjwIncomedetailDisplayWindow;
   				var win = new WjwIncomedetailDisplayWindow();
				var winForm = win.wjwIncomedetailDisplay;
				win.show();
				winForm.outAccno.setValue(vrecord.data.outAccno);
				winForm.outAccname.setValue(vrecord.data.outAccname);
				winForm.outBank.setValue(vrecord.data.outBank);
				winForm.inAccno.setValue(vrecord.data.inAccno);
				winForm.inName.setValue(vrecord.data.inName);
				winForm.inBank.setValue(vrecord.data.inBank);
				var store = winForm.editorGrid.store;
				store.removeAll();
				for( i=1;i<4;i++){
					var item = vrecord.data['item'+i]==1?'医疗':(vrecord.data['item'+i]==2?
							'药品':vrecord.data['item'+i]==3?'其他':4);
					if(item==4){
						continue;
					}
					var rcd = new detailRecords({
						   itemCode:vrecord.data['item'+i+'Code'],
						   item:item,
						   itemDw:vrecord.data['item'+i+'Dw'],
						   itemNum:vrecord.data['item'+i+'Num'],
						   itemSt:vrecord.data['item'+i+'St'],
						   itemAmt:vrecord.data['item'+i+'Amt']
					});					
					store.add(rcd);
				}
		
   			}else{
   				Ext.Msg.alert('系统提示','不能同时查看多条记录！');
   			}
   		}else{
   			Ext.Msg.alert('系统提示','请选择一条记录！');
   		}    	
    },
    //=================
    onDeleteClick: function(){
    	var records = this.getSelectionModel().getSelections();
    	var ids = {};
		var valueStr = [];
    	for(var i=0;i<records.length;i++){
    		if(records[i].data.status == 6){
					Ext.Msg.alert('系统提示','已记账完成的记录不能作废！');
					return;
				}
    		if(records[i].data.status == 4){
					Ext.Msg.alert('系统提示','记录已作废！');
					return;
				}
    		valueStr.push(records[i].get('id'));
    	}
		ids['ids'] = valueStr;
		
    	if(records.length>0){
    		Ext.Msg.confirm('系统提示：',"确定作废这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_DELETE,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示','作废成功！');
    						wjwIncomedetailGrid.vbbar.doLoad(wjwIncomedetailGrid.vbbar.cursor);
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




/***************************************************************************************/
DistributeGridDisplay = Ext.extend(Ext.grid.EditorGridPanel, {
	constructor: function(height,width){

		this.store = new Ext.data.Store({  
            reader:new Ext.data.ArrayReader({id:0},detailRecords),  
            data:[]  
          }),
//		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([
//		                                     this.vsm,
 		           {header:'项目编码',dataIndex:'itemCode',width:100,sortable:true,hidden:false},
 		            {header:'项目名称',dataIndex:'item',width:130,sortable:true,hidden:false},
 		           {header:'单位',dataIndex:'itemDw',width:80,sortable:true,hidden:false},
 		           {header:'数量',dataIndex:'itemNum',width:80,sortable:true,hidden:false},
 		           {header:'收缴标准',dataIndex:'itemSt',width:80,sortable:true,hidden:false},
 		           {header:'金额',dataIndex:'itemAmt',width:100,sortable:true,hidden:false}

		           ]);
		DistributeGridDisplay.superclass.constructor.call(this,{
			region: 'center',
			frame: true,
			id:'myEditGrid',
			height: height,
			width:width,
			clicksToEdit:'auto',
            viewConfig: {
                forceFit: false
            },
            loadMask: new Ext.LoadMask(document.body,{ 
				msg: '正在载入数据，请稍后...',
				store   : this.store
			}),
			store:this.store,
			tbar:this.vtbar,
			sm: this.vsm,
			cm: this.vcm
		});
	}
});
/***************************************************************************************/

/**********************************************************************/
WjwIncomedetailDisplay = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.inAccno = new Ext.form.TextField({
            fieldLabel: '收款人账号',
            name: 'inAccno',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("inAccno").value,
            blankText: '',
            vtype: null,
            maxLength: 32,
            colspan: '长度超过不能32'
        }),
			
		this.inName = new Ext.form.TextField({
            fieldLabel: '收款人姓名',
            name: 'inName',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("inName").value,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
		this.inBank = new Ext.form.TextField({
            fieldLabel: '收款人开户行',
            name: 'inBank',
            xtype: 'textfield',
//          disabled : true,
            readOnly: true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("inBank").value,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
        this.outAccno = new Ext.form.TextField({
            fieldLabel: '付款人账号',
            name: 'outAccno',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:300,
            blankText: '',
            vtype: null,
            maxLength: 32,
            colspan: '长度超过不能32'
        }),
        this.outAccname = new Ext.form.TextField({
            fieldLabel: '付款人姓名',
            name: 'outAccname',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:300,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
        this.outBank = new Ext.form.TextField({
            fieldLabel: '付款人开户行',
            name: 'outBank',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:300,
            blankText:'',
            vtype: null,
            maxLength: 200,
            maxLengthText: '长度超过不能200',
        }),
		
		this.editorGrid = new DistributeGridDisplay(250,900);
		
        
		WjwIncomedetailDisplay.superclass.constructor.call(this, {
        		region: 'north',
	            anchor: '30%',
	            autoHeight:true,
	            autoWidth:true,//
	            labelWidth: 10,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'table',
	            layoutConfig: {columns: 6},
	            items:[
	                   {xtype:'label',text:'收款人账号'},{colspan:2,items:[this.inAccno]},{xtype:'label',text:'付款人账号'},{colspan:2,items:[this.outAccno]},
	                   {xtype:'label',text:'收款人姓名'},{colspan:2,items:[this.inName]},{xtype:'label',text:'付款人姓名'},{colspan:2,items:[this.outAccname]},
	                   {xtype:'label',text:'收款人开户行'},{colspan:2,items:[this.inBank]},{xtype:'label',text:'付款人开户行'},{colspan:2,items:[this.outBank]},
	                   {colspan:6,items:[this.editorGrid]}
	            ],
	            buttonAlign :'center',
	            buttons: [
	               {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
	            ]
	        });
	},
	
	//关闭
    onCloseClick: function(){
    	if(wjwIncomedetailGrid.wjwIncomedetailDisplayWindow)
    	wjwIncomedetailGrid.wjwIncomedetailDisplayWindow.wjwIncomedetailDisplay.getForm().reset();
        this.ownerCt.hide();
    }

	
});
/**********************************************************************/
/********************WjwIncomedetailDisplayWindow组件*************************/
WjwIncomedetailDisplayWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwIncomedetailDisplay = new WjwIncomedetailDisplay();
    	
    	WjwIncomedetailDisplayWindow.superclass.constructor.call(this, {
			title: '详细信息',
			width: 900,
//			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwIncomedetailDisplay]
        });
    }
});

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwIncomedetailGrid = new WjwIncomedetailGrid(Ext.getBody().getViewSize().height);
    wjwIncomedetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwIncomedetailGrid   
		]
	});
});

