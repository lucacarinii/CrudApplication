import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import { Link, useLocation } from 'react-router-dom'
import { baseUrl } from '../util/Constants'
import { AuthContext } from '../AuthContextJWT/AuthContext'
import { Button, Grid, Paper } from '@mui/material'

const EmployeeDetail = (props) => {
  const [employee, setEmployee] = useState()
  const { token, setToken } = useContext(AuthContext)
  const location = useLocation()
  useEffect(() => {
    axios
      .get(baseUrl + 'employees/' + location.state.emplId, {
        headers: {
          'Access-Control-Allow-Origin': '*',
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setEmployee(response.data.data)
      })
  }, [])

  if (!employee) {
    return <div>Loading</div>
  } else {
    return (
      <Grid>
        <Paper
          elevation={10}
          style={{
            padding: 10,
            height: '35vh',
            width: 380,
            margin: '20px auto',
          }}
        >
          <label>
            <b>Name:</b>
          </label>
          <p>{employee.name}</p>
          <label>
            <b>Surname:</b>
          </label>
          <p>{employee.surname}</p>
          <label>
            <b>Email:</b>
          </label>
          <p>{employee.email}</p>
          <Link to="/employees">
            <Button
              style={{ marginTop: '15px' }}
              type="submit"
              color="primary"
              fullWidth
              variant="contained"
            >
              All employees
            </Button>
          </Link>
          <Link to="/homepage">
            <Button
              style={{ marginTop: '15px' }}
              type="submit"
              color="primary"
              fullWidth
              variant="contained"
            >
              Homepage
            </Button>
          </Link>
        </Paper>
      </Grid>
    )
  }
}

export default EmployeeDetail
