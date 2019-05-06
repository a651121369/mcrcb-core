var ENTITY_URL_LIST = "/WjwAccchange/findDqfDetails";
var DQF_URL_DETAIL = "/WjwAccount/dqfDetail";
var DQF_URL_RZ = "/WjwAccount/dqfAccountRz";
var DQF_URL_QF = "/WjwAccount/dqfAccountQf"
var	ENTITY_URL_BACK = "/WjwAccchange/dqfRollback";
var PAGESIZE=10;

var dataStore = new Ext.data.JsonStore({  
    autoLoad : true,  
    fields:['itemId', 'itemName' ],  
    data: [  
        {itemId:'1',itemName:'利息'},  
        {itemId:'2',itemName:'不明来账'}
//      {itemId:'3',itemName:'其他'}
               
    ]  
});  
DqfDetailForm = Ext.extend(Ext.ux.Form,{
	
	constructor:function(){
		this.id_ = this.createHidden('ID','id');
		this.accNo = this.createHidden('账户号','accNo');
		this.custName = this.createTextField('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp账户名','custName','130%','',null,60,'长度超过不能60');
		this.amount = this.createTextField('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp清分金额','amount','90%','',null,21,'长度超过不能21');
		this.medcAmt = this.createTextField('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp医疗金额','medcAmt','90%','',null,21,'长度超过不能21');
		this.drugAmt = this.createTextField('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp药品金额','drugAmt','90%','',null,21,'长度超过不能21');		
		this.note2 = this.createHidden('note2','note2');
		this.data1 = this.createHidden('data','data');
//		this.data2 = this.createHidden('data2','data2');
		
		DqfDetailForm.superclass.constructor.call(this,{
			anchor: '100%',
        	title: '待清分账户明细入账',
        	collapsible: true,
            layout: 'tableform',
	        layoutConfig: {columns:4},
	        autoHeight:true,
            labelWidth: 100,
            frame: true,
            bodyStyle:"padding: 5px 5px 0",
            items:[                  
                   this.custName,
                   this.amount,
                   this.medcAmt,
                   this.drugAmt,
                   this.accNo,
                   this.id_,
                   this.note2,
                   this.data1
//                   this.data2
    
 			],
 			buttonAlign:'center',
 			buttons:[
				{text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
				{text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
 			]
		});
	},
	addFormClick: function(){
		
		if(this.getForm().isValid()){						
			this.getForm().submit({
				waitMsg: '正在提交数据...',
				url: DQF_URL_QF,
				method: 'POST',
				success: function(form,action){
					Ext.Msg.alert("系统提示：","待清分账务清分成功！");
					wjwAccchangeGrid.dqfDetailAddWindow.hide();
					wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});	
					
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
			
		}
	},
	
	//关闭
    onCloseClick: function(){
    	if(wjwAccchangeGrid.dqfDetailAddWindow)
    		wjwAccchangeGrid.dqfDetailAddWindow.dqfDetailForm.getForm().reset();
        this.ownerCt.hide();
    }
	
});






/***************************************DqfAllInForm组件***************************************************/
DqfAllInForm = Ext.extend(Ext.ux.Form,{
	
	constructor:function(){
//		this.id_ = this.createHidden('ID','id','95%');
//		this.accNo = this.createTextField('账户号','accNo','95%','',null,32,'长度超过不能32');
//		this.custName = this.createTextField('账户名','custName','95%','',null,60,'长度超过不能60');
//		this.amount = this.createTextField('账户余额','amount','95%','',null,21,'长度超过不能21');
		this.accNo = this.createSearchCombo('<font color="red">*</font>请选择账户:','accNo','custName','accNo','95%',"/WjwAccount/findDqfAccs");
		this.date =  this.createDateField('<font color="red">*</font>入账日期','date','Y-m-d','95%');
		this.rzAmt = new Ext.form.NumberField({
            fieldLabel: '<font color="red">*</font>入账总金额',
            name: 'rzAmt',
            id:'rzAmt',
            allowBlank: false,
            allowNegative :false,
            maxLength:21,
            maxLengthText:'长度超过不能21', 
            anchor: '95%',
            cls:'forbiddenZH',
            blankText: '该选项为必填项,请输入内容...'
        });
		this.unkType = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    lazyRender:true,
		    name : 'payType',
		    anchor: '95%',
		    width: 300,
		    fieldLabel : '<font color="red">*</font>入账类型',
		    mode: 'local',
		    store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'myId',
		            'displayText'
		        ],
		    	data: [[1, '利息'], [2, '不明来账']]
		    }),
		    valueField: 'myId',
		    displayField: 'displayText',
		    value:2//默认选中转账

		}); 
		this.unkType.allowBlank = false;
		this.date.allowBlank = false;
		
		DqfAllInForm.superclass.constructor.call(this,{
			anchor: '100%',
        	title: '待清分账户入账',
        	collapsible: true,
            layout: 'tableform',
	        layoutConfig: {columns: 2},
	        autoHeight:true,
            labelWidth: 80,
            frame: true,
            bodyStyle:"padding: 5px 5px 0",
            items:[                  
                   this.accNo,
                   this.rzAmt,
                   this.unkType,
                   this.date
 			],
 			buttonAlign:'center',
 			buttons:[
				{text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
				{text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
 			]
		});
	},
	addFormClick: function(){
		var accNo = this.accNo.getValue();
		var rzAmt = this.rzAmt.getValue();
		var date = this.date.getValue().format('Ymd');	
		var unkType = this.unkType.getValue();
		if(this.getForm().isValid()){
			Ext.Msg.confirm('系统提示：',"入账 金额 为 ："+this.rzAmt.getValue(),function(btn){
				if(btn == 'yes'){
					
					Ext.Ajax.request({
							waitMsg: '正在提交数据...',
							url: DQF_URL_RZ,
							method: 'POST',
							params: {accNo:accNo,rzAmt:rzAmt,unkType:unkType,date:date},
							success: function(form,action){
								Ext.Msg.alert("系统提示：","待清分账务入账成功！");
								wjwAccchangeGrid.dqfAllInAddWindow.hide();
								wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
							},
							failure: function(form,action){
								Ext.MessageBox.alert("系统提示：",action.result.message);
							}
						});
					}
				})
		}
	},
	
	//关闭
    onCloseClick: function(){
    	if(wjwAccchangeGrid.dqfAllInAddWindow)
    		wjwAccchangeGrid.dqfAllInAddWindow.dqfAllInForm.getForm().reset();
        this.ownerCt.hide();
//    	this.ownerCt.close();
    }
	
});

