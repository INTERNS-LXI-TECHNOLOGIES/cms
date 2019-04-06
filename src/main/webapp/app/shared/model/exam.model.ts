import { Moment } from 'moment';
import { IExamHall } from 'app/shared/model/exam-hall.model';

export interface IExam {
  id?: number;
  examDate?: Moment;
  startingTime?: string;
  duration?: string;
  examScheduleId?: number;
  subjectId?: number;
  halls?: IExamHall[];
}

export const defaultValue: Readonly<IExam> = {};
