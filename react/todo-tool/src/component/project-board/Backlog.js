import React, { Component } from "react";
import ProjectTask from "./project-task/ProjectTask";

class Backlog extends Component {
  render() {
    const { tasks } = this.props;

    const task = tasks.map((project_task) => (
      <ProjectTask key={project_task.id} project_task={project_task} />
    ));

    let todoItems = [];
    let inProgressItems = [];
    let doneItems = [];

    for (let i = 0; i < task.length; i++) {
      console.log("INSIDE");
      if (task[i].props.project_task.status === "TO_DO") {
        todoItems.push(task[i]);
      }
      if (task[i].props.project_task.status === "IN_PROGRESS") {
        inProgressItems.push(task[i]);
      }
      if (task[i].props.project_task.status === "DONE") {
        doneItems.push(task[i]);
      }
    }

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {todoItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgressItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {doneItems}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
