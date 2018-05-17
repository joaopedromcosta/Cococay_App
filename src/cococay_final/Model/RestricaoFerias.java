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
@Table(name = "RESTRICAO_FERIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RestricaoFerias.findAll", query = "SELECT r FROM RestricaoFerias r")
    , @NamedQuery(name = "RestricaoFerias.findByIdRestricao", query = "SELECT r FROM RestricaoFerias r WHERE r.idRestricao = :idRestricao")
    , @NamedQuery(name = "RestricaoFerias.findByDataInicio", query = "SELECT r FROM RestricaoFerias r WHERE r.dataInicio = :dataInicio")
    , @NamedQuery(name = "RestricaoFerias.findByDataFim", query = "SELECT r FROM RestricaoFerias r WHERE r.dataFim = :dataFim")
    , @NamedQuery(name = "RestricaoFerias.findByMotivo", query = "SELECT r FROM RestricaoFerias r WHERE r.motivo = :motivo")})
public class RestricaoFerias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RESTRICAO")
    private Long idRestricao;
    @Basic(optional = false)
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Basic(optional = false)
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @Basic(optional = false)
    @Column(name = "MOTIVO")
    private String motivo;
    @JoinColumn(name = "ID_FUNCIONARIO", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idFuncionario;

    public RestricaoFerias() {
    }

    public RestricaoFerias(Long idRestricao) {
        this.idRestricao = idRestricao;
    }

    public RestricaoFerias(Long idRestricao, Date dataInicio, Date dataFim, String motivo) {
        this.idRestricao = idRestricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.motivo = motivo;
    }

    public Long getIdRestricao() {
        return idRestricao;
    }

    public void setIdRestricao(Long idRestricao) {
        this.idRestricao = idRestricao;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
        hash += (idRestricao != null ? idRestricao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestricaoFerias)) {
            return false;
        }
        RestricaoFerias other = (RestricaoFerias) object;
        if ((this.idRestricao == null && other.idRestricao != null) || (this.idRestricao != null && !this.idRestricao.equals(other.idRestricao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.RestricaoFerias[ idRestricao=" + idRestricao + " ]";
    }
    
}
