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

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Register />} />
      <Route path="login" element={<Login />} />
      <Route path="homepage" element={<Homepage />} />
      <Route path="employees" element={<Employees />} />
      <Route path="findemployee" element={<FindEmployee />} />
      <Route path="updateemployee" element={<UpdateEmployee />} />
      <Route path="deleteemployee" element={<DeleteEmployee />} />
    </Routes>
  </BrowserRouter>
)
