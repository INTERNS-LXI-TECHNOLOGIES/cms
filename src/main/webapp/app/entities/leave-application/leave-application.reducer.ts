import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILeaveApplication, defaultValue } from 'app/shared/model/leave-application.model';

export const ACTION_TYPES = {
  FETCH_LEAVEAPPLICATION_LIST: 'leaveApplication/FETCH_LEAVEAPPLICATION_LIST',
  FETCH_LEAVEAPPLICATION: 'leaveApplication/FETCH_LEAVEAPPLICATION',
  CREATE_LEAVEAPPLICATION: 'leaveApplication/CREATE_LEAVEAPPLICATION',
  UPDATE_LEAVEAPPLICATION: 'leaveApplication/UPDATE_LEAVEAPPLICATION',
  DELETE_LEAVEAPPLICATION: 'leaveApplication/DELETE_LEAVEAPPLICATION',
  RESET: 'leaveApplication/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILeaveApplication>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LeaveApplicationState = Readonly<typeof initialState>;

// Reducer

export default (state: LeaveApplicationState = initialState, action): LeaveApplicationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LEAVEAPPLICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LEAVEAPPLICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LEAVEAPPLICATION):
    case REQUEST(ACTION_TYPES.UPDATE_LEAVEAPPLICATION):
    case REQUEST(ACTION_TYPES.DELETE_LEAVEAPPLICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LEAVEAPPLICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LEAVEAPPLICATION):
    case FAILURE(ACTION_TYPES.CREATE_LEAVEAPPLICATION):
    case FAILURE(ACTION_TYPES.UPDATE_LEAVEAPPLICATION):
    case FAILURE(ACTION_TYPES.DELETE_LEAVEAPPLICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEAVEAPPLICATION_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEAVEAPPLICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LEAVEAPPLICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_LEAVEAPPLICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LEAVEAPPLICATION):
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

const apiUrl = 'api/leave-applications';

// Actions

export const getEntities: ICrudGetAllAction<ILeaveApplication> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LEAVEAPPLICATION_LIST,
    payload: axios.get<ILeaveApplication>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILeaveApplication> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LEAVEAPPLICATION,
    payload: axios.get<ILeaveApplication>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILeaveApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LEAVEAPPLICATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILeaveApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LEAVEAPPLICATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILeaveApplication> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LEAVEAPPLICATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
