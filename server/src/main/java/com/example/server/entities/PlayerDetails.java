package com.example.server.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "player_details")
public class PlayerDetails {

    @Id
    private int id;

    @Column(name = "player_name")
    private String name;

    @Column(name="cap_number")
    private Integer capNumber;

    @Column(name = "shirt_number")
    private Integer shirtNumber;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapNumber() {
        return capNumber;
    }

    public Integer getShirtNumber() {
        return shirtNumber;
    }
}
