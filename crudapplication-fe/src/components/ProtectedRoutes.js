import axios from 'axios'
import React, { useState } from 'react'
import { Outlet, Navigate } from 'react-router-dom'

const ProtectedRoutes = () => {
  const [isAuth, setIsAuth] = useState()
  const baseUrl = 'http://0.0.0.0:8080/'
  
  useState(() => {
    axios
    .get(baseUrl + 'islogged/' + localStorage.getItem('email'), {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
    .then((response) => {
      setIsAuth(Boolean(response.data.data))
      return isAuth
    })
  }, [])

  if(isAuth === undefined) return null

  return isAuth ? <Outlet /> : <Navigate to="/" />
}

export default ProtectedRoutes
