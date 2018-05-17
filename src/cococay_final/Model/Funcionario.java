/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "FUNCIONARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f")
    , @NamedQuery(name = "Funcionario.findByIdFuncionario", query = "SELECT f FROM Funcionario f WHERE f.idFuncionario = :idFuncionario")
    , @NamedQuery(name = "Funcionario.findByNome", query = "SELECT f FROM Funcionario f WHERE f.nome = :nome")
    , @NamedQuery(name = "Funcionario.findByMorada", query = "SELECT f FROM Funcionario f WHERE f.morada = :morada")
    , @NamedQuery(name = "Funcionario.findByCodigoPostal", query = "SELECT f FROM Funcionario f WHERE f.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "Funcionario.findByDataNascimento", query = "SELECT f FROM Funcionario f WHERE f.dataNascimento = :dataNascimento")
    , @NamedQuery(name = "Funcionario.findByNumeroCartaoCidadao", query = "SELECT f FROM Funcionario f WHERE f.numeroCartaoCidadao = :numeroCartaoCidadao")
    , @NamedQuery(name = "Funcionario.findByNumeroSegurancaSocial", query = "SELECT f FROM Funcionario f WHERE f.numeroSegurancaSocial = :numeroSegurancaSocial")
    , @NamedQuery(name = "Funcionario.findByNumeroContribuinte", query = "SELECT f FROM Funcionario f WHERE f.numeroContribuinte = :numeroContribuinte")
    , @NamedQuery(name = "Funcionario.findByIban", query = "SELECT f FROM Funcionario f WHERE f.iban = :iban")
    , @NamedQuery(name = "Funcionario.findByNumeroDiasFeriasTotal", query = "SELECT f FROM Funcionario f WHERE f.numeroDiasFeriasTotal = :numeroDiasFeriasTotal")
    , @NamedQuery(name = "Funcionario.findByNumeroDiasFeriasAtraso", query = "SELECT f FROM Funcionario f WHERE f.numeroDiasFeriasAtraso = :numeroDiasFeriasAtraso")
    , @NamedQuery(name = "Funcionario.findByDataContrato", query = "SELECT f FROM Funcionario f WHERE f.dataContrato = :dataContrato")
    , @NamedQuery(name = "Funcionario.findBySalario", query = "SELECT f FROM Funcionario f WHERE f.salario = :salario")
    , @NamedQuery(name = "Funcionario.findByEstado", query = "SELECT f FROM Funcionario f WHERE f.estado = :estado")})
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FUNCIONARIO")
    private Long idFuncionario;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "MORADA")
    private String morada;
    @Basic(optional = false)
    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;
    @Column(name = "DATA_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
    @Basic(optional = false)
    @Column(name = "NUMERO_CARTAO_CIDADAO")
    private String numeroCartaoCidadao;
    @Basic(optional = false)
    @Column(name = "NUMERO_SEGURANCA_SOCIAL")
    private String numeroSegurancaSocial;
    @Basic(optional = false)
    @Column(name = "NUMERO_CONTRIBUINTE")
    private String numeroContribuinte;
    @Basic(optional = false)
    @Column(name = "IBAN")
    private String iban;
    @Column(name = "NUMERO_DIAS_FERIAS_TOTAL")
    private Short numeroDiasFeriasTotal;
    @Column(name = "NUMERO_DIAS_FERIAS_ATRASO")
    private Short numeroDiasFeriasAtraso;
    @Column(name = "DATA_CONTRATO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataContrato;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "SALARIO")
    private BigDecimal salario;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @ManyToMany(mappedBy = "funcionarioCollection")
    private Collection<Chat> chatCollection;
    @ManyToMany(mappedBy = "funcionarioCollection")
    private Collection<Equipa> equipaCollection;
    @OneToMany(mappedBy = "idDestinatario")
    private Collection<MensagemChat> mensagemChatCollection;
    @OneToMany(mappedBy = "idEmissor")
    private Collection<MensagemChat> mensagemChatCollection1;
    @OneToMany(mappedBy = "idFuncionarioFalta")
    private Collection<Falta> faltaCollection;
    @OneToMany(mappedBy = "idFuncionarioRegista")
    private Collection<Falta> faltaCollection1;
    @JoinColumn(name = "ID_FUNCAO_TRABALHO", referencedColumnName = "ID_FUNCAO_TRABALHO")
    @ManyToOne
    private FuncaoTrabalho idFuncaoTrabalho;
    @OneToMany(mappedBy = "idFuncionario")
    private Collection<Login> loginCollection;
    @OneToMany(mappedBy = "idFuncionario")
    private Collection<Ferias> feriasCollection;
    @OneToMany(mappedBy = "idFuncionario")
    private Collection<RestricaoFerias> restricaoFeriasCollection;

    public Funcionario() {
    }

    public Funcionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Funcionario(Long idFuncionario, String nome, String morada, String codigoPostal, String numeroCartaoCidadao, String numeroSegurancaSocial, String numeroContribuinte, String iban, BigDecimal salario, short estado) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.morada = morada;
        this.codigoPostal = codigoPostal;
        this.numeroCartaoCidadao = numeroCartaoCidadao;
        this.numeroSegurancaSocial = numeroSegurancaSocial;
        this.numeroContribuinte = numeroContribuinte;
        this.iban = iban;
        this.salario = salario;
        this.estado = estado;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumeroCartaoCidadao() {
        return numeroCartaoCidadao;
    }

    public void setNumeroCartaoCidadao(String numeroCartaoCidadao) {
        this.numeroCartaoCidadao = numeroCartaoCidadao;
    }

    public String getNumeroSegurancaSocial() {
        return numeroSegurancaSocial;
    }

    public void setNumeroSegurancaSocial(String numeroSegurancaSocial) {
        this.numeroSegurancaSocial = numeroSegurancaSocial;
    }

    public String getNumeroContribuinte() {
        return numeroContribuinte;
    }

    public void setNumeroContribuinte(String numeroContribuinte) {
        this.numeroContribuinte = numeroContribuinte;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Short getNumeroDiasFeriasTotal() {
        return numeroDiasFeriasTotal;
    }

    public void setNumeroDiasFeriasTotal(Short numeroDiasFeriasTotal) {
        this.numeroDiasFeriasTotal = numeroDiasFeriasTotal;
    }

    public Short getNumeroDiasFeriasAtraso() {
        return numeroDiasFeriasAtraso;
    }

    public void setNumeroDiasFeriasAtraso(Short numeroDiasFeriasAtraso) {
        this.numeroDiasFeriasAtraso = numeroDiasFeriasAtraso;
    }

    public Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Date dataContrato) {
        this.dataContrato = dataContrato;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Chat> getChatCollection() {
        return chatCollection;
    }

    public void setChatCollection(Collection<Chat> chatCollection) {
        this.chatCollection = chatCollection;
    }

    @XmlTransient
    public Collection<Equipa> getEquipaCollection() {
        return equipaCollection;
    }

    public void setEquipaCollection(Collection<Equipa> equipaCollection) {
        this.equipaCollection = equipaCollection;
    }

    @XmlTransient
    public Collection<MensagemChat> getMensagemChatCollection() {
        return mensagemChatCollection;
    }

    public void setMensagemChatCollection(Collection<MensagemChat> mensagemChatCollection) {
        this.mensagemChatCollection = mensagemChatCollection;
    }

    @XmlTransient
    public Collection<MensagemChat> getMensagemChatCollection1() {
        return mensagemChatCollection1;
    }

    public void setMensagemChatCollection1(Collection<MensagemChat> mensagemChatCollection1) {
        this.mensagemChatCollection1 = mensagemChatCollection1;
    }

    @XmlTransient
    public Collection<Falta> getFaltaCollection() {
        return faltaCollection;
    }

    public void setFaltaCollection(Collection<Falta> faltaCollection) {
        this.faltaCollection = faltaCollection;
    }

    @XmlTransient
    public Collection<Falta> getFaltaCollection1() {
        return faltaCollection1;
    }

    public void setFaltaCollection1(Collection<Falta> faltaCollection1) {
        this.faltaCollection1 = faltaCollection1;
    }

    public FuncaoTrabalho getIdFuncaoTrabalho() {
        return idFuncaoTrabalho;
    }

    public void setIdFuncaoTrabalho(FuncaoTrabalho idFuncaoTrabalho) {
        this.idFuncaoTrabalho = idFuncaoTrabalho;
    }

    @XmlTransient
    public Collection<Login> getLoginCollection() {
        return loginCollection;
    }

    public void setLoginCollection(Collection<Login> loginCollection) {
        this.loginCollection = loginCollection;
    }

    @XmlTransient
    public Collection<Ferias> getFeriasCollection() {
        return feriasCollection;
    }

    public void setFeriasCollection(Collection<Ferias> feriasCollection) {
        this.feriasCollection = feriasCollection;
    }

    @XmlTransient
    public Collection<RestricaoFerias> getRestricaoFeriasCollection() {
        return restricaoFeriasCollection;
    }

    public void setRestricaoFeriasCollection(Collection<RestricaoFerias> restricaoFeriasCollection) {
        this.restricaoFeriasCollection = restricaoFeriasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFuncionario != null ? idFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.idFuncionario == null && other.idFuncionario != null) || (this.idFuncionario != null && !this.idFuncionario.equals(other.idFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Funcionario[ idFuncionario=" + idFuncionario + " ]";
    }
    
}
