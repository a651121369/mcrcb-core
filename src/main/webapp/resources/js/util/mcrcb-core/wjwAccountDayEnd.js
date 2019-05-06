var ENTITY_URL_LIST = "/mcrcb-core/getAccDayEndInfo";
var GET_IN_ACCOUNT_URL = "/mcrcb-core/accountInfo";
var PAGESIZE=50;

/********************WjwAccountUpdateWindow组件*************************/
WjwPaymainUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.WjwPaymain = new WjwPaymain();
    	this.WjwPaymain.buttons[0].show();   //隐藏添加按钮
    	
    	WjwPaymainUpdateWindow.superclass.constructor.call(this, {
			title: '日结',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.WjwPaymain]
        });
    }
});

WjwPaymain = Ext.extend(Ext.ux.Form,{
	constructor: function(){
//		this.PAY_ACC_TRUE = this.createTextField('收入主账户','PAY_ACC_TRUE','95%','',null,60,'长度超过不能60');
//		this.GET_ACC_TRUE = this.createTextField('支出主账户','GET_ACC_TRUE','95%','',null,60,'长度超过不能60');
//		this.PAY_ACCNAME_TRUE = this.createTextField('收入主账户名','PAY_ACCNAME_TRUE','95%','',null,60,'长度超过不能60');
//		this.GET_ACCNAME_TRUE = this.createTextField('支出主账户名','GET_ACCNAME_TRUE','95%','',null,32,'长度超过不能32');
		this.GET_ACC_TRUE = new Ext.form.TextField({
            fieldLabel: '收入主账户',
            name: 'GET_ACC_TRUE',
            xtype: 'textfield',
            disabled : true,
            allowBlank: false,
            anchor: '95%',
            width:200,
            value:document.getElementById("GET_ACC_TRUE").value,
            blankText: '',
            vtype: null,
            maxLength: 32,
            colspan: '长度超过不能32'
        }),
        this.PAY_ACC_TRUE = new Ext.form.TextField({
            fieldLabel: '支出主账户',
            name: 'PAY_ACC_TRUE',
            xtype: 'textfield',
            disabled : true,
            allowBlank: false,
            anchor: '95%',
            width:200,
            value:document.getElementById("PAY_ACC_TRUE").value,
            blankText: '',
            vtype: null,
            maxLength: 32,
            colspan: '长度超过不能32'
        }),
        this.GET_ACCNAME_TRUE = new Ext.form.TextField({
            fieldLabel: '收入主账户名',
            name: 'GET_ACCNAME_TRUE',
            xtype: 'textfield',
            disabled : true,
            allowBlank: false,
            anchor: '95%',
            width:200,
            value:document.getElementById("GET_ACCNAME_TRUE").value,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
        this.PAY_ACCNAME_TRUE = new Ext.form.TextField({
            fieldLabel: '支出主账户名',
            name: 'PAY_ACCNAME_TRUE',
            xtype: 'textfield',
            disabled : true,
            allowBlank: false,
            anchor: '95%',
            width:200,
            value:document.getElementById("PAY_ACCNAME_TRUE").value,
            blankText: '',
            vtype: null,
            maxLength: 200,
            colspan: '长度超过不能200'
        }),
        this.GET_ACCMONEY_TRUE = new Ext.form.TextField({
            fieldLabel: '收入主账户余额',
            name: 'GET_ACCMONEY_TRUE',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:200,
            blankText:'',
            vtype: null,
            maxLength: 30,
            maxLengthText: '长度超过不能1000'
        }),
        this.PAY_ACCMONEY_TRUE = new Ext.form.TextField({
            fieldLabel: '支出主账户余额',
            name: 'PAY_ACCMONEY_TRUE',
            xtype: 'textfield',
            readOnly: false,
            allowBlank: true,
            anchor: '95%',
            width:200,
            blankText:'',
            vtype: null,
            maxLength: 30,
            maxLengthText: '长度超过不能1000'
        }),
//		this.PAY_ACCMONEY_TRUE = this.createTextField('收入主账户余额','GET_ACCMONEY_TRUE','100%','',null,30,'长度超过不能30');
//		this.GET_ACCMONEY_TRUE = this.createTextField('&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp支出主账户余额','PAY_ACCMONEY_TRUE','100%','',null,20,'长度超过不能20');
		
		this.PAY_ACC_TRUE.allowBlank = true;
		this.PAY_ACCNAME_TRUE.allowBlank = true;
		this.PAY_ACCMONEY_TRUE.allowBlank = true;
		this.GET_ACC_TRUE.allowBlank = true;
		this.GET_ACCNAME_TRUE.allowBlank = true;
		this.GET_ACCMONEY_TRUE.allowBlank = true;
		
        WjwPaymain.superclass.constructor.call(this, {
        	region: 'north',
            anchor: '100%',
            autoHeight:true,
            autoWidth:true,//
            labelWidth: 200,
            labelAlign :'right',
            frame: true,
            bodyStyle:"padding: 5px 5px 0",
            layout: 'table',
            layoutConfig: {columns: 6},
            items:[
                   {xtype:'label',text:'收入主账户'},{colspan:2,items:[this.GET_ACC_TRUE]},{xtype:'label',text:'支出主账户'},{colspan:2,items:[this.PAY_ACC_TRUE]},
                   {xtype:'label',text:'收入主账户名'},{colspan:2,items:[this.GET_ACCNAME_TRUE]},{xtype:'label',text:'支出主账户名'},{colspan:2,items:[this.PAY_ACCNAME_TRUE]},
                   {xtype:'label',text:'收入主账户余额'},{colspan:2,items:[this.GET_ACCMONEY_TRUE]},{xtype:'label',text:'支出主账户余额'},{colspan:2,items:[this.PAY_ACCMONEY_TRUE]}
            ],
            buttonAlign :'center',
            buttons: [
               {text: '确定', width: 20,iconCls:'edit', hidden: true, handler: this.updateFormClick, scope: this}
            ]
	        });
	},
	
	updateFormClick: function(){
		if(this.getForm().isValid()){
			PAY_ACC_TRUE = this.PAY_ACC_TRUE.getValue();
			PAY_ACCNAME_TRUE = this.PAY_ACCNAME_TRUE.getValue();
			GET_ACC_TRUE = this.GET_ACC_TRUE.getValue();
			GET_ACCNAME_TRUE = this.GET_ACCNAME_TRUE.getValue();
		    this.getForm().submit({
				waitMsg: '正在提交，请稍后...',
				url: "/mcrcb-core/addAccDayEndInfo",
				method: 'POST',
				params: {
					PAY_ACC_TRUE: PAY_ACC_TRUE,PAY_ACCNAME_TRUE:PAY_ACCNAME_TRUE,
					GET_ACC_TRUE:GET_ACC_TRUE,GET_ACCNAME_TRUE:GET_ACCNAME_TRUE
//						PAY_ACCMONEY_TRUE:PAY_ACCMONEY_TRUE,GET_ACCMONEY_TRUE:GET_ACCMONEY_TRUE
			    },
				success: function(form,action){
					Ext.Msg.alert("系统提示：","日结成功！");
					wjwPaymainGrid.store.load({params:{
					    	start:0,
					    	limit:PAGESIZE
				    	}});
					wjwPaymainGrid.wjwPaymainUpdateWindow.hide();
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
					wjwPaymainGrid.store.load({params:{
				    	start:0,
				    	limit:PAGESIZE
			    	}});
					wjwPaymainGrid.wjwPaymainUpdateWindow.hide();
				}
		});
		}
	}
});



/****************WjwPaymainGrid***********************/
WjwPaymainGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
		            {name:'PAY_ACC_TRUE'},
		            {name:'PAY_ACCNAME_TRUE'},
		            {name:'PAY_ACCMONEY_TRUE'},
		            {name:'GET_ACC_TRUE'},
		            {name:'GET_ACCNAME_TRUE'},
		            {name:'GET_ACCMONEY_TRUE'},
		            {name:'PAY_ACC_FAL'},
		            {name:'PAY_ACCNAME_FAL'},
		            {name:'PAY_ACCMONEY_FAL'},
		            {name:'GET_ACC_FAL'},
		            {name:'GET_ACCNAME_FAL'},
		            {name:'GET_ACCMONEY_FAL'},
		            {name:'STATUS_DAYEND'},
		            {name:'DATE_DAYEND'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			        '-',{xtype:'button',text:'日结',iconCls:'edit',handler:this.onAddClick,scope:this}, 
					'-','-','-',{xtype:'label',text:'收入主账户'},{xtype:'textfield',id:'GET_ACC_TRUE'},
					'-',{xtype:'label',text:'收入主账户名'},{xtype:'textfield',id:'GET_ACCNAME_TRUE'},
					'-',{xtype:'label',text:'支出主账户'},{xtype:'textfield',id:'PAY_ACC_TRUE'},
					'-',{xtype:'label',text:'支出主账户名'},{xtype:'textfield',id:'PAY_ACCNAME_TRUE'},
			       '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
		    	   		    var PAY_ACC_TRUE =  Ext.getCmp('PAY_ACC_TRUE').getValue();
		    	   		    var PAY_ACCNAME_TRUE =  Ext.getCmp('PAY_ACCNAME_TRUE').getValue();
		    	   		    var GET_ACC_TRUE =  Ext.getCmp('GET_ACC_TRUE').getValue();
		    	   		    var GET_ACCNAME_TRUE =  Ext.getCmp('GET_ACCNAME_TRUE').getValue();
		    	   		    wjwPaymainGrid.store.baseParams= {
		    	   		    		PAY_ACC_TRUE:PAY_ACC_TRUE,
		    	   		    		PAY_ACCNAME_TRUE:PAY_ACCNAME_TRUE,
		    	   		    		GET_ACC_TRUE:GET_ACC_TRUE,
		    	   		    		GET_ACCNAME_TRUE:GET_ACCNAME_TRUE
		    	   			};
		    	   		    wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			}
			]
		});
		this.vbbar = this.createPagingToolbar(PAGESIZE);

		this.vsm = new Ext.grid.CheckboxSelectionModel();
		this.vcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		             this.vsm,
		            {header:'收入主账户',dataIndex:'GET_ACC_TRUE',width:130,align:'center',sortable:true,hidden:false},
		            {header:'收入主账户名',dataIndex:'GET_ACCNAME_TRUE',width:200,align:'center',sortable:true,hidden:false},
		            {header:'收入主账户余额',dataIndex:'GET_ACCMONEY_TRUE',width:130,align:'right',sortable:true,hidden:false,
		            	renderer: function(value){
			            		return Ext.util.Format.number(value,'0,000.00');
							}},
		            {header:'支出主账户',dataIndex:'PAY_ACC_TRUE',width:130,align:'center',sortable:true,hidden:false},
		            {header:'支出主账户名',dataIndex:'PAY_ACCNAME_TRUE',width:200,align:'center',sortable:true,hidden:false},
		            {header:'支出主账户余额',dataIndex:'PAY_ACCMONEY_TRUE',width:130,align:'right',sortable:true,hidden:false,
		            	renderer: function(value){
			            		return Ext.util.Format.number(value,'0,000.00');
							}},
		            {header:'虚拟收入主账户',dataIndex:'GET_ACC_FAL',width:130,align:'center',sortable:true,hidden:false},
		            {header:'虚拟收入主账户名',dataIndex:'GET_ACCNAME_FAL',width:180,align:'center',sortable:true,hidden:false},
		            {header:'虚拟收入主账户余额',dataIndex:'GET_ACCMONEY_FAL',width:150,align:'right',sortable:true,hidden:false,
		            	renderer: function(value){
			            		return Ext.util.Format.number(value,'0,000.00');
							}},
		            {header:'虚拟支出主账户',dataIndex:'PAY_ACC_FAL',width:130,align:'center',sortable:true,hidden:false},
		            {header:'虚拟支出主账户名',dataIndex:'PAY_ACCNAME_FAL',width:180,align:'center',sortable:true,hidden:false},
		            {header:'虚拟支出主账户余额',dataIndex:'PAY_ACCMONEY_FAL',width:150,align:'right',sortable:true,hidden:false,
		            	renderer: function(value){
			            		return Ext.util.Format.number(value,'0,000.00');
							}},
		            {header:'日结状态',dataIndex:'STATUS_DAYEND',width:80,align:'center',sortable:true,hidden:false,
		            	renderer:function(data, metadata, record){
	                        if(data == "1"){
		            			return '<font color="blue">日结成功</font>';
		            		}else if(data == "2"){
		            			return '<font color="blue">支出主账户与虚拟支出主账户余额不符</font>';
		            		}else if(data == "3"){
		            			return '<font color="blue">虚拟支出主账户与虚拟支出子账户余额不符</font>';
		            		}else if(data == "4"){
		            			return '<font color="blue">收入主账户与虚拟收入主账户余额不符</font>';
		            		}else if(data == "5"){
		            			return '<font color="blue">虚拟收入主账户与虚拟收入子账户余额不符</font>';
		            		}else{
		            			return data;
		            		}
		            	}
		            },
		            {header:'日结日期',dataIndex:'DATE_DAYEND',width:80,align:'right',sortable:true,hidden:false}
		           ]);
		WjwPaymainGrid.superclass.constructor.call(this,{
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
    	this.wjwPaymainUpdateWindow = new WjwPaymainUpdateWindow();
    	var win = this.wjwPaymainUpdateWindow;
    	
    	
    	Ext.Ajax.request({
			url:GET_IN_ACCOUNT_URL,
			method: 'POST',
			success: function(resp,opts){
				var result = Ext.util.JSON.decode(resp.responseText); 
				var winForm = win.WjwPaymain;
				winForm.GET_ACC_TRUE.setValue(result.GET_ACC_TRUE);
				winForm.GET_ACCNAME_TRUE.setValue(result.GET_ACCNAME_TRUE);
				winForm.PAY_ACC_TRUE.setValue(result.PAY_ACC_TRUE);
				winForm.PAY_ACCNAME_TRUE.setValue(result.PAY_ACCNAME_TRUE)
			},
			failure: function(action){
				Ext.Msg.alert('系统提示',action);
			}
		});
    	
		win.show();
		win.WjwPaymain.getForm().reset();
    }
//	onAddClick: function(){
//    	if(!this.WjwPaymainUpdateWindow)
//    		this.WjwPaymainUpdateWindow = new WjwPaymainUpdateWindow();
//    	var win = this.WjwPaymainUpdateWindow;
//		win.show();
//		win.WjwPaymain.getForm().reset();
//    }
});

/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwPaymainGrid = new WjwPaymainGrid(Ext.getBody().getViewSize().height);
    wjwPaymainGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaymainGrid   
		]
	});
});

