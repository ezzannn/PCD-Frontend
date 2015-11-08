package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;


@SuppressWarnings("serial")@Entity
public class ChefDepartementDirect extends Model {
    @Id
    public Long id;
    public String nom;
    public String prenom;
    public String login;
    public Double salaire;
    public String passwd;
    public Long tel;
    public Double pond;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public DirecteurCommercial dc;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="cdd")
    @JsonIgnore
    public List<ResponsableRegionalBoutique> rgb;

    public static Finder<Long, ChefDepartementDirect> find() {
        return new Finder<Long, ChefDepartementDirect>(Long.class, ChefDepartementDirect.class);
    }
}
