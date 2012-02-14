/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Mike
 */
@Embeddable
public class PreisPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "preis")
    private BigInteger preis;
    @Basic(optional = false)
    @Column(name = "artikelid")
    private int artikelid;

    public PreisPK() {
    }

    public PreisPK(BigInteger preis, int artikelid) {
        this.preis = preis;
        this.artikelid = artikelid;
    }

    public BigInteger getPreis() {
        return preis;
    }

    public void setPreis(BigInteger preis) {
        this.preis = preis;
    }

    public int getArtikelid() {
        return artikelid;
    }

    public void setArtikelid(int artikelid) {
        this.artikelid = artikelid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preis != null ? preis.hashCode() : 0);
        hash += (int) artikelid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreisPK)) {
            return false;
        }
        PreisPK other = (PreisPK) object;
        if ((this.preis == null && other.preis != null) || (this.preis != null && !this.preis.equals(other.preis))) {
            return false;
        }
        if (this.artikelid != other.artikelid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "admin.PreisPK[ preis=" + preis + ", artikelid=" + artikelid + " ]";
    }
    
}
