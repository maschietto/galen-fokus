$(function() {
    $("#datepicker").datepicker();
    
    $("input[name^=ponuda_data]").datepicker();
    $("input[name^= novi_datum_ponude]").datepicker();
  
       
    
    
    $('.selectpicker').selectpicker({
        style: 'btn-info',
        size: 4
    });
});
      