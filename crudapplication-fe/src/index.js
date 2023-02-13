import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import Register from './components/form/Register'
import Login from './components/form/Login'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Homepage from './components/pages/Homepage'
import Employees from './components/employee/Employees'
import FindEmployee from './components/employee/FindEmployee'
import UpdateEmployee from './components/employee/UpdateEmployee'
import DeleteEmployee from './components/employee/DeleteEmployee'
import ProtectedRoutes from './components/ProtectedRoutes'
import { AuthProvider } from './components/AuthContextJWT/AuthContext'
import EmployeeDetail from './components/employee/EmployeeDetail'

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <AuthProvider>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Register />} />
        <Route path="login" element={<Login />} />
        <Route element={<ProtectedRoutes />}>
          <Route path="homepage" element={<Homepage />} />
          <Route path="employees" element={<Employees />} />
          <Route path="findemployee" element={<FindEmployee />} />
          <Route path="updateemployee" element={<UpdateEmployee />} />
          <Route path="deleteemployee" element={<DeleteEmployee />} />
          <Route path="employeedetail" element={<EmployeeDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </AuthProvider>
)
