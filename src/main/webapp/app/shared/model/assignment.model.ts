import { Moment } from 'moment';

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

export const enum Department {
  CSE = 'CSE',
  ME = 'ME',
  ECE = 'ECE',
  EEE = 'EEE',
  CE = 'CE',
  OTHER = 'OTHER'
}

export interface IAssignment {
  id?: number;
  submissionDate?: Moment;
  topic?: string;
  semester?: Semester;
  department?: Department;
}

export const defaultValue: Readonly<IAssignment> = {};
