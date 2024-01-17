import React from 'react';
import { Box, Button, Grid, TextField, MenuItem, FormControl, Select, InputLabel } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

export default class DevicesOperations extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      devices: [],
      newDeviceUserUuid:'',
      newDeviceDescription: '',
      newDeviceAddress: '',
      newDeviceConsumption: 0
      };
    this.handleChange = this.handleChange.bind(this);
    this.handleAddDevice = this.handleAddDevice.bind(this);
    this.handleDeleteDevice = this.handleDeleteDevice.bind(this);
    this.handleEditDevice = this.handleEditDevice.bind(this);
  }

  componentDidMount() {
    fetch('http://localhost:8081/device', {
      method: 'GET',
      headers:
      {
        'Authorization': localStorage.getItem('jwtToken')
      }
      })
      .then(res => res.json())
      .then(data => this.setState({ devices: data }))
      .then(() => console.log(this.state.devices))
      .catch(err => console.log(err));
    fetch('http://localhost:8080/user', {
      method: 'GET',
      headers:
      {
        'Authorization': localStorage.getItem('jwtToken')
      }
      })
      .then(res => res.json())
      .then(data => this.setState({ users: data }))
      .then(() => console.log(this.state.users))
      .catch(err => console.log(err));
  }

  refreshData = () => {
    fetch('http://localhost:8080/user', {
      method: 'GET',
      headers:
      {
        'Authorization': localStorage.getItem('jwtToken')
      }
      })
      .then(res => res.json())
      .then(data => this.setState({ users: data }))
      .catch(err => console.log(err));

    fetch('http://localhost:8081/devices', {
      method: 'GET',
      headers:
      {
        'Authorization': localStorage.getItem('jwtToken')
      }
      })
      .then(response => response.json())
      .then(data => this.setState({ devices: data }))
      .catch(err => console.log(err));

    this.componentDidMount();
  }

  handleDeleteDevice(id) {
    // Send DELETE request to server to delete device with given id
    fetch(`http://localhost:8081/device/${id}`, {
      method: 'DELETE',
      headers:
      {
        'Authorization': localStorage.getItem('jwtToken')
      }
    })
      .then(res => {
        if (res.ok) {
          // Remove deleted device from state
          this.setState(prevState => ({
            devices: prevState.devices.filter(device => device.uuid !== id)
          }));
          alert('Device deleted successfully');
        }
        else {
          console.log('Error deleting device');
        }
      })
      .catch(err => console.log(err));
  }

  handleEditDevice(device) {
    console.log("handle edit device: " + device)
    fetch(`http://localhost:8081/device/${device.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('jwtToken')
      },
      body: JSON.stringify({
        userUuid: device.userId,
        description: device.description,
        address: device.address,
        maxHourlyConsumption: device.maxHC
      })
    })
      .then(res => {
        if (res.ok) {
          console.log('Device updated successfully');
          alert('Device updated successfully');
          this.componentDidMount();
        }
        else {
          console.log('Error updating device');
          alert('Error updating device');
        }
      })
      .catch(err => console.log(err));
  }

  handleAddDevice() {
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('jwtToken')
      },
      body: JSON.stringify({
          userUuid: this.state.newDeviceUserUuid,
          description: this.state.newDeviceDescription,
          address: this.state.newDeviceAddress,
          maxHourlyConsumption: this.state.newDeviceConsumption
      })
    };
    

    fetch('http://localhost:8081/device', requestOptions)
      .then(res => {
        if (res.ok) {
          console.log('Device added successfully');
          alert('Device added successfully');
          this.componentDidMount();
        }
        else {
          console.log('Error adding device');
          alert('Error adding device');
        }
      })
      .catch(err => console.log(err));
  }
  
  handleChange(event) {
    const { name, value } = event.target;
    console.log("name: " + name + " value: " + value);
    this.setState({
        [name]: value
    });
  }

  render() {
    const columns = [
      { field: 'id', headerName: 'Device Id', editable: false, width: 300 },
      { field: 'userId', headerName: 'User', editable: true, width: 300},
      { field: 'description', headerName: 'Description', editable: true, width: 300 },
      { field: 'address', headerName: 'Address', editable: true, width: 300 },
      { field: 'maxHC', headerName: 'Max Hourly Consumption', editable: true, width: 100},
      { field: 'actions', headerName: 'Actions', sortable: false, width: 200,
        renderCell: (params) => {
          const device = params.row;
          return (
            <>
              <Button variant="contained" color="primary" onClick={() => this.handleEditDevice(device)}>
                Edit
              </Button>
              <Button variant="contained" color="error" onClick={() => this.handleDeleteDevice(device.id)}>
                Delete
              </Button>
            </>
          );
        }
      }
    ];

    const rows = this.state.devices.map(device => ({ id: device.uuid, userId: device.userUuid, //userId: this.state.users.find(user => user.uuid === device.userUuid).username, 
      description: device.description, address: device.address, maxHC: device.maxHourlyConsumption }));

    return (
      <Box sx={{ p: 3 }}>
        <h1>Devices</h1>
        <DataGrid
          columns={columns}
          rows={rows}
          pageSize={10}
          rowsPerPageOptions={[10]}
          autoHeight
        />
        <br/>
        <Grid>
          <FormControl variant="outlined" sx={{ minWidth: 120 }}>
            <InputLabel id="demo-simple-select-label">User</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              name='newDeviceUserUuid'
              onChange={this.handleChange}
              value={this.state.newDeviceUserUuid}
              required
            >
              {this.state.users.map(user => (
                <MenuItem value={user.uuid}>{user.username}</MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
            label="Description"
            name='newDeviceDescription'
            onChange={this.handleChange}
            value={this.state.newDeviceDescription}
            variant="outlined"
            required
          />
          <TextField
            label="Address"
            name='newDeviceAddress'
            onChange={this.handleChange}
            value={this.state.newDeviceAddress}
            variant="outlined"
            required
          />
          <TextField
            label="Max Hourly Consumption"
            name='newDeviceConsumption'
            onChange={this.handleChange}
            value={this.state.newDeviceConsumption}
            variant="outlined"
            required
          />
          <br/>
          <Button variant="contained" color="success" onClick={this.handleAddDevice}>
            Add New Device
          </Button>
          <Button variant="contained" color="primary" onClick={this.refreshData}>
            Refresh
          </Button>
        </Grid>
      </Box>
    );
  }
}
