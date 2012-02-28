package admin;

import admin.Bestellung;
import admin.Preis;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-28T09:30:42")
@StaticMetamodel(Artikel.class)
public class Artikel_ { 

    public static volatile SingularAttribute<Artikel, String> bezeichnung;
    public static volatile CollectionAttribute<Artikel, Preis> preisCollection;
    public static volatile CollectionAttribute<Artikel, Bestellung> bestellungCollection;
    public static volatile SingularAttribute<Artikel, Integer> artikelid;

}