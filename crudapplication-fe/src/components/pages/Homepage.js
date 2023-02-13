import React, { useContext, useEffect } from 'react'
import { Grid, Paper, Button } from '@mui/material'
import { Link } from 'react-router-dom'
import axios from 'axios'
import { baseUrl } from '../util/Constants'
import { AuthContext } from '../AuthContextJWT/AuthContext'

const Homepage = () => {
  const { token, setToken } = useContext(AuthContext)
  const { refreshToken, setRefreshToken } = useContext(AuthContext)

  useEffect(() => {
    axios
      .post(
        baseUrl + 'refresh',
        {
          refreshToken: refreshToken,
          email: localStorage.getItem('email'),
        },
        {
          headers: {
            'Access-Control-Allow-Origin': '*',
          },
        }
      )
      .then((response) => {
        setToken(response.data.data.token)
        setRefreshToken(response.data.data.refreshToken)
      })
  }, [])

  return (
    <Grid>
      <Paper
        elevation={10}
        style={{
          padding: 10,
          height: '23vh',
          width: 380,
          margin: '250px auto',
        }}
      >
        <Link to="/employees">
          <Button
            style={{ marginTop: '15px' }}
            type="submit"
            color="primary"
            fullWidth
            variant="contained"
          >
            All Employees
          </Button>
        </Link>
        <Link to="/findemployee">
          <Button
            style={{ marginTop: '15px' }}
            type="submit"
            color="primary"
            fullWidth
            variant="contained"
          >
            Find Employee
          </Button>
        </Link>
      </Paper>
    </Grid>
  )
}

export default Homepage