/**************DqfAllInAddWindow*********************/
DqfAllInAddWindow = Ext.extend(Ext.Window,{
	
	constructor: function(grid){
		this.dqfAllInForm = new DqfAllInForm();
    	this.dqfAllInForm.buttons[0].show();   //隐藏添加按钮
    	this.dqfAllInForm.buttons[1].show();   //显示修改按钮
		DqfAllInAddWindow.superclass.constructor.call(this,{
			title: '入账',
			 width: 1000,
			 anchor: '100%',
			autoHeight:true,
			resizable : false, //可变尺寸的；可调整大小的
			 plain: true,
			 modal: true,
			closeAction: 'hide',
			items:[this.dqfAllInForm]
		});
	}
});


/********************DqfDetailAddWindow组件*************************/
DqfDetailAddWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.dqfDetailForm = new DqfDetailForm();
    	this.dqfDetailForm.buttons[0].show();   //隐藏添加按钮
    	this.dqfDetailForm.buttons[1].show();   //显示修改按钮

    	
    	DqfDetailAddWindow.superclass.constructor.call(this, {
			title: '清分',
			width: 1100,
			height:400,
			anchor: '100%',
//			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			autoScroll:true,
			closeAction: 'hide',
            items: [this.dqfDetailForm]
            
        });
    }
});



/*************************************************************************************/
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
		            {name:'flag'},
		            {name:'tradeCount'},
		            {name:'unkType'},
		             {name:'descstr'}
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
				    '-',{xtype:'button',text:'待清分账务入账',iconCls:'add',handler:this.onAddClick,scope:this},
				    '-',{xtype:'button',text:'待清分账务明细分割',iconCls:'edit',handler:this.onModifyClick,scope:this},
				    '-',{xtype:'button',text:'待清分账务撤销',iconCls:'redo',handler:this.onBackClick,scope:this},
				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Y-m-d',editable: false},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Y-m-d',editable: false},
			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
