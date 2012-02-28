package admin;

import admin.Bestellung;
import admin.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-28T09:30:42")
@StaticMetamodel(Kunde.class)
public class Kunde_ { 

    public static volatile SingularAttribute<Kunde, String> strasse;
    public static volatile SingularAttribute<Kunde, Integer> postleitzahl;
    public static volatile SingularAttribute<Kunde, String> vorname;
    public static volatile SingularAttribute<Kunde, String> zuname;
    public static volatile SingularAttribute<Kunde, Route> routeid;
    public static volatile CollectionAttribute<Kunde, Bestellung> bestellungCollection;
    public static volatile SingularAttribute<Kunde, String> ort;
    public static volatile SingularAttribute<Kunde, Integer> kundeid;

}