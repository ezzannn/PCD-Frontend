package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dell on 05/04/2015.
 */
@Entity
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, length = 30)
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Vendeur extends Model {
    @Id
    public Long id;
    public String nom;
    public String prenom;
    public String login;
    public Double salaire;
    public String passwd;
    public Long   tel;
    @Column(name = "dtype", insertable = false, updatable = false)
    public String dtype;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="vendeur")
    @JsonIgnore
    public List<Objectifs> objectifs;

    public static Finder<Long, Vendeur> find() {
        return new Finder<>(Long.class, Vendeur.class);
    }
}
