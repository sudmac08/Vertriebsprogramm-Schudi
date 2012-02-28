/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marco
 */
@Entity
@Table(name = "bestellung")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Bestellung.findAll", query = "SELECT b FROM Bestellung b"),
    @NamedQuery(name = "Bestellung.findByKundeid", query = "SELECT b FROM Bestellung b WHERE b.bestellungPK.kundeid = :kundeid"),
    @NamedQuery(name = "Bestellung.findByArtikelid", query = "SELECT b FROM Bestellung b WHERE b.bestellungPK.artikelid = :artikelid"),
    @NamedQuery(name = "Bestellung.findByDatum", query = "SELECT b FROM Bestellung b WHERE b.bestellungPK.datum = :datum"),
    @NamedQuery(name = "Bestellung.findByMenge", query = "SELECT b FROM Bestellung b WHERE b.menge = :menge")
})
public class Bestellung implements Serializable
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BestellungPK bestellungPK;
    @Basic(optional = false)
    @Column(name = "menge")
    private int menge;
    @JoinColumn(name = "kundeid", referencedColumnName = "kundeid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kunde kunde;
    @JoinColumn(name = "artikelid", referencedColumnName = "artikelid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikel artikel;

    public Bestellung()
    {
    }

    public Bestellung(BestellungPK bestellungPK)
    {
        this.bestellungPK = bestellungPK;
    }

    public Bestellung(BestellungPK bestellungPK, int menge)
    {
        this.bestellungPK = bestellungPK;
        this.menge = menge;
    }

    public Bestellung(int kundeid, int artikelid, Date datum)
    {
        this.bestellungPK = new BestellungPK(kundeid, artikelid, datum);
    }

    public BestellungPK getBestellungPK()
    {
        return bestellungPK;
    }

    public void setBestellungPK(BestellungPK bestellungPK)
    {
        this.bestellungPK = bestellungPK;
    }

    public int getMenge()
    {
        return menge;
    }

    public void setMenge(int menge)
    {
        this.menge = menge;
    }

    public Kunde getKunde()
    {
        return kunde;
    }

    public void setKunde(Kunde kunde)
    {
        this.kunde = kunde;
    }

    public Artikel getArtikel()
    {
        return artikel;
    }

    public void setArtikel(Artikel artikel)
    {
        this.artikel = artikel;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (bestellungPK != null ? bestellungPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestellung))
        {
            return false;
        }
        Bestellung other = (Bestellung) object;
        if ((this.bestellungPK == null && other.bestellungPK != null) || (this.bestellungPK != null && !this.bestellungPK.equals(other.bestellungPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "admin.Bestellung[ bestellungPK=" + bestellungPK + " ]";
    }
    
}
