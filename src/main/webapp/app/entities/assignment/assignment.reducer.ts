import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAssignment, defaultValue } from 'app/shared/model/assignment.model';

export const ACTION_TYPES = {
  FETCH_ASSIGNMENT_LIST: 'assignment/FETCH_ASSIGNMENT_LIST',
  FETCH_ASSIGNMENT: 'assignment/FETCH_ASSIGNMENT',
  CREATE_ASSIGNMENT: 'assignment/CREATE_ASSIGNMENT',
  UPDATE_ASSIGNMENT: 'assignment/UPDATE_ASSIGNMENT',
  DELETE_ASSIGNMENT: 'assignment/DELETE_ASSIGNMENT',
  RESET: 'assignment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAssignment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AssignmentState = Readonly<typeof initialState>;

// Reducer

export default (state: AssignmentState = initialState, action): AssignmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ASSIGNMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ASSIGNMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ASSIGNMENT):
    case REQUEST(ACTION_TYPES.UPDATE_ASSIGNMENT):
    case REQUEST(ACTION_TYPES.DELETE_ASSIGNMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ASSIGNMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ASSIGNMENT):
    case FAILURE(ACTION_TYPES.CREATE_ASSIGNMENT):
    case FAILURE(ACTION_TYPES.UPDATE_ASSIGNMENT):
    case FAILURE(ACTION_TYPES.DELETE_ASSIGNMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSIGNMENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSIGNMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ASSIGNMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_ASSIGNMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ASSIGNMENT):
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

const apiUrl = 'api/assignments';

// Actions

export const getEntities: ICrudGetAllAction<IAssignment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ASSIGNMENT_LIST,
    payload: axios.get<IAssignment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAssignment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ASSIGNMENT,
    payload: axios.get<IAssignment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAssignment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ASSIGNMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAssignment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ASSIGNMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAssignment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ASSIGNMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
