package admin;

import admin.Artikel;
import admin.BestellungPK;
import admin.Kunde;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-28T09:30:42")
@StaticMetamodel(Bestellung.class)
public class Bestellung_ { 

    public static volatile SingularAttribute<Bestellung, Integer> menge;
    public static volatile SingularAttribute<Bestellung, Kunde> kunde;
    public static volatile SingularAttribute<Bestellung, Artikel> artikel;
    public static volatile SingularAttribute<Bestellung, BestellungPK> bestellungPK;

}