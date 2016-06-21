$('.leftside-order').on('submit', function(event) {
  event.preventDefault();
  console.log("click");
  $('.order-group input[name="order-list"]').val($('input[name="order"]').val());
  $('input[name="order"]').val("");
})
$('#size').on('click', function(event) {
  event.preventDefault();
  console.log(size);
 // $('#sizeList').html($('input[name="size"]').val());
  $('input[name="size"]').val("");
})




//////object create testclick
$(".order-group").on('submit', function(event) {
  event.preventDefault();
  console.log("click");
  var pizzaToSave = {
    name: $('.order-group input[name="order-list"]').val(),
    size: $('input[name="size-list"]').val(),
    crust: $('input[name="crust-list"]').val(),
    sauce: $('input[name="sauce-list"]').val(),
    topping1: $('input[name="topping1-list"]').val(),
    topping2: $('input[name="topping2-list"]').val(),
    topping3: $('input[name="topping3-list"]').val(),
  }
  // debugger
  
  console.log(pizzaToSave);

  $('input').val("");
})
