# Shopping cart

### Assumption : 
- User can add as many as products in the cart, no restriction
- Not covering Thread Safety scenarios like race condition over product availability etc.
- Products are already available in the inventory with available quantity

### Requirements
* User can search the product in available inventory - based on product name or tags
* User can add any product from their available products in cart
* User can add or change the quantity of any product in cart
* Display Product with total price including taxes
    - Currently, it is service tax - 12.5%
    - Corona tax, swatch bharat tax goes on, delivery charges (out of scope)
* Show total amount with only 2 decimal points, apply round of logic

### Validations 
* Cart is empty, show 0 Rs. as total Amount
