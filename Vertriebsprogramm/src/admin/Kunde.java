/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marco
 */
@Entity
@Table(name = "kunde")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Kunde.findAll", query = "SELECT k FROM Kunde k"),
    @NamedQuery(name = "Kunde.findByKundeid", query = "SELECT k FROM Kunde k WHERE k.kundeid = :kundeid"),
    @NamedQuery(name = "Kunde.findByVorname", query = "SELECT k FROM Kunde k WHERE k.vorname = :vorname"),
    @NamedQuery(name = "Kunde.findByZuname", query = "SELECT k FROM Kunde k WHERE k.zuname = :zuname"),
    @NamedQuery(name = "Kunde.findByStrasse", query = "SELECT k FROM Kunde k WHERE k.strasse = :strasse"),
    @NamedQuery(name = "Kunde.findByPostleitzahl", query = "SELECT k FROM Kunde k WHERE k.postleitzahl = :postleitzahl"),
    @NamedQuery(name = "Kunde.findByOrt", query = "SELECT k FROM Kunde k WHERE k.ort = :ort")
})
public class Kunde implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kundeid")
    private Integer kundeid;
    @Basic(optional = false)
    @Column(name = "vorname")
    private String vorname;
    @Basic(optional = false)
    @Column(name = "zuname")
    private String zuname;
    @Basic(optional = false)
    @Column(name = "strasse")
    private String strasse;
    @Basic(optional = false)
    @Column(name = "postleitzahl")
    private int postleitzahl;
    @Basic(optional = false)
    @Column(name = "ort")
    private String ort;
    @JoinColumn(name = "routeid", referencedColumnName = "routeid")
    @ManyToOne(optional = false)
    private Route routeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kunde")
    private Collection<Bestellung> bestellungCollection;

    public Kunde()
    {
    }

    public Kunde(Integer kundeid)
    {
        this.kundeid = kundeid;
    }

    public Kunde(Integer kundeid, String vorname, String zuname, String strasse, int postleitzahl, String ort)
    {
        this.kundeid = kundeid;
        this.vorname = vorname;
        this.zuname = zuname;
        this.strasse = strasse;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
    }

    public Integer getKundeid()
    {
        return kundeid;
    }

    public void setKundeid(Integer kundeid)
    {
        this.kundeid = kundeid;
    }

    public String getVorname()
    {
        return vorname;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    public String getZuname()
    {
        return zuname;
    }

    public void setZuname(String zuname)
    {
        this.zuname = zuname;
    }

    public String getStrasse()
    {
        return strasse;
    }

    public void setStrasse(String strasse)
    {
        this.strasse = strasse;
    }

    public int getPostleitzahl()
    {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl)
    {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt()
    {
        return ort;
    }

    public void setOrt(String ort)
    {
        this.ort = ort;
    }

    public Route getRouteid()
    {
        return routeid;
    }

    public void setRouteid(Route routeid)
    {
        this.routeid = routeid;
    }

    @XmlTransient
    public Collection<Bestellung> getBestellungCollection()
    {
        return bestellungCollection;
    }

    public void setBestellungCollection(Collection<Bestellung> bestellungCollection)
    {
        this.bestellungCollection = bestellungCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (kundeid != null ? kundeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kunde))
        {
            return false;
        }
        Kunde other = (Kunde) object;
        if ((this.kundeid == null && other.kundeid != null) || (this.kundeid != null && !this.kundeid.equals(other.kundeid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "admin.Kunde[ kundeid=" + kundeid + " ]";
    }
    
}
