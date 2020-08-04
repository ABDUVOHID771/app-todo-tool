import React from "react";
import { Link } from "react-router-dom";

export default () => {
  return (
    <React.Fragment>
      <Link to="/add-project" className="btn btn-lg btn-info">
        Create a Project
      </Link>
    </React.Fragment>
  );
};
