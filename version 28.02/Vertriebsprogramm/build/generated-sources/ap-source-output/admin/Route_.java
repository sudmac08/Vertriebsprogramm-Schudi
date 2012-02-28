package admin;

import admin.Kunde;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-02-28T09:30:42")
@StaticMetamodel(Route.class)
public class Route_ { 

    public static volatile SingularAttribute<Route, Integer> routeid;
    public static volatile SingularAttribute<Route, Character> routename;
    public static volatile CollectionAttribute<Route, Kunde> kundeCollection;

}