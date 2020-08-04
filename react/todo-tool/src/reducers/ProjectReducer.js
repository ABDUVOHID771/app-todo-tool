import {
  GET_PROJECTS,
  GET_PROJECT,
  PUT_PROJECT,
  DELETE_PROJECT,
} from "../action/Types";

const initialState = {
  projects: [],
  project: {},
};

export default function (state = initialState, action) {
  switch (action.type) {
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload,
      };
    case GET_PROJECT:
      return {
        ...state,
        project: action.payload,
      };

    case PUT_PROJECT:
      return {
        ...state,
        project: action.payload,
      };
    case DELETE_PROJECT:
      return {
        ...state,
        projects: state.projects.filter(
          (project) => project.uuid !== action.payload
        ),
      };
    default:
      return state;
  }
}
