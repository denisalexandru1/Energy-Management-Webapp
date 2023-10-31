import React from 'react';
import { Box, Button, Grid, TextField, MenuItem, FormControlLabel, Checkbox } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

export default class DevicesOfUser extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            devices: []
        };
    }

    componentDidMount() {
        const requestOptions = {
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'GET'
        };

        fetch('http://localhost:8081/device/user/' + this.props.userId, requestOptions)
            .then(res => {
                if (res.ok) {
                    res.json().then(body => {
                        console.log(body);
                        this.setState({
                            devices: body
                        });
                    })
                        .catch(err => {
                            console.log(err);
                        })
                }
                else {
                    console.log("Error");
                    alert("Error");
                }
            })
    }

    render() {
        const columns = [
            {field : 'id', headerName: 'Id', width: 350},
            {field : 'description', headerName: 'Description', width: 150},
            {field : 'address', headerName: 'Address', width: 150},
            {field : 'maxHC', headerName: 'Max Hourly Consumption', width: 150}
        ];

        const rows = this.state.devices.map(device => ({id: device.uuid, description: device.description, address: device.address, maxHC: device.maxHourlyConsumption}));

        return (
            <Box sx = {{p: 3}}>
                <h2>My Devices</h2>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    autoHeight
                />
            </Box>
        );
    }
}

