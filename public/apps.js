$(document).ready(function(){
  // pizza.init();
})

var pizza = {
  url: "",
  pizzaArr: [],
  intit: function () {
    pizza.events();
    pizza.styling();
  },
  styling: function () {
    pizza.read();
  },


  events: function () {

  },


  createPizza: function(element) {
    $.ajax({
      url: pizza.url,
      method:'POST',
      data: element,
      success: function(data) {
        console.log("yes!", data);

      },
      error: function() {
        console.error("create error", err);
      }
    })
  },

  readPizza: function() {
    $.ajax({
      url: pizza.url,
      method:'GET',
      success: function(data) {
        console.log("yes!", data);

      },
      error: function() {
        console.error("read error", err);
      }
    })
  },

  updatePizza: function(data) {
    $.ajax({
      url: pizza.url,
      method:'PUT',
      data: data,
      success: function(data) {
        console.log("yes!", data);

      },
      error: function() {
        console.error("update error", err);
      }
    })
  },

  deletePizza: function(pizzaID) {
    var deleteOrder = pizza.url + '/' + pizzaID;
    $.ajax({
      url: deleteOrder,
      method:'DELETE',
      success: function(data) {
        console.log("yes!", data);

      },
      error: function() {
        console.error("delete error", err);
      }
    })
  },

}
