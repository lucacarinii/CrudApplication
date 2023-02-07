import React, { useContext, useState, useEffect } from 'react'
import axios from 'axios'
import { AuthContext } from '../AuthContextJWT/AuthContext'

const Employees = () => {
  const { token, setToken } = useContext(AuthContext)
  const [employees, setEmployees] = useState([])

  const baseUrl = 'http://0.0.0.0:8080/'

  useEffect(() => {
    axios
      .get(baseUrl + 'employees', {
        headers: {
          'Access-Control-Allow-Origin': '*',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setEmployees(response.data.data)
      })
  }, [])
  return (
    <>
      {employees.map((employee) => (
        <p key={employee.emplId}>
          {employee.name} {employee.surname}
        </p>
      ))}
    </>
  )
}

export default Employees
