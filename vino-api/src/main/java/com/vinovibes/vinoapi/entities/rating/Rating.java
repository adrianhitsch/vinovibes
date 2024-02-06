package com.vinovibes.vinoapi.entities.rating;

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

    @Column(name = "user_comment")
    private String userComment;

    private String vintage;

    private double price;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "wine_id")
    private Long wineId;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdateDate = new Date();
    }
}
