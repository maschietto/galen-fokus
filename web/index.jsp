<%@page import="model.Zaposleni"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ULAZ</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/cupertino/datapicker.css">
        <link rel="stylesheet" href="assets/css/main.css">
        <script src="assets/js/dragdown.js" type="text/javascript"></script>
      
  
      
                   
        <script src="assets/js/JsInteraktivniDodaci/rotacija.js" type="text/javascript"></script>
        <script src="assets/js/jquery-1.10.2.js" type="text/javascript"></script>

        <script src="assets/js/jquery.min.js" type="text/javascript"></script> 
        <script src="assets/js/jquery-ui.js"></script>
        <script src="assets/js/jquery.ui.datepicker-sr-SR.js"></script>
        
        <script src="assets/js/lexa.js" type="text/javascript"></script>
        <script src="assets/js/zaZasebneStrane/zaIndexStranu.js" type="text/javascript"></script>
        <script src="assets/js/AjaxPozivi/Zaposleni_js_ajax.js" type="text/javascript"></script>
        <script src="assets/js/bootstrap.min.js"></script>
         <script src="assets/js/main.js"></script>
        

        
 
        
    </head>

   
       
   
   
   <body>
      
       <div id="administracija_korisnika" style="display: none;" >
                   
           <!--<script>frameLooper('admin_pojava_text');</script>-->
           <%@include file="jspf/adminParticije/AdminPartRegular.jspf"%>  
             
       </div>
        
       <div id="login_korisnika" style="display: block;" >
           <%@include file="jspf/LoginRegular.jspf"%>  
       </div>
        
       
        <%@include file="jspf/tockici.jspf"%>  
        
    </body>
</html>
