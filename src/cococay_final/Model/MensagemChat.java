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
@Table(name = "MENSAGEM_CHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MensagemChat.findAll", query = "SELECT m FROM MensagemChat m")
    , @NamedQuery(name = "MensagemChat.findByIdMensagem", query = "SELECT m FROM MensagemChat m WHERE m.idMensagem = :idMensagem")
    , @NamedQuery(name = "MensagemChat.findByMensagem", query = "SELECT m FROM MensagemChat m WHERE m.mensagem = :mensagem")
    , @NamedQuery(name = "MensagemChat.findByDataMensagem", query = "SELECT m FROM MensagemChat m WHERE m.dataMensagem = :dataMensagem")
    , @NamedQuery(name = "MensagemChat.findByLida", query = "SELECT m FROM MensagemChat m WHERE m.lida = :lida")})
public class MensagemChat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MENSAGEM")
    private Long idMensagem;
    @Basic(optional = false)
    @Column(name = "MENSAGEM")
    private String mensagem;
    @Basic(optional = false)
    @Column(name = "DATA_MENSAGEM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMensagem;
    @Basic(optional = false)
    @Column(name = "LIDA")
    private short lida;
    @JoinColumn(name = "ID_CHAT", referencedColumnName = "ID_CHAT")
    @ManyToOne
    private Chat idChat;
    @JoinColumn(name = "ID_DESTINATARIO", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idDestinatario;
    @JoinColumn(name = "ID_EMISSOR", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idEmissor;

    public MensagemChat() {
    }

    public MensagemChat(Long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public MensagemChat(Long idMensagem, String mensagem, Date dataMensagem, short lida) {
        this.idMensagem = idMensagem;
        this.mensagem = mensagem;
        this.dataMensagem = dataMensagem;
        this.lida = lida;
    }

    public Long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(Date dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public short getLida() {
        return lida;
    }

    public void setLida(short lida) {
        this.lida = lida;
    }

    public Chat getIdChat() {
        return idChat;
    }

    public void setIdChat(Chat idChat) {
        this.idChat = idChat;
    }

    public Funcionario getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Funcionario idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Funcionario getIdEmissor() {
        return idEmissor;
    }

    public void setIdEmissor(Funcionario idEmissor) {
        this.idEmissor = idEmissor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensagem != null ? idMensagem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MensagemChat)) {
            return false;
        }
        MensagemChat other = (MensagemChat) object;
        if ((this.idMensagem == null && other.idMensagem != null) || (this.idMensagem != null && !this.idMensagem.equals(other.idMensagem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.MensagemChat[ idMensagem=" + idMensagem + " ]";
    }
    
}
