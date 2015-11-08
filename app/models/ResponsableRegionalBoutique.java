package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class ResponsableRegionalBoutique extends Model {
    @Id
    public Long id;
    public String nom;
    public String prenom;
    public String login;
    public Double salaire;
    public String passwd;
    public Long tel;
    public Double pond;
    public String zone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ChefDepartementDirect cdd;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="rgb")
    @JsonIgnore
    public List<Boutique> boutique;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="rgb")
    @JsonIgnore
    public List<Franchise> franchise;

    public static Finder<Long, ResponsableRegionalBoutique> find() {
        return new Finder<Long, ResponsableRegionalBoutique>(Long.class, ResponsableRegionalBoutique.class);
    }
}
