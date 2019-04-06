import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStudyMaterial, defaultValue } from 'app/shared/model/study-material.model';

export const ACTION_TYPES = {
  FETCH_STUDYMATERIAL_LIST: 'studyMaterial/FETCH_STUDYMATERIAL_LIST',
  FETCH_STUDYMATERIAL: 'studyMaterial/FETCH_STUDYMATERIAL',
  CREATE_STUDYMATERIAL: 'studyMaterial/CREATE_STUDYMATERIAL',
  UPDATE_STUDYMATERIAL: 'studyMaterial/UPDATE_STUDYMATERIAL',
  DELETE_STUDYMATERIAL: 'studyMaterial/DELETE_STUDYMATERIAL',
  SET_BLOB: 'studyMaterial/SET_BLOB',
  RESET: 'studyMaterial/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStudyMaterial>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type StudyMaterialState = Readonly<typeof initialState>;

// Reducer

export default (state: StudyMaterialState = initialState, action): StudyMaterialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STUDYMATERIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STUDYMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STUDYMATERIAL):
    case REQUEST(ACTION_TYPES.UPDATE_STUDYMATERIAL):
    case REQUEST(ACTION_TYPES.DELETE_STUDYMATERIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_STUDYMATERIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STUDYMATERIAL):
    case FAILURE(ACTION_TYPES.CREATE_STUDYMATERIAL):
    case FAILURE(ACTION_TYPES.UPDATE_STUDYMATERIAL):
    case FAILURE(ACTION_TYPES.DELETE_STUDYMATERIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDYMATERIAL_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDYMATERIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STUDYMATERIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_STUDYMATERIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STUDYMATERIAL):
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

const apiUrl = 'api/study-materials';

// Actions

export const getEntities: ICrudGetAllAction<IStudyMaterial> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STUDYMATERIAL_LIST,
    payload: axios.get<IStudyMaterial>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IStudyMaterial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STUDYMATERIAL,
    payload: axios.get<IStudyMaterial>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStudyMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STUDYMATERIAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStudyMaterial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STUDYMATERIAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStudyMaterial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STUDYMATERIAL,
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
