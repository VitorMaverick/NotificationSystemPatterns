import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { deleteEntity, getEntity } from './mensagem-enviada.reducer';

export const MensagemEnviadaDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const mensagemEnviadaEntity = useAppSelector(state => state.notificationsystem.mensagemEnviada.entity);
  const updateSuccess = useAppSelector(state => state.notificationsystem.mensagemEnviada.updateSuccess);

  const handleClose = () => {
    navigate(`/notificationsystem/mensagem-enviada${pageLocation.search}`);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(mensagemEnviadaEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="mensagemEnviadaDeleteDialogHeading">
        Confirme a exclusão
      </ModalHeader>
      <ModalBody id="notificationSystemApp.notificationSystemMensagemEnviada.delete.question">
        Tem certeza de que deseja excluir Mensagem Enviada {mensagemEnviadaEntity.id}?
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancelar
        </Button>
        <Button id="jhi-confirm-delete-mensagemEnviada" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Excluir
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default MensagemEnviadaDeleteDialog;
