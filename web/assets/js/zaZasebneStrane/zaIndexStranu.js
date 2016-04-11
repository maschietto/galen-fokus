               
               window.onload = function(){ 
                   
                   
             
                   setTimeout(function(){var elem = document.getElementById('tockiciBre');
                   elem.style.transition = 'opacity 2s linear 0s';
                   elem.style.opacity = 1;},500); 
                   setTimeout(function(){var elem = document.getElementById('loadSlickaLOgo');
                   elem.style.transition = 'opacity 2s linear 0s';
                   elem.style.opacity = 1;},500); 
                   setTimeout(function(){var elem = document.getElementById('loadSlickaLOgo');
                   elem.style.transition = 'opacity 2.5s linear 0s';
                   elem.style.opacity = 0;},1500); 
                   setTimeout(function(){var elem = document.getElementById('logovanje');
                   elem.style.transition = 'opacity 3s linear 0s';
                   elem.style.opacity = 1;},2500);
                   setTimeout(function(){var elemental = document.getElementById('strelice');
                   elemental.style.transition = 'opacity 2s linear 0s';
                   elemental.style.opacity = 1;},3000);
               
            }    
           
           
           
           function prikazi_login(id){
               var element = document.getElementById(id);
               if(element.style.display == 'none') 
                  setTimeout(function(){element.style.display = 'block';},220);
            }
            
             function ugasi_login(id){
                var element = document.getElementById(id);
                if(element.style.display == 'block') element.style.display = 'none';
            }
             
             
             function fadeOut(el){
                var elem = document.getElementById(el);
                elem.style.transition = 'opacity 0.1s linear 0s';
                elem.style.opacity = 0;
            
        }
             function fadeIn(el){
                var elem = document.getElementById(el);
                elem.style.transition = 'opacity 0.6s linear 0s';
                elem.style.opacity = 1;
        }
        
       
          function fadeInAdmin(id){
                var elem = document.getElementById(id);
              
                elem.style.transition = 'opacity 2s linear 0s';
                elem.style.opacity = 1;
                
               
          
        }
               function fadeOutLogin(id){
                var elem = document.getElementById(id);
                elem.style.transition = 'opacity 1s linear 0s';
                elem.style.opacity = 0;
        }
        
        
        
        function aktiviranjeDeaktiviranjeDiv(divId,varijabla){
   
            var odeljak = document.getElementById(divId);
            
            
            if( varijabla == "deaktiviraj"){
           
        
            
            var nodes = document.getElementById(divId).getElementsByTagName('*');
           
            for(var i = 0; i < nodes.length; i++)
            {
            nodes[i].disabled = true;
            }
            odeljak.disabled = true;
            
             setTimeout( function(){odeljak.style.display= "none";},1000);
            
            }else{
            
             var nodes2 = document.getElementById(divId).getElementsByTagName('*');
            for(var i = 0; i < nodes2.length; i++)
            {
            nodes2[i].disabled = false;
            }
            odeljak.disabled = false;
//            odeljak.style.display = "block";
        }
            
        }
        
               function frameLooper(idText) {
                     
                   var myString = document.getElementById(idText).innerHTML;
                   var myArray = myString.split("");
                   var loopTimer;
                   
	if(myArray.length > 0) {
		document.getElementById(idText).innerHTML += myArray.shift();
	} else {
		clearTimeout(loopTimer); 
                return false;
	}
         loopTimer = setTimeout(function(){frameLooper();},70);
        }
        
        
        function slideInDiv1(el){
            
	var elem = document.getElementById(el);
	elem.style.display = "block";
           setTimeout(function(){ elem.style.transition = "left 0.5s ease-in 0s";
	elem.style.left = "0px";
        },50);
    }
        function slideOutDiv2(el){
	var elem = document.getElementById(el);
	elem.style.transition = "left 0.3s ease-out 0s";
	elem.style.left = "-1400px";
        setTimeout(function(){elem.style.display = "none";}, 200);
        }
     
      function slideInTabela(el){
	var elem = document.getElementById(el);
        elem.style.display = "block";
        
        setTimeout(function (){elem.style.transition = "right 0.5s ease-in 0s";
	elem.style.right = "-430px";}, 150);
	
        }
        
          function slideOutTabela(el){
         
	var elem = document.getElementById(el);
        
	elem.style.transition = "right 0.2s ease-out 0s";
	elem.style.right = "-1500px";
        setTimeout(function(){elem.style.display = "none";}, 200);
       // setTimeout(function(){elem.style.display = "none";}, 200);
       
        //setTimeout(function(){elem.style.display = "none";}, 200);
        }  
          
          
        function slideInTOP(el){
 
	var elem = document.getElementById(el);
        elem.style.transition = "top 0.5s ease-out 0s";
	elem.style.top = "0px";
        }  
          
               function slideOutTOP(el){

	var elem = document.getElementById(el);
        elem.style.transition = "top 0.5s ease-out 0s";
	elem.style.top = "1000px";
         setTimeout(function(){elem.style.display = "none";}, 200);
        }  
        
        
        function slideInBottom(el){
 
	var elem = document.getElementById(el);
        elem.style.transition = "bottom 0.5s ease-out 0s";
	elem.style.bottom = "0px";
     
        }  
        
        
        function slideOutBottom(el){

	var elem = document.getElementById(el);
        elem.style.transition = "bottom 0.5s ease-out 0s";
	elem.style.bottom = "1600px";   
        //setTimeout(function(){elem.style.display = "none";}, 200);
        }  
        
        function slideOutListaKor(el){
	var elem = document.getElementById(el);
	elem.style.transition = "left 0.3s ease-out 0s";
	elem.style.left = "-1400px";
        setTimeout(function(){elem.style.display = "none";}, 200);
        }
        
       
     
  function rotacija() {
    var count = 0;
    var elem2 = document.getElementById('ponuda_zasebno');
    elem2.style.MozTransform = 'scale(0.5) rotate('+count+'deg)';
    elem2.style.WebkitTransform = 'scale(0.5) rotate('+count+'deg)';
    if (count == 360) {count = 0}
    count+=45;
    window.setTimeout(rotate, 100);
  }
  window.setTimeout(rotate, 100);