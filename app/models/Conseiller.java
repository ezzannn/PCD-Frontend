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
@DiscriminatorValue("conseiller")
public class Conseiller extends Vendeur{
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Boutique boutique;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Franchise franchise;

    public static Finder<Long, Conseiller> find = new Finder<>(Long.class, Conseiller.class);
}
