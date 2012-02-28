/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mike
 */
@Entity
@Table(name = "preis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preis.findAll", query = "SELECT p FROM Preis p"),
    @NamedQuery(name = "Preis.findByPreis", query = "SELECT p FROM Preis p WHERE p.preisPK.preis = :preis"),
    @NamedQuery(name = "Preis.findByDatum", query = "SELECT p FROM Preis p WHERE p.datum = :datum"),
    @NamedQuery(name = "Preis.findByArtikelid", query = "SELECT p FROM Preis p WHERE p.preisPK.artikelid = :artikelid")})
public class Preis implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreisPK preisPK;
    @Basic(optional = false)
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @JoinColumn(name = "artikelid", referencedColumnName = "artikelid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikel artikel;

    public Preis() {
    }

    public Preis(PreisPK preisPK) {
        this.preisPK = preisPK;
    }

    public Preis(PreisPK preisPK, Date datum) {
        this.preisPK = preisPK;
        this.datum = datum;
    }

    public Preis(BigInteger preis, int artikelid) {
        this.preisPK = new PreisPK(preis, artikelid);
    }

    public PreisPK getPreisPK() {
        return preisPK;
    }

    public void setPreisPK(PreisPK preisPK) {
        this.preisPK = preisPK;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preisPK != null ? preisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preis)) {
            return false;
        }
        Preis other = (Preis) object;
        if ((this.preisPK == null && other.preisPK != null) || (this.preisPK != null && !this.preisPK.equals(other.preisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "admin.Preis[ preisPK=" + preisPK + " ]";
    }
    
}
