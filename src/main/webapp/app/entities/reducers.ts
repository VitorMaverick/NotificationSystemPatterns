import cliente from 'app/entities/notificationSystem/cliente/cliente.reducer';
import segmento from 'app/entities/notificationSystem/segmento/segmento.reducer';
import agendamento from 'app/entities/notificationSystem/agendamento/agendamento.reducer';
import template from 'app/entities/notificationSystem/template/template.reducer';
import mensagemEnviada from 'app/entities/notificationSystem/mensagem-enviada/mensagem-enviada.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  cliente,
  segmento,
  agendamento,
  template,
  mensagemEnviada,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
