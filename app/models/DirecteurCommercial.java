package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@SuppressWarnings("serial")
@Entity
public class DirecteurCommercial extends Model {
    @Id
    public Long id;
    public String nom;
    public String prenom;
    public String login;
    public String passwd;
    public Long tel;
    @OneToOne(fetch = FetchType.LAZY, mappedBy="dc")
    @JsonIgnore
    public ChefDepartementDirect cdd;
    @OneToOne(fetch = FetchType.LAZY, mappedBy="dc")
    @JsonIgnore
    public ChefDepartementIndirect cdi;

    public static Finder<Long, DirecteurCommercial> find() {
        return new Finder<Long, DirecteurCommercial>(Long.class, DirecteurCommercial.class);
    }
}