var ENTITY_URL_LIST = "/WjwAccchange/queryTradeDetail";
var PAGESIZE=50;
/*
 * WJW_ACCCHANGE 
 * @author liuyong
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

				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,
				    	value:new Date().getFirstDateOfMonth ().format('Ymd')},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,
				    	value:new Date().format('Ymd')},
				    '-',{xtype:'label',text:'选择账户'},
				    new Ext.form.ComboBox({
				    	width:400,
			        	autoLoad: true,
			            typeAhead: false,
			            emptyText: '请选择...',
			            triggerAction: 'all',
			            isFormField: true,
//			            listWidth : 160,
//			            mode: 'local',
			            mode: 'remote',
			            hiddenName :'accNo',
			            name:'accNo',
			            allowBlank: true,
			            blankText:'请选择...',
			            forceSelection: false,
			            lastQuery: '',
			            displayField:'accName',
			            valueField:'accNo',
			            id:'accNo',
			            selectOnFocus: true,
			            store: new Ext.data.Store({
			                proxy: new Ext.data.HttpProxy({url: '/WjwAccount/findAllAccounts', method: 'POST'}),
			                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'accNo'},{name:'accName'}]))
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
//				    '-',{xtype:'label',text:'子账户'},
//				    new Ext.form.ComboBox({
//			        	autoLoad: true,
//			            typeAhead: false,
//			            emptyText: '请选择...',
//			            triggerAction: 'all',
//			            isFormField: true,
////			            listWidth : 160,
////			            mode: 'local',
//			            mode: 'remote',
//			            hiddenName :'NAME',
//			            name:'NAME',
//			            allowBlank: true,
//			            blankText:'请选择...',
//			            forceSelection: false,
//			            lastQuery: '',
//			            displayField:'NAME',
//			            valueField:'CODE',
//			            id:'UNIT_NO',
//			            selectOnFocus: true,
//			            store: new Ext.data.Store({
//			                proxy: new Ext.data.HttpProxy({url: '/public/getOrganization', method: 'POST'}),
//			                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'CODE'},{name:'NAME'}]))
//			            }),
//			            editable : true,
//			            listeners : {
//			            	'beforequery':function(e){  
//			            		var combo = e.combo; 
//			            		combo.collapse();
//			                	if(!e.forceAll){  
//			                		var value = e.query;  
//			                		combo.store.filterBy(function(record,id){  
//			                			var text = record.get(combo.displayField);  
//			                			//用自己的过滤规则,如写正则式  
//			                			return (text.indexOf(value)!=-1);    //实现的核心
//			                		}); 
//			                		combo.onLoad(); 
//			                		combo.expand();  
//			                		return false;  
//			                	}
//			            	},
//			            	'focus':function(combo){  
//			                	combo.store.load();
//			            	} 
//			            }  
//			        }),
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
		    	   			var startTime = Ext.getCmp('startTime').getValue();
		    	   			var endTime = Ext.getCmp('endTime').getValue();
		    	   			var accNo = Ext.getCmp('accNo').getValue();
		    	   			if(startTime){
		    	   				startTime = startTime.format('Ymd');
		    	   			}
		    	   			if(endTime){
		    	   				endTime = endTime.format('Ymd');
		    	   			}
		    	   			if(startTime!=null&&startTime!=''&&endTime!=null&&endTime!=''){
			    	   			if(startTime>endTime){
					       			Ext.Msg.alert('系统提示','截止日期应大于等于开始日期。');
					       			return false;
			    	   			}
		    	   			}
		    	   			
		    	   			params= {startTime:startTime,endTime:endTime,accNo:accNo};
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
    	   			}},
    	   	        '-',{xtype:'button',text:'导出Excel文件',iconCls:'excel',handler:function(){
		       			
		       			Ext.Msg.confirm("系统提示：", "确定下载查询结果吗？", function(btn){

		       				if(btn=="yes"){
				    	   		var startTime = Ext.getCmp('startTime').getValue().format('Ymd');
				    	   		var endTime = Ext.getCmp('endTime').getValue().format('Ymd');
				    	   		var accNo = Ext.getCmp('accNo').getValue();
				    	   		var url="/WjwAccchange/downloadTradeDetail?startTime="+startTime+"&endTime="+endTime+"&accNo="+accNo ;
			    	   			var w  = window.open(url,'_blank');
					    	    w.location.href = url;
			    	   	 
		       				}
		       				
		       			});
		       		},scope:this}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:80,sortable:true,hidden:true},
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:150,sortable:true,hidden:false},
		            {header:'己方账号',dataIndex:'accNo',width:100,sortable:true,hidden:true},
		            {header:'己方账户名',dataIndex:'accName',width:180,sortable:true,hidden:false},
		            {header:'对方账号',dataIndex:'dfAccno',width:100,sortable:true,hidden:true},
		            {header:'对方账户名',dataIndex:'dfAccname',width:180,sortable:true,hidden:false},
		            {header:'余额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'交易金额',dataIndex:'tranAmt',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }
		            	},
		    
		            {header:'药品金额',dataIndex:'drugAmt',width:100,sortable:true,hidden:false,align: 'right',			            
				            renderer: function(value){
				            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
				            }},
		            {header:'医疗金额',dataIndex:'medcAmt',width:100,sortable:true,hidden:false,align: 'right',			            
					            renderer: function(value){
					            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
					            }},
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
		            	}}
		            
//		            {header:'凭证号',dataIndex:'note1',width:100,sortable:true,hidden:false},
//		            {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:false},
//		            {header:'状态',dataIndex:'flag',width:100,sortable:true,hidden:true,
//		            	renderer:function(data){
//		            		if(data ==1){
//		            			return '入账';
//		            		}else if(data ==2){
//		            			return '清分';
//		            		}else if(data==3){
//		            			return '利息';
//		            		}else{
//		            			return data;
//		            		}		            		
//		            	}}
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
	}
});


/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"  
    var startTime = new Date().getFirstDateOfMonth ().format('Ymd')
    var endTime = new Date().format('Ymd');
    wjwAccchangeGrid = new WjwAccchangeGrid(Ext.getBody().getViewSize().height);
    wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE,startTime:startTime,endTime:endTime}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwAccchangeGrid   
		]
	});
});

