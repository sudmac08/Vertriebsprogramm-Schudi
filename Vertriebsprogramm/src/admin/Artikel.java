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
@Table(name = "artikel")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a"),
    @NamedQuery(name = "Artikel.findByArtikelid", query = "SELECT a FROM Artikel a WHERE a.artikelid = :artikelid"),
    @NamedQuery(name = "Artikel.findByBezeichnung", query = "SELECT a FROM Artikel a WHERE a.bezeichnung = :bezeichnung")
})
public class Artikel implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "artikelid")
    private Integer artikelid;
    @Basic(optional = false)
    @Column(name = "bezeichnung")
    private String bezeichnung;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikel")
    private Collection<Preis> preisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikel")
    private Collection<Bestellung> bestellungCollection;

    public Artikel()
    {
    }

    public Artikel(Integer artikelid)
    {
        this.artikelid = artikelid;
    }

    public Artikel(Integer artikelid, String bezeichnung)
    {
        this.artikelid = artikelid;
        this.bezeichnung = bezeichnung;
    }

    public Integer getArtikelid()
    {
        return artikelid;
    }

    public void setArtikelid(Integer artikelid)
    {
        this.artikelid = artikelid;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung)
    {
        this.bezeichnung = bezeichnung;
    }

    @XmlTransient
    public Collection<Preis> getPreisCollection()
    {
        return preisCollection;
    }

    public void setPreisCollection(Collection<Preis> preisCollection)
    {
        this.preisCollection = preisCollection;
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
        hash += (artikelid != null ? artikelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikel))
        {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.artikelid == null && other.artikelid != null) || (this.artikelid != null && !this.artikelid.equals(other.artikelid)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "admin.Artikel[ artikelid=" + artikelid + " ]";
    }
    
}
