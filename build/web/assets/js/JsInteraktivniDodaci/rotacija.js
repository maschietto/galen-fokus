    var looper;
            var degrees = 0;
            var looper2;
            var degrees2 =0;
        function rotateAnimation(el,speed){
	var elem = document.getElementById(el);
	if(navigator.userAgent.match("Chrome")){
		elem.style.WebkitTransform = "rotate("+degrees+"deg)";
	} else if(navigator.userAgent.match("Firefox")){
		elem.style.MozTransform = "rotate("+degrees+"deg)";
	} else if(navigator.userAgent.match("MSIE")){
		elem.style.msTransform = "rotate("+degrees+"deg)";
	} else if(navigator.userAgent.match("Opera")){
		elem.style.OTransform = "rotate("+degrees+"deg)";
	} else {
		elem.style.transform = "rotate("+degrees+"deg)";
	}
	looper = setTimeout('rotateAnimation(\''+el+'\','+speed+')',speed);
	degrees++;
	if(degrees > 359){
		degrees = 1;
	}
	document.getElementById("status").innerHTML = "rotate("+degrees+"deg)";
        }
         
        function rotateAnimationBack(el,speed){
	var elem = document.getElementById(el);
	if(navigator.userAgent.match("Chrome")){
		elem.style.WebkitTransform = "rotate("+degrees2+"deg)";
	} else if(navigator.userAgent.match("Firefox")){
		elem.style.MozTransform = "rotate("+degrees2+"deg)";
	} else if(navigator.userAgent.match("MSIE")){
		elem.style.msTransform = "rotate("+degrees2+"deg)";
	} else if(navigator.userAgent.match("Opera")){
		elem.style.OTransform = "rotate("+degrees2+"deg)";
	} else {
		elem.style.transform = "rotate("+degrees2+"deg)";
	}
	looper2 = setTimeout('rotateAnimationBack(\''+el+'\','+speed+')',speed);
	degrees2--;
	if(degrees2 < -359){
		degrees2 = -1;
	}
	document.getElementById("status").innerHTML = "rotate("+degrees2+"deg)";
        }

