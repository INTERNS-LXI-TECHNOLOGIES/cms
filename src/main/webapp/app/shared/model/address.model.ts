export interface IAddress {
  id?: number;
  houseName?: string;
  street?: string;
  district?: string;
  pincode?: number;
  state?: string;
  country?: string;
}

export const defaultValue: Readonly<IAddress> = {};
