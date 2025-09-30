import React, { useEffect } from 'react';
// eslint-disable-line

import MenuItem from 'app/shared/layout/menus/menu-item'; // eslint-disable-line
import { addTranslationSourcePrefix } from 'app/shared/reducers/locale';
import { useAppDispatch, useAppSelector } from 'app/config/store';

const EntitiesMenu = () => {
  const lastChange = useAppSelector(state => state.locale.lastChange);
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(addTranslationSourcePrefix('services/notificationsystem/'));
  }, [lastChange]);

  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/notificationsystem/cliente">
        Cliente
      </MenuItem>
      <MenuItem icon="asterisk" to="/notificationsystem/segmento">
        Segmento
      </MenuItem>
      <MenuItem icon="asterisk" to="/notificationsystem/agendamento">
        Agendamento
      </MenuItem>
      <MenuItem icon="asterisk" to="/notificationsystem/template">
        Template
      </MenuItem>
      <MenuItem icon="asterisk" to="/notificationsystem/mensagem-enviada">
        Mensagem Enviada
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
