package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class ChefServicePdv extends Model {
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
    public ChefDepartementIndirect cdi;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="csp")
    @JsonIgnore
    public List<ResponsableRegionalPdv> rgp;

    public static Finder<Long, ChefServicePdv> find() {
        return new Finder<Long, ChefServicePdv>(Long.class, ChefServicePdv.class);
    }
}
