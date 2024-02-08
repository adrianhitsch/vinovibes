package com.vinovibes.vinoapi.entities.wine;

import com.vinovibes.vinoapi.enums.WineType;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for wine.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "wines")
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    private String region;

    private Integer vintage;

    private Double rating;

    private String description;

    private String producer;

    @Column(name = "restaurant_price")
    private Double restaurantPrice;

    @Column(name = "store_price")
    private Double storePrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WineType type;

    private String imageUrl;

    @Column(nullable = false)
    private boolean disabled;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "last_update_date", nullable = false)
    private Date lastUpdateDate;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
        lastUpdateDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdateDate = new Date();
    }
}
