import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './segmento.reducer';

export const SegmentoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const segmentoEntity = useAppSelector(state => state.notificationsystem.segmento.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="segmentoDetailsHeading">Segmento</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{segmentoEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{segmentoEntity.nome}</dd>
          <dt>
            <span id="descricao">Descricao</span>
          </dt>
          <dd>{segmentoEntity.descricao}</dd>
          <dt>
            <span id="canalEnvio">Canal Envio</span>
          </dt>
          <dd>{segmentoEntity.canalEnvio}</dd>
          <dt>
            <span id="agendamentoId">Agendamento Id</span>
          </dt>
          <dd>{segmentoEntity.agendamentoId}</dd>
          <dt>Agendamento</dt>
          <dd>{segmentoEntity.agendamento ? segmentoEntity.agendamento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notificationsystem/segmento" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notificationsystem/segmento/${segmentoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SegmentoDetail;
