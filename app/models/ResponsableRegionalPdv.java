package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class ResponsableRegionalPdv extends Model {
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
    public ChefServicePdv csp;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="rgp")
    @JsonIgnore
    public List<Pdv> pdv;

    public static Finder<Long, ResponsableRegionalPdv> find() {
        return new Finder<Long, ResponsableRegionalPdv>(Long.class, ResponsableRegionalPdv.class);
    }
}
