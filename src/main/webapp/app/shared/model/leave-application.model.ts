import { Moment } from 'moment';
import { IAttatchment } from 'app/shared/model/attatchment.model';

export interface ILeaveApplication {
  id?: number;
  reason?: string;
  fromDate?: Moment;
  toDate?: Moment;
  attatchments?: IAttatchment[];
  appliedById?: number;
}

export const defaultValue: Readonly<ILeaveApplication> = {};
