package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by Dell on 05/04/2015.
 */
@Entity
public class Objectifs extends Model {
    @EmbeddedId
    private ObjectifsPK id = new ObjectifsPK();
    public Long objectif;
    public Long realisation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "vendeur_id", referencedColumnName="id", insertable = false, updatable = false)
    public Vendeur vendeur;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "produit_id", referencedColumnName="id", insertable = false, updatable = false)
    public Produit produit;


    public static Finder<ObjectifsPK, Objectifs> find() {
        return new Finder<ObjectifsPK, Objectifs>(ObjectifsPK.class, Objectifs.class);
    }
}
