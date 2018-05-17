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
@Table(name = "OBJETIVO_MENSAGEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObjetivoMensagem.findAll", query = "SELECT o FROM ObjetivoMensagem o")
    , @NamedQuery(name = "ObjetivoMensagem.findByIdObjetivo", query = "SELECT o FROM ObjetivoMensagem o WHERE o.idObjetivo = :idObjetivo")
    , @NamedQuery(name = "ObjetivoMensagem.findByDataMostragem", query = "SELECT o FROM ObjetivoMensagem o WHERE o.dataMostragem = :dataMostragem")
    , @NamedQuery(name = "ObjetivoMensagem.findByMensagem", query = "SELECT o FROM ObjetivoMensagem o WHERE o.mensagem = :mensagem")})
public class ObjetivoMensagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBJETIVO")
    private Long idObjetivo;
    @Basic(optional = false)
    @Column(name = "DATA_MOSTRAGEM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMostragem;
    @Basic(optional = false)
    @Column(name = "MENSAGEM")
    private String mensagem;

    public ObjetivoMensagem() {
    }

    public ObjetivoMensagem(Long idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public ObjetivoMensagem(Long idObjetivo, Date dataMostragem, String mensagem) {
        this.idObjetivo = idObjetivo;
        this.dataMostragem = dataMostragem;
        this.mensagem = mensagem;
    }

    public Long getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Long idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public Date getDataMostragem() {
        return dataMostragem;
    }

    public void setDataMostragem(Date dataMostragem) {
        this.dataMostragem = dataMostragem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObjetivo != null ? idObjetivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjetivoMensagem)) {
            return false;
        }
        ObjetivoMensagem other = (ObjetivoMensagem) object;
        if ((this.idObjetivo == null && other.idObjetivo != null) || (this.idObjetivo != null && !this.idObjetivo.equals(other.idObjetivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.ObjetivoMensagem[ idObjetivo=" + idObjetivo + " ]";
    }
    
}
