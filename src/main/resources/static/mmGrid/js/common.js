   var formHtml = function(){
                var html = '<form class="form-inline"><div class="row "><div class="data" style = "vertical-align: middle;"> <select class="form-control"><option>must</option><option>must_not</option><option>should</option></select> <select class="form-control">';
                for (var i = 0; i < classNames.length; i++) {
                    html = html + '<option>'+classNames[i]+'</option>';
                }
                html = html + '</select> <select style = "width:100px" class="form-control"><option>=</option><option>></option><option><</option><option>>=</option><option><=</option><option>prefix</option><option>queryStr</option><option>missing</option><option>in</option><option>matchPhrase</option></select>';
                html = html + ' <input type="text" class="form-control">';
                html = html + ' <input type="button" class="form-control" onclick = "addFormDiv()" value = " + ">';            
                html = html + ' <input type="button" class="form-control" onclick = "delFormDiv(this)" value = " - "></div></div></from>';            
                return html;
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
            	$('#fh').html(html);
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
