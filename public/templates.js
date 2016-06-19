var pizzaTmpls = {
  myPizza:`
    <ul class="final-order" data-id=" <%= _id %>">
    <li> <%= name %> </li>
    <li> <%= size %> </li>
    <li> <%= crust %> </li>
    <li> <%= sauce %> </li>
    <li> <%= topping1 %> </li>
    <li> <%= topping2 %> </li>
    <li> <%= topping3 %> </li>
    <li> <p><a class="btn btn-primary btn-lg delete" href="#">Cancel Order</a></p> </li>
    </ul>
   `
}
