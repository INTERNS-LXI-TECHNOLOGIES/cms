import { Moment } from 'moment';
import { IUserDomain } from 'app/shared/model/user-domain.model';

export const enum EventType {
  ARTS = 'ARTS',
  SPORTS = 'SPORTS',
  OTHER = 'OTHER'
}

export interface IEvent {
  id?: number;
  eventCategory?: EventType;
  eventName?: string;
  eventDate?: Moment;
  eventVenue?: string;
  active?: boolean;
  cordinators?: IUserDomain[];
}

export const defaultValue: Readonly<IEvent> = {
  active: false
};
