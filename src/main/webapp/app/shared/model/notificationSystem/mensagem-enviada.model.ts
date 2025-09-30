import dayjs from 'dayjs';
import { ICliente } from 'app/shared/model/notificationSystem/cliente.model';
import { ITemplate } from 'app/shared/model/notificationSystem/template.model';

export interface IMensagemEnviada {
  id?: number;
  dataEnvio?: dayjs.Dayjs;
  status?: string | null;
  clienteId?: number;
  templateId?: number;
  cliente?: ICliente | null;
  template?: ITemplate | null;
}

export const defaultValue: Readonly<IMensagemEnviada> = {};
