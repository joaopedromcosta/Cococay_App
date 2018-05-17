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
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "FALTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Falta.findAll", query = "SELECT f FROM Falta f")
    , @NamedQuery(name = "Falta.findByIdFalta", query = "SELECT f FROM Falta f WHERE f.idFalta = :idFalta")
    , @NamedQuery(name = "Falta.findByDataFalta", query = "SELECT f FROM Falta f WHERE f.dataFalta = :dataFalta")
    , @NamedQuery(name = "Falta.findByDescricao", query = "SELECT f FROM Falta f WHERE f.descricao = :descricao")
    , @NamedQuery(name = "Falta.findByJustificada", query = "SELECT f FROM Falta f WHERE f.justificada = :justificada")})
public class Falta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FALTA")
    private Long idFalta;
    @Basic(optional = false)
    @Column(name = "DATA_FALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFalta;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "JUSTIFICADA")
    private short justificada;
    @JoinColumn(name = "ID_FUNCIONARIO_FALTA", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idFuncionarioFalta;
    @JoinColumn(name = "ID_FUNCIONARIO_REGISTA", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idFuncionarioRegista;

    public Falta() {
    }

    public Falta(Long idFalta) {
        this.idFalta = idFalta;
    }

    public Falta(Long idFalta, Date dataFalta, short justificada) {
        this.idFalta = idFalta;
        this.dataFalta = dataFalta;
        this.justificada = justificada;
    }

    public Long getIdFalta() {
        return idFalta;
    }

    public void setIdFalta(Long idFalta) {
        this.idFalta = idFalta;
    }

    public Date getDataFalta() {
        return dataFalta;
    }

    public void setDataFalta(Date dataFalta) {
        this.dataFalta = dataFalta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public short getJustificada() {
        return justificada;
    }

    public void setJustificada(short justificada) {
        this.justificada = justificada;
    }

    public Funcionario getIdFuncionarioFalta() {
        return idFuncionarioFalta;
    }

    public void setIdFuncionarioFalta(Funcionario idFuncionarioFalta) {
        this.idFuncionarioFalta = idFuncionarioFalta;
    }

    public Funcionario getIdFuncionarioRegista() {
        return idFuncionarioRegista;
    }

    public void setIdFuncionarioRegista(Funcionario idFuncionarioRegista) {
        this.idFuncionarioRegista = idFuncionarioRegista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFalta != null ? idFalta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Falta)) {
            return false;
        }
        Falta other = (Falta) object;
        if ((this.idFalta == null && other.idFalta != null) || (this.idFalta != null && !this.idFalta.equals(other.idFalta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Falta[ idFalta=" + idFalta + " ]";
    }
    
}
