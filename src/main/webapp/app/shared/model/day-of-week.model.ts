export interface IDayOfWeek {
  id?: number;
  sub1?: string;
  sub2?: string;
  sub3?: string;
  sub4?: string;
  sub5?: string;
  sub6?: string;
  sub7?: string;
  timeTableId?: number;
}

export const defaultValue: Readonly<IDayOfWeek> = {};
