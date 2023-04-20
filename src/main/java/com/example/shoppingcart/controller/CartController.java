package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.CartItem;
import com.example.shoppingcart.model.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private Cart cart;                                              // reference til indkøbskurv session


    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        cart = (Cart) session.getAttribute("cart");                 // check om attributten ‘cart’ findes i sessions objektet

        if(cart == null){
            cart = new Cart();                                      // hvis ikke - opret en ny indkøbskurv (cart)
            session.setAttribute("cart", cart);                     // opret indkøbskurvattributten (cart) i session objektet
            session.setMaxInactiveInterval(30);                     // sæt sessionslevetiden til 30 sec til testformål
        }

        model.addAttribute("items", cart.getItems());               // tilføj attributterne ‘items’ til model objektet
        model.addAttribute("total", cart.getTotal());               // tilføj attributterne ‘total’ til model objektet

        return "cart";                                              // returner cart.html, der viser ui
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String name, @RequestParam double price, @RequestParam int quantity) {
        CartItem cartItem = new CartItem(name, price, quantity);    // opret nyt item

        cart.addItem(cartItem);                                     // tilføj item til cart

        return "redirect:/cart";                                    // redirect til /cart
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam int index) {
        cart.removeItem(index);                                     // slet item på ‘index’ i cart

        return "redirect:/cart";                                    // redirect til /cart
    }

    @PostMapping("/cart/empty")
    public String emptyCart(HttpSession session) {
        session.invalidate();                                       // fjern ‘cart’ attribute fra session objekt

        return "redirect:/cart";                                    // redirect til /cart
    }

}
