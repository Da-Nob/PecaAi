package com.edu.ifce.pecaai.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_loja")
public class Loja {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false, unique = true)
        private String cnpj;

        

        public Loja() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCnpj() {
                return cnpj;
        }

        public void setCnpj(String cnpj) {
                this.cnpj = cnpj;
        }

        public Loja(Long id, String nome, String cnpj) {
                this.id = id;
                this.nome = nome;
                this.cnpj = cnpj;
        }

                

        
}
