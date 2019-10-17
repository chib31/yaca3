package com.example.server.entities;

import com.example.server.JsonObjects.PlayerBattingDetailsJson;
import com.example.server.utils.WicketType;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

import static com.example.server.utils.Utils.*;

@Entity
@Data
@Immutable
@Table(name = "player_batting_innings_details")
public class PlayerBattingDetails {

    @Id
    private Integer id;

    @Column(name = "scorecard_name")
    private String playerName;

    @Column
    private String opposition;

    @Column
    private Date date;

    @Column
    private Integer position;

    @Column
    private Integer deliveries;

    @Column
    private Integer runs;

    @Column
    private Integer fours;

    @Column
    private Integer sixes;

    @Column
    private String wicketType;

    @Column
    private Integer teamTotal;

    @Column
    private String result;

    public PlayerBattingDetailsJson getJson() {
      PlayerBattingDetailsJson jSon = new PlayerBattingDetailsJson();
      jSon.setId(this.id);
      jSon.setPlayerName(this.playerName);
      jSon.setOpposition(this.opposition);
      jSon.setDate(this.date);
      jSon.setPosition(this.position);
      jSon.setDeliveries(this.deliveries);
      jSon.setRuns(this.runs);
      jSon.setFours(this.fours);
      jSon.setSixes(this.sixes);
      jSon.setWicketType(getWicketTypeFromSql(this.wicketType).getType());
      jSon.setStrikeRate(calculateStrikeRate(this.runs, this.deliveries));
      jSon.setPercentOfTotal(calculatePercentOfTotal(this.runs, this.teamTotal));
      jSon.setFixture(String.format("%s vs %s (%s)", this.date.toString(), this.opposition,
          getResultFromSql(this.result).getValue()));
      return jSon;
    }
}