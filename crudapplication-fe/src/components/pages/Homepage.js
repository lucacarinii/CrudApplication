import React from 'react'
import { Grid, Paper, Button } from '@mui/material'
import { Link } from 'react-router-dom'

const Homepage = () => {
  return (
    <Grid>
      <Paper
        elevation={10}
        style={{
          padding: 10,
          height: '70vh',
          width: 380,
          margin: '20px auto',
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
        <Link to="/updateemployee">
          <Button
            style={{ marginTop: '15px' }}
            type="submit"
            color="primary"
            fullWidth
            variant="contained"
          >
            Update Employee
          </Button>
        </Link>
        <Link to="/deleteemployee">
          <Button
            style={{ marginTop: '15px' }}
            type="submit"
            color="primary"
            fullWidth
            variant="contained"
          >
            Delete Employee
          </Button>
        </Link>
      </Paper>
    </Grid>
  )
}

export default Homepage
