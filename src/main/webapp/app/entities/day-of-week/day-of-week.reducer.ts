import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDayOfWeek, defaultValue } from 'app/shared/model/day-of-week.model';

export const ACTION_TYPES = {
  FETCH_DAYOFWEEK_LIST: 'dayOfWeek/FETCH_DAYOFWEEK_LIST',
  FETCH_DAYOFWEEK: 'dayOfWeek/FETCH_DAYOFWEEK',
  CREATE_DAYOFWEEK: 'dayOfWeek/CREATE_DAYOFWEEK',
  UPDATE_DAYOFWEEK: 'dayOfWeek/UPDATE_DAYOFWEEK',
  DELETE_DAYOFWEEK: 'dayOfWeek/DELETE_DAYOFWEEK',
  RESET: 'dayOfWeek/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDayOfWeek>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DayOfWeekState = Readonly<typeof initialState>;

// Reducer

export default (state: DayOfWeekState = initialState, action): DayOfWeekState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DAYOFWEEK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DAYOFWEEK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DAYOFWEEK):
    case REQUEST(ACTION_TYPES.UPDATE_DAYOFWEEK):
    case REQUEST(ACTION_TYPES.DELETE_DAYOFWEEK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DAYOFWEEK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DAYOFWEEK):
    case FAILURE(ACTION_TYPES.CREATE_DAYOFWEEK):
    case FAILURE(ACTION_TYPES.UPDATE_DAYOFWEEK):
    case FAILURE(ACTION_TYPES.DELETE_DAYOFWEEK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DAYOFWEEK_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DAYOFWEEK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DAYOFWEEK):
    case SUCCESS(ACTION_TYPES.UPDATE_DAYOFWEEK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DAYOFWEEK):
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

const apiUrl = 'api/day-of-weeks';

// Actions

export const getEntities: ICrudGetAllAction<IDayOfWeek> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DAYOFWEEK_LIST,
    payload: axios.get<IDayOfWeek>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDayOfWeek> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DAYOFWEEK,
    payload: axios.get<IDayOfWeek>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDayOfWeek> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DAYOFWEEK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDayOfWeek> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DAYOFWEEK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDayOfWeek> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DAYOFWEEK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
