var PAGESIZE=50;
/***************************************************************/
DownloadBackupGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url:"/DownloadBackup/pager",method:'POST'}),
		reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
	            {name:'id'},{name:'name'},{name:'time'},{name:'fileName'},{name:'address'}             
				 ])
	});
		this.vtbar = new Ext.Toolbar({
			items: [
			       '-',{xtype:'button',text:'备份',iconCls:'add',handler:this.onAddClick,scope:this}, 
			       '-',{xtype:'button',text:'下载',iconCls:'edit',handler:this.onEditClick,scope:this},			      					
			]
		});
		
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'ID',dataIndex:'id',width:30,sortable:true,hidden:true},
		            {header:'备份人',dataIndex:'name',width:120,sortable:true,hidden:false},
		            {header:'备份时间',dataIndex:'time',width:120,sortable:true,hidden:false},
		            {header:'备份文件名',dataIndex:'fileName',width:200,sortable:true,hidden:false},
		            {header:'备份地址',dataIndex:'address',width:700,sortable:true,hidden:false},
		           ]);
		DownloadBackupGrid.superclass.constructor.call(this,{
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
    	Ext.Ajax.request({
			url:"/DownloadBackup/Backup",
			method: 'POST',
			success: function(form,action){
				Ext.Msg.alert("系统提示：","备份成功！");
				DownloadBackupGrid.store.load({params:{start:0,limit:PAGESIZE}});
			},
			failure: function(form,action){
				Ext.Msg.alert('系统提示',"备份失败！");
			}
		});    	
    },
    
    onEditClick:function(){
    	var records = this.getSelectionModel().getSelections();
    	if(records.length>0){
    		if(records.length==1){
    			var id = records[0].id;
    			var url="/DownloadBackup/download?id="+id;
    			var w  = window.open(url,'_blank');
    			w.location.href = url;
//    			Ext.Ajax.request({
//    				url:"/DownloadBackup/download",
//    				method: 'POST',
//    				params:{add:add,fileName:fileName},
//    				success: function(form,action){
//    					Ext.Msg.alert("系统提示：","下载成功！");
//    					DownloadBackupGrid.store.load({params:{start:0,limit:PAGESIZE}});
//    				},
//    				failure: function(form,action){
//    					Ext.Msg.alert('系统提示',"下载失败！");
//    				}
//    			});
    			
    		}else{
    			Ext.Msg.alert('系统提示','不能同时下载多条记录！');	
    		}
    		
    	}else{
    		Ext.Msg.alert('系统提示','请选择一条记录！');
    	}
    }
});

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    DownloadBackupGrid = new DownloadBackupGrid(Ext.getBody().getViewSize().height);
    DownloadBackupGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        DownloadBackupGrid	   
		]
	});
});