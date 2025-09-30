package br.edu.acad.ifma.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Segmento.
 */
@Entity
@Table(name = "segmento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Segmento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "canal_envio", nullable = false)
    private String canalEnvio;

    @NotNull
    @Column(name = "agendamento_id", nullable = false)
    private Long agendamentoId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agendamento agendamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Segmento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Segmento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Segmento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCanalEnvio() {
        return this.canalEnvio;
    }

    public Segmento canalEnvio(String canalEnvio) {
        this.setCanalEnvio(canalEnvio);
        return this;
    }

    public void setCanalEnvio(String canalEnvio) {
        this.canalEnvio = canalEnvio;
    }

    public Long getAgendamentoId() {
        return this.agendamentoId;
    }

    public Segmento agendamentoId(Long agendamentoId) {
        this.setAgendamentoId(agendamentoId);
        return this;
    }

    public void setAgendamentoId(Long agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public Agendamento getAgendamento() {
        return this.agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Segmento agendamento(Agendamento agendamento) {
        this.setAgendamento(agendamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Segmento)) {
            return false;
        }
        return getId() != null && getId().equals(((Segmento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Segmento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", canalEnvio='" + getCanalEnvio() + "'" +
            ", agendamentoId=" + getAgendamentoId() +
            "}";
    }
}
