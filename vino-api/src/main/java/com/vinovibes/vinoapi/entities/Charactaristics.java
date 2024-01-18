//package com.vinovibes.vinoapi.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Data
//@Entity
//@Table(name = "wine_characteristics")
//public class Charactaristics {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    @Column(name = "characteristic", nullable = false)
//    private String characteristic;
//
//    @ManyToMany
//    @JoinTable(
//            name = "wine_characteristics",
//            joinColumns = @JoinColumn(name = "characteristic_id"),
//            inverseJoinColumns = @JoinColumn(name = "wine_id"))
//    private List<Wine> wines;
//}
