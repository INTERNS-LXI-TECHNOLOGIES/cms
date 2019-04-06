import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExamSchedule, defaultValue } from 'app/shared/model/exam-schedule.model';

export const ACTION_TYPES = {
  FETCH_EXAMSCHEDULE_LIST: 'examSchedule/FETCH_EXAMSCHEDULE_LIST',
  FETCH_EXAMSCHEDULE: 'examSchedule/FETCH_EXAMSCHEDULE',
  CREATE_EXAMSCHEDULE: 'examSchedule/CREATE_EXAMSCHEDULE',
  UPDATE_EXAMSCHEDULE: 'examSchedule/UPDATE_EXAMSCHEDULE',
  DELETE_EXAMSCHEDULE: 'examSchedule/DELETE_EXAMSCHEDULE',
  RESET: 'examSchedule/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExamSchedule>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ExamScheduleState = Readonly<typeof initialState>;

// Reducer

export default (state: ExamScheduleState = initialState, action): ExamScheduleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXAMSCHEDULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXAMSCHEDULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EXAMSCHEDULE):
    case REQUEST(ACTION_TYPES.UPDATE_EXAMSCHEDULE):
    case REQUEST(ACTION_TYPES.DELETE_EXAMSCHEDULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EXAMSCHEDULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXAMSCHEDULE):
    case FAILURE(ACTION_TYPES.CREATE_EXAMSCHEDULE):
    case FAILURE(ACTION_TYPES.UPDATE_EXAMSCHEDULE):
    case FAILURE(ACTION_TYPES.DELETE_EXAMSCHEDULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXAMSCHEDULE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXAMSCHEDULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXAMSCHEDULE):
    case SUCCESS(ACTION_TYPES.UPDATE_EXAMSCHEDULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXAMSCHEDULE):
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

const apiUrl = 'api/exam-schedules';

// Actions

export const getEntities: ICrudGetAllAction<IExamSchedule> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXAMSCHEDULE_LIST,
    payload: axios.get<IExamSchedule>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IExamSchedule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXAMSCHEDULE,
    payload: axios.get<IExamSchedule>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IExamSchedule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXAMSCHEDULE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExamSchedule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXAMSCHEDULE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExamSchedule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXAMSCHEDULE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
