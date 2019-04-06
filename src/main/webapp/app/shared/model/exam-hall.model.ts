import { IExam } from 'app/shared/model/exam.model';

export interface IExamHall {
  id?: number;
  hallNumber?: number;
  batch?: string;
  rollNumFrom?: number;
  rollNumTo?: number;
  invigialtor?: string;
  exams?: IExam[];
}

export const defaultValue: Readonly<IExamHall> = {};
