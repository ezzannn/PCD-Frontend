package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class Franchise extends Model {
    @Id
    public Long id;
    public String nom;
    public Integer nbConseillers;
    public Double pond;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ResponsableRegionalBoutique rgb;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="franchise")
    @JsonIgnore
    public List<Conseiller> conseiller;

    public static Finder<Long, Franchise> find() {
        return new Finder<Long, Franchise>(Long.class, Franchise.class);
    }
}
