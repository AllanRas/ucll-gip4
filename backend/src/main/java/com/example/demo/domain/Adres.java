package com.example.demo.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name="Adres", schema="esportmanagerdb")
public class Adres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "GEMEENTE")
    @ApiModelProperty(example = "Leuven")
    private String gemeente;

    @Column(name="STRAAT")
    @ApiModelProperty(example = "Leuvensebaan")
    private String straat;

    @Column(name = "HUISNuMMER")
    @ApiModelProperty(example = "43")
    private String huisnummer;

    @Column(name = "POSTCODE")
    @ApiModelProperty(example = "3000")
    private String postcode;

    public Adres(){}

    private Adres(Builder builder){
        setId(builder.id);
        setGemeente(builder.gemeente);
        setStraat(builder.straat);
        setHuisnummer(builder.huisnummer);
        setPostcode(builder.postcode);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }



    public static final class Builder {
        private long id;
        private String gemeente;
        private String straat;
        private String huisnummer;
        private String postcode;

        public Builder() {}

        public Builder(Adres copy){
            this.id = copy.getId();
            this.gemeente = copy.getGemeente();
            this.straat = copy.getStraat();
            this.huisnummer = copy.getHuisnummer();
            this.postcode = copy.getPostcode();
        }

        public Builder id(Long val){
            id = val;
            return this;
        }

        public Builder gemeente(String val){
            gemeente = val;
            return this;
        }

        public Builder straat(String val){
            straat = val;
            return this;
        }

        public Builder huisnummer(String val){
            huisnummer = val;
            return this;
        }

        public Builder postcode(String val){
            postcode = val;
            return this;
        }

        public Adres build(){ return new Adres(this);}
    }
}
