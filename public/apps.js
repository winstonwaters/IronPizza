$(document).ready(function() {
<<<<<<< HEAD
    pizzaPage.init();
})

var pizzaPage = {
    url: "http://tiny-tiny.herokuapp.com/collections/ironPizza",
    pizzaArr: [],
    init: function() {
        pizzaPage.events();
        pizzaPage.styling();
    },
    styling: function() {
        pizzaPage.readPizza();
    },


    events: function() {
        $('#order').on("click", "submit", function(event) {
            event.preventDefault();
            console.log("click");
            $('.order-group input[name="order-list"]').val($('input[name="order"]').val());
            $('input[name="order"]').val("");
        })

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
            pizzaPage.createPizza(pizzaToSave);
            // pizzaPage.create(JSON.stringify(pizzaToSave));

            // $('input').val("");
        })

    },


    createPizza: function(pizzaToSave) {
        $.ajax({
            // contentType: "application/json; charset=utf-8",
            url: pizzaPage.url,
            method: 'POST',
            data: pizzaToSave,
            success: function(data) {
                console.log("yes! created", data);
                // pizzaPage.readPizza();
            },
            error: function() {
                console.error("create error", err);
            }
        })
    },

    readPizza: function() {
        $.ajax({
            url: pizzaPage.url,
            method: 'GET',
            success: function(data) {
                console.log("yes! read", data);
                data.forEach(function(element, idx) {
                    var pizzaHtmlStr = pizzaPage.htmlGenerator(pizzaTmpls.myPizza, element)
                    $('.chat-window').append(pizzaHtmlStr);
                    pizzaPage.pizzaArr.push(data);

                });

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
                console.log("yes! update", data);

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
                console.log("yes! deleted", data);

            },
            error: function() {
                console.error("delete error", err);
            }
        })
    },
    templification: function(template) {
        return _.template(template);
    },

    htmlGenerator: function(template, data) {
        var tmpl = pizzaPage.templification(template);
        return tmpl(data);
    },


};
