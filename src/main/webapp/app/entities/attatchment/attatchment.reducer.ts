import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAttatchment, defaultValue } from 'app/shared/model/attatchment.model';

export const ACTION_TYPES = {
  FETCH_ATTATCHMENT_LIST: 'attatchment/FETCH_ATTATCHMENT_LIST',
  FETCH_ATTATCHMENT: 'attatchment/FETCH_ATTATCHMENT',
  CREATE_ATTATCHMENT: 'attatchment/CREATE_ATTATCHMENT',
  UPDATE_ATTATCHMENT: 'attatchment/UPDATE_ATTATCHMENT',
  DELETE_ATTATCHMENT: 'attatchment/DELETE_ATTATCHMENT',
  SET_BLOB: 'attatchment/SET_BLOB',
  RESET: 'attatchment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAttatchment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AttatchmentState = Readonly<typeof initialState>;

// Reducer

export default (state: AttatchmentState = initialState, action): AttatchmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ATTATCHMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ATTATCHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ATTATCHMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ATTATCHMENT):
    case REQUEST(ACTION_TYPES.DELETE_ATTATCHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ATTATCHMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ATTATCHMENT):
    case FAILURE(ACTION_TYPES.CREATE_ATTATCHMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ATTATCHMENT):
    case FAILURE(ACTION_TYPES.DELETE_ATTATCHMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTATCHMENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTATCHMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ATTATCHMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ATTATCHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ATTATCHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/attatchments';

// Actions

export const getEntities: ICrudGetAllAction<IAttatchment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ATTATCHMENT_LIST,
    payload: axios.get<IAttatchment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAttatchment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ATTATCHMENT,
    payload: axios.get<IAttatchment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAttatchment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ATTATCHMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAttatchment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ATTATCHMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAttatchment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ATTATCHMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
