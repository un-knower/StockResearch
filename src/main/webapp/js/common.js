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
