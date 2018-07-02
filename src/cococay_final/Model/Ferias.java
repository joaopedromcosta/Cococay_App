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
@Table(name = "FERIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ferias.findAll", query = "SELECT f FROM Ferias f")
    , @NamedQuery(name = "Ferias.findByIdFerias", query = "SELECT f FROM Ferias f WHERE f.idFerias = :idFerias")
    , @NamedQuery(name = "Ferias.findByDataInicio", query = "SELECT f FROM Ferias f WHERE f.dataInicio = :dataInicio")
    , @NamedQuery(name = "Ferias.findByDataFim", query = "SELECT f FROM Ferias f WHERE f.dataFim = :dataFim")
    , @NamedQuery(name = "Ferias.findByVerificadoRh", query = "SELECT f FROM Ferias f WHERE f.verificadoRh = :verificadoRh")
    , @NamedQuery(name = "Ferias.findByAprovado", query = "SELECT f FROM Ferias f WHERE f.aprovado = :aprovado")})
public class Ferias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FERIAS")
    private Long idFerias;
    @Basic(optional = false)
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Basic(optional = false)
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @Basic(optional = false)
    @Column(name = "VERIFICADO_RH")
    private short verificadoRh;
    @Basic(optional = false)
    @Column(name = "APROVADO")
    private short aprovado;
    @JoinColumn(name = "ID_FUNCIONARIO", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idFuncionario;

    public Ferias() {
    }

    public Ferias(Long idFerias) {
        this.idFerias = idFerias;
    }

    public Ferias(Long idFerias, Date dataInicio, Date dataFim, short verificadoRh, short aprovado) {
        this.idFerias = idFerias;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.verificadoRh = verificadoRh;
        this.aprovado = aprovado;
    }

    public Long getIdFerias() {
        return idFerias;
    }

    public void setIdFerias(Long idFerias) {
        this.idFerias = idFerias;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public boolean getVerificadoRh() {
        if(this.verificadoRh == 1)
            return true;
        else
            return false;
    }

    public void setVerificadoRh(short verificadoRh) {
        this.verificadoRh = verificadoRh;
    }

    public boolean getAprovado() {
        if(this.aprovado == 1)
            return true;
        else
            return false;
    }

    public void setAprovado(short aprovado) {
        this.aprovado = aprovado;
    }

    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFerias != null ? idFerias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ferias)) {
            return false;
        }
        Ferias other = (Ferias) object;
        if ((this.idFerias == null && other.idFerias != null) || (this.idFerias != null && !this.idFerias.equals(other.idFerias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Ferias[ idFerias=" + idFerias + " ]";
    }
    
}
