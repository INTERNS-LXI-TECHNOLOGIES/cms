import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserDomain, defaultValue } from 'app/shared/model/user-domain.model';

export const ACTION_TYPES = {
  FETCH_USERDOMAIN_LIST: 'userDomain/FETCH_USERDOMAIN_LIST',
  FETCH_USERDOMAIN: 'userDomain/FETCH_USERDOMAIN',
  CREATE_USERDOMAIN: 'userDomain/CREATE_USERDOMAIN',
  UPDATE_USERDOMAIN: 'userDomain/UPDATE_USERDOMAIN',
  DELETE_USERDOMAIN: 'userDomain/DELETE_USERDOMAIN',
  RESET: 'userDomain/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserDomain>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UserDomainState = Readonly<typeof initialState>;

// Reducer

export default (state: UserDomainState = initialState, action): UserDomainState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERDOMAIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERDOMAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERDOMAIN):
    case REQUEST(ACTION_TYPES.UPDATE_USERDOMAIN):
    case REQUEST(ACTION_TYPES.DELETE_USERDOMAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERDOMAIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERDOMAIN):
    case FAILURE(ACTION_TYPES.CREATE_USERDOMAIN):
    case FAILURE(ACTION_TYPES.UPDATE_USERDOMAIN):
    case FAILURE(ACTION_TYPES.DELETE_USERDOMAIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERDOMAIN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERDOMAIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERDOMAIN):
    case SUCCESS(ACTION_TYPES.UPDATE_USERDOMAIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERDOMAIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/user-domains';

// Actions

export const getEntities: ICrudGetAllAction<IUserDomain> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERDOMAIN_LIST,
    payload: axios.get<IUserDomain>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUserDomain> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERDOMAIN,
    payload: axios.get<IUserDomain>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserDomain> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERDOMAIN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserDomain> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERDOMAIN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserDomain> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERDOMAIN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
