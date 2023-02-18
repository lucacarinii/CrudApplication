import React, { useContext, useState, useEffect } from 'react'
import { AuthContext } from '../AuthContextJWT/AuthContext'
import DynamicTable from '../DynamicTable'
import getEmployees from './GetEmployees'
import deleteEmployee from './DeleteEmployee'

const Employees = () => {
  const { token, setToken } = useContext(AuthContext)
  const [employees, setEmployees] = useState([])
  console.log(token)
  useEffect(() => {
    getEmployees(token, setEmployees)
  }, [])

  if (!employees || employees.length === 0) {
    return <div>Loading...</div>
  } else {
    return (
      <DynamicTable
        data={employees}
        link="/homepage"
        hpButton={true}
        deleteEmployee={deleteEmployee}
        token={token}
        getList={getEmployees}
        setList={setEmployees}
      />
    )
  }
}

export default Employees
