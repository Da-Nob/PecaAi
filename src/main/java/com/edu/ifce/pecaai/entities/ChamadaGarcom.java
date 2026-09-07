package com.edu.ifce.pecaai.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_chamada_garcom")
public class ChamadaGarcom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private Integer numeroMesa;

    @Column(nullable = false)
    private Boolean statusAtendido = false;

    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false)
    private Loja loja;

    

    public ChamadaGarcom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Boolean getStatusAtendido() {
        return statusAtendido;
    }

    public void setStatusAtendido(Boolean statusAtendido) {
        this.statusAtendido = statusAtendido;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public ChamadaGarcom(Long id, Integer numeroMesa, Boolean statusAtendido, Loja loja) {
        this.id = id;
        this.numeroMesa = numeroMesa;
        this.statusAtendido = statusAtendido;
        this.loja = loja;
    }


    
    
}
