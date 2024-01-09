package bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Status status;
    @Column(nullable = false)
    private BigDecimal total;
    @CreationTimestamp
    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;
    @Column(nullable = false, name = "shipping_address")
    private String shippingAddress;
    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();
    @Column(nullable = false)
    private boolean isDeleted = false;

    public enum Status {
        PENDING,
        COMPLETED,
        DELIVERED,
        CANCELED
    }
}
