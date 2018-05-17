/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final;

import cococay_final.Model.Funcionario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author joaocosta-ipvc
 */
public class Repositorio {
    
    EntityManager em;
    EntityManagerFactory emf;
    
    List<Funcionario> funcionarios;

    public Repositorio() {
        funcionarios = new ArrayList<>();
    }
    
    
    
    
}
