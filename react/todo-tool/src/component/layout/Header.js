import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { logout } from "../../action/SecurityActions";
import React, { Component } from "react";

class Header extends Component {
  logout() {
    this.props.logout();
    window.location.href = "/";
  }
  render() {
    const { validToken, user } = this.props.security;
    const userAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link className="nav-link" to={"/dashboard"}>
              Dashboard
            </Link>
          </li>
        </ul>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link className="nav-link " to={"/dashboard"}>
              <i className="fas fa-user-circle mr-1" />
              {user.sub}
            </Link>
          </li>
          <li className="nav-item">
            <Link
              className="nav-link"
              to={"/logout"}
              onClick={this.logout.bind(this)}
            >
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );

    const userNotAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link className="nav-link" to={"/register"}>
              Sign Up
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to={"/login"}>
              Login
            </Link>
          </li>
        </ul>
      </div>
    );

    let headers;
    if (validToken && user) {
      headers = userAuthenticated;
    } else {
      headers = userNotAuthenticated;
    }

    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container-fluid">
          <Link className="navbar-brand" to={"/"}>
            Project Management Tool
          </Link>

          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>
          {headers}
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, { logout })(Header);
