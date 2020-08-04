import React from "react";
import "./App.css";
import Header from "./component/layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./component/project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import Dashboard from "./component/Dashboard";
import UpdateProject from "./component/project/UpdateProject";
import ProjectBoard from "./component/project-board/ProjectBoard";
import AddProjectTask from "./component/project-board/project-task/AddProjectTask";
import UpdateProjectTask from "./component/project-board/project-task/UpdateProjectTask";
import Landing from "./component/layout/Landing";
import Register from "./component/user-management/Register";
import Login from "./component/user-management/Login";
import jwt_decode from "jwt-decode";
import { SET_CURRENT_USER } from "./action/Types";
import { logout } from "./action/SecurityActions";
import setJwt from "./component/user-management/SetJwt";
import SecureRoute from "./component/user-management/SecureRoute";

const jwt = localStorage.jwt;
if (jwt) {
  setJwt(jwt);
  const decoded = jwt_decode(jwt);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded,
  });

  const currentTime = Date.now() / 1000;
  if (decoded.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          <Switch>
            <SecureRoute exact path="/dashboard" component={Dashboard} />
            <SecureRoute exact path="/add-project" component={AddProject} />
            <SecureRoute
              exact
              path="/updateProject/:id"
              component={UpdateProject}
            />
            <SecureRoute
              exact
              path="/project-board/:id"
              component={ProjectBoard}
            />
            <SecureRoute
              exact
              path="/add-project-task/:id"
              component={AddProjectTask}
            />
            <SecureRoute
              exact
              path="/update-project-task/:backlog_id/:pt_id"
              component={UpdateProjectTask}
            />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
