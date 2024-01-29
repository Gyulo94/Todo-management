import "./App.css";
import { Routes, Route, Navigate } from "react-router-dom";
import ListTodo from "./components/ListTodo";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Todo from "./components/Todo";
import Register from "./components/Register";
import Login from "./components/Login";
import { isUserLoggedIn } from "./service/AuthService";

function App() {
  const AuthenticatedRoute = ({ children }) => {
    const isAuth = isUserLoggedIn();
    if (isAuth) {
      return children;
    }
    return <Navigate to="/" />;
  };

  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Login />} />
        <Route
          path="/todos"
          element={
            <AuthenticatedRoute>
              <ListTodo />
            </AuthenticatedRoute>
          }
        />
        <Route
          path="/add-todo"
          element={
            <AuthenticatedRoute>
              <Todo />
            </AuthenticatedRoute>
          }
        />
        <Route
          path="/update-todo/:id"
          element={
            <AuthenticatedRoute>
              <Todo />
            </AuthenticatedRoute>
          }
        />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
