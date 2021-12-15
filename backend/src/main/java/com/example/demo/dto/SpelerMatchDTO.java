package com.example.demo.dto;

import java.io.Serializable;

public class SpelerMatchDTO implements Serializable {
    private long id;
    private long spelerid;
    private long matchid;

    private SpelerMatchDTO(Builder builder) {
        setId(builder.id);
        setSpelerid(builder.spelerid);
        setMatchid(builder.matchid);
    }

    public SpelerMatchDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpelerid() {
        return spelerid;
    }

    public void setSpelerid(long spelerid) {
        this.spelerid = spelerid;
    }

    public long getMatchid() {
        return matchid;
    }

    public void setMatchid(long matchid) {
        this.matchid = matchid;
    }

    @Override
    public String toString() {
        return "SpelerMatchDTO{" +
                "id=" + id +
                ", spelerid=" + spelerid +
                ", matchid=" + matchid +
                '}';
    }

    public static final class Builder {
        private long id;
        private long spelerid;
        private long matchid;

        public Builder(){

        }

        public Builder(SpelerMatchDTO copy){
            this.id = copy.getId();
            this.spelerid = copy.getSpelerid();
            this.matchid = copy.getMatchid();
        }

        public Builder id(long val){
            id = val;
            return this;
        }
        public Builder speler(long val){
            spelerid = val;
            return this;
        }
        public Builder match(long val){
            matchid = val;
            return this;
        }

        public SpelerMatchDTO build(){return new SpelerMatchDTO(this);}

    }
}
