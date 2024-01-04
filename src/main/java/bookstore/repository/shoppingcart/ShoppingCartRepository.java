package bookstore.repository.shoppingcart;

import bookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("""
    FROM ShoppingCart sc
    JOIN FETCH sc.user u
    JOIN FETCH sc.cartItems ci
    JOIN FETCH ci.book
    WHERE u.id =:id
            """)
    Optional<ShoppingCart> findShoppingCartByUserId(Long id);
}
