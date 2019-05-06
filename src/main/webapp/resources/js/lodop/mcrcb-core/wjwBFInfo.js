var ENTITY_URL_LIST = "/mcrcb-core/getWjwBFInfo";
var PAGESIZE=50;

/****************IpasJxjgCkHyGrid***********************/
IpasJxjgCkHyGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'UNIT_NAME'},
		            {name:'BF_TIME'},
		            {name:'AMOUNT'},
//		            {name:'BF_ITEM'},
		            {name:'IN_BFQ_AMT'},
		            {name:'IN_BFH_AMT'},
		            {name:'OUT_BFQ_AMT'},
		            {name:'OUT_BFH_AMT'}
					 ])
		});
		this.vbbar= this.createPagingToolbar(PAGESIZE);
		this.vtbar = new Ext.Toolbar({
			items: [
			        	{xtype:'label',text:'交易日期'},{xtype:'datefield',id:'beginDate',format: 'Ymd',editable: false},'',{xtype:'label',text:' 至 '},'',{xtype:'datefield',id:'endDate',format: 'Ymd',editable: false},
			        	'-',{xtype:'label',text:'机构编号:'},
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
				            forceSelection: false,
				            lastQuery: '',
				            displayField:'NAME',
				            valueField:'CODE',
				            id:'UNIT_NO',
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
//						'-',{xtype:'label',text:'机构名称:'},{xtype:'textfield',width:80,id:'UNIT_NAME'},
				       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
				    	    var UNIT_NO = Ext.getCmp('UNIT_NO').getValue();
//				    	    var UNIT_NAME = Ext.getCmp('UNIT_NAME').getValue();
		    	   			var beginDate = Ext.getCmp('beginDate').getValue();
		    	   			var endDate = Ext.getCmp('endDate').getValue();
		    	   			if(beginDate){
		    	   				beginDate = beginDate.format('Ymd');
		    	   			}
		    	   			if(endDate){
		    	   				endDate = endDate.format('Ymd');
		    	   			}
		    	   			if(beginDate <= endDate){
		    	   				ipasJxjgCkHyGrid.store.baseParams= {
		    	   					beginDate:beginDate,
		    	   					endDate:endDate,
		    	   					UNIT_NO:UNIT_NO
//		    	   					UNIT_NAME:UNIT_NAME
		    	   			};
		    	   				ipasJxjgCkHyGrid.store.load({params:{start:0,limit:PAGESIZE}});
		    	   			}else{
		    	   				Ext.Msg.alert('系统提示', '交易起始日期不能大于结束');
		    	   			}
		       			}
		       
		       		},
		       '-',{xtype:'button',text:'导出Excel文件',iconCls:'excel',handler:function(){
		       			
		       			Ext.Msg.confirm("系统提示：", "确定下载查询结果吗？", function(btn){

		       				if(btn=="yes"){
		       					
		       					var beginDate = Ext.getCmp('beginDate').getValue();
			    	   			var endDate = Ext.getCmp('endDate').getValue();
			    	   			var UNIT_NO = Ext.getCmp('UNIT_NO').getValue();
//			    	   			var UNIT_NAME = Ext.getCmp('UNIT_NAME').getValue();
			    	   			if(beginDate){
			    	   				beginDate = beginDate.format('Ymd');
			    	   			}
			    	   			if(endDate){
			    	   				endDate = endDate.format('Ymd');
			    	   			}
			    	   			if(beginDate > endDate){
			    	   				Ext.Msg.alert('系统提示', '交易起始日期不能大于结束');
			    	   				return ;
			    	   			}
		       					
			    	   		   var url="/mcrcb-core/getWjwBFInfoDown?beginDate="+beginDate+"&endDate="+endDate+
			    	   		   		"&UNIT_NO="+UNIT_NO ;
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
//		             this.vsm,
		            {header:'',dataIndex:'UNIT_NAME',width:180,align:'center',sortable:true,hidden:false},
		            {header:'',dataIndex:'BF_TIME',width:100,align:'center',sortable:true,hidden:false},
		            {header:'',dataIndex:'AMOUNT',width:100,align:'right',sortable:true,hidden:false,
		            	renderer: function(value){
		            		return Ext.util.Format.number(value,'0,000.00');
						}},
//		            {header:'',dataIndex:'BF_ITEM',width:100,align:'right',sortable:true,hidden:false,
//		            	renderer:function(data, metadata, record){
//	                        if(data ==1){
//		            			return '药品';
//		            		}else if(data ==2){
//		            			return '医疗';
//		            		}else{
//		            			return data;
//		            		}
//		            	}
//		            },
		            
		            {header:'拨付前',dataIndex:'IN_BFQ_AMT',width:120,sortable:true,hidden:false,align: 'right',
			            renderer: function(value){
		            		return Ext.util.Format.number(value,'0,000.00');
						}},
		            {header:'拨付后',dataIndex:'IN_BFH_AMT',width:120,sortable:true,hidden:false,align: 'right',
			            renderer: function(value){
		            		return '<a onclick="document.onClickdgck();" style="color:blue;cursor: pointer;">'+Ext.util.Format.number(value,'0,000.00')+'</a>';
						}},

		            {header:'拨付前',dataIndex:'OUT_BFQ_AMT',width:120,sortable:true,hidden:false,align: 'right',
			            renderer: function(value){
		            		return Ext.util.Format.number(value,'0,000.00');
						}},
		            {header:'拨付后',dataIndex:'OUT_BFH_AMT',width:120,sortable:true,hidden:false,align: 'right',
			            renderer: function(value){
		            		return '<a onclick="document.onClickcxck();" style="color:blue;cursor: pointer;">'+Ext.util.Format.number(value,'0,000.00')+'</a>';
						}}
		           ]);
		IpasJxjgCkHyGrid.superclass.constructor.call(this,{
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
			plugins : [new Ext.ux.grid.ColumnHeaderGroup({
                rows : [[
                        {header : "<div style='padding-top:12px;text-align:center'></div>", colspan : 1,align : 'center'},
                        {header : "<div style='padding-top:12px;text-align:center'>机构名称</div>",colspan : 1,align : 'center'},
                        {header : "<div style='padding-top:12px;text-align:center'>拨付时间</div>",colspan : 1,align : 'center'},
                        {header : "<div style='padding-top:12px;text-align:center'>拨付金额</div>",colspan : 1,align : 'center'},
//                        {header : "<div style='padding-top:12px;text-align:center'>科目</div>",colspan : 1,align : 'center'},
                        {header : "<div style='padding-top:12px;text-align:center'>收入账户余额(元)</div>",colspan : 2,align : 'center'},
                        {header : "<div style='padding-top:12px;text-align:center'>支出账户余额(元)</div>",colspan : 2,align : 'center'}
                ]]
            })],
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
    
    ipasJxjgCkHyGrid = new IpasJxjgCkHyGrid(Ext.getBody().getViewSize().height);
    ipasJxjgCkHyGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        ipasJxjgCkHyGrid   
		]
	});
});

