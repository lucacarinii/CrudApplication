import React, { useContext, useState, useEffect } from 'react'
import axios from 'axios'
import { AuthContext } from '../AuthContextJWT/AuthContext'
import DynamicTable from '../DynamicTable'
import { baseUrl } from '../util/Constants'

const Employees = () => {
  const { token, setToken } = useContext(AuthContext)
  const [employees, setEmployees] = useState([])

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

  if (!employees || employees.length === 0) {
    return <div>Loading...</div>
  } else {
    return <DynamicTable data={employees} link="/homepage" hpButton={true} />
  }
}

export default Employees
