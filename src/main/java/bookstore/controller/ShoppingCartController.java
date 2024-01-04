package bookstore.controller;

import bookstore.dto.shoppingcart.AddToCartRequestDto;
import bookstore.dto.shoppingcart.ShoppingCartDto;
import bookstore.dto.shoppingcart.UpdateCartItemDto;
import bookstore.model.User;
import bookstore.service.cartitem.CartItemService;
import bookstore.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @PostMapping
    @Operation(summary = "Add cart item",
            description = "Add new cart item and creates new cart if they don't exist")
    public ShoppingCartDto addToCart(@RequestBody @Valid AddToCartRequestDto requestDto,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToCart(requestDto, user.getId());
    }

    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "Retrieves current user's shopping cart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCartByUserId(user.getId());
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete cart item",
            description = "Safely deletes cart item from current shopping cart")
    public ShoppingCartDto removeBookFromShoppingCart(@PathVariable Long cartItemId,
                                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartItemService.deleteCartItemById(cartItemId, user.getId());
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update cart item",
            description = "Updates quantity in particular cart item")
    public ShoppingCartDto updateCartItemById(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return cartItemService.updateCartItemById(
                cartItemId, user.getId(), requestDto.getQuantity());
    }
}
