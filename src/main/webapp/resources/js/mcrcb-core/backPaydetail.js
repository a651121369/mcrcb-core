var ENTITY_URL_LIST = "/WjwPaydetail/toBackPaydetail";
var ENTITY_URL_UPDATE = "/WjwPaydetail/update";
var URL_GET_ZBDETAILS = "/WjwPaydetail/findZbDetails";
var URL_GET_ECONOMIC = "/WjwPaydetail/findEconomic";
var PAGESIZE=50;
/* 
 * @author liuyong
 * @date 2015-11-04
 */

var dataStore = new Ext.data.JsonStore({  
    autoLoad : true,  
    fields:['itemId', 'itemName' ],  
    data: [  
        {itemId:'1',itemName:'医疗'},  
        {itemId:'2',itemName:'药品'}
//        {itemId:'3',itemName:'其他'}
    ]  
});  

var dataStore2 = new Ext.data.JsonStore({  
    autoLoad : true,  
    fields:['payId', 'payName' ],  
    data: [  
        {payId:'1',payName:'现金'},  
        {payId:'2',payName:'转账'}          
    ]  
});  
WjwPaydetail = Ext.extend(Ext.ux.Form,{
	constructor: function(){
		this.id_ = this.createHidden('ID','id');
		this.amount = new Ext.form.NumberField({
          fieldLabel: '金额',
          name: 'amount',
          allowBlank: false,
          allowNegative :false,
          maxLength:21,
          maxLengthText:'长度超过不能21', 
          anchor: '95%',
          cls:'forbiddenZH',
          blankText: '该选项为必填项,请输入内容...'
      });
		this.unitName = this.createTextField('机构名称','unitName','95%','',null,200,'长度超过不能200');
		this.inAccno = this.createTextField('<font color="red">*</font>收款人账号','inAccno','95%','',null,32,'长度超过不能32');
		this.inName = this.createTextField('<font color="red">*</font>收款人名称','inName','95%','',null,200,'长度超过不能200');
		this.inBank = this.createTextField('<font color="red">*</font>收款人开户行','inBank','95%','',null,200,'长度超过不能200');
		this.payTime = this.createDateField('支付申请日期','payTime','Ymd','95%');
//		this.zjFld = this.createTextField('资金性质','zjFld','95%','',null,60,'长度超过不能60');
//		this.payWay = new Ext.form.ComboBox({
//			fieldLabel:'<font color="red">*</font>结算方式',
//			name:"payWay",
//			mode:'local',
//			allowBlank:true,
//			anchor:'95%',
//			hiddenName:'payWay',
//			valueField : "payId",
//			displayField : "payName",
//			editable : false,
//			frame : true,
//			layout : 'form',
//			forceSelection : true,
//			isFormField: true,
//			triggerAction : 'all',
//			store:dataStore2
//		});
//		this.topYsdw = this.createTextField('一级预算单位','topYsdw','95%','',null,30,'长度超过不能30');
//		this.footYsdw = this.createTextField('基层预算单位','footYsdw','95%','',null,30,'长度超过不能30');
//		this.itmeYs = this.createTextField('预算科目','itmeYs','95%','',null,30,'长度超过不能30');
//		this.funcFl = this.createTextField('功能分类','funcFl','95%','',null,60,'长度超过不能60');
//		this.ecnoFl = new Ext.form.ComboBox({
//				   fieldLabel:'经济分类',
//				   anchor:'95%',
//              	   autoLoad: true,
//	               triggerAction: 'all',
//	               mode: 'remote',
//	               name:'ecnoFl',
//	               hiddenName :'ecnoFl',
//	               allowBlank: true,
//	               displayField:'ecName',
//	               valueField:'ecName',
//	               forceSelection:false,
//	               store: new Ext.data.Store({
//	                   proxy: new Ext.data.HttpProxy({url:URL_GET_ECONOMIC, method: 'POST'}),
//	                   reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'ecCode'},{name:'ecName'}]))
//	               }),
//	               editable : true,
//	               listeners : {
//	               	'beforequery':function(e){  
//	               		var combo = e.combo; 
//	               		combo.collapse();
//	                   	if(!e.forceAll){  
//	                   		var value = e.query;  
//	                   		combo.store.filterBy(function(record,id){  
//	                   			var text = record.get(combo.displayField);  
//	                   			//用自己的过滤规则,如写正则式  
//	                   			return (text.indexOf(value)!=-1);    //实现的核心
//	                   		}); 
//	                   		combo.onLoad(); 
//	                   		combo.expand();  
//	                   		return false;  
//	                   	}
//	               	},
//	               	'focus':function(combo){  
//	                   	combo.store.load();
//	               	} 
//	               }  
//	           });
//		this.zbDetail = new Ext.form.ComboBox({
//			  fieldLabel:'<font color="red">*</font>指标摘要',
//			  anchor:'95%',
//			  autoLoad: true,
//              triggerAction: 'all',
//              mode: 'remote',
//              name:'zbDetail',
//              hiddenName :'zbDetail',
//              allowBlank: false,
//              displayField:'zbName',
//              valueField:'zbName',
//              forceSelection:true,
//              store: new Ext.data.Store({
//                  proxy: new Ext.data.HttpProxy({url:URL_GET_ZBDETAILS, method: 'POST'}),
//                  reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'zbCode'},{name:'zbName'}]))
//              }),
//              editable : true,
//              listeners : {
//              	'beforequery':function(e){  
//              		var combo = e.combo; 
//              		combo.collapse();
//                  	if(!e.forceAll){  
//                  		var value = e.query;  
//                  		combo.store.filterBy(function(record,id){  
//                  			var text = record.get(combo.displayField);  
//                  			//用自己的过滤规则,如写正则式  
//                  			return (text.indexOf(value)!=-1);    //实现的核心
//                  		}); 
//                  		combo.onLoad(); 
//                  		combo.expand();  
//                  		return false;  
//                  	}
//              	},
//              	'focus':function(combo){  
//                  	combo.store.load();
//              	} 
//              }  
//          });
//		this.yt = this.createTextField('<font color="red">*</font>用途','yt','95%','',null,100,'长度超过不能100');
//		this.item = new Ext.form.ComboBox({
//			fieldLabel:'<font color="red">*</font>科目',
//			name:"item",
//			mode:'local',
//			allowBlank:true,
//			anchor:'95%',
//			hiddenName:'item',
//			valueField : "itemId",
//			displayField : "itemName",
//			editable : false,
//			frame : true,
//			layout : 'form',
//			forceSelection : true,
//			isFormField: true,
//			triggerAction : 'all',
//			store:dataStore
//		});
//		this.note1 = this.createTextField('备注','note1','95%','',null,100,'长度超过不能200');

		this.amount.allowBlank = false;
		this.unitName.allowBlank = false;
		this.inAccno.allowBlank = false;
		this.inName.allowBlank = false;
		this.inBank.allowBlank = false;
		this.payTime.allowBlank = false;
//		this.zjFld.allowBlank = true;
//		this.payWay.allowBlank = false;
//		this.topYsdw.allowBlank = true;
//		this.footYsdw.allowBlank = true;
//		this.itmeYs.allowBlank = true;
//		this.funcFl.allowBlank = true;
//		this.ecnoFl.allowBlank = true;
//		this.zbDetail.allowBlank = false;
//		this.yt.allowBlank = true;
//		this.item.allowBlank = false;
//		this.note1.allowBlank = true;

        
        WjwPaydetail.superclass.constructor.call(this, {
	            anchor: '100%',
	            autoHeight:true,
	            labelWidth: 90,
	            labelAlign :'left',
	            frame: true,
	            bodyStyle:"padding: 5px 5px 0",
	            layout: 'tableform',
//	            labelWidth:90,
	            layoutConfig: {columns: 2},
	            items:[					
					this.unitName,
					this.amount,
					this.inAccno,
//					this.item,
					this.inName,
//					this.payWay,
					this.inBank,
					this.payTime,
//					this.zbDetail,
//					this.yt,									
//					this.ecnoFl,
//					this.zjFld,					
//					this.topYsdw,
//					this.footYsdw,
//					this.itmeYs,
//					this.funcFl,								
//					this.note1,
					this.id_

	            ],
	            buttonAlign :'center',
	            buttons: [
	               {text: '保存', width: 20,iconCls: 'save', hidden: false,handler:this.addFormClick,scope:this},
	               {text: '修改', width: 20,iconCls:'edit', hidden: true, handler: this.updateFormClick, scope: this},
	               {text: '重置', width: 20,iconCls:'redo', hidden: true, handler: this.onResumeClick, scope: this},               
	               {text: '清空', width: 20, iconCls:'redo',  handler: this.resetFormClick, scope: this},
	               {text: '关闭', width: 20,iconCls:'delete', handler: this.onCloseClick, scope: this}
	            ]
	        });
	},

	updateFormClick: function(){
		
		if(this.getForm().isValid()){
			this.getForm().submit({
				waitMsg: '正在提交，请稍后...',
				url: ENTITY_URL_UPDATE,
				method: 'POST',
				success: function(form,action){
					Ext.MessageBox.alert("系统提示：","修改成功！");
					wjwPaydetailGrid.wjwPaydetailUpdateWindow.hide();
					wjwPaydetailGrid.vbbar.doLoad(wjwPaydetailGrid.vbbar.cursor);
				},
				failure: function(form,action){
					Ext.MessageBox.alert("系统提示：",action.result.message);
				}
			});
		}
	},
	//关闭
    onCloseClick: function(){
    	if(wjwPaydetailGrid.wjwPaydetailUpdateWindow)
    	wjwPaydetailGrid.wjwPaydetailUpdateWindow.wjwPaydetail.getForm().reset();
        this.ownerCt.hide();
    },
  //清空
    resetFormClick: function() {        
        this.getForm().reset();
    }, 
  //重置
    onResumeClick: function() {        
    	wjwPaydetailGrid.onModifyClick();
    }
	
});

