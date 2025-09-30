package br.edu.acad.ifma.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.edu.acad.ifma.domain.Template} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String tituloMensagem;

    @NotNull
    private String corpoMensagem;

    private String tipoMensagem;

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

    public String getTituloMensagem() {
        return tituloMensagem;
    }

    public void setTituloMensagem(String tituloMensagem) {
        this.tituloMensagem = tituloMensagem;
    }

    public String getCorpoMensagem() {
        return corpoMensagem;
    }

    public void setCorpoMensagem(String corpoMensagem) {
        this.corpoMensagem = corpoMensagem;
    }

    public String getTipoMensagem() {
        return tipoMensagem;
    }

    public void setTipoMensagem(String tipoMensagem) {
        this.tipoMensagem = tipoMensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateDTO)) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tituloMensagem='" + getTituloMensagem() + "'" +
            ", corpoMensagem='" + getCorpoMensagem() + "'" +
            ", tipoMensagem='" + getTipoMensagem() + "'" +
            "}";
    }
}
