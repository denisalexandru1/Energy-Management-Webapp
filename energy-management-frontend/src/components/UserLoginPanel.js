import React from 'react';
import { Typography, Box, TextField, Button } from '@mui/material';

export default class UserLoginPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      isAdmin: false
    };
    this.logInListener = this.logInListener.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  logInListener() {
    const requestOptions = {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify({
        username: this.state.username,
        password: this.state.password
      })
    };

    fetch('http://localhost:8082/login', requestOptions)
      .then(res => {
        if (res.ok) {
          return res.json();
        } else {
          throw new Error('Login failed');
        }
      })
      .then(body => {
        console.log(body.token);

        if (body.token !== undefined) {
          this.props.setToken(body.token);
          console.log("Token saved");
          this.props.setLoggedIn();
          this.props.setIsAdmin(body.user.isAdmin);
          this.props.setLoggedUserId(body.user.uuid);
          this.props.setLoggedUsername(body.user.username);

          console.log("Logged in successfully");
          alert("Logged in successfully");
        } else {
          alert("invalid token: " + body.token)
          console.log("Invalid username or password");
          alert("Invalid username or password");
        }
      })
      .catch(err => {
        console.log(err);
        alert("Error during login");
      });
  }


  
  handleChange(event) {
    const { name, value } = event.target;
    this.setState({
      [name]: value
    });
  }

  render() {
    return (
      <Box sx={{ p: 3 }} allign="center">
        <Typography variant="h4" sx={{ mb: 2 }}>
          Login Panel
        </Typography>
        <Box sx={{ display: 'flex', flexDirection: 'column', maxWidth: '400px' }}>
          <TextField
            label="Username"
            name="username"
            onChange={this.handleChange}
            value={this.state.username}
            variant="outlined"
            sx={{ mb: 2 }}
          />
          <TextField
            label="Password"
            name="password"
            type="password"
            onChange={this.handleChange}
            value={this.state.password}
            variant="outlined"
            sx={{ mb: 2 }}
          />
          <Button variant="contained" onClick={this.logInListener}>
            Log In
          </Button>
        </Box>
      </Box>
    );
  }
}
