"use strict";$(document).ready(function(){pizzaPage.init()});var pizzaPage={url:"/pizza",pizzaArr:[],init:function(){pizzaPage.events(),pizzaPage.styling()},styling:function(){pizzaPage.readPizza()},events:function(){$(".navbar-header").on("click",function(){event.preventDefault(),console.log("you clicked header"),$(".pizza").addClass("hidden"),$(".ordering-container").removeClass("hidden")}),$("#dropdownSize").on("click",function(){event.preventDefault(),console.log("you clicked size"),$("#sizedrop").removeClass("hidden")}),$("#dropdownCrust").on("click",function(){event.preventDefault(),console.log("you clicked crust"),$("#crustdrop").removeClass("hidden")}),$("#dropdownSauce").on("click",function(){event.preventDefault(),console.log("you clicked sauce"),$("#saucedrop").removeClass("hidden")}),$("#dropdownMeat").on("click",function(){event.preventDefault(),console.log("you clicked meat"),$("#meatdrop").removeClass("hidden")}),$("#dropdownVeggie").on("click",function(){event.preventDefault(),console.log("you clicked veggie"),$("#veggiedrop").removeClass("hidden")}),$("#dropdownCheese").on("click",function(){event.preventDefault(),console.log("you clicked cheese"),$("#cheesedrop").removeClass("hidden")}),$(".dropdown-item").on("click",function(){event.preventDefault(),console.log("you are customizing"),$(".dropdown-menu").addClass("hidden")}),$(".size").on("click",function(e){e.preventDefault(),console.log($(this)),$('input[name="size-list"]').val($(this).text())}),$(".crust").on("click",function(e){e.preventDefault(),console.log($(this)),$('input[name="crust-list"]').val($(this).text())}),$(".sauce").on("click",function(e){e.preventDefault(),console.log($(this)),$('input[name="sauce-list"]').val($(this).text())}),$(".topping1").on("click",function(e){e.preventDefault(),console.log($(this)),$('input[name="topping1-list"]').val($(this).text())}),$(".topping2").on("click",function(e){e.preventDefault(),console.log($(this)),$('input[name="topping2-list"]').val($(this).text())}),$(".topping3").on("click",function(e){e.preventDefault(),console.log($(this)),$('input[name="topping3-list"]').val($(this).text())}),$("#geoff").on("click",function(){event.preventDefault(),$('.order-group input[name="size-list"]').val("large"),$('.order-group input[name="crust-list"]').val("stuffed"),$('.order-group input[name="sauce-list"]').val("tomato"),$('.order-group input[name="topping1-list"]').val("pepperoni"),$('.order-group input[name="topping2-list"]').val("pepperoni"),$('.order-group input[name="topping3-list"]').val("pepperoni")}),$("#ben").on("click",function(){event.preventDefault(),$('.order-group input[name="size-list"]').val("large"),$('.order-group input[name="crust-list"]').val("thick"),$('.order-group input[name="sauce-list"]').val("tomato"),$('.order-group input[name="topping1-list"]').val("buffalo chicken"),$('.order-group input[name="topping2-list"]').val(""),$('.order-group input[name="topping3-list"]').val("")}),$("#lydell").on("click",function(){event.preventDefault(),$('.order-group input[name="size-list"]').val("large"),$('.order-group input[name="crust-list"]').val("thick"),$('.order-group input[name="sauce-list"]').val("tomato"),$('.order-group input[name="topping1-list"]').val("buffalo chicken"),$('.order-group input[name="topping2-list"]').val("pepperoni"),$('.order-group input[name="topping3-list"]').val("bacon")}),$("#winston").on("click",function(){event.preventDefault(),$('.order-group input[name="size-list"]').val("medium"),$('.order-group input[name="crust-list"]').val("thin"),$('.order-group input[name="sauce-list"]').val("tomato basil"),$('.order-group input[name="topping1-list"]').val("mozzaerlla"),$('.order-group input[name="topping2-list"]').val("basil"),$('.order-group input[name="topping3-list"]').val("parmesan")}),$(".order-group").on("submit",function(e){e.preventDefault(),console.log("YAYAYAYY CLICKED");var n={name:$('.order-group input[name="order-list"]').val(),size:$('input[name="size-list"]').val(),crust:$('input[name="crust-list"]').val(),sauce:$('input[name="sauce-list"]').val(),topping:[{topping:$('input[name="topping1-list"]').val()},{topping:$('input[name="topping2-list"]').val()},{topping:$('input[name="topping3-list"]').val()}]};console.log(n),pizzaPage.createPizza(JSON.stringify(n)),$("input").val(""),$(".pizza").removeClass("hidden"),$(".ordering-container").addClass("hidden")}),$("#cancel").on("click",function(e){e.preventDefault();var n=$(this).children().data("id");console.log(this,n),pizzaPage.deletePizza(n),$(".pizza").addClass("hidden"),$(".ordering-container").removeClass("hidden")})},createPizza:function(e){console.log(e),$.ajax({contentType:"application/json; charset=utf-8",url:pizzaPage.url,method:"POST",data:e,success:function(e){console.log("yes! created",e),console.log(),pizzaPage.readPizza()},error:function(){console.error("create error",err)}})},readPizza:function(){$.ajax({url:pizzaPage.url,method:"GET",success:function(e){e=JSON.parse(e),console.log("yes! read",e),$("#cancel").html(""),e.forEach(function(n,o){var t=pizzaPage.htmlGenerator(pizzaTmpls.myPizza,n);$("#cancel").append(t),pizzaPage.pizzaArr.push(e)}),console.log(e)},error:function(){console.error("read error",err)}})},updatePizza:function(e){$.ajax({url:pizzaPage.url,method:"PUT",data:e,success:function(e){console.log("yes! update",e),pizzaPage.readPizza()},error:function(){console.error("update error",err)}})},deletePizza:function(e){var n=pizzaPage.url+"/"+e;$.ajax({url:n,method:"DELETE",success:function(){console.log("yes! deleted"),console.log(n),pizzaPage.readPizza()},error:function(){console.error("delete error",err)}})},templification:function(e){return _.template(e)},htmlGenerator:function(e,n){var o=pizzaPage.templification(e);return o(n)}};