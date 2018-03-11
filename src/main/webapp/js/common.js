   var formHtml = function(){
                var html = '<div class="row"><div class="col-md-12 data"></select> <select style = "width:100px"><option>must</option><option>must_not</option><option>should</option></select> <select>';
                for (var i = 0; i < classNames.length; i++) {
                    html = html + '<option>'+classNames[i]+'</option>';
                }
                html = html + '</select> <select style = "width:100px"><option>=</option><option>></option><option><</option><option>>=</option><option><=</option><option>prefix</option><option>queryStr</option><option>missing</option><option>in</option></select>';
                html = html + '<input type="text">';
                html = html + '<button class="btn btn-default" onclick = "addFormDiv()">+</button>';            
                html = html + '<button class="btn btn-default" onclick = "delFormDiv(this)">-</button></div></div>';            
                return html;
            }
   
   //高亮
   var highliht = function(val){
   	val=parseFloat(val);
       if(val > 0){
           return '<span style="color: #b00">' + fixed2(val) + '</span>';
       }else if(val < 0){
           return '<span style="color: #0b0">' + fixed2(val) + '</span>';
       }
       return fixed2(val);
   };
   
   var highlihtNor = function(val){
	   	val=parseFloat(val);
	       if(val > 0){
	           return '<span style="color: #b00">' + val + '</span>';
	       }else if(val < 0){
	           return '<span style="color: #0b0">' + val + '</span>';
	       }
	       return val;
	   };
   
   var   moformat=function(val){
   	val=parseFloat(val);
   	  if(typeof val != 'number'){
             return '';
         }
   	  var   nnn=val/10000;
 		   var   ccc="万";
 		   if(Math.abs(nnn)>=10000){
 			    nnn=nnn/10000;
 			    ccc="亿";
 		   }
   	  
   	   if(val > 0){
   		    
            return '<span style="color: #b00">' + fixed2(nnn) +ccc+ '</span>';
        }else if(val < 0){
            return '<span style="color: #0b0">' + fixed2(nnn) + ccc+'</span>';
        }  
   	
   }
   
   var   moformat2=function(val){
   	val=parseFloat(val)*10000;
   	  if(typeof val != 'number'){
             return '';
         }
   	  var   nnn=val/10000;
 		   var   ccc="万";
 		   if(Math.abs(nnn)>=10000){
 			    nnn=nnn/10000;
 			    ccc="亿";
 		   }
   	  
   	   if(val > 0){
   		    
            return '<span style="color: #b00">' + fixed2(nnn) +ccc+ '</span>';
        }else if(val < 0){
            return '<span style="color: #0b0">' + fixed2(nnn) + ccc+'</span>';
        }  
   	
   }

   var fixed2 = function(val){
           if(typeof val != 'number'){
               return '';
           }
           return val.toFixed(2);
       }

   //加百分号
   var fixed2percentage = function(val){
       return highliht(val)+'%';
   }
   
   var fixed2percentage2 = function(val){
   		val = parseFloat(val) * 100
       return highliht(val)+'%';
   }
   var sub2 = function(val){
		return val.substr(0,4);
   }
   
   var fixed2percentage3 = function(val){
   		val = parseFloat(val) * 1000
       return highliht(val)+'%';
   }
   
   function getDatas(){
       var datas = [];
       $('#fh .data').each(function(i){
           var obj = $(this);
           var j = {
           	must:obj.find('select').eq(0).val(),
               name:obj.find('select').eq(1).val(),
               type:obj.find('select').eq(2).val(),
               value:obj.find('input').eq(0).val()
           }
           datas[i] = j;
       });
       return datas;
   }
   
   function opencl(){
   		$('#clName').val('');
   		$('#clModal').modal('show');
   }
   
   function savecl(bq){
   		var name = $('#clName').val();
   		if(name == ''){
   			alert('请填写策略名称');
   			return;
   		}
   		var str=JSON.stringify(getDatas());
   		$.ajax({  
        	type : "GET",  //提交方式  
        	url : "http://www.estock.com:8080/stock/savecl",//路径  
        	data : {
        		datas:str,
        		name:name,
        		bq:bq
        	},//数据，这里使用的是Json格式进行传输  
        	success : function(result) {//返回数据根据结果进行相应的处理  
        		alert('保存成功');
        		getcl(bq,name);
            }  
        }); 
   }
   
   function getcl(bq,op){
   		$.ajax({  
        	type : "GET",  //提交方式  
        	url : "http://www.estock.com:8080/stock/getcl",//路径  
        	data : {
        		sort:'date.asc',
        		datas:'[{"must":"must","name":"bq","type":"=","value":"'+bq+'"}]'
        	},//数据，这里使用的是Json格式进行传输  
        	success : function(result) {//返回数据根据结果进行相应的处理  
        		var item = result['items'];
	        	var html = '<option value = "-1">未选择</option>';
	        	for(var i=0;i<item.length;i++){
	        		if(op != item[i]['name']){
	        			html = html + "<option value = '"+item[i]["datas"]+"'>"+item[i]["name"]+"</option>";
	        		}else{
	        			html = html + "<option value = '"+item[i]["datas"]+"' selected>"+item[i]["name"]+"</option>";
	        		}
	        	}
	        	$('#clselect').html(html);
            }  
        }); 
   }
   
   function getDatasHtml(datas){
            	var data = $.parseJSON(datas);  
            	var musts = ['must' , 'must_not' , 'should'];
            	var types = ['=' , '>' , '<' , '>=' , '<=' , 'prefix' , 'queryStr' , 'missing' , 'in'];
            	var html = '';   
            	for(var k = 0;k<data.length;k++){
            		var d = data[k];
            		html = html + '<form class="form-inline"><div class="row "><div class="data" style = "vertical-align: middle;"> <select class="form-control">';
            		for (var i = 0; i < musts.length; i++) {
            			if(d["must"] == musts[i]){
            				html = html + '<option selected>'+musts[i]+'</option>';
            			}else{
            				html = html + '<option>'+musts[i]+'</option>';
            			}
            		}
            		html = html + '</select> <select class="form-control">';
	                for (var i = 0; i < classNames.length; i++) {
	                	if(d["name"] == classNames[i]){
	                		html = html + '<option selected>'+classNames[i]+'</option>';
	                	}else{
	                		html = html + '<option>'+classNames[i]+'</option>';
	                	}
	                }
	                html = html + '</select> <select style = "width:100px" class="form-control">';
	                for (var i = 0; i < types.length; i++) {
	                	if(d["type"] == types[i]){
            				html = html + '<option selected>'+types[i]+'</option>';
            			}else{
            				html = html + '<option>'+types[i]+'</option>';
            			}
	                }
	                html = html + '</select> <input type="text" class="form-control" value = '+d["value"]+'>';
	                html = html + ' <input type="button" class="form-control" onclick = "addFormDiv()" value = " + ">';            
	                html = html + ' <input type="button" class="form-control" onclick = "delFormDiv(this)" value = " - "></div></div></from>';
            	}
            	$('#fh').append(html);
            }
