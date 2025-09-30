package br.edu.acad.ifma.repository;

import br.edu.acad.ifma.domain.MensagemEnviada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MensagemEnviada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MensagemEnviadaRepository extends JpaRepository<MensagemEnviada, Long> {}
