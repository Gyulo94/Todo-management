import React from "react";
import { NavLink } from "react-router-dom";
import { isUserLoggedIn, logout } from "../service/AuthService";
import { useNavigate } from "react-router-dom";

export default function Header() {
  const isAuth = isUserLoggedIn();
  const navigator = useNavigate();

  const handleLogout = () => {
    logout();
    navigator("/login");
  };

  return (
    <div>
      <header>
        <nav className="navbar navbar-expand-md navbar-dark bg-dark">
          <div>
            <a href="/" className="navbar-brand">
              할 일 관리 어플리케이션
            </a>
          </div>
          <div className="collapse navbar-collapse">
            <ul className="navbar-nav">
              {isAuth && (
                <li className="nav-item">
                  <NavLink to="/todos" className="nav-link">
                    할 일 목록
                  </NavLink>
                </li>
              )}
            </ul>
          </div>
          <ul className="navbar-nav">
            {!isAuth && (
              <li className="nav-item">
                <NavLink to="/register" className="nav-link">
                  회원가입
                </NavLink>
              </li>
            )}
            {!isAuth && (
              <li className="nav-item">
                <NavLink to="/login" className="nav-link">
                  로그인
                </NavLink>
              </li>
            )}
            {isAuth && (
              <li className="nav-item">
                <NavLink
                  to="/login"
                  className="nav-link"
                  onClick={handleLogout}
                >
                  로그아웃
                </NavLink>
              </li>
            )}
          </ul>
        </nav>
      </header>
    </div>
  );
}
