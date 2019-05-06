var ENTITY_URL_LIST = "/WjwAccchange/findQfMx";
var PAGESIZE=50;

/*
 * WJW_ACCCHANGE 
 * @author chenyong
 * @date 2015-11-04
 */

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
		            {name:'note2'},
		            {name:'flag'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
//				    '-',{xtype:'button',text:'待清分账务入账',iconCls:'add',handler:this.onAddClick,scope:this},
//				    '-',{xtype:'button',text:'待清分账务明细分割',iconCls:'edit',handler:this.onModifyClick,scope:this},
//				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Y-m-d',editable: false},
//				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Y-m-d',editable: false},
//			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
//			       			var params = {};
//			       			wjwAccchangeGrid.vtbar.items.each(function(item,index,length){ 
//		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
//		       						if (item.getXType() == 'datefield') {
//		       							params[item.getId()] = item.getValue().format(item.format);
//		       						} else {
//		       							params[item.getId()] = item.getValue();
//		       						}
//		       					}
//							});
//		    	   			var startTime = Ext.getCmp('startTime').getValue();
//		    	   			var endTime = Ext.getCmp('endTime').getValue();
//		    	   			if(startTime){
//		    	   				startTime = startTime.format('Ymd');
//		    	   			}
//		    	   			if(endTime){
//		    	   				endTime = endTime.format('Ymd');
//		    	   			}
//		    	   			if(startTime!=null&&startTime!=''&&endTime!=null&&endTime!=''){
//			    	   			if(startTime>endTime){
//					       			Ext.Msg.alert('系统提示','截止日期应大于等于开始日期。');
//					       			return false;
//			    	   			}
//		    	   			}
//		    	   			params= {note2:document.getElementById('note2').value,startTime:startTime,endTime:endTime};
//	    	   				wjwAccchangeGrid.store.baseParams= params;
//	    	   				wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
//	    	   			}
//	       			},
//					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
//						wjwAccchangeGrid.vtbar.items.each(function(item,index,length){   
//							if((""+item.getXType()).indexOf("field") != -1) {
//								item.setValue('');
//							}
//						  });  
//    	   			}}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);	
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:100,sortable:true,hidden:true},
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:true},
		            {header:'机构名称',dataIndex:'unitName',width:100,sortable:true,hidden:true},
		            {header:'己方账号',dataIndex:'accNo',width:100,sortable:true,hidden:false},
		            {header:'己方账户名',dataIndex:'accName',width:180,sortable:true,hidden:false,
		              	renderer: function(value){
			            	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+value+'</a>';	
						}
		            	},
		            {header:'对方账号',dataIndex:'dfAccno',width:100,sortable:true,hidden:false},
		            {header:'对方账户名',dataIndex:'dfAccname',width:180,sortable:true,hidden:false},
		            {header:'交易金额',dataIndex:'tranAmt',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }
		            	},
		            {header:'余额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'药品金额',dataIndex:'drugAmt',width:100,sortable:true,hidden:false},
		            {header:'医疗金额',dataIndex:'medcAmt',width:100,sortable:true,hidden:false},
		            {header:'其他金额',dataIndex:'otherAmt',width:100,sortable:true,hidden:true,
		            	renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'交易时间',dataIndex:'tranTime',width:150,sortable:true,hidden:false},
		            {header:'收入/支出',dataIndex:'inOrOut',width:100,sortable:true,hidden:false,
		            	renderer:function(data){
		            		if(data ==1){
		            			return '收入';
		            		}else if(data ==2){
		            			return '支出';
		            		}else{
		            			return data;
		            		}		            		
		            	}},
		            
//		            {header:'凭证号',dataIndex:'note1',width:100,sortable:true,hidden:false},
//		            {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:false},
		            {header:'状态',dataIndex:'flag',width:100,sortable:true,hidden:true,
		            	renderer:function(data){
		            		if(data ==1){
		            			return '入账';
		            		}else if(data ==2){
		            			return '清分';
		            		}else if(data==3){
		            			return '利息';
		            		}else{
		            			return data;
		            		}		            		
		            	}}
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
	 			
		if(!this.dqfAllInAddWindow)
		this.dqfAllInAddWindow = new DqfAllInAddWindow();
			
	    var win = this.dqfAllInAddWindow;
		var winForm = win.dqfAllInForm;
		
		win.show();
					
	    winForm.id_.setValue(vrecord.data['id']);
	    winForm.id_.setReadOnly(true);
		winForm.accNo.setValue(vrecord.data.accNo);
		winForm.accNo.setReadOnly(true);
		winForm.custName.setValue(vrecord.data.custName);
		winForm.custName.setReadOnly(true);
		winForm.amount.setValue(vrecord.data.amount);
		winForm.amount.setReadOnly(true);	
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {  			
   			if(records.length == 1){   				
   				vrecord = records[0];
   				if(vrecord.data.flag!=1){
   					Ext.Msg.alert('系统提示','该条记录已清分！');
   					return;
   				}
				this.dqfDetailAddWindow = new DqfDetailAddWindow();
   				
   		    	var win = this.dqfDetailAddWindow;
				var winForm = win.dqfDetailForm;
   				var accNo = vrecord.data.accNo;   				
				var parent = vrecord.data.dfAccno;
				winForm.id_.setValue(vrecord.data.id);
				winForm.accNo.setValue(vrecord.data.accNo);
				winForm.custName.setValue(vrecord.data.accName);
				winForm.custName.setReadOnly(true);
				winForm.amount.setValue(vrecord.data.tranAmt);
				winForm.amount.setReadOnly(true);
   		    	Ext.Ajax.request({
   		    		url:DQF_URL_DETAIL,
   		    		params:{accNo:accNo,parent:parent},
   		    		method:'POST', 
   		    		success:function(resp,options){
   		    			var results = Ext.util.JSON.decode(resp.responseText);
   		    			for(var i=0;i<results.length;i++){
   		    				var obj = results[i];

   		    				winForm.add(new Ext.form.TextField({
   		    					anchor:'95%',
   								allowBlank : true, 
   								fieldLabel:obj.custName,
   								name:"accNo"+i,
   								value:obj.accNo,
   								readOnly:true
   							}));
   		    				winForm.add(new Ext.form.TextField({
   		    					anchor:'95%',
   								allowBlank : true, 
   								fieldLabel:'金额：',
   								name:"amount"+i
   							}));
   		    			}
   		    			win.show();
   		    			
   		    		},
   		    		failure:function(action){
   		    			Ext.MessageBox.alert("系统提示：",action);
   		    		}
   		    	});
   		    	
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
    var note2 = document.getElementById('note2').value;  
    wjwAccchangeGrid = new WjwAccchangeGrid(Ext.getBody().getViewSize().height);
    wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE,note2:note2}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwAccchangeGrid   
		]
	});
});

