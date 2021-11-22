package com.example.demo.domain;

import liquibase.pro.packaged.B;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Speler", schema="esportmanagerdb")
public class Speler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ADRES_ID")
    private Adres adres;

    @Column(name = "ACTIEF")
    private boolean actief;

    @Column(name = "RESERVE")
    private boolean reserve;

    @Column(name = "GEBOORTEDATUM")
    private Date geboortedatum;

    public Speler(){}

    private Speler(Builder builder){
        setId(builder.id);
        setUser(builder.user);
        setAdres(builder.adres);
        setActief(builder.actief);
        setReserve(builder.reserve);
        setGeboortedatum(builder.geboortedatum);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public boolean isActief() {
        return actief;
    }

    public void setActief(boolean actief) {
        this.actief = actief;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public static final class Builder {
        private long id;
        private User user;
        private Adres adres;
        private boolean actief;
        private boolean reserve;
        private Date geboortedatum;

        public Builder(){}

        public Builder(Speler copy){
            this.id = copy.getId();
            this.user = copy.getUser();
            this.adres = copy.getAdres();
            this.actief = copy.isActief();
            this.reserve = copy.isReserve();
            this.geboortedatum = copy.getGeboortedatum();
        }

        public Builder user(User val){
            user = val;
            return this;
        }

        public Builder adres(Adres val){
            adres = val;
            return this;
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder actief(boolean val){
            actief = val;
            return this;
        }

        public Builder reserve(boolean val){
            reserve = val;
            return this;
        }

        public Builder geboortedatum(Date val){
            geboortedatum = val;
            return this;
        }

        public Speler build(){return new Speler(this);}
    }

}
