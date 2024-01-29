import React, { useEffect, useState } from "react";
import {
  completeTodo,
  deleteTodo,
  getAllTodos,
  inCompleteTodo,
} from "../service/TodoService";
import { useNavigate } from "react-router-dom";
import { isAdminUser } from "../service/AuthService";

export default function ListTodo() {
  const [todos, setTodos] = useState([]);

  const navigator = useNavigate();

  const isAdmin = isAdminUser();

  useEffect(() => {
    listTodos();
  }, []);

  function listTodos() {
    getAllTodos()
      .then((res) => {
        setTodos(res.data), console.log(res);
      })
      .catch((err) => console.error(err));
  }

  const addNewTodo = () => {
    navigator("/add-todo");
  };

  const updateTodo = (id) => {
    console.log(id);
    navigator(`/update-todo/${id}`);
  };

  const removeTodo = (id) => {
    deleteTodo(id)
      .then((res) => listTodos())
      .catch((err) => console.error(err));
  };

  const markCompleteTodo = (id) => {
    completeTodo(id)
      .then((res) => listTodos())
      .catch((err) => console.error(err));
  };

  const markInCompleteTodo = (id) => {
    inCompleteTodo(id)
      .then((res) => listTodos())
      .catch((err) => console.error(err));
  };

  return (
    <div className="container">
      <h2 className="text-center">List of Todos</h2>
      {isAdmin && (
        <button className="btn btn-primary mb-2" onClick={addNewTodo}>
          할 일 추가
        </button>
      )}
      <div>
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>할 일</th>
              <th>상세내용</th>
              <th>완료 여부</th>
              <th>버튼</th>
            </tr>
          </thead>
          <tbody>
            {todos.map((todo) => (
              <tr key={todo.id}>
                <td>{todo.title}</td>
                <td>{todo.description}</td>
                <td>{todo.completed ? "YES" : "NO"}</td>
                <td>
                  {isAdmin && (
                    <button
                      className="btn btn-info"
                      onClick={() => updateTodo(todo.id)}
                    >
                      Update
                    </button>
                  )}
                  {isAdmin && (
                    <button
                      className="btn btn-danger"
                      onClick={() => removeTodo(todo.id)}
                      style={{ marginLeft: "10px" }}
                    >
                      Delete
                    </button>
                  )}

                  <button
                    className="btn btn-success"
                    onClick={() => markCompleteTodo(todo.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    Complete
                  </button>
                  <button
                    className="btn btn-info"
                    onClick={() => markInCompleteTodo(todo.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    In Complete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
