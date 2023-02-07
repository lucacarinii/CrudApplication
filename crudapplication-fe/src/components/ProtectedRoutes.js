import axios from 'axios'
import React, { useContext, useState } from 'react'
import { Outlet, Navigate } from 'react-router-dom'
import { AuthContext } from './AuthContextJWT/AuthContext'

const ProtectedRoutes = () => {
  const [isAuth, setIsAuth] = useState()
  const baseUrl = 'http://0.0.0.0:8080/'
  const { token, setToken } = useContext(AuthContext)

  useState(() => {
    axios
      .get(baseUrl + 'islogged/' + localStorage.getItem('email'), {
        headers: {
          'Access-Control-Allow-Origin': '*',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setIsAuth(Boolean(response.data.data))
        return isAuth
      })
  }, [])

  if (isAuth === undefined) return null

  return isAuth ? <Outlet /> : <Navigate to="/" />
}

export default ProtectedRoutes
