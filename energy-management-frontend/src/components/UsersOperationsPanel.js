import React from 'react';
import { Box, Button, Grid, TextField, MenuItem, FormControlLabel, Checkbox } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

export default class UsersOperations extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      newUserUsername:'',
      newUserPassword: '',
      newUserIsAdmin: false,
      };
    this.handleChange = this.handleChange.bind(this);
    this.handleAddUser = this.handleAddUser.bind(this);
    this.handleDeleteUser = this.handleDeleteUser.bind(this);
    this.handleEditUser = this.handleEditUser.bind(this);
  }

  componentDidMount() {
    fetch('http://localhost:8082/user')
      .then(res => res.json())
      .then(data => this.setState({ users: data }))
      .then(() => console.log(this.state.users))
      .catch(err => console.log(err));
  }

  handleDeleteUser(id) {
    // Send DELETE request to server to delete user with given id
    fetch(`http://localhost:8082/user/${id}`, {
      method: 'DELETE'
    })
      .then(res => {
        if (res.ok) {
          // Remove deleted user from state
          this.setState(prevState => ({
            users: prevState.users.filter(user => user.uuid !== id)
          }));
          alert('User deleted successfully');
        }
        else {
          console.log('Error deleting doctor');
        }
      })
      .catch(err => console.log(err));
  }

  handleEditUser(user) {
    console.log("handle edit user: " + JSON.stringify(user))
    fetch(`http://localhost:8082/user/${user.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: user.username,
        password: user.password,
        isAdmin: user.isAdmin
      })
    })
      .then(res => {
        if (res.ok) {
          console.log('User updated successfully');
          alert('User updated successfully');
          this.componentDidMount();
        }
        else {
          console.log('Error updating user');
          alert('Error updating user');
        }
      })
      .catch(err => console.log(err));
  }

  handleAddUser() {
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: this.state.newUserUsername,
        password: this.state.newUserPassword,
        isAdmin: this.state.newUserIsAdmin
      })
    };
  
    fetch('http://localhost:8082/user', requestOptions)
      .then(res => {
        if (res.ok) {
          console.log('User added successfully');
          alert('User added successfully');
          this.componentDidMount();
        } else {
          console.log('Error adding User');
          alert('Error adding User');
        }
      })
      .catch(err => console.log(err));
  }
  
  
  handleChange = (event) => {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;
  
    this.setState({
      [name]: value
    });
  }

  render() {
    const columns = [
      { field: 'id', headerName: 'Id', editable: false, width: 350 },
      { field: 'username', headerName: 'Username', editable: true, width: 200 },
      { field: 'password', headerName: 'Password', editable: true, width: 150 },
      { field: 'isAdmin', headerName: 'Is Admin?', editable: false, width: 150,
        renderCell: (params) => {
          let user = params.row;
          return (
            <Checkbox
              checked={user.isAdmin}
              disabled
            />
          );
        }
      },
      { field: 'actions', headerName: 'Actions', sortable: false, width: 200,
        renderCell: (params) => {
          const editedUser = params.row;
          return (
            <>
              <Button variant="contained" color="primary" onClick={() => this.handleEditUser(editedUser)}>
                Edit
              </Button>
              <Button variant="contained" color="error" onClick={() => this.handleDeleteUser(editedUser.id)}>
                Delete
              </Button>
            </>
          );
        }
      }
    ];

    const rows = this.state.users.map(user => ({ id: user.uuid, username: user.username, password: user.password, isAdmin: user.isAdmin }));

    return (
      <Box sx={{ p: 3 }}>
        <h1>Users</h1>
        <DataGrid
          columns={columns}
          rows={rows}
          pageSize={10}
          rowsPerPageOptions={[10]}
          autoHeight
        />
        <br/>
        <Grid>
          <TextField
            label="Username"
            name='newUserUsername'
            onChange={this.handleChange}
            value={this.state.newUserUsername}
            variant="outlined"
            required
          />
          <TextField
            label="Password"
            name='newUserPassword'
            onChange={this.handleChange}
            value={this.state.newUserPassword}
            variant="outlined"
            required
          />
          <FormControlLabel
            control={
            <Checkbox 
              checked={this.state.newUserIsAdmin} 
              onChange={this.handleChange} 
              name='newUserIsAdmin'
            />
            }
            label="Is Admin?"
            labelPlacement="top"
            required
          />
          <br/>
          <Button variant="contained" color="success" onClick={this.handleAddUser}>
            Add New User
          </Button>
        </Grid>
      </Box>
    );
  }
}