////			       			wjwAccchangeGrid.vtbar.items.each(function(item,index,length){ 
////		       					if((""+item.getXType()).indexOf("field") != -1 && item.getValue() != '') {
////		       						if (item.getXType() == 'datefield') {
////		       							params[item.getId()] = item.getValue().format(item.format);
////		       						} else {
////		       							params[item.getId()] = item.getValue();
////		       						}
////		       					}
////							});
		    	   			var startTime = Ext.getCmp('startTime').getValue();
		    	   			var endTime = Ext.getCmp('endTime').getValue();
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
		    	   			params= {startTime:startTime,endTime:endTime};
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
    	   			}}
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
		              	renderer: function(value,metadata){
		              		metadata.attr = 'ext:qtip="点击查看清分明细信息！"';
			            	return '<a onclick="document.onAClick();" style="color:blue;cursor: pointer;">'+value+'</a>';	
						}
		            	},
		            {header:'对方账号',dataIndex:'dfAccno',width:100,sortable:true,hidden:false},
		            {header:'对方账户名',dataIndex:'dfAccname',width:180,sortable:true,hidden:false},
		            {header:'余额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
			        {header:'医疗金额',dataIndex:'medcAmt',width:100,sortable:true,hidden:false},
		            {header:'药品金额',dataIndex:'drugAmt',width:100,sortable:true,hidden:false},
		            
		            {header:'交易金额',dataIndex:'tranAmt',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }
		            	},
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
		            {header:'其他',dataIndex:'otherAmt',width:100,sortable:true,hidden:true},
		            {header:'凭证号',dataIndex:'note1',width:100,sortable:true,hidden:true},
		            {header:'NOTE2',dataIndex:'note2',width:100,sortable:true,hidden:true},
		            {header:'状态',dataIndex:'flag',width:100,sortable:true,hidden:false,
		            	renderer:function(data){
		            		if(data ==1){
		            			return '<font color="blue">入账</font>';
		            		}else if(data ==2){
		            			return '<font color="blue">清分</font>';
		            		}else if(data==3){
		            			return '<font color="blue">利息</font>';
		            		}else{
		            			return data;
		            		}		            		
		            	}},
		              {header:'入账类型',dataIndex:'unkType',width:100,sortable:true,hidden:false,
			            	renderer:function(data){
			            		if(data ==1){
			            			return '<font color="blue">利息收入</font>';
			            		}else if(data ==2){
			            			return '<font color="blue">不明来账收入</font>';
			            		
			            		}else{
			            			return data;
			            		}		            		
			           }},
			           {header:'不明来账笔数',dataIndex:'tradeCount',width:100,sortable:true,hidden:false},
			           {header:'摘要',dataIndex:'descstr',width:100,sortable:false,hidden:false}
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
					
    },
    onBackClick:function(){
    	var records = this.getSelectionModel().getSelections();
    	if(records.length>0){
    		if(records.length==1){
    			vrecord = records[0];
    			var id = vrecord.data.id;
    			var status = vrecord.data.flag;
//    			var accNo = vrecord.data.accNo;
//    			var dfAccno = vrecord.data.dfAccno;
        		Ext.Msg.confirm('系统提示：',"确定撤销这条记录吗？",function(btn){
        			if(btn == 'yes'){
        				Ext.Ajax.request({
        					url: ENTITY_URL_BACK,
        					method: 'POST',
        					params: {id:id,status:status},
        					success: function(form,action){
        						Ext.Msg.alert('系统提示','撤销成功！');
        						wjwAccchangeGrid.vbbar.doLoad(wjwAccchangeGrid.vbbar.cursor);
        					},
        					failure: function(form,action){
    							Ext.MessageBox.alert("系统提示：",action.result.message);
    						}
        				});
        			}
        		});
    			
    		}else{
    			Ext.Msg.alert("系统提示：","不能同时撤销多条记录");
    		}
    	}else{
    		Ext.Msg.alert("系统提示：","请选择一条记录");
    	}
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
				winForm.note2.setValue(vrecord.data.note2);
				winForm.accNo.setValue(vrecord.data.accNo);
				winForm.custName.setValue(vrecord.data.accName);
				winForm.custName.setReadOnly(true);
				winForm.amount.setValue(vrecord.data.tranAmt);
				winForm.amount.setReadOnly(true);
				if(vrecord.data.medcAmt){
					winForm.medcAmt.setValue(vrecord.data.medcAmt);
					winForm.medcAmt.setReadOnly(true);
				}else{
					winForm.medcAmt.setValue(0);
					winForm.medcAmt.setReadOnly(true);
				}
				if(vrecord.data.drugAmt){
					winForm.drugAmt.setValue(vrecord.data.drugAmt);
					winForm.drugAmt.setReadOnly(true);
				}else{
					winForm.drugAmt.setValue(0);
					winForm.drugAmt.setReadOnly(true);
				}
				
   		    	Ext.Ajax.request({
   		    		url:DQF_URL_DETAIL,
   		    		params:{accNo:accNo,parent:parent},
   		    		method:'POST', 
   		    		success:function(resp,options){
   		    			var results = Ext.util.JSON.decode(resp.responseText);
   		    			for(var i=0;i<results.length;i++){
   		    				var obj = results[i];

   		    				winForm.add(new Ext.form.TextField({
   		    					anchor:'130%',
   								allowBlank : true, 
   								fieldLabel:obj.custName,
   								labelWidth: 400,
   							    name:"accNo"+i,
   								value:obj.accNo,
   								readOnly:true
   							}));


   		    				winForm.add(new Ext.form.NumberField({
   		    		            fieldLabel: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp金额',
   		    		            name:"amount"+i,
   		    		            allowBlank: true,
//   		    		        allowNegative :false,
   		    		            maxLength:21,
   		    		            maxLengthText:'长度超过不能21', 
   		    		            anchor: '90%',
   		    		            cls:'forbiddenZH'
//   		    		            blankText: '该选项为必填项,请输入内容...'
   		    		        }));
   		    				winForm.add(new Ext.form.NumberField({
   		    		            fieldLabel: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp医疗金额',
   		    		            name:"item1Amt"+i,
   		    		            allowBlank: true,
//   		    		        allowNegative :false,
   		    		            maxLength:21,
   		    		            maxLengthText:'长度超过不能21', 
   		    		            anchor: '90%',
   		    		            cls:'forbiddenZH'
//   		    		            blankText: '该选项为必填项,请输入内容...'
   		    		        }));
   		    				winForm.add(new Ext.form.NumberField({
   		    		            fieldLabel: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp药品金额',
   		    		            name:"item2Amt"+i,
   		    		            allowBlank: true,
//   		    		        allowNegative :false,
   		    		            maxLength:21,
   		    		            maxLengthText:'长度超过不能21', 
   		    		            anchor: '90%',
   		    		            cls:'forbiddenZH'
//   		    		            blankText: '该选项为必填项,请输入内容...'
   		    		        }));
// 		    				winForm.add(new Ext.form.NumberField({
//   		    		            fieldLabel: '金额:',
//   		    		            name:"item3Amt"+i,
//   		    		            allowBlank: true,
//   		    		        allowNegative :false,
//   		    		            maxLength:21,
//   		    		            maxLengthText:'长度超过不能21', 
//   		    		            anchor: '95%',
//   		    		            cls:'forbiddenZH'
//   		    		            blankText: '该选项为必填项,请输入内容...'
//   		    		        }));


   		    				
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
    }

});

/************************************************************/
document.onAClick = function(value){
	var note2 = wjwAccchangeGrid.getSelectionModel().getSelections()[0].data.note2;
	var accNo = wjwAccchangeGrid.getSelectionModel().getSelections()[0].data.accNo;	
	parent.Home.AddTab(note2+accNo+new Date(),"待清分账务明细", '/WjwAccchange/qfMx?accNo='+accNo+'&note2='+note2);
	
};


/*************onReady组件渲染处理***********************/
Ext.onReady(function(){
    Ext.QuickTips.init();                               //开启快速提示
    Ext.form.Field.prototype.msgTarget = 'side';        //提示方式"side"
    
    wjwAccchangeGrid = new WjwAccchangeGrid(Ext.getBody().getViewSize().height);
    wjwAccchangeGrid.store.load({params:{start:0,limit:PAGESIZE}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwAccchangeGrid   
		]
	});
});
