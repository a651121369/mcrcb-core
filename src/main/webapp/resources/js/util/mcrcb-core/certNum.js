var PAGESIZE=50;
/***************************************************************/
WjwCertNumGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url:"/wjwCertNum/pager",method:'POST'}),
		reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
	            {name:'time'},{name:'certNo'},{name:'userName'},{name:'unitName'},{name:'note'}              
				 ])
	});
		this.vtbar = new Ext.Toolbar({
			items: [
			        '-',{xtype:'label',text:'凭证号'},{xtype:'textfield',id:'certNo'},
			        
			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
		       			var params = {};
		       			wjwCertNumGrid.vtbar.items.each(function(item,index,length){ 
	       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
	       						if (item.getXType() == 'datefield') {
	       							params[item.getId()] = item.getValue().format(item.format);
	       						} else {
	       							params[item.getId()] = item.getValue();
	       						}
	       					}
						});
		       			wjwCertNumGrid.store.baseParams= params;
		       			wjwCertNumGrid.store.load({params:{start:0,limit:PAGESIZE}});
    	   			}
       			},
				'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
					wjwCertNumGrid.vtbar.items.each(function(item,index,length){   
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
					 new Ext.grid.RowNumberer(),
//		             this.vsm,
//		            {header:'ID',dataIndex:'id',width:30,sortable:true,hidden:true},
		            {header:'日期',dataIndex:'time',width:150,sortable:true,hidden:false},
		            {header:'凭证号',dataIndex:'certNo',width:150,sortable:true,hidden:false},
		            {header:'修改人',dataIndex:'userName',width:150,sortable:true,hidden:false},
		            {header:'卫生院',dataIndex:'unitName',width:150,sortable:true,hidden:false},
		            {header:'备注',dataIndex:'note',width:300,sortable:true,hidden:false},
		           ]);
		WjwCertNumGrid.superclass.constructor.call(this,{
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
	
});

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwCertNumGrid = new WjwCertNumGrid(Ext.getBody().getViewSize().height);
    wjwCertNumGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwCertNumGrid	   
		]
	});
});