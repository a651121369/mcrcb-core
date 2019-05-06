var ENTITY_URL_LIST = "/WjwBfhzb/appropriateDetail";
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
		            {name:'unitNo'},
		            {name:'unitName'},
		            {name:'amount'},
		            {name:'drugAmt'},
		            {name:'medcAmt'},
		            {name:'tranAmt'},
		            {name:'bfTime'}
		
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [

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
			        '-',{xtype:'button',text:'导出Excel文件',iconCls:'excel',handler:function(){
			       			
			       			Ext.Msg.confirm("系统提示：", "确定下载查询结果吗？", function(btn){

			       				if(btn=="yes"){
			       				   var id = document.getElementById('id').value;
				    	   		   var url="/WjwBfhzb/dowloadAppropriateDetail?id="+id ;
			    	   			   var w  = window.open(url,'_blank');
					    	       w.location.href = url;
				    	   	 
			       				}
			       				
			       			});
			       		},scope:this}
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
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:false},
		            {header:'机构名称',dataIndex:'unitName',width:200,sortable:true,hidden:false},	
		            {header:'拨付总金额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
		            {header:'拨付药品金额',dataIndex:'drugAmt',width:100,sortable:true,hidden:false,align: 'right',			            
				            renderer: function(value){
				            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
				            }},
		            {header:'拨付医疗金额',dataIndex:'medcAmt',width:100,sortable:true,hidden:false,align: 'right',			            
					            renderer: function(value){
					            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
					            }},
		            {header:'拨付时间',dataIndex:'bfTime',width:150,sortable:true,hidden:false}
		  
		           
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
    var id = document.getElementById('id').value;  
    wjwAccchangeGrid = new WjwAccchangeGrid(Ext.getBody().getViewSize().height);
    wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE,id:id}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwAccchangeGrid   
		]
	});
});

