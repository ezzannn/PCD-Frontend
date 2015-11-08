package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class ResponsableRegionalDistribution extends Model {
    @Id
    public Long id;
    public String nom;
    public String prenom;
    public String passwd;
    public String login;
    public Double salaire;
    public Long tel;
    public Double pond;
    public String zone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public ChefServiceDistribution csd;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="rgd")
    @JsonIgnore
    public List<Distributeur> distributeur;

    public static Finder<Long, ResponsableRegionalDistribution> find() {
        return new Finder<Long, ResponsableRegionalDistribution>(Long.class, ResponsableRegionalDistribution.class);
    }
}
