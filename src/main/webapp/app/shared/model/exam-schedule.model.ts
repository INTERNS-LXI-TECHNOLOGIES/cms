import { Moment } from 'moment';
import { IExam } from 'app/shared/model/exam.model';

export const enum Department {
  CSE = 'CSE',
  ME = 'ME',
  ECE = 'ECE',
  EEE = 'EEE',
  CE = 'CE',
  OTHER = 'OTHER'
}

export const enum Semester {
  S1 = 'S1',
  S2 = 'S2',
  S3 = 'S3',
  S4 = 'S4',
  S5 = 'S5',
  S6 = 'S6',
  S7 = 'S7',
  S8 = 'S8',
  NA = 'NA'
}

export interface IExamSchedule {
  id?: number;
  title?: string;
  startDate?: Moment;
  endDate?: Moment;
  active?: boolean;
  department?: Department;
  semester?: Semester;
  exams?: IExam[];
}

export const defaultValue: Readonly<IExamSchedule> = {
  active: false
};
