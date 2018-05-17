/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "FERIADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feriado.findAll", query = "SELECT f FROM Feriado f")
    , @NamedQuery(name = "Feriado.findByIdFeriado", query = "SELECT f FROM Feriado f WHERE f.idFeriado = :idFeriado")
    , @NamedQuery(name = "Feriado.findByDesignacao", query = "SELECT f FROM Feriado f WHERE f.designacao = :designacao")
    , @NamedQuery(name = "Feriado.findByDataFeriado", query = "SELECT f FROM Feriado f WHERE f.dataFeriado = :dataFeriado")
    , @NamedQuery(name = "Feriado.findByEstado", query = "SELECT f FROM Feriado f WHERE f.estado = :estado")})
public class Feriado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FERIADO")
    private Long idFeriado;
    @Column(name = "DESIGNACAO")
    private String designacao;
    @Basic(optional = false)
    @Column(name = "DATA_FERIADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFeriado;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;

    public Feriado() {
    }

    public Feriado(Long idFeriado) {
        this.idFeriado = idFeriado;
    }

    public Feriado(Long idFeriado, Date dataFeriado, short estado) {
        this.idFeriado = idFeriado;
        this.dataFeriado = dataFeriado;
        this.estado = estado;
    }

    public Long getIdFeriado() {
        return idFeriado;
    }

    public void setIdFeriado(Long idFeriado) {
        this.idFeriado = idFeriado;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Date getDataFeriado() {
        return dataFeriado;
    }

    public void setDataFeriado(Date dataFeriado) {
        this.dataFeriado = dataFeriado;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFeriado != null ? idFeriado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feriado)) {
            return false;
        }
        Feriado other = (Feriado) object;
        if ((this.idFeriado == null && other.idFeriado != null) || (this.idFeriado != null && !this.idFeriado.equals(other.idFeriado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Feriado[ idFeriado=" + idFeriado + " ]";
    }
    
}
