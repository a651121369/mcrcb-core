var SCHOOL_GRID_STORE_URL = '/mcrcb-core/getWjwZFInfo';
var PAGESIZE=20;

/*********************CertNumGrid*********************/
CertNumGrid = Ext.extend(UxGrid, {
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url:"/wjwCertNum/pager",method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'time'},{name:'certNo'},{name:'userName'},{name:'unitName'},{name:'note'}              
					 ])
		});
		
		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([
			 new Ext.grid.RowNumberer(),
	        {header:'日期',dataIndex:'time',width:150,sortable:true,hidden:false},
	        {header:'凭证号',dataIndex:'certNo',width:150,sortable:true,hidden:false},
	        {header:'修改人',dataIndex:'userName',width:150,sortable:true,hidden:false},
	        {header:'卫生院',dataIndex:'unitName',width:150,sortable:true,hidden:false},
	        {header:'备注',dataIndex:'note',width:250,sortable:true,hidden:false},
	     ]);
		CertNumGrid.superclass.constructor.call(this,{
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
});
/**************CertNumGridWindow*********************/
CertNumGridWindow = Ext.extend(Ext.Window,{
	constructor: function(grid){
		this.CertNumGrid = new CertNumGrid();
		CertNumGridWindow.superclass.constructor.call(this,{
			title: '凭证号信息',
			width: 900,
			anchor: '100%',
			autoHeight:true,
			autoScroll:true,
			resizable : false, //可变尺寸的；可调整大小的
			plain: true,
			modal: true,
			closeAction: 'hide',
			items:[this.CertNumGrid]
		});
	}
});


/****************GenerafitGrid***********************/
SchoolGrid = Ext.extend(UxGrid,{
	pageSizeCombo: null,
	vtbar:null,				//面板顶部的工具条	
	vbbar:null,				//面板底部的工具条
    store:null,
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url:SCHOOL_GRID_STORE_URL,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
			        {name:'UNIT_NAME'},{name:'PAY_TIME'},  {name:'IN_NAME'},{name:'OPER_NO'},                                                           
					{name:'IN_ACCNO'},{name:'AMOUNT'},{name:'ITEM'},{name:'STATUS'},{name:'YT'},
					{name:'NOTE1'}
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
//					'-',{xtype:'label',text:'机构名称:'},{xtype:'textfield',width:80,id:'UNIT_NAME'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
					    	    var UNIT_NO = Ext.getCmp('UNIT_NO').getValue();
//					    	    var UNIT_NAME = Ext.getCmp('UNIT_NAME').getValue();
			    	   			var beginDate = Ext.getCmp('beginDate').getValue();
			    	   			var endDate = Ext.getCmp('endDate').getValue();
			    	   			if(beginDate){
			    	   				beginDate = beginDate.format('Ymd');
			    	   			}
			    	   			if(endDate){
			    	   				endDate = endDate.format('Ymd');
			    	   			}
			    	   			if(beginDate <= endDate){
			    	   			schoolGrid.store.baseParams= {
			    	   					beginDate:beginDate,
			    	   					endDate:endDate,
			    	   					UNIT_NO:UNIT_NO
//			    	   					UNIT_NAME:UNIT_NAME
			    	   			};
			    	   			schoolGrid.store.load({params:{start:0,limit:PAGESIZE}});
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
//				    	   			var UNIT_NAME = Ext.getCmp('UNIT_NAME').getValue();
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
			       					
				    	   		   var url="/mcrcb-core/getWjwZFInfoDown?beginDate="+beginDate+"&endDate="+endDate+
				    	   		   		"&UNIT_NO="+UNIT_NO ;
			    	   			   var w  = window.open(url,'_blank');
					    	       w.location.href = url;
				    	   	 
			       				}
			       				
			       			});
			       		},scope:this}
			]
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel(); 
		SchoolGrid.superclass.constructor.call(this, {
        	region:'center',
        	stripeRows: true,
            frame: false,
            height: height,
            viewConfig: {
                forceFit: false
            },
            loadMask: {
                msg : '正在载入数据,请稍候...'
            },
            sm: sm,
            cm: new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
                                         // sm,
                                          {header: '机构名称', width: 180,align:'center', sortable: true, dataIndex: 'UNIT_NAME'},
                                          {header: '支付申请日期', width: 120,align:'center', sortable: true, dataIndex: 'PAY_TIME'},
                                          {header: '凭证编号', width: 120,align:'center', sortable: true, dataIndex: 'OPER_NO',
                                        	  renderer:function(data, metadata, record, rowIndex, columnIndex, store){
                      	                    	metadata.attr = 'ext:qtip="点击查询凭证号修改信息！"';
                      	                    	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+data+'</a>';
                      	                    }
                                          },
                                          {header: '收款人', width: 180,align:'center', sortable: true, dataIndex: 'IN_NAME'},
                                          {header: '收款账户', width: 180,align:'center', sortable: true, dataIndex: 'IN_ACCNO'},
                                          {header: '申请金额', width: 80,align:'right', sortable: true, dataIndex: 'AMOUNT',
                                        	  renderer: function(value){
                      		            		return Ext.util.Format.number(value,'0,000.00');
                      						}},
//                                          {header: '科目', width: 80,align:'center', sortable: true, dataIndex: 'ITEM'},
                                          {header:'科目',dataIndex:'ITEM',width:100,align:'center',sortable:true,hidden:false,
	                      		            	renderer:function(data, metadata, record){
	                      	                        if(data==1){
	                      		            			return '医疗';
	                      		            		}else if(data==2){
	                      		            			return '药品';
	                      		            		}else{
	                      		            			return '其他';
	                      		            		}
	                      		            	}
                      		              },
                      		              {header:'状态',dataIndex:'STATUS',width:100,align:'center',sortable:true,hidden:false,
	                      		            	renderer:function(data, metadata, record){
	                      	                        if(data==1){
	                      		            			return '申请';
	                      		            		}else if(data==2){
	                      		            			return '初审';
	                      		            		}else if(data==3){
	                      		            			return '复审';
	                      		            		}else if(data==4){
	                      		            			return '作废';
	                      		            		}else if(data==5){
	                      		            			return '未记账';
	                      		            		}else if(data==6){
	                      		            			return '记账完成';
	                      		            		}else{
	                      		            			return data;
	                      		            		}
	                      		            	}
                    		              },
                                          {header: '用途', width: 150,align:'center', sortable: true, dataIndex: 'YT'},
                                          {header: '备注', width: 150,align:'center', sortable: true, dataIndex: 'NOTE1'}
            ]),
            tbar: this.vtbar,
            bbar: this.vbbar,
            ds: this.store
        });
    }
		
		
   
    
});

/************************************************************/
document.onAClick = function(value){
	var certNo = schoolGrid.getSelectionModel().getSelections()[0].data.OPER_NO;
	this.CertNumGridWindow = new CertNumGridWindow();
	var win = this.CertNumGridWindow;
	win.show();
	win.CertNumGrid.store.load({params:{start:0,limit:99999,certNo:certNo}});	
};

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    schoolGrid = new SchoolGrid(Ext.getBody().getViewSize().height);
    schoolGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        schoolGrid   
		]
	});
});