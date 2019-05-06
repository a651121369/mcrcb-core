var SCHOOL_GRID_STORE_URL = '/mcrcb-core/getWjwJKInfo';
var PAGESIZE=20;


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
			        {name:'UNIT_NAME'},{name:'INC_TIME'},  {name:'AMOUNT'},                                                           
					{name:'DRUG_AMT'},{name:'MEDICAL_AMT'},{name:'OUT_ACCNAME'},
					{name:'STATUS'},{name:'NOTE1'}
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
//			    	   			var UNIT_NAME = Ext.getCmp('UNIT_NAME').getValue();
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
			       					
				    	   		   var url="/mcrcb-core/getWjwJKInfoDown?beginDate="+beginDate+"&endDate="+endDate+
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
                                          {header: '缴款时间', width: 100,align:'center', sortable: true, dataIndex: 'INC_TIME'},
                                          {header: '缴款金额', width: 100,align:'right', sortable: true, dataIndex: 'AMOUNT',
                                        	  renderer: function(value){
                      		            		return Ext.util.Format.number(value,'0,000.00');
                      						}},
                                          {header: '药品收入', width: 100,align:'right', sortable: true, dataIndex: 'DRUG_AMT',
                      							renderer: function(value){
                      			            		return Ext.util.Format.number(value,'0,000.00');
                      							}},
                                          {header: '医疗收入', width: 100,align:'right', sortable: true, dataIndex: 'MEDICAL_AMT',
                      								renderer: function(value){
                      				            		return Ext.util.Format.number(value,'0,000.00');
                      								}},
                                          {header: '缴款人', width: 180,align:'center', sortable: true, dataIndex: 'OUT_ACCNAME'},
                                          {header: '状态', width: 180,align:'center', sortable: true, dataIndex: 'STATUS',
                                        		renderer:function(data, metadata, record){
                        		            		if(data ==5){
                        		            			return '<font color="blue">未记账</font>';
                        		            		}else if(data ==6){
                        		            			return '<font color="blue">记账完成</font>';
                        		            		}else{
                        		            			return data;
                        		            		}
                        		            		
                        		            	}},
                                          {header: '备注', width: 150,align:'center', sortable: true, dataIndex: 'NOTE1'}
            ]),
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
    
    schoolGrid = new SchoolGrid(Ext.getBody().getViewSize().height);
    schoolGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        schoolGrid   
		]
	});
});