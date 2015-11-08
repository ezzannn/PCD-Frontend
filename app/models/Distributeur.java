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
@DiscriminatorValue("distributeur")
public class Distributeur  extends Vendeur{
    public Double pond;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ResponsableRegionalDistribution rgd;

    public static Finder<Long, Distributeur> find = new Finder<>(Long.class, Distributeur.class);
}
