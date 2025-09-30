export interface IAgendamento {
  id?: number;
  periodicidade?: string;
  horaEnvio?: string | null;
}

export const defaultValue: Readonly<IAgendamento> = {};
