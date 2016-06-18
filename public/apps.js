$(document).ready(function() {
    // pizza.init();
})

var pizzaPage = {
    url: "http://tiny-tiny.herokuapp.com/collections/ironPizza",
    pizzaArr: [],
    intit: function() {
        pizzaPage.events();
        pizzaPage.styling();
    },
    styling: function() {
        pizzaPage.read();
    },


    events: function() {
        $('form').on('submit', function(event) {
            event.preventDefault();
            var pizzaToSave = {
                    name: $('input[name="orderList"]').val(),
                    size: $('input[name="sizeList"]').val(),
                    crust: $('input[name="long"]').val(),
                    sauce: $('input[name="desc"]').val(),
                    topping1: $('input[name="desc"]').val(),
                    topping2: $('input[name="desc"]').val(),
                    topping3: $('input[name="desc"]').val(),
                }
                // debugger
            console.log(pizzaToSave);
            pizzaPage.create(JSON.stringify(pizzaToSave));

            $('input').val("");
        })
    },


    createPizza: function(element) {
        $.ajax({
            url: pizza.url,
            method: 'POST',
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
            method: 'GET',
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
            method: 'PUT',
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
            method: 'DELETE',
            success: function(data) {
                console.log("yes!", data);

            },
            error: function() {
                console.error("delete error", err);
            }
        })
    },

}
