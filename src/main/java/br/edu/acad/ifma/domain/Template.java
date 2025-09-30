package br.edu.acad.ifma.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "titulo_mensagem", nullable = false)
    private String tituloMensagem;

    @NotNull
    @Column(name = "corpo_mensagem", nullable = false)
    private String corpoMensagem;

    @Column(name = "tipo_mensagem")
    private String tipoMensagem;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Template id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Template nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTituloMensagem() {
        return this.tituloMensagem;
    }

    public Template tituloMensagem(String tituloMensagem) {
        this.setTituloMensagem(tituloMensagem);
        return this;
    }

    public void setTituloMensagem(String tituloMensagem) {
        this.tituloMensagem = tituloMensagem;
    }

    public String getCorpoMensagem() {
        return this.corpoMensagem;
    }

    public Template corpoMensagem(String corpoMensagem) {
        this.setCorpoMensagem(corpoMensagem);
        return this;
    }

    public void setCorpoMensagem(String corpoMensagem) {
        this.corpoMensagem = corpoMensagem;
    }

    public String getTipoMensagem() {
        return this.tipoMensagem;
    }

    public Template tipoMensagem(String tipoMensagem) {
        this.setTipoMensagem(tipoMensagem);
        return this;
    }

    public void setTipoMensagem(String tipoMensagem) {
        this.tipoMensagem = tipoMensagem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Template)) {
            return false;
        }
        return getId() != null && getId().equals(((Template) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tituloMensagem='" + getTituloMensagem() + "'" +
            ", corpoMensagem='" + getCorpoMensagem() + "'" +
            ", tipoMensagem='" + getTipoMensagem() + "'" +
            "}";
    }
}
