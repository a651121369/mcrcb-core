Ext.namespace("Ext.ux");

//重写TextField，
Ext.override(Ext.form.TextField, {      //区别汉字和英文的长度
    validateValue : function(value){
    	
    	var strLen = this.countStrLen(value);//调用“自定义计算字符串的长度”的函数
    	
        if(value.length < 1 || value === this.emptyText){ //如果字符串为空
             if(this.allowBlank){//如果TextField允许为空
                 this.clearInvalid();//清除当前表单项上的所有非法styles/messages(样式/消息)
                 return true;
             }else{
                 this.markInvalid(this.blankText);
                 return false;
             }
        }
        if(value.length < this.minLength){
            this.markInvalid(String.format(this.minLengthText, this.minLength));
            return false;
        }
        if(strLen > this.maxLength){
            this.markInvalid(String.format(this.maxLengthText, this.maxLength));
            return false;
        }
        if(this.vtype){
            var vt = Ext.form.VTypes;
            if(!vt[this.vtype](value, this)){
                this.markInvalid(this.vtypeText || vt[this.vtype +'Text']);
                return false;
            }
        }
        if(typeof this.validator == "function"){
            var msg = this.validator(value);
            if(msg !== true){
                this.markInvalid(msg);
                return false;
            }
        }
        if(this.regex && !this.regex.test(value)){
            this.markInvalid(this.regexText);
            return false;
        }
        return true;
    },
    countStrLen: function(strTemp) {  //自定义计算字符串的长度,区分英文和汉字
        var  i,sum;  
        sum=0;  
        for(i=0;i<strTemp.length;i++) {  
            if  ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255))  
                sum=sum+1;  
            else   
                sum=sum+2;  
        }  
        return  sum;  
    }
});

//Ext.extend方法是用来实现类的继承,如果重写了构造函数，则应该在必要的时候调用超类(superclass)的构造函数，initComponent和onRender也一样。否则可能会造成一些不必要的麻烦。
//extend(Object subclass,Object superclass,[Object overrides] : Object
//第一个参数：子类,第二个参数：父类 ,第三个参数：要覆盖或新增的属性

