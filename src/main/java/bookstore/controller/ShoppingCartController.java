package bookstore.controller;

import bookstore.dto.shoppingcart.AddToCartRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.model.User;
import bookstore.service.cartitem.CartItemService;
import bookstore.service.shoppingcart.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PostMapping
    public ShoppingCartDto addToCart(@RequestBody @Valid AddToCartRequestDto requestDto,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(requestDto, user.getId());
    }

    @GetMapping
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(user.getId());

    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public ShoppingCartDto removeBookFromShoppingCart(@PathVariable Long cartItemId) {
        return cartItemService.deleteBookById(cartItemId);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartDto updateBookQuantity(
            @Valid @RequestBody AddToCartRequestDto requestDto,
            Authentication authentication) {
        return null;
    }
}
