package com.vinovibes.vinoapi.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    private double rating;

    private String description;

    private String producer;

    private double restaurantPrice;

    private double storePrice;

    @Column(nullable = false)
    private boolean disabled;

    @Column(name = "creation_date",nullable = false)
    private Date creationDate;

    @Column(name = "last_update_date",nullable = false)
    private Date lastUpdateDate;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
        lastUpdateDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdateDate = new Date();
    }




    //
//    private String grape;
//
//    @ElementCollection
//    @CollectionTable(name = "wine_characteristics", joinColumns = @JoinColumn(name = "wine_id"))
//    @Column(name = "characteristic", nullable = false)
//    private List<String> characteristics;
//

    //
//    @Column(name = "created_by",nullable = false)
//    private Long createdBy;


    //
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_id"))
//    private User user;

    //    @Lob
//    @Column(name = "image", columnDefinition = "BLOB")
//    private byte[] image;
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private WineType type;
//

}
