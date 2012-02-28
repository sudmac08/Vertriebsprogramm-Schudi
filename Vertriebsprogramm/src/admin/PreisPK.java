/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Marco
 */
@Embeddable
public class PreisPK implements Serializable
{
    @Basic(optional = false)
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Basic(optional = false)
    @Column(name = "artikelid")
    private int artikelid;
    @Basic(optional = false)
    @Column(name = "preis")
    private float preis;

    public PreisPK()
    {
    }

    public PreisPK(Date datum, int artikelid, float preis)
    {
        this.datum = datum;
        this.artikelid = artikelid;
        this.preis = preis;
    }

    public Date getDatum()
    {
        return datum;
    }

    public void setDatum(Date datum)
    {
        this.datum = datum;
    }

    public int getArtikelid()
    {
        return artikelid;
    }

    public void setArtikelid(int artikelid)
    {
        this.artikelid = artikelid;
    }

    public float getPreis()
    {
        return preis;
    }

    public void setPreis(float preis)
    {
        this.preis = preis;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (datum != null ? datum.hashCode() : 0);
        hash += (int) artikelid;
        hash += (int) preis;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreisPK))
        {
            return false;
        }
        PreisPK other = (PreisPK) object;
        if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum)))
        {
            return false;
        }
        if (this.artikelid != other.artikelid)
        {
            return false;
        }
        if (this.preis != other.preis)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "admin.PreisPK[ datum=" + datum + ", artikelid=" + artikelid + ", preis=" + preis + " ]";
    }
    
}
