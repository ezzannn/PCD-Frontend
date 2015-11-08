package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Dell on 06/04/2015.
 */
@Embeddable
public class ObjectifsPK implements Serializable {
    @Column(name="produit_id")
    public Long produit_id;
    @Column(name="vendeur_id")
    public Long vendeur_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectifsPK)) return false;

        ObjectifsPK that = (ObjectifsPK) o;

        if (!produit_id.equals(that.produit_id)) return false;
        if (!vendeur_id.equals(that.vendeur_id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = produit_id.hashCode();
        result = 31 * result + vendeur_id.hashCode();
        return result;
    }
}
