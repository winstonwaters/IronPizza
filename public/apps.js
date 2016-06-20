$(document).ready(function() {
    pizzaPage.init();
})

var pizzaPage = {
    url: "/pizza",
    pizzaArr: [],
    init: function() {
        pizzaPage.events();
        pizzaPage.styling();
    },
    styling: function() {
        pizzaPage.readPizza();
    },
    events: function() {

        //refresh pizzaPage
        $('.navbar-header').on('click', function() {
          event.preventDefault();
          console.log('you clicked header');
          $('.pizza').addClass('hidden');
          $('.ordering-container').removeClass('hidden');
        }),
        //dropdown size
        $("#dropdownSize").on('click', function() {
          event.preventDefault();
          console.log('you clicked size');
          $('#sizedrop').removeClass('hidden');
        });
        //dropdown crust
        $("#dropdownCrust").on('click', function() {
          event.preventDefault();
          console.log('you clicked crust');
          $('#crustdrop').removeClass('hidden');
        });
        //dropdown sauce
        $("#dropdownSauce").on('click', function() {
          event.preventDefault();
          console.log('you clicked sauce');
          $('#saucedrop').removeClass('hidden');
        });
        //dropdown meat
        $("#dropdownMeat").on('click', function() {
          event.preventDefault();
          console.log('you clicked meat');
          $('#meatdrop').removeClass('hidden');
        });
        //dropdown veggie
        $("#dropdownVeggie").on('click', function() {
          event.preventDefault();
          console.log('you clicked veggie');
          $('#veggiedrop').removeClass('hidden');
        });
        //dropdown cheese
        $("#dropdownCheese").on('click', function() {
          event.preventDefault();
          console.log('you clicked cheese');
          $('#cheesedrop').removeClass('hidden');
        });


        //refreshes option menu on licking a custom topping
        $(".dropdown-item").on('click', function(){
          event.preventDefault();
          console.log('you are customizing');
          $('.dropdown-menu').addClass('hidden');
        });

        //loging custom item
        $('.size').on('click', function(event) {
          event.preventDefault();
          console.log($(this));
          $('input[name="size-list"]').val($(this).text());
        });
        $('.crust').on('click', function(event) {
          event.preventDefault();
          console.log($(this));
          $('input[name="crust-list"]').val($(this).text());
        });
        $('.sauce').on('click', function(event) {
          event.preventDefault();
          console.log($(this));
          $('input[name="sauce-list"]').val($(this).text());
        });
        $('.topping1').on('click', function(event) {
          event.preventDefault();
          console.log($(this));
          $('input[name="topping1-list"]').val($(this).text());
        });
        $('.topping2').on('click', function(event) {
          event.preventDefault();
          console.log($(this));
          $('input[name="topping2-list"]').val($(this).text());
        });
        $('.topping3').on('click', function(event) {
          event.preventDefault();
          console.log($(this));
          $('input[name="topping3-list"]').val($(this).text());
        });


        $('#geoff').on('click', function() {
          event.preventDefault();
          $('.order-group input[name="size-list"]').val('large');
          $('.order-group input[name="crust-list"]').val('stuffed');
          $('.order-group input[name="sauce-list"]').val('tomato');
          $('.order-group input[name="topping1-list"]').val('pepperoni');
          $('.order-group input[name="topping2-list"]').val('pepperoni');
          $('.order-group input[name="topping3-list"]').val('pepperoni');
        });

        $('#ben').on('click', function() {
          event.preventDefault();
          $('.order-group input[name="size-list"]').val('large');
          $('.order-group input[name="crust-list"]').val('thick');
          $('.order-group input[name="sauce-list"]').val('tomato');
          $('.order-group input[name="topping1-list"]').val('buffalo chicken');
          $('.order-group input[name="topping2-list"]').val('');
          $('.order-group input[name="topping3-list"]').val('');
        });
        $('#lydell').on('click', function() {
          event.preventDefault();
          $('.order-group input[name="size-list"]').val('large');
          $('.order-group input[name="crust-list"]').val('thick');
          $('.order-group input[name="sauce-list"]').val('tomato');
          $('.order-group input[name="topping1-list"]').val('buffalo chicken');
          $('.order-group input[name="topping2-list"]').val('pepperoni');
          $('.order-group input[name="topping3-list"]').val('bacon');
        });
        $('#winston').on('click', function() {
          event.preventDefault();
          $('.order-group input[name="size-list"]').val('medium');
          $('.order-group input[name="crust-list"]').val('thin');
          $('.order-group input[name="sauce-list"]').val('tomato basil');
          $('.order-group input[name="topping1-list"]').val('mozzaerlla');
          $('.order-group input[name="topping2-list"]').val('basil');
          $('.order-group input[name="topping3-list"]').val('parmesan');
        });

        $(".order-group").on('submit', function(event) {
            event.preventDefault();
            console.log("YAYAYAYY CLICKED");
            var pizzaToSave = {
                    name: $('.order-group input[name="order-list"]').val(),
                    size: $('input[name="size-list"]').val(),
                    crust: $('input[name="crust-list"]').val(),
                    sauce: $('input[name="sauce-list"]').val(),
                    topping:[{topping: $('input[name="topping1-list"]').val()},{topping:$('input[name="topping2-list"]').val()},{topping:$('input[name="topping3-list"]').val()}]
                    // topping2: $('input[name="topping2-list"]').val(),
                    // topping3: $('input[name="topping3-list"]').val(),
                }
                // debugger

            console.log(pizzaToSave);
//            pizzaPage.createPizza(pizzaToSave);
             pizzaPage.createPizza(JSON.stringify(pizzaToSave));

            $('input').val("");
            $('.pizza').removeClass('hidden');
            $('.ordering-container').addClass('hidden');
        })
        //cancel pizza order
        $("#cancel").on("click", function(event){
          event.preventDefault();
          var pizzaID = $(this).children().data('id');
          console.log(this,pizzaID);
          pizzaPage.deletePizza(pizzaID);
          $('.pizza').addClass('hidden');
          $('.ordering-container').removeClass('hidden');
        });

    },



    createPizza: function(pizzaToSave) {
      console.log(pizzaToSave);
        $.ajax({
            contentType: "application/json; charset=utf-8",
            url: pizzaPage.url,
            method: 'POST',
            data: pizzaToSave,
            success: function(data) {
                console.log("yes! created", data);
                console.log();
                pizzaPage.readPizza();
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
                data = JSON.parse(data);
                console.log("yes! read", data);
                // data = JSON.parse(data);
                $('#cancel').html("");
                data.forEach(function(element, idx) {
                    var pizzaHtmlStr = pizzaPage.htmlGenerator(pizzaTmpls.myPizza, element)
                    $('#cancel').append(pizzaHtmlStr);
                    pizzaPage.pizzaArr.push(data);

                });

                console.log(data);
            },
            error: function() {
                console.error("read error", err);
            }
        })
    },

    updatePizza: function(pizzaArr) {
        $.ajax({
            url: pizzaPage.url,
            method: 'PUT',
            data: pizzaArr,
            success: function(data) {
                console.log("yes! update", data);
                pizzaPage.readPizza();

            },
            error: function() {
                console.error("update error", err);
            }
        })
    },

    deletePizza: function(pizzaID) {
        var deleteOrder = pizzaPage.url +'/'+pizzaID;
        $.ajax({
            url: deleteOrder,
            method: 'DELETE',
            success: function() {
                console.log("yes! deleted");
                console.log(deleteOrder);
                pizzaPage.readPizza();
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
