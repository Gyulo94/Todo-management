import React, { useState, useEffect } from "react";
import { getTodo, saveTodo, updateTodo } from "../service/TodoService";
import { useNavigate, useParams } from "react-router-dom";

export default function Todo() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [completed, setCompleted] = useState(false);
  const navigator = useNavigate();
  const { id } = useParams();

  const saveOrUpdateTodo = (e) => {
    e.preventDefault();

    const todo = { title, description, completed };
    console.log(todo);

    if (id) {
      updateTodo(id, todo)
        .then((res) => {
          navigator("/todos");
        })
        .catch((err) => console.error(err));
    } else {
      saveTodo(todo)
        .then((res) => {
          console.log(res.data);
          navigator("/todos");
        })
        .catch((err) => console.error(err));
    }
  };

  const pageTitle = () => {
    if (id) {
      return <h2 className="text-center">Update Todo</h2>;
    } else {
      <h2 className="text-center">Add Todo</h2>;
    }
  };

  useEffect(() => {
    if (id) {
      getTodo(id)
        .then((res) => {
          console.log(res.data);
          setTitle(res.data.title);
          setDescription(res.data.description);
          setCompleted(res.data.completed);
        })
        .catch((err) => console.error(err));
    }
  }, [id]);
  return (
    <div className="container">
      <br />
      <br />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          {pageTitle()}
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">할 일:</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Todo Title"
                  name="title"
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">상세설명:</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Todo Description"
                  name="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
              </div>
              <div className="form-group mb-2">
                <label className="form-label">완료 여부:</label>
                <select
                  className="form-control"
                  value={completed}
                  onChange={(e) => setCompleted(e.target.value)}
                >
                  <option value="false">No</option>
                  <option value="true">Yes</option>
                </select>
              </div>
              <button
                className="btn btn-success"
                onClick={(e) => saveOrUpdateTodo(e)}
              >
                제출
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
