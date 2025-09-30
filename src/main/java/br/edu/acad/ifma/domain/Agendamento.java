package br.edu.acad.ifma.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Agendamento.
 */
@Entity
@Table(name = "agendamento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "periodicidade", nullable = false)
    private String periodicidade;

    @Column(name = "hora_envio")
    private String horaEnvio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agendamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodicidade() {
        return this.periodicidade;
    }

    public Agendamento periodicidade(String periodicidade) {
        this.setPeriodicidade(periodicidade);
        return this;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getHoraEnvio() {
        return this.horaEnvio;
    }

    public Agendamento horaEnvio(String horaEnvio) {
        this.setHoraEnvio(horaEnvio);
        return this;
    }

    public void setHoraEnvio(String horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agendamento)) {
            return false;
        }
        return getId() != null && getId().equals(((Agendamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agendamento{" +
            "id=" + getId() +
            ", periodicidade='" + getPeriodicidade() + "'" +
            ", horaEnvio='" + getHoraEnvio() + "'" +
            "}";
    }
}
