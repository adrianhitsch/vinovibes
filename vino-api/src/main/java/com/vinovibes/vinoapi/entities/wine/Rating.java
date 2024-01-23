package com.vinovibes.vinoapi.entities.wine;

import com.vinovibes.vinoapi.entities.user.User;
import com.vinovibes.vinoapi.enums.PriceType;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;

    private String user_comment;

    private String vintage;

    private double price;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USER_RATING"))
    private User user;

    private Long wineId;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }
}
