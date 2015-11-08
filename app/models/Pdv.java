package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
@DiscriminatorValue("pdv")
public class Pdv extends Vendeur{
    public Long id;
    public Float pond;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ResponsableRegionalPdv  rgp;

    public static Finder<Long, Pdv> find = new Finder<>(Long.class, Pdv.class);
}
