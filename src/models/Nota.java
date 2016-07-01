package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo Attique Santana
 * @version 1.0 17/03/2015
 * @version 1.1 01/07/2016
 * Foi adicionado o suporte ao JPA
 */
@Entity
public class Nota implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Temporal(TemporalType.DATE)
    @Column
    private LocalDateTime dataOcorrencia;
    @Column(length = 2000)
    private String texto;
    @Column
    private String projeto;

    public Nota(Long id, String titulo, LocalDateTime dataOcorrencia, String texto, String projeto) {
        this.id = id;
        this.titulo = titulo;
        this.dataOcorrencia = dataOcorrencia;
        this.texto = texto;
        this.projeto = projeto;
    }

    public Nota() {
   
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }
    
    
}//fim classe
