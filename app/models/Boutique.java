package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class Boutique extends Model {
    @Id
    public Long id;
    public String nom;
    public Integer nbConseillers;
    public Double pond;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ResponsableRegionalBoutique rgb;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="boutique")
    @JsonIgnore
    public List<Conseiller> conseiller;


    public static Finder<Long, Boutique> find() {
        return new Finder<Long, Boutique>(Long.class, Boutique.class);
    }
}
