package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@SuppressWarnings("serial")@Entity
public class ChefDepartementIndirect extends Model {
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
    @OneToOne(fetch = FetchType.LAZY, mappedBy="cdi")
    @JsonIgnore
    public ChefServiceDistribution csd;
    @OneToOne(fetch = FetchType.LAZY, mappedBy="cdi")
    @JsonIgnore
    public ChefServiceMagasin csm;
    @OneToOne(fetch = FetchType.LAZY, mappedBy="cdi")
    @JsonIgnore
    public ChefServicePdv csp;

    public static Finder<Long, ChefDepartementIndirect> find() {
        return new Finder<Long, ChefDepartementIndirect>(Long.class, ChefDepartementIndirect.class);
    }
}
