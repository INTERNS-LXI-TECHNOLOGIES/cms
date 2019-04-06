import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExam, defaultValue } from 'app/shared/model/exam.model';

export const ACTION_TYPES = {
  FETCH_EXAM_LIST: 'exam/FETCH_EXAM_LIST',
  FETCH_EXAM: 'exam/FETCH_EXAM',
  CREATE_EXAM: 'exam/CREATE_EXAM',
  UPDATE_EXAM: 'exam/UPDATE_EXAM',
  DELETE_EXAM: 'exam/DELETE_EXAM',
  RESET: 'exam/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExam>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ExamState = Readonly<typeof initialState>;

// Reducer

export default (state: ExamState = initialState, action): ExamState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXAM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXAM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EXAM):
    case REQUEST(ACTION_TYPES.UPDATE_EXAM):
    case REQUEST(ACTION_TYPES.DELETE_EXAM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EXAM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXAM):
    case FAILURE(ACTION_TYPES.CREATE_EXAM):
    case FAILURE(ACTION_TYPES.UPDATE_EXAM):
    case FAILURE(ACTION_TYPES.DELETE_EXAM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXAM_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXAM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXAM):
    case SUCCESS(ACTION_TYPES.UPDATE_EXAM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXAM):
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

const apiUrl = 'api/exams';

// Actions

export const getEntities: ICrudGetAllAction<IExam> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXAM_LIST,
    payload: axios.get<IExam>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IExam> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXAM,
    payload: axios.get<IExam>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IExam> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXAM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExam> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXAM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExam> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXAM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
