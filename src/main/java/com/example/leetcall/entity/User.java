package com.example.leetcall.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "leetcallUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    // leetcode username
    private String username;

    private String password;

}
