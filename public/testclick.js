$('#order').on('click', function(event) {
  event.preventDefault();
 $('#nameList').html($('input[name="order"]').val());
  $('input[name="order"]').val("");
})
$('#size').on('click', function(event) {
  event.preventDefault();
 $('#sizeList').html($('input[name="size"]').val());
  $('input[name="size"]').val("");
})