/********************wjwPaydetailUpdateWindow组件*************************/
WjwPaydetailUpdateWindow = Ext.extend(Ext.Window, {
	constructionForm : null,
    constructor: function() {
    	this.wjwPaydetail = new WjwPaydetail();
    	this.wjwPaydetail.buttons[0].hide();   //隐藏添加按钮
    	this.wjwPaydetail.buttons[1].show();   //显示修改按钮
    	this.wjwPaydetail.buttons[2].hide();   //显示重置按钮
    	this.wjwPaydetail.buttons[3].hide();   //显示重置按钮
    	this.wjwPaydetail.buttons[4].show();   //隐藏清空按钮
    	
    	WjwPaydetailUpdateWindow.superclass.constructor.call(this, {
			title: '修改',
			width: 700,
			anchor: '100%',
			autoHeight: true,
			resizable: false,
			plain: true,
			modal: true,
			closeAction: 'hide',
            items: [this.wjwPaydetail]
        });
    }
});
/****************WjwPaydetailGrid***********************/
WjwPaydetailGrid = Ext.extend(UxGrid,{
	constructor: function(height,width){
		this.store = new Ext.data.Store({
			
			proxy: new Ext.data.HttpProxy({url:ENTITY_URL_LIST,method:'POST'}),
			reader: new Ext.data.JsonReader({totalProperty:'total',root:'rows'},[
					{name:'id'},          
					{name:'unitNo'},      
					{name:'amount'},      
					{name:'unitName'},    
					{name:'outAccno'},    
					{name:'outAccname'},  
					{name:'outBank'},     
					{name:'inAccno'},     
					{name:'inName'},      
					{name:'inBank'},      
					{name:'payTime'},     
					{name:'zjFld'},       
					{name:'payWay'},      
					{name:'topYsdw'},     
					{name:'footYsdw'},    
					{name:'itmeYs'},      
					{name:'funcFl'},      
					{name:'ecnoFl'},      
					{name:'zbDetail'},    
					{name:'currency'},    
					{name:'status'},      
					{name:'operNo'},      
					{name:'yt'},          
					{name:'item'},        
					{name:'connNo'},      
					{name:'note1'},       
					{name:'note2'},       
					{name:'xnAcctno'},    
					{name:'xnAcctName'},  
					{name:'zcAcctno'},    
					{name:'zcAcctname'},  
					{name:'fhUser'},      
					{name:'fhTime'},	   
					{name:'backFlg'},	 
					{name:'note1'},	 
					{name:'backVoucher'} 
					 ])
		});
		this.vtbar = new Ext.Toolbar({
			items: [
			        '-',{xtype:'button',text:'修改',iconCls:'edit',handler:this.onModifyClick,scope:this},
					'-',{xtype:'button',text:'打印凭证',iconCls:'add',handler:this.onAddClick,scope:this},					
					'-',{xtype:'button',text:'批量打印凭证',iconCls:'add',handler:this.batchPrintClick,scope:this},
//					'-',{xtype:'label',text:'单位名称'},
//					new Ext.form.ComboBox({
//			        	autoLoad: true,
//			            typeAhead: false,
//			            emptyText: '请选择...',
//			            triggerAction: 'all',
//			            isFormField: true,
////			            mode: 'local',
//			            mode: 'remote',
//			            hiddenName :'unitNo',
//			            name:'NAME',
//			            allowBlank: true,
//			            blankText:'请选择...',
//			            forceSelection: true,
//			            lastQuery: '',
//			            displayField:'NAME',
//			            valueField:'CODE',
//			            id:'unitNo',
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
				    '-',{xtype:'label',text:'开始日期'},{xtype:'datefield',id:'startTime',format: 'Ymd',editable: false,
				    	value:new Date().getFirstDateOfMonth ().format('Ymd')},
				    '-',{xtype:'label',text:'结束日期'},{xtype:'datefield',id:'endTime',format: 'Ymd',editable: false,
				    	value:new Date().format('Ymd')},
				    	'-',{xtype:'label',text:'机构编号:'},
				        new Ext.form.ComboBox({
				        	autoLoad: true,
				            typeAhead: false,
				            emptyText: '请选择...',
				            triggerAction: 'all',
				            isFormField: true,
//				            listWidth : 160,
//				            mode: 'local',
				            mode: 'remote',
				            hiddenName :'unitNo',
				            name:'NAME',
				            allowBlank: true,
				            blankText:'请选择...',
				            forceSelection: false,
				            lastQuery: '',
				            displayField:'NAME',
				            valueField:'CODE',
				            id:'unitNo',
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
						'-',{xtype:'label',text:'记账状态'},
						new Ext.form.ComboBox({
							id:'status',
						    typeAhead: true,
						    triggerAction: 'all',
						    lazyRender:true,
						    name : 'status',
						    width:80,
						    fieldLabel : '',
						    mode: 'local',
						    store: new Ext.data.ArrayStore({
						        id: 0,
						        fields: [
						            'status',
						            'displayText'
						        ],
						    	data: [[5, '未记账'], [6, '记账完成']]
						    }),
						    valueField: 'status',
						    displayField: 'displayText'

						}),
			        '-',{xtype:'button',text:'查询',iconCls:'query',handler:function(){
			       			var params = {};
			       			wjwPaydetailGrid.vtbar.items.each(function(item,index,length){ 
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
		    	   			var status = Ext.getCmp('status').getValue();
		    	   			var unitNo = Ext.getCmp('unitNo').getValue();
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
		    	   			
		    	   			params= {startTime:startTime,endTime:endTime,unitNo:unitNo,status:status};
	    	   				wjwPaydetailGrid.store.baseParams= params;
	    	   				wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE}});
	    	   			}
	       			},
//					'-',{xtype:'button',text:'重置',iconCls:'refresh',handler:function(){
//						wjwPaydetailGrid.vtbar.items.each(function(item,index,length){   
//							if((""+item.getXType()).indexOf("field") != -1) {
//								item.setValue('');
//							}
//						  });  
//    	   			}},
    	   	        '-',{xtype:'button',text:'导出Excel文件',iconCls:'excel',handler:function(){
		       			
		       			Ext.Msg.confirm("系统提示：", "确定下载查询结果吗？", function(btn){

		       				if(btn=="yes"){
				    	   		var startTime = Ext.getCmp('startTime').getValue().format('Ymd');
				    	   		var endTime = Ext.getCmp('endTime').getValue().format('Ymd');
				    	   		var status = Ext.getCmp('status').getValue();
				    	   		var unitNo = Ext.getCmp('unitNo').getValue();
				    	   		var url="/WjwPaydetail/downloadBackPay?startTime="+startTime+"&endTime="+endTime+"&unitNo="+unitNo+"&status="+status;
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
		            {header:'机构号',dataIndex:'unitNo',width:100,sortable:true,hidden:true},
		            {header:'机构名称',dataIndex:'unitName',width:150,sortable:true,hidden:false},
//		            {header:'付款人账号',dataIndex:'outAccno',width:100,sortable:true,hidden:false},
//		            {header:'付款人姓名',dataIndex:'outAccname',width:180,sortable:true,hidden:false},
//		            {header:'付款人开户行',dataIndex:'outBank',width:100,sortable:true,hidden:false},
		            {header:'收款人账号',dataIndex:'inAccno',width:180,sortable:true,hidden:false},
		            {header:'收款人姓名',dataIndex:'inName',width:180,sortable:true,hidden:false},
		            {header:'收款人开户行',dataIndex:'inBank',width:180,sortable:true,hidden:false},		            
		            {header:'申请时间',dataIndex:'payTime',width:180,sortable:true,hidden:false},
		            {header:'凭证编号',dataIndex:'operNo',width:180,sortable:true,hidden:false},
		            {header:'退汇标志',dataIndex:'backFlg',width:180,sortable:true,hidden:false,
		            	renderer:function(data){
		            		if(data ==1){
		            			return "<font color='red'>退汇未处理</font>";
		            		}else if(data ==2){
		            			return "<font color='#0E54EA'>退汇已记账</font>";
		            		}else if(data==3){
		            			return "<font color='blue'>退汇</font>";
		            		}else{
		            			return data;
		            		}		            		
		            	}
		            },
		            {header:'退汇凭证号',dataIndex:'backVoucher',width:180,sortable:true,hidden:false},		            
		            {header:'支付金额',dataIndex:'amount',width:100,sortable:true,hidden:false,align: 'right',			            
			            renderer: function(value){
			            	return ''+Ext.util.Format.number(value,'0,000.00')+'';
			            }},
			        {header:'状态',dataIndex:'status',width:180,sortable:true,hidden:false,
			            	renderer:function(value){
			            		if(value==1){
			            			return "<font color='#1974C3'>申请</font>";
			            		}else if(value==2){
			            			return "<font color='#468847'>初审</font>";
			            		}else if(value==3){
			            			return "<font color='#B94A48'>复审</font>";
			            		}else if(value==4){
			            			return "<font color='red'>作废</font>";
			            		}else if(value==5){
			            			return "<font color='blue'>未记账</font>";
			            		}else if(value==6){
			            			return "<font color='#0E54EA'>记账完成</font>";
			            		}
			            	}    	
			        },		          
			        
			        {header:'科目',dataIndex:'item',width:180,sortable:true,hidden:false,
			              	renderer:function(data){
			            		if(data ==1){
			            			return '医疗';
			            		}else if(data ==2){
			            			return '药品';
			            		}else{
			            			return data;
			            		}		            		
			         }},
			        {header:'用途',dataIndex:'yt',width:180,sortable:true,hidden:false},
			        {header:'指标摘要',dataIndex:'zbDetail',width:180,sortable:true,hidden:false},
		            {header:'经济分类',dataIndex:'ecnoFl',width:180,sortable:true,hidden:false},		            
//		            {header:'币种',dataIndex:'currency',width:180,sortable:true,hidden:false},
		            {header:'资金性质',dataIndex:'zjFld',width:180,sortable:true,hidden:false},
		            {header:'一级预算单位',dataIndex:'topYsdw',width:180,sortable:true,hidden:false},
		            {header:'基层预算单位',dataIndex:'footYsdw',width:180,sortable:true,hidden:false},
		            {header:'预算项目',dataIndex:'itmeYs',width:180,sortable:true,hidden:false},
		            {header:'功能分类',dataIndex:'funcFl',width:180,sortable:true,hidden:false},
		            {header:'退汇原因',dataIndex:'note1',width:180,sortable:true,hidden:false}
//		            {header:'关联号',dataIndex:'connNo',width:180,sortable:true,hidden:false},
//		            {header:'备注1',dataIndex:'note1',width:180,sortable:true,hidden:false},
//		            {header:'备注2',dataIndex:'note2',width:180,sortable:true,hidden:false},
//		            {header:'支出子账号',dataIndex:'xnAcctno',width:180,sortable:true,hidden:false},
//		            {header:'支出子账户名',dataIndex:'xnAcctName',width:180,sortable:true,hidden:false},
//		            
//		            {header:'支出主账号',dataIndex:'zcAcctno',width:180,sortable:true,hidden:false},
//		            {header:'支出主账户名',dataIndex:'zcAcctname',width:180,sortable:true,hidden:false},
//		            
//		            {header:'完成操作人',dataIndex:'fhUser',width:180,sortable:true,hidden:false},
//		            {header:'完成时间',dataIndex:'fhTime',width:180,sortable:true,hidden:false}
		     
		           ]);
		WjwPaydetailGrid.superclass.constructor.call(this,{
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
    	var parentId = document.getElementById('parentId').value;
    	if(parentId!=0){
    		Ext.Msg.alert('系统提示','没有操作权限！');
    		return;
    	}
    	var records = this.getSelectionModel().getSelections();
    	if(records.length==1){
    		if(records[0].get('status')==5){
    			window.open('/WjwPaydetail/LodopPage?id='+records[0].get('id'));
    		}else{
    			Ext.Msg.alert('系统提示','只有未记账状态可打印支付凭证！');
    		}
    	}else{
    		Ext.Msg.alert('系统提示','请选择一条记录！');
    	}
    },
    batchPrintClick: function(){
     	var parentId = document.getElementById('parentId').value;
    	if(parentId!=0){
    		Ext.Msg.alert('系统提示','没有操作权限！');
    		return;
    	}
    	var records = this.getSelectionModel().getSelections();
    	if(records.length>0){
        	var ids = new String();
        	for(i=0;i<records.length;i++){
         		if(records[i].get('status')==5){
         			ids += (records[i].get('id')+",");	
        		}
        	}
        	if(ids==""){
        		Ext.Msg.alert('系统提示','不能打印已记账纪录！');
        		return;
        	}
        	window.open('/WjwPaydetail/listLodopPage?ids='+ids);
    	}else{
    		Ext.Msg.alert('系统提示','请选择一条记录！');
    	}
    },
    onModifyClick: function() {
    	var records = this.getSelectionModel().getSelections();
   		if(records.length > 0) {
   			if(records.length == 1){
   				vrecord = records[0];
   				if(vrecord.data.status==6){
   					Ext.Msg.alert('系统提示','该笔纪录已记账！');
   					return;
   				}
   				if(!this.wjwPaydetailUpdateWindow)
					this.wjwPaydetailUpdateWindow = new WjwPaydetailUpdateWindow();
   				
   		    	var win = this.wjwPaydetailUpdateWindow;
				var winForm = win.wjwPaydetail;
				
				win.show();			
   		    	winForm.id_.setValue(vrecord.data['id']);
				winForm.amount.setValue(vrecord.data.amount);
				winForm.amount.setReadOnly(true);
				winForm.unitName.setValue(vrecord.data.unitName);
				winForm.unitName.setReadOnly(true);
				winForm.inAccno.setValue(vrecord.data.inAccno);
				winForm.inName.setValue(vrecord.data.inName);
				winForm.inBank.setValue(vrecord.data.inBank);
				winForm.payTime.setValue(vrecord.data.payTime);
				winForm.payTime.setReadOnly(true);
//				winForm.zjFld.setValue(vrecord.data.zjFld);
//				winForm.payWay.setValue(vrecord.data.payWay);
//				winForm.topYsdw.setValue(vrecord.data.topYsdw);
//				winForm.footYsdw.setValue(vrecord.data.footYsdw);
//				winForm.itmeYs.setValue(vrecord.data.itmeYs);
//				winForm.funcFl.setValue(vrecord.data.funcFl);
//				winForm.ecnoFl.setValue(vrecord.data.ecnoFl);
//				winForm.zbDetail.setValue(vrecord.data.zbDetail);
//				winForm.yt.setValue(vrecord.data.yt);
//				winForm.item.setValue(vrecord.data.item);
//				winForm.note1.setValue(vrecord.data.note1);
   		    	
   			}else{
   				Ext.Msg.alert('系统提示','不能修改多条记录！');
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
    var startTime = new Date().getFirstDateOfMonth ().format('Ymd')
    var endTime = new Date().format('Ymd');
    wjwPaydetailGrid = new WjwPaydetailGrid(Ext.getBody().getViewSize().height);
    wjwPaydetailGrid.store.load({params:{start:0,limit:PAGESIZE,startTime:startTime,endTime:endTime}});
	new Ext.Viewport({
		layout: 'border',
		items: [
		        wjwPaydetailGrid   
		]
	});
});