//function base(){this.name = 'fkeede';}//"name"为实例属性
//base.prototype.addr = "addr";
//var sub = Ext.extend(base,{age:20}); //实例属性 和原型属性都可以继承到
//alert(new sub().name);alert(new sub().addr);
//如果在继承时在override参数中定义了constructor方法，则子类不会继承父类在构造函数中定义的属性，
//但是可以继承在prototype中定义的属性，在这种情况下只有在constructor中调用父类的构造函数
//myClass.superclass.constructor.call(this,……）;才会继承父类在构造函数中定义的对象。
Ext.ux.Form = Ext.extend(Ext.form.FormPanel, {
    frame: true,
    bodyStyle:"padding: 5px 5px 0",
    initComponent: function() {
    	//obj1.method1.call(obj2,argument1,argument2)
    	//call的作用就是把obj1的方法放到obj2上使用，后面的argument1..这些做为参数传入
    	//function add(a,b){alert(a+b); }
    	//function sub(a,b){alert(a-b); }
    	//add.call(sub,3,1);
    	//这个例子中的意思就是用 add 来替换 sub，add.call(sub,3,1) == add(3,1) ，所以运行结果为：alert(4);
    	Ext.ux.Form.superclass.initComponent.call(this);
    },
    initEvents : function(){
        Ext.ux.Form.superclass.initEvents.call(this);
    },
    createButton: function(text,handler) {
    	var btn = new Ext.Button({
    	    text: text,
    	    handler: handler//当按钮被点击时调用的方法 (可以用来替代点击事件)
    	});
    	return btn;
    },
    createTextField: function(fieldLabel, name, anchor, blankText, vtype, maxLength, maxLengthText,inputType, colspan) {    //生成一个通用的TextField
        var tf = new Ext.form.TextField({
            fieldLabel: fieldLabel,
            name: name,
            xtype: 'textfield',
            readOnly: false,
            allowBlank: false,
            anchor: anchor,
            blankText: '该选项为必填项,请输入内容...',
            vtype: vtype,
            maxLength: maxLength,
            maxLengthText: maxLengthText,
            inputType:inputType,
            colspan: colspan
        });
        return tf;
    },
    createNumberField: function(fieldLabel, name, anchor, decimalPrecision, colspan) {    //生成一个通用的TextField
        var nf = new Ext.form.NumberField({
            fieldLabel: fieldLabel,
            name: name,
            readOnly: false,
            allowBlank: false,
            allowNegative :false,//值为false时只允许为正数(默认为 true，即默认允许为负数)
            decimalPrecision :decimalPrecision ,//设置小数点后最大精确位数(默认为 2)。
            anchor: anchor,
            cls:'forbiddenZH',//禁用中文输入法
            blankText: '该选项为必填项,请输入内容...',
            colspan: colspan
        });
        return nf;
    },
    createPassWordField: function(fieldLabel, name, anchor, blankText, vtype, maxLength, maxLengthText) {  //生成一个通用的密码输入框  
        var tf = new Ext.form.TextField({
            fieldLabel: fieldLabel,
            name: name,
            xtype: 'textfield',
            inputType :'password',
            readOnly: false,
            allowBlank: false,
            anchor: anchor,
            blankText: '该选项为必填项,请输入内容...',
            vtype: vtype,
            maxLength: maxLength,
            maxLengthText: maxLengthText
        });
        return tf;
    },
    createRadio: function(boxLabel,name,checked,inputValue) {    //生成一个通用的Radio
        var radio = new Ext.form.Radio({
            boxLabel:boxLabel,
            checked:checked,
            name:name,
            labelSeparator:"",
            inputValue:inputValue,
            anchor:"90%"
        });
        return radio;
    },
    createCombo: function(fieldLabel,id,name,formName,anchor,url,extra1,extra2,colspan) {    //生成一个通用的ComboBox
        var combo = new Ext.form.ComboBox({
        	colspan:colspan,
        	autoLoad: true,
            fieldLabel: fieldLabel,
            emptyText: '请选择...',
            isFormField: true,
            anchor: anchor,
            mode: 'local',
            hiddenName :formName,
            name:formName,
            allowBlank: false,
            blankText:'请选择...',
            forceSelection: true,
            lastQuery: '',
            triggerAction: 'all',
            displayField:name,
            valueField:id,
            store: new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url: url, method: 'POST'}),
                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:id},{name:name},{name:extra1},{name:extra2}]))
            }),
            editable : false
        });
        return combo;
    }, 
    createSearchCombo: function(fieldLabel,id,name,formName,anchor,url,extra1,extra2,colspan) {    //生成一个支持模糊查询的ComboBox
        var comboBox = new Ext.form.ComboBox({
        	colspan:colspan,
        	autoLoad: true,
            fieldLabel: fieldLabel,
            typeAhead: false,
            emptyText: '请选择...',
            triggerAction: 'all',
            isFormField: true,
            anchor: anchor,
//            mode: 'local',
            mode: 'remote',
            hiddenName :formName,
            name:formName,
            allowBlank: false,
            blankText:'请选择...',
            forceSelection: true,
            lastQuery: '',
            displayField:name,
            valueField:id,
            selectOnFocus: true,
            store: new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url: url, method: 'POST'}),
                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:id},{name:name},{name:extra1},{name:extra2}]))
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
        });
        return comboBox;
    }, 
    createMemoryCombo: function(fieldLabel,id,name,anchor,jsonData,formName) {//从JavaScript对象获得ComboBox数据源
    	 var combo = new Ext.form.ComboBox({
            fieldLabel: fieldLabel,
            emptyText: '请选择...',
            isFormField: true,
            anchor: anchor,
            mode: 'local',
            name: formName,
            hiddenName :formName,
            allowBlank: false,
            blankText:'请选择...',
            forceSelection: true,
            triggerAction: 'all',
            displayField:name,
            valueField:id,
            store: new Ext.data.Store({
                proxy: new Ext.data.MemoryProxy(jsonData),
                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:id},{name:name}]))
            }),
            editable : false
        });
        return combo;
    },
    /**
     * 生成一个通用的查询数据字典combo,此函数依赖于ST.ux.ExtField.js文件
     * @param fieldLabel
     * @param formName
     * @param anchor
     * @param dictTypeCode
     * @returns {ST.ux.ExtField.ComboBox}
     */
    createDictCombo: function(fieldLabel, formName, anchor, dictTypeCode, showAll) {    //生成一个通用的ComboBox
        var combo = new ST.ux.ExtField.ComboBox({
        	dictTypeCode: dictTypeCode,
            fieldLabel: fieldLabel,
            isFormField: true,
            anchor: anchor,
            hiddenName :formName,
            name:formName,
            allowBlank: false,
            forceSelection: true,
            showAll:showAll
        });
        return combo;
    },
    createDictComboww: function(fieldLabel, formName, anchor, dictTypeCode, colspan) {    //生成一个通用的ComboBox
        var combo = new Ext.form.ComboBox({
        	colspan:colspan,
        	autoLoad: true,
            fieldLabel: fieldLabel,
            emptyText: '请选择...',
            isFormField: true,
            anchor: anchor,
            mode: 'local',
            hiddenName :formName,
            name:formName,
            allowBlank: true,
            blankText:'请选择...',
            forceSelection: true,
            lastQuery: '',
            triggerAction: 'all',
            displayField:'name',
            valueField:'code',
            store: new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url: '/dict/queryDictEntries', method: 'POST'}),
                reader: new Ext.data.JsonReader({},new Ext.data.Record.create([{name:'code'},{name:'name'}]))
            }),
            editable : false
        });
        combo.store.load({params: {dictTypeCode: dictTypeCode}});
        return combo;
    },
    /**
     * 生成一个通用的本地combo
     * @param {} jsonData 格式必须是：[['-1','请选择…'],['1', '是'],['0', '否']]
     * @param {} anchor
     * @param {} allowBlank 
     * @param {} helpText  帮助文本
     * @return {}
     */
    createSimpleCombo: function(fieldLabel,jsonData,anchor,allowBlank,helpText) {  
       		var itemCls=(allowBlank?'':'required-field');
    		var dataStore = new Ext.data.SimpleStore({
    			fields : ['id', 'name'],
    			data : jsonData
    		});
    		var combox = new Ext.form.ComboBox({
    				fieldLabel : fieldLabel,
    				emptyText : "请选择...",
    				store : dataStore,
    				editable : false,
    				hiddenName : 'id',
    				valueField : 'id',
    				displayField : 'name',
    				typeAhead : true,
    				triggerAction : 'all',
    				selectOnFocus : true,
    				anchor: anchor,
    				itemCls:itemCls,
               		allowBlank:allowBlank,
    				mode : 'local'
    		});	
    		return combox;
    },
    createTextArea: function(fieldLabel,name,height,anchor, colspan) {
    	var ta = new Ext.form.TextArea({
    	    fieldLabel:fieldLabel,
            name:name,
            height:height,
            anchor:anchor,
            colspan:colspan
    	});
    	return ta;
    },
    createHidden: function(fieldLabel,name) {
    	var hidden = new Ext.form.Hidden({
    	    fieldLabel:fieldLabel,
    	    name:name
    	});
    	return hidden;
    },
    createHtmlEditor: function(fieldLabel, anchor) {      //生成一个通用的HtmlEditor
        var he = new Ext.form.HtmlEditor({
            fieldLabel: fieldLabel,
            height: 270,
            anchor: anchor,
            enableLinks: false,
            enableSourceEdit: false,
            fontFamilies:['宋体','隶书','黑体']
        });
        return he;
    },
    createButton: function(text,fn,scope) {
    	var btn = new Ext.Button({
    		align: 'left',
    		text: text,
    		handler: fn,
    		scope: scope
    	});
    	return btn;
    },
    createDateField: function(fieldLabel, name, format, anchor) {
    	var df =  new Ext.form.DateField({
			fieldLabel: fieldLabel,
			name: name,
			format: format,
			anchor: anchor,
			allowBlank: false,
			blankText: '请选择时间'
		});
		return df;
    },
    createLabel: function(fieldLabel,text) {
    	var label = new Ext.form.Label({
            width: 90,
            height: 40,
            fieldLabel: fieldLabel,
            text: text
        });
        return label;
    },
    createDateMonthField: function(fieldLabel, name, format, anchor) {
    	var df =  new Ext.form.DateField({
			fieldLabel: fieldLabel,
			plugins: 'monthPickerPlugin',
			name: name,
			format: format,
			anchor: anchor,
			allowBlank: false,
			blankText: '请选择时间'
		});
		return df;
    },
    /**
     * 创建下拉树
     * @param fieldLabel 标签
     * @param rootName 根节点名子
     * @param hiddenName 
     * @param url 请求URL
     * @param anchor 
     * @returns
     */
    createTreeCombo: function(fieldLabel, rootName, hiddenName, url, anchor) {
    	var df =  new Ext.ux.TreeCombo({
    		fieldLabel:fieldLabel,
    		rootVisible: false,
    		url: url,
    		allowBlank: false,
    		hiddenName: hiddenName,
    		name:hiddenName,
    		rootName: rootName,
    		anchor: anchor
		});
		return df;
    }
});
//Ext.reg（xtype字符串， 对象）;
//创建一个新的xtype
Ext.reg("uxForm", Ext.ux.Form);

