package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Dell on 05/04/2015.
 */
@Entity

public class Produit extends Model {
    @Id
    public Long id;
    public String type;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="produit")
    @JsonIgnore
    List<Objectifs> objectifs;

    public static Finder<Long, Produit> find() {
        return new Finder<Long, Produit>(Long.class, Produit.class);
    }
}
