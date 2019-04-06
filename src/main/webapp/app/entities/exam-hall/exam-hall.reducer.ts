import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExamHall, defaultValue } from 'app/shared/model/exam-hall.model';

export const ACTION_TYPES = {
  FETCH_EXAMHALL_LIST: 'examHall/FETCH_EXAMHALL_LIST',
  FETCH_EXAMHALL: 'examHall/FETCH_EXAMHALL',
  CREATE_EXAMHALL: 'examHall/CREATE_EXAMHALL',
  UPDATE_EXAMHALL: 'examHall/UPDATE_EXAMHALL',
  DELETE_EXAMHALL: 'examHall/DELETE_EXAMHALL',
  RESET: 'examHall/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExamHall>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ExamHallState = Readonly<typeof initialState>;

// Reducer

export default (state: ExamHallState = initialState, action): ExamHallState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXAMHALL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXAMHALL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EXAMHALL):
    case REQUEST(ACTION_TYPES.UPDATE_EXAMHALL):
    case REQUEST(ACTION_TYPES.DELETE_EXAMHALL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EXAMHALL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXAMHALL):
    case FAILURE(ACTION_TYPES.CREATE_EXAMHALL):
    case FAILURE(ACTION_TYPES.UPDATE_EXAMHALL):
    case FAILURE(ACTION_TYPES.DELETE_EXAMHALL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXAMHALL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXAMHALL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXAMHALL):
    case SUCCESS(ACTION_TYPES.UPDATE_EXAMHALL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXAMHALL):
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

const apiUrl = 'api/exam-halls';

// Actions

export const getEntities: ICrudGetAllAction<IExamHall> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXAMHALL_LIST,
    payload: axios.get<IExamHall>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IExamHall> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXAMHALL,
    payload: axios.get<IExamHall>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IExamHall> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXAMHALL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExamHall> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXAMHALL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExamHall> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXAMHALL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
