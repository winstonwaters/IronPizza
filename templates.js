var pizzaTmpls = {
  myPizza:`
    <ul class="final-order" data-id="<%= id %>">
    <li> <%= name %> </li>
    <li> <%= size %> </li>
    <li> <%= crust %> </li>
    <li> <%= sauce %> </li>
    <li> <p><a class="btn btn-primary btn-lg delete" href="#">Cancel Order</a></p> </li>
    </ul>
   `
}
// <li> <%= topping[0] %> </li>
// <li> <%= topping[1] %> </li>
// <li> <%= topping[2] %> </li>
