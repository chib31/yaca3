package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;


@SqlResultSetMapping(name="result", classes={
    @ConstructorResult(targetClass = PlayerBattingGrouped.class, columns = {
        @ColumnResult(name="id"),
        @ColumnResult(name="scorecard_name"),
        @ColumnResult(name="deliveries"),
        @ColumnResult(name="runs"),
        @ColumnResult(name="fours"),
        @ColumnResult(name="sixes")
    })
})
@Data
@Entity
public class PlayerBattingGrouped {

  @Id
  private Integer id;

  private String playername;

  private Integer deliveries;

  private Integer runs;

  private Integer fours;

  private Integer sixes;

  public PlayerBattingGrouped(Integer id,
                              String playername,
                              Integer deliveries,
                              Integer runs,
                              Integer fours,
                              Integer sixes) {
    this.id = id;
    this.playername = playername;
    this.deliveries = deliveries;
    this.runs = runs;
    this.fours = fours;
    this.sixes = sixes;
  }
}