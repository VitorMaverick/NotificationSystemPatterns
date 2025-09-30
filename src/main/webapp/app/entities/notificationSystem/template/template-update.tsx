import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './template.reducer';

export const TemplateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const templateEntity = useAppSelector(state => state.notificationsystem.template.entity);
  const loading = useAppSelector(state => state.notificationsystem.template.loading);
  const updating = useAppSelector(state => state.notificationsystem.template.updating);
  const updateSuccess = useAppSelector(state => state.notificationsystem.template.updateSuccess);

  const handleClose = () => {
    navigate(`/notificationsystem/template${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...templateEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...templateEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notificationSystemApp.notificationSystemTemplate.home.createOrEditLabel" data-cy="TemplateCreateUpdateHeading">
            Criar ou editar Template
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="template-id" label="Código" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nome"
                id="template-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Titulo Mensagem"
                id="template-tituloMensagem"
                name="tituloMensagem"
                data-cy="tituloMensagem"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField
                label="Corpo Mensagem"
                id="template-corpoMensagem"
                name="corpoMensagem"
                data-cy="corpoMensagem"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField label="Tipo Mensagem" id="template-tipoMensagem" name="tipoMensagem" data-cy="tipoMensagem" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notificationsystem/template" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Voltar</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Salvar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TemplateUpdate;
