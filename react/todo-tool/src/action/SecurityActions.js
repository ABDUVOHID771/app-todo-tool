import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./Types";
import jwt_decode from "jwt-decode";
import setJwt from "../component/user-management/SetJwt";

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("http://192.168.99.100:8080/rest/user/register", newUser);
    history.push("/login");
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

export const login = (AuthenticationRequest) => async (dispatch) => {
  try {
    const res = await axios.post(
      "http://192.168.99.100:8080/authenticate",
      AuthenticationRequest
    );
    const { jwt } = res.data;
    localStorage.setItem("jwt", jwt);
    console.log(jwt);
    setJwt(jwt);
    const decoded = jwt_decode(jwt);
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwt");
  delete axios.defaults.headers.common["Authorization"];
  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};
