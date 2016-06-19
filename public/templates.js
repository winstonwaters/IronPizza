var pizzaTmpls = {
  myPizza:`
    <ul class="final-order" data-id="<%= id %>">
    <li> <%= name %> </li>
    <li> <%= size %> </li>
    <li> <%= crust %> </li>
    <li> <%= sauce %> </li>
    <li> <%= meat %> </li>
    <li> <%= veggie %> </li>
    <li> <%= cheese %> </li>
    <li> <p><a class="btn btn-primary btn-lg delete" href="#">Cancel Order</a></p> </li>
    </ul>
   `
}
