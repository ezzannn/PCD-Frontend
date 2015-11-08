package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by Dell on 28/03/2015.
 */
@Entity
public class ChefServiceMagasin extends Model {
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

    public static Finder<Long, ChefServiceMagasin> find() {
        return new Finder<Long, ChefServiceMagasin>(Long.class, ChefServiceMagasin.class);
    }
}
