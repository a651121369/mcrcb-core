var ENTITY_URL_LIST = "/select/Wjw_PayMain_All";
var ENTITY_URL_SAVE = "/WjwPaydetail/save";
var ENTITY_URL_UPDATE = "/WjwPaydetail/update";
var ENTITY_URL_DELETE = "/WjwPaydetail/delete";
var ENTITY_URL_AMPULATE = "/WjwPaydetail/ampulate";
var URL_GET_ZBDETAILS = "/WjwPaydetail/findZbDetails";
var URL_GET_ECONOMIC = "/WjwPaydetail/findEconomic";
var GET_PAY_ACCOUNT_URL = "/WjwPaydetail/getPayAccInfo";
var PAGESIZE=10;
/*
 * WJW_PAYDETAIL 
 * @author chenyong
 * @date 2015-11-04
 */
var parentId;

/*********************PopupGrid*********************/
PopupGrid = Ext.extend(UxGrid, {
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url:'/public/getInAcc',method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'inName'},{name:'inAccno'},{name:'inBank'}		            
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: ['-',{xtype:'',text:'<font color="red">友情提示：双击带入！</font>',width:300},
			        '-',{xtype:'label',text:'收款人姓名'},{xtype:'textfield',id:'acctName'},
			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			        	var params = {};
			        	popupGrid.vtbar.items.each(function(item,index,length){ 
	       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
	       						if (item.getXType() == 'datefield') {
	       							params[item.getId()] = item.getValue().format(item.format);
	       						} else {
	       							params[item.getId()] = item.getValue();
	       						}
	       					}
						});
			        	Ext.getCmp('myGrid').store.baseParams= params;
			        	Ext.getCmp('myGrid').store.load({params:{start:0,limit:PAGESIZE}});
			        }
			        },
			        '-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
			        	popupGrid.vtbar.items.each(function(item,index,length){   
			        		if((""+item.getXType()).indexOf("field") != -1) {
			        			item.setValue('');
			        		}
			        	});  
			        }}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);
		
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([
		            //this.vsm,
					{header:'收款人姓名',dataIndex:'inName',width:170,sortable:true,hidden:false},
		            {header:'收款人帐号',dataIndex:'inAccno',width:210,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'inBank',width:280,sortable:true,hidden:false}
		           
		           ]);
		PopupGrid.superclass.constructor.call(this,{
			region: 'center',
			id:'myGrid',
			frame: true,
			height: 310,
			width:width,
			clicksToEdit:'auto',
            viewConfig: {
                forceFit: false
            },
            loadMask: new Ext.LoadMask(document.body,{ 
				msg: '正在载入数据，请稍后... ',
				store: this.store
			}),
			store:this.store,
			tbar:this.vtbar,
			bbar:this.vbbar,
			sm: this.vsm,
			cm: this.vcm
		});
	},
	 listeners:{  
		 rowdblclick:function(grid,row){  
			 grid.getSelectionModel().each(function(rec){
				 var inName = rec.get('inName');
				 var inAccno = rec.get('inAccno');
				 var inBank = rec.get('inBank');
				 var reco = Ext.getCmp('myEditGrid').getSelectionModel().getSelected();
				 reco.data['inName'] = inName;
         		 reco.data['inAccno'] = inAccno;
         		 reco.data['inBank'] = inBank;
         		 reco.commit();
			 });
//			 Ext.getCmp("windodo").close();
//			 document.getElementById('windodo').style.display = 'none'; 
//			 Ext.getDom('windodo').close();
	     }
	 }
});
/**************PopupGridWindow*********************/
PopupGridWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.popupGrid = new PopupGrid();
		PopupGridWindow.superclass.constructor.call(this,{
//			id: 'windodo',
			title: '收款人信息',
			 width: 692,
			 anchor: '100%',
			autoHeight:true,
			autoScroll:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.popupGrid]
		});
	}
});

/************************************************/

var RecordsAll=Ext.data.Record.create([  
          {name:'inAccno',mapping:0},{name:'inName',mapping:1},{name:'inBank',mapping:2},
          {name:'amount',mapping:3,type:'number',allowBlank:false},{name:'zjFld',mapping:4},{name:'payWay',mapping:5},
          {name:'item',mapping:6},{name:'topYsdw',mapping:7},{name:'footYsdw',mapping:8},
          {name:'itmeYs',mapping:9},{name:'zbDetail',mapping:10},{name:'funcFl',mapping:11},
          {name:'ecnoFl',mapping:12},{name:'yt',mapping:13}
       ]);
DistributeGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	constructor: function(height,width){
		
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'添加一行',iconCls:'add',handler:function(){
			    	   var row = new Ext.data.Record({ 
			    		   inAccno:'',inName:'',inBank:'',item:'',amount:'',zjFld:'',
			    		   payWay:'',topYsdw:'',footYsdw:'',itmeYs:'',zbDetail:'',funcFl:'',
			    		   ecnoFl:'',yt:''
			    		});

			    	   Ext.getCmp('myEditGrid').store.insert(0,row);
			    	   Ext.getCmp('myEditGrid').getSelectionModel().selectRange(0, 0, true);
			    	   Ext.getCmp('myEditGrid').startEditing (0,1);
			       }}, 
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:function(){
			    	   
			    	   		   if(Ext.getCmp('myEditGrid').activeEditor!=null){
			    	   			   Ext.getCmp('myEditGrid').activeEditor.completeEdit();
			    	   		   }
			    	   		   
		  			    	   var records = Ext.getCmp('myEditGrid').getSelectionModel().getSelections();		  			    	 
		  			    	   //账户可支出总金额

 		  			    	   for(i=0;i<records.length;i++){
 		  			    		   
 		  			    		   if(records[i].data['item']=='医疗'){		  		
	 		  			    			Ext.getCmp('payMedcAmt').setValue((parseFloat(Ext.getCmp('payMedcAmt').value)+parseFloat(records[i].data['amount'])).toFixed(2));
	 		  			    			Ext.getCmp('appMedcAmt').setText(parseFloat(Ext.getCmp('appMedcAmt').text-records[i].data['amount']).toFixed(2));
 		  			    		   }else if(records[i].data['item']=='药品'){
	 		  			    			Ext.getCmp('payDrugAmt').setValue((parseFloat(Ext.getCmp('payDrugAmt').value)+parseFloat(records[i].data['amount'])).toFixed(2));
	 		  			    			Ext.getCmp('appDrugAmt').setText(parseFloat(Ext.getCmp('appDrugAmt').text-records[i].data['amount']).toFixed(2));
 		  			    		   }
 		  			    		   
 		  			    		   Ext.getCmp('payAmount').setValue((parseFloat(Ext.getCmp('payAmount').value)+parseFloat(records[i].data['amount'])).toFixed(2));
 		  			    		   Ext.getCmp('appAmount').setText(parseFloat(Ext.getCmp('appAmount').text-records[i].data['amount']).toFixed(2));
 		  			    	   }
 		  			    	   Ext.getCmp('myEditGrid').store.remove(records);

		  			    	   
			       }},
			       "-",{xtype:'label',text:'申请支付总金额: '},{xtype:'label',text:'0.00',id:'appAmount'},
			       "-",{xtype:'label',text:'申请医疗总金额: '},{xtype:'label',text:'0.00',id:'appMedcAmt'},
			       "-",{xtype:'label',text:'申请药品总金额: '},{xtype:'label',text:'0.00',id:'appDrugAmt'}
			]
		});
		this.store = new Ext.data.Store({  
            reader:new Ext.data.ArrayReader({id:0},RecordsAll),  
            data:[]
          }),
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([
		            this.vsm,
		            {header:'<font color="red">*</font>收款人姓名',dataIndex:'inName',width:150,sortable:true,
		            		listeners:{
		            			'click':function(){
		            				this.popupGridWindow = new PopupGridWindow();
		            				var win = this.popupGridWindow;
		            				win.show();
		            				win.popupGrid.store.load({params:{start:0,limit:PAGESIZE}});
		            			}
		            		}
		            },
 		           {header:'<font color="red">*</font>收款人帐号',dataIndex:'inAccno',width:200,sortable:true},
 		           {header:'<font color="red">*</font>收款人开户行',dataIndex:'inBank',width:280,sortable:true},
  		           {header:'<font color="red">*</font>科目',dataIndex:'item',width:100,sortable:true,
 		            	editor:new Ext.form.ComboBox({
		                     editable:false,  
		                     displayField:'item',  
		                     mode:'local',  
		                     allowBlank: false,
		                     forceSelection:true,
		                     triggerAction:'all',  
		                     store:new Ext.data.SimpleStore({  
		                       fields:['item'],  
		                       data:[['医疗'],['药品']]  
		                     })
		            	})
 		            },
 		            {header:'<font color="red">*</font>付款金额',dataIndex:'amount',width:100,sortable:true,
 		            	editor:new Ext.form.NumberField({
 		                  fieldLabel: '',
 		                  name: 'amount',
 		                  readOnly: false,
 		                  allowBlank: false,
 		                  allowNegative :false,//值为false时只允许为正数(默认为 true，即默认允许为负数)
 		                  cls:'forbiddenZH',//禁用中文输入法
 		                  blankText: '该选项为必填项,请输入内容...',
 		                  listeners:{
 		                	  blur:function(e){
 		                	   if(Ext.getCmp('myEditGrid').activeEditor.completeEdit()!=null){
 		                		   Ext.getCmp('myEditGrid').activeEditor.completeEdit();
 					    	   }
 		  			    	   Ext.getCmp('myEditGrid').getSelectionModel().selectAll();
 		  			    	   var records = Ext.getCmp('myEditGrid').getSelectionModel().getSelections();
 		  			    	   //账户可支出总金额
 		  			    	   var amount = new Number();
 		  			    	   var medcAmt = new Number();
 		  			    	   var drugAmt = new Number();
 		  			    	   amount = parseFloat(document.getElementById("accAmount").value).toFixed(2);
 		  			    	   medcAmt = parseFloat(document.getElementById("medcAmt").value).toFixed(2);
 		  			    	   drugAmt = parseFloat(document.getElementById("drugAmt").value).toFixed(2);
	 		  			 	   var appAmount = new Number();
		  			    	   var appMedcAmt = new Number();
		  			    	   var appDrugAmt = new Number();
 		  			    	   for(i=0;i<records.length;i++){ 
 		  			    		   if(records[i].data['item']=='医疗'){
	 		  			    		   appMedcAmt += parseFloat(records[i].data['amount']);
	 		  			    		   if(parseFloat(appMedcAmt.toFixed(2))>parseFloat(medcAmt)){
	 		  			    			   Ext.Msg.alert('系统提示:','申请医疗金额大于可支出余额');
	 		  			    			 return;
	 		  			    		   }
 		  			    		   }else if(records[i].data['item']=='药品'){
	 		  			    		   appDrugAmt += parseFloat(records[i].data['amount']);
	 		  			    		   
	 		  			    		   if(parseFloat(appDrugAmt.toFixed(2))>parseFloat(drugAmt)){
;
	 		  			    			   Ext.Msg.alert('系统提示:','申请药品金额大于可支出余额');
	 		  			    			   return;
	 		  			    		   }
 		  			    		   }else{
 		  			    			   Ext.Msg.alert('系统提示:','请选择科目');
 		  			    			   appAmount += parseFloat(records[i].data['amount']);
 		  			    			   return;
 		  			    		   }
 		  			    		   appAmount += parseFloat(records[i].data['amount']);	  			    				   
 		  			    	   }
		  			    	   
		  			    	   Ext.getCmp('payAmount').setValue(parseFloat(amount-appAmount).toFixed(2));	    
		  			    	   Ext.getCmp('payMedcAmt').setValue(parseFloat(medcAmt-appMedcAmt).toFixed(2));
		  			    	   Ext.getCmp('payDrugAmt').setValue(parseFloat(drugAmt-appDrugAmt).toFixed(2));
		  			    	   Ext.getCmp('appAmount').setText(parseFloat(appAmount).toFixed(2));
		  			    	   Ext.getCmp('appMedcAmt').setText(parseFloat(appMedcAmt).toFixed(2));
		  			    	   Ext.getCmp('appDrugAmt').setText(parseFloat(appDrugAmt).toFixed(2));
 		                	  }
 		                  }
 		                
 		            	})
 		            },

 		            {header:'<font color="red">*</font>结算方式',dataIndex:'payWay',width:100,sortable:true,
 		            	editor:new Ext.form.ComboBox({
		                     editable:false,  
		                     allowBlank: false,
		                     forceSelection:true,
		                     displayField:'payWay',  
		                     mode:'local',  
		                     triggerAction:'all',
		                     store:new Ext.data.SimpleStore({  
		                       fields:['payWay'],  
		                       data:[['现金'],['转账']]  
		                     })
		            	})
 		            },

 		            {header:'<font color="red">*</font>指标摘要',dataIndex:'zbDetail',width:150,sortable:true,
 		            	editor:new Ext.form.ComboBox({
 		               autoLoad: true,
 		               triggerAction: 'all',
 		               mode: 'remote',
 		               name:'zbCode',
 		               hiddenName :'zbCode',
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
 		           })

//                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
//                        	metadata.attr = 'ext:qtip="' + data + '"';
//                        	return data;
//                        }
 		            },
 		            {header:'<font color="red">*</font>用途',dataIndex:'yt',width:200,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:false, 
                            maxLength:30,
                            maxLengthText:"输入字数只能为15",
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		            {header:'经济分类',dataIndex:'ecnoFl',width:150,sortable:true,
 		             	editor:new Ext.form.ComboBox({
 	 		               autoLoad: true,
 	 		               triggerAction: 'all',
 	 		               mode: 'remote',
 	 		               name:'ecCode',
 	 		               hiddenName :'ecCode',
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
 	 		           })

 		            },
// 		            {header:'经济分类',dataIndex:'ecnoFl',width:100,sortable:true,
// 		            	editor:new Ext.form.TextField({  
//                            allowBlank:true  
//                        }),
//                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
//                        	metadata.attr = 'ext:qtip="' + data + '"';
//                        	return data;
//                        }
// 		            },
 		            {header:'资金性质',dataIndex:'zjFld',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		            {header:'一级预算单位',dataIndex:'topYsdw',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		            {header:'基层预算单位',dataIndex:'footYsdw',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
 		            {header:'预算项目',dataIndex:'itmeYs',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
                        }),
                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                        	metadata.attr = 'ext:qtip="' + data + '"';
                        	return data;
                        }
 		            },
// 		            {header:'指标摘要',dataIndex:'zbDetail',width:100,sortable:true,
// 		            	editor:new Ext.form.TextField({  
//                            allowBlank:false  
//                        }),
//                        renderer:function(data, metadata, record, rowIndex, columnIndex, store){
//                        	metadata.attr = 'ext:qtip="' + data + '"';
//                        	return data;
//                        }
// 		            },

 		            {header:'功能分类',dataIndex:'funcFl',width:100,sortable:true,
 		            	editor:new Ext.form.TextField({  
                            allowBlank:true  
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
WjwPaydetail = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.unitNo = new Ext.form.TextField({
            fieldLabel: '申请单位名称',
            name: 'unitName',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("orgName").value,
            blankText: '该选项为必填项,请输入内容...',
            vtype: null,
            maxLength: 30,
            colspan: '长度超过不能30'
        }),
			
		this.unitNum = new Ext.form.TextField({
            fieldLabel: '申请单位编号',
            name: 'unitNo',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("orgId").value,
            blankText: '该选项为必填项,请输入内容...',
            vtype: null,
            maxLength: 30,
            colspan: '长度超过不能30'
        }),
		this.accAmount = new Ext.form.TextField({
			id:'payAmount',
            fieldLabel: '账户可支出余额',
            name: 'accAmount',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("accAmount").value,
            blankText: '该选项为必填项,请输入内容...',
            vtype: null,
            maxLength: 30,
            colspan: '长度超过不能30'
        }),
        this.medcAmt = new Ext.form.TextField({
        	id:'payMedcAmt',
            fieldLabel: '医疗可支出余额',
            name: 'medcAmt',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("medcAmt").value,
            blankText: '该选项为必填项,请输入内容...',
            vtype: null,
            maxLength: 30,
            colspan: '长度超过不能30'
        }),
        this.drugAmt = new Ext.form.TextField({
        	id:'payDrugAmt',
            fieldLabel: '药品可支出余额',
            name: 'drugAmt',
            xtype: 'textfield',
//          disabled : true,
            readOnly:true,
            allowBlank: false,
            anchor: '95%',
            width:300,
            value:document.getElementById("drugAmt").value,
            blankText: '该选项为必填项,请输入内容...',
            vtype: null,
            maxLength: 30,
            colspan: '长度超过不能30'
        }),
		this.payType = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    lazyRender:true,
		    name : 'payType',
		    anchor: '95%',
		    width: 300,
		    fieldLabel : '<font color="red">*</font>支出类型',
		    mode: 'local',
		    store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'myId',
		            'displayText'
		        ],
//		        data: [[2, '转账']]
		    	data: [[1, '现金'], [2, '转账']]
		    }),
		    valueField: 'myId',
		    displayField: 'displayText',
		    value:2//默认选中转账

		});
//		this.remark = this.createTextField('<font color="red">*</font>备注','remark','95%','',null,1000,'长度超过不能1000');
		this.remark = new Ext.form.TextField({
            fieldLabel: '备注',
            name: 'remark',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:755,
            blankText:'',
            vtype: null,
            maxLength: 1000,
            maxLengthText: '长度超过不能1000',
        });
		
//		this.medcAmt = new Ext.form.Hidden({
//			
//    	    fieldLabel:'可支出医疗金额',
//    	    disabled : true,
//    	    name:'medcAmt',
//    	    value:document.getElementById("medcAmt").value
//    	});
//		this.drugAmt = new Ext.form.Hidden({
//    	    fieldLabel:'可支出药品金额',
//    	    disabled : true,
//    	    name:'drugAmt',
//    	    value:document.getElementById("drugAmt").value
//    	});
		this.editorGrid = new DistributeGrid(250,900);
		
		this.payType.allowBlank = false;
        
        WjwPaydetail.superclass.constructor.call(this, {
        		region: 'north',
	            anchor: '100%',
	            autoHeight:true,
	            autoWidth:true,//
	            labelWidth: 90,
	            labelAlign :'right',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'table',
//	            layoutConfig: 'column',
//	            layout:{type:'table',columns:3},
	            layoutConfig: {columns: 6},
	            items:[
	                   {xtype:'label',text:'申请单位名称'},{colspan:2,items:[this.unitNo]},{xtype:'label',text:'申请单位编号'},{colspan:2,items:[this.unitNum]},
	                   {xtype:'label',text:'账户可支出余额'},{colspan:2,items:[this.accAmount]},{xtype:'label',text:'医疗可支出余额'},{colspan:2,items:[this.medcAmt]},
	                   {xtype:'label',text:'药品可支出余额'},{colspan:2,items:[this.drugAmt]},{xtype:'label',text:'支出类型'},{colspan:2,items:[this.payType]},
	                   {xtype:'label',text:'备注'},{colspan:5,items:[this.remark]},
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
	comBox:function(){
		alert(123);
	},
	addFormClick: function(){
		if(this.getForm().isValid()){
			this.editorGrid.getSelectionModel().selectAll();
			var records = this.editorGrid.getSelectionModel().getSelections();
			if(records.length<=0){
				Ext.Msg.alert("系统提示：","请至少添加1条支付申请记录！");
				return;
			}
			if(records.length>7){
				Ext.Msg.alert("系统提示：","支付申请一次不能超过7条记录！");
				return;
			}
			var inAccno=new Array();var inName=new Array(); var amount=new Array();
			var inBank=new Array();var zjFld=new Array();var payWay=new Array();
			var topYsdw=new Array();var footYsdw=new Array();var itmeYs=new Array();
			var funcFl=new Array();var ecnoFl=new Array();var yt=new Array();
			var zbDetail=new Array();var item = new Array();
			for(var i=0;i<records.length;i++){
				if(records[i].data['inAccno']==null||records[i].data['inAccno'].trim()==""){
					Ext.Msg.alert("系统提示：","收款人账号不能为空！");
					return;
				}
				inAccno.push(records[i].data['inAccno']);
				if(records[i].data['inName']==null||records[i].data['inName'].trim()==""){
					Ext.Msg.alert("系统提示：","收款人姓名不能为空！");
					return;
				}
				inName.push(records[i].data['inName']);
				if(!records[i].data['amount']){
					Ext.Msg.alert("系统提示：","金额不能为空！");
					return;
				}
				amount.push(records[i].data['amount']);
				if(records[i].data['inBank']==null||records[i].data['inBank'].trim()==""){
					Ext.Msg.alert("系统提示：","收款人开户行不能为空！");
					return;
				}
				inBank.push(records[i].data['inBank']);
				
				zjFld.push(records[i].data['zjFld']);
				if(records[i].data['payWay']==null||records[i].data['payWay'].trim()==""){
					Ext.Msg.alert("系统提示：","结算方式不能为空！");
					return;
				}
				payWay.push(records[i].data['payWay']);
				topYsdw.push(records[i].data['topYsdw']);
				footYsdw.push(records[i].data['footYsdw']);
				itmeYs.push(records[i].data['itmeYs']);
				funcFl.push(records[i].data['funcFl']);
				ecnoFl.push(records[i].data['ecnoFl']);
				if(records[i].data['yt']==null||records[i].data['yt'].trim()==""){
					Ext.Msg.alert("系统提示：","用途不能为空！");
					return;
				}
				yt.push(records[i].data['yt']);
				if(records[i].data['zbDetail']==null||records[i].data['zbDetail'].trim()==""){
					Ext.Msg.alert("系统提示：","指标摘要不能为空！");
					return;
				}
				zbDetail.push(records[i].data['zbDetail']);
				if(records[i].data['item']==null||records[i].data['item'].trim()==""){
					Ext.Msg.alert("系统提示：","科目不能为空！");
					return;
				}
				item.push(records[i].data['item']);
			}
			var mdAmt = new Number();
			var dgAmt = new Number();
			for(var i=0;i<item.length;i++){
				if(item[i]=='医疗'){
					mdAmt += parseFloat(amount[i]);
				}else{
					dgAmt += parseFloat(amount[i]);
				}
			}
			var sqAmount = parseFloat(mdAmt)+parseFloat(dgAmt);

			if(parseFloat(document.getElementById("medcAmt").value)<parseFloat(mdAmt.toFixed(2))){				
				Ext.Msg.alert("系统提示：","医疗申请总金额大于可支出余额！");
				return false;
			}
			
			if(parseFloat(document.getElementById("drugAmt").value)<parseFloat(dgAmt.toFixed(2))){				
				Ext.Msg.alert("系统提示：","药品申请总金额大于可支出余额！");
				return false;
			}

			if(parseFloat(document.getElementById("accAmount").value)<parseFloat(sqAmount.toFixed(2))){	
				Ext.Msg.alert("系统提示：","申请总金额大于可支出余额！");
				return false;
			}
			this.getForm().submit({
				waitMsg: '正在提交数据...',
				url: ENTITY_URL_SAVE,
				method: 'POST',
				params: {
					inAccno: inAccno,inName:inName,amount:amount,inBank:inBank,
					zjFld:zjFld,payWay:payWay,topYsdw:topYsdw,footYsdw:footYsdw,
					itmeYs:itmeYs,funcFl:funcFl,ecnoFl:ecnoFl,yt:yt,
					zbDetail:zbDetail,item:item
			    },
				success: function(form,action){
					Ext.Msg.alert("系统提示：","申请成功！");
					//wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
					 parentId = document.getElementById("parentId").value;
				     if(parentId==0){
				    	parentId="";
				     }else{
				    	parentId="#######";
				     }
				     wjwPaydetailGrid.store.load({params:{
					    	start:0,
					    	limit:PAGESIZE,
					    	parentId:parentId,
					    	orgId:document.getElementById("orgId").value
				    	}});
					wjwPaydetailGrid.wjwPaydetailWindow.hide();
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：","申请失败！");
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
    }
	
});

/**************WjwPaydetailWindow*********************/
WjwPaydetailWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.wjwPaydetail = new WjwPaydetail();
		this.wjwPaydetail.buttons[0].text='申请';
		WjwPaydetailWindow.superclass.constructor.call(this,{
			title: '申请',
			 width: 900,
			 anchor: '100%',
			autoHeight:true,
			autoScroll:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.wjwPaydetail]
		});
	}
});

/****************WjwPaydetailGrid***********************/
WjwPaydetailGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'id'},{name:'unitName'},{name:'unitNo'},{name:'payType'},
		            {name:'sqTime'},{name:'status'},{name:'dspUserno'},
		            {name:'csUserno'},{name:'fsUserno'},{name:'csTime'},{name:'fsTime'},
		            {name:'connNo'},{name:'dspUsername'},{name:'csUsername'},{name:'fsUsername'},
		            {name:'sqUserno'},{name:'sqUsername'},{name:'remark'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'申请',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onEditClick,scope:this},
			       '-',{xtype:'button',text:'作废',iconCls:'delete',handler:this.onDeleteClick,scope:this},
			       '-',{xtype:'button',text:'删除',iconCls:'delete',handler:this.onAmputateClick,scope:this},
					'-',{xtype:'label',text:'单位名称'},
					new Ext.form.ComboBox({
			        	autoLoad: true,
			            typeAhead: false,
			            emptyText: '请选择...',
			            triggerAction: 'all',
			            isFormField: true,
//			            mode: 'local',
			            mode: 'remote',
			            hiddenName :'NAME',
			            name:'NAME',
			            allowBlank: true,
			            blankText:'请选择...',
			            forceSelection: false,
			            lastQuery: '',
			            displayField:'NAME',
			            valueField:'NAME',
			            id:'unitName',
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
					'-',{xtype:'label',text:'申请开始日期'},{xtype:'datefield',format:'Ymd',id:'startTime',editable : false,value:new Date().getFirstDateOfMonth ().format('Ymd')},
					'-',{xtype:'label',text:'申请截止日期'},{xtype:'datefield',format:'Ymd',id:'endTime',editable : false,value:new Date().format('Ymd')},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			wjwPaydetailGrid.vtbar.items.each(function(item,index,length){ 
		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
		       						if (item.getXType() == 'datefield') {
		       							params[item.getId()] = item.getValue().format(item.format);
		       						} else {
		       							params[item.getId()] = item.getValue();
		       						}
		       					}
							});
			       			params[Ext.getCmp('unitName').getId()]=Ext.getCmp('unitName').getValue();
			       			if(Ext.getCmp('startTime').getValue() > Ext.getCmp('endTime').getValue()){
				       			Ext.Msg.alert('系统提示','开始时间不能大于结束时间！');
				       			return false;
				       		}
	    	   				wjwPaydetailGrid.store.baseParams= params;
	    	   				wjwPaydetailGrid.store.load({params:{
	    	   						start:0,
	    	   						limit:PAGESIZE,
	    	   						parentId:parentId,
	    	   						orgId:document.getElementById("orgId").value
	    	   					}});
	    	   			}
	       			},
					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
						Ext.getCmp('unitName').setValue('');
						Ext.getCmp('startTime').setValue(new Date().getFirstDateOfMonth ().format('Ymd')); 
						Ext.getCmp('endTime').setValue(new Date().format('Ymd'));
					}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:100,sortable:true,hidden:true},
		            {header:'申请单位名称',dataIndex:'unitName',width:100,sortable:true,
			            renderer:function(data, metadata, record, rowIndex, columnIndex, store){
	                    	metadata.attr = 'ext:qtip="点击打印支付申请书！"';
	                    	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+data+'</a>';
	                    }
		            },
		            {header:'申请单位编号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'申请人姓名',dataIndex:'sqUsername',width:100,sortable:true,hidden:false},
		            {header:'申请人编号',dataIndex:'sqUserno',width:100,sortable:true,hidden:true},
		            {header:'申请时间',dataIndex:'sqTime',width:100,sortable:true,hidden:false},
		            {header:'待审批人姓名',dataIndex:'dspUsername',width:100,sortable:true,hidden:false},
		            {header:'待审批人编号',dataIndex:'dspUserno',width:100,sortable:true,hidden:false},
		            {header:'初审人姓名',dataIndex:'csUsername',width:100,sortable:true,hidden:false},
		            {header:'初审人编号',dataIndex:'csUserno',width:100,sortable:true,hidden:true},
		            {header:'复审人姓名',dataIndex:'fsUsername',width:100,sortable:true,hidden:false},
		            {header:'复审人编号',dataIndex:'fsUserno',width:100,sortable:true,hidden:true},
		            {header:'支出类型',dataIndex:'payType',width:100,sortable:true,hidden:false},
		            {header:'关联号',dataIndex:'connNo',width:100,sortable:true,hidden:true},
		            {header:'状态',dataIndex:'status',width:100,sortable:true,
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
		            {header:'备注',dataIndex:'remark',width:100,sortable:true,hidden:false}
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
//    	if(!this.wjwPaydetailWindow)
//    		this.wjwPaydetailWindow = new WjwPaydetailWindow();
//    	var win = this.wjwPaydetailWindow;
    	Ext.Ajax.request({
			url:GET_PAY_ACCOUNT_URL,
			method: 'POST',
			success: function(resp,opts){
				var result = Ext.util.JSON.decode(resp.responseText);
				Ext.getCmp('payAmount').setValue(result.accAmount);
				Ext.getCmp('payMedcAmt').setValue(result.medcAmt);
				Ext.getCmp('payDrugAmt').setValue(result.drugAmt);
			},
			failure: function(action){
				Ext.Msg.alert('系统提示',action.result.message);
			}
		});
    	this.wjwPaydetailWindow = new WjwPaydetailWindow();
    	var win = this.wjwPaydetailWindow;
		win.show();
		win.wjwPaydetail.getForm().reset();
    },
    onEditClick:function(){
    	var records = this.getSelectionModel().getSelections();
    	if(records.length>0){
    		if(records.length==1){
    			var record = records[0];
    			if(records[0].data.status==1){
    				var connNo = records[0].data.connNo;
    				parent.Home.AddTab(connNo+new Date(),"支付申请明细修改", '/WjwPaydetail/toModifyPaydetail?connNo='+connNo);
    			}else{
    				Ext.Msg.alert('系统提示','只能修改申请状态的记录！');
    			}
    		}else{
    			Ext.Msg.alert('系统提示','不能同时修改多条记录！');	
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
    		if(records[i].data.status != 1){
				Ext.Msg.alert('系统提示','非申请状态的记录不能作废！');
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
    						Ext.Msg.alert('系统提示',"操作成功！");
    						//wjwPaydetailGrid.vbbar.doLoad(wjwPaydetailGrid.vbbar.cursor);
    						 parentId = document.getElementById("parentId").value;
						     if(parentId==0){
						    	parentId="";
						     }else{
						    	parentId="#######";
						     }
						     wjwPaydetailGrid.store.load({params:{
							    	start:0,
							    	limit:PAGESIZE,
							    	parentId:parentId,
							    	orgId:document.getElementById("orgId").value
						    	}});
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
    onAmputateClick: function(){
    	var records = this.getSelectionModel().getSelections();
    	var ids = {};
		var valueStr = [];
    	for(var i=0;i<records.length;i++){
    		if(records[i].data.status != 4){
				Ext.Msg.alert('系统提示','非作废状态的记录不能删除！');
				return;
			}
    		valueStr.push(records[i].get('id'));
    	}
		ids['ids'] = valueStr;
		
    	if(records.length>0){
    		Ext.Msg.confirm('系统提示：',"确定删除这"+records.length+"条信息吗？",function(btn){
    			if(btn == 'yes'){
    				Ext.Ajax.request({
    					url: ENTITY_URL_AMPULATE,
    					method: 'POST',
    					params: ids,
    					success: function(form,action){
    						Ext.Msg.alert('系统提示',"删除成功！");
    						//wjwPaydetailGrid.vbbar.doLoad(wjwPaydetailGrid.vbbar.cursor);
    						 parentId = document.getElementById("parentId").value;
						     if(parentId==0){
						    	parentId="";
						     }else{
						    	parentId="#######";
						     }
						     wjwPaydetailGrid.store.load({params:{
							    	start:0,
							    	limit:PAGESIZE,
							    	parentId:parentId,
							    	orgId:document.getElementById("orgId").value
						    	}});
    					},
    					failure: function(form,action){
    						alert(action.result.message);
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
/************************************************************/
document.onAClick = function(value){
	var cnnNo = wjwPaydetailGrid.getSelectionModel().getSelections()[0].data.connNo;
	var status = wjwPaydetailGrid.getSelectionModel().getSelections()[0].data.status;
	var sqTime = wjwPaydetailGrid.getSelectionModel().getSelections()[0].data.sqTime;
	if(status==1){
		window.open('/WjwPaydetail/PayDetailMx?cnnNo='+cnnNo+'&sqTime='+sqTime);
	}else{
		Ext.MessageBox.alert("系统提示：",'只有申请状态可打印支付申请书！');
	}
	
};
/*************onReady组件渲染处理***********************/
Ext.onReady(function(){

    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    parentId = document.getElementById("parentId").value;
    if(parentId==0){
    	parentId="";
    }else{
    	parentId="#######";
    }
    wjwPaydetailGrid = new WjwPaydetailGrid(Ext.getBody().getViewSize().height);
    wjwPaydetailGrid.store.load({params:{
	    	start:0,
	    	limit:PAGESIZE,
	    	parentId:parentId,
	    	orgId:document.getElementById("orgId").value
    	}});
    
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaydetailGrid   
		]
	});
	
	
	popupGrid = new PopupGrid(Ext.getBody().getViewSize().height);
	popupGrid.store.load({params:{start:0,limit:PAGESIZE}});
	
		new Ext.Viewport({
			layout: 'border',
			items: [popupGrid ]
		});
	
	
});

