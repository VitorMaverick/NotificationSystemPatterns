import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cliente.reducer';

export const ClienteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clienteEntity = useAppSelector(state => state.notificationsystem.cliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clienteDetailsHeading">Cliente</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{clienteEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{clienteEntity.nome}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{clienteEntity.email}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{clienteEntity.telefone}</dd>
          <dt>
            <span id="segmentoId">Segmento Id</span>
          </dt>
          <dd>{clienteEntity.segmentoId}</dd>
          <dt>Segmento</dt>
          <dd>{clienteEntity.segmento ? clienteEntity.segmento.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notificationsystem/cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notificationsystem/cliente/${clienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClienteDetail;
