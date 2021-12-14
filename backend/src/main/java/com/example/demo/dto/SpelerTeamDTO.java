package com.example.demo.dto;

import com.example.demo.domain.Speler;
import com.example.demo.domain.SpelerTeam;
import com.example.demo.domain.Team;

import java.io.Serializable;

public class SpelerTeamDTO implements Serializable {

        private long id;
        private long spelerid;
        private long teamid;
        private boolean reserve;

    public SpelerTeamDTO(Builder builder) {
        setId(builder.id);
        setReserve(builder.reserve);
        setSpelerid(builder.spelerid);
        setTeamid(builder.teamid);
    }

    public long getId() {
            return id;
        }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public long getSpelerid() {
        return spelerid;
    }

    public void setSpelerid(long spelerid) {
        this.spelerid = spelerid;
    }

    public long getTeamid() {
        return teamid;
    }

    public void setTeamid(long teamid) {
        this.teamid = teamid;
    }

    public static final class Builder {
            private long id;
            private long spelerid;
            private long teamid;
            private boolean reserve;

            public Builder(){}

            public Builder(SpelerTeamDTO copy){
                this.id = copy.getId();
                this.spelerid = copy.getSpelerid();
                this.teamid = copy.getTeamid();
                this.reserve = copy.isReserve();
            }

            public Builder id(long val){
                id = val;
                return this;
            }
            public Builder speler(long val){
                spelerid = val;
                return this;
            }
            public Builder team(long val){
                teamid = val;
                return this;
            }
            public Builder reserve(boolean val){
                reserve = val;
                return this;
            }

            public SpelerTeamDTO build(){return  new SpelerTeamDTO(this);}

        }
}
