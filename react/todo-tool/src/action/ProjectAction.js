import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from "./Types";

export const createProject = (project, history) => async (dispatch) => {
  try {
    await axios.post("http://192.168.99.100:8080/rest/project", project);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
    history.push("/dashboard");
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const getProjects = () => async (dispatch) => {
  const res = await axios.get("http://192.168.99.100:8080/rest/project");
  dispatch({
    type: GET_PROJECTS,
    payload: res.data,
  });
};

export const getProject = (id, history) => async (dispatch) => {
  const res = await axios.get(`http://192.168.99.100:8080/rest/project/${id}`);
  dispatch({
    type: GET_PROJECT,
    payload: res.data,
  });
};

export const putProject = (id, project, history) => async (dispatch) => {
  try {
    await axios.put(`http://192.168.99.100:8080/rest/project/${id}`, project);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const deleteProject = (id) => async (dispatch) => {
  if (window.confirm(`This project will be deleted from your account`)) {
    await axios.delete(`http://192.168.99.100:8080/rest/project/${id}`);
    dispatch({
      type: DELETE_PROJECT,
      payload: id,
    });
  }
};
