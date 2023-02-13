import * as React from 'react'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import SearchIcon from '@mui/icons-material/Search'
import { Link } from 'react-router-dom'
import Button from '@mui/material/Button'

const DynamicTable = (props) => {
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="dynamic table">
        <TableHead>
          <TableRow>
            {props.hpButton ? (
              <TableCell>
                <Link to={props.link}>
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
              </TableCell>
            ) : (
              <></>
            )}
          </TableRow>
          <TableRow>
            {Object.keys(props.data[0]).map((key) => (
              <TableCell key={key}>{key}</TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {props.data.map((row, index) => (
            <TableRow key={index}>
              {Object.values(row).map((value, index) =>
                index === 0 ? (
                  <TableCell key={index}>
                    <Link
                      to="/EmployeeDetail"
                      state={{ emplId: value }}
                      replace
                    >
                      <SearchIcon />
                    </Link>
                  </TableCell>
                ) : (
                  <TableCell key={index}>{value}</TableCell>
                )
              )}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default DynamicTable
