import React from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import Home from './components/Home';
import LogInHome from './components/LogInHome';
import AdminDashboard from './components/AdminDashboard';
import UserDashboard from './components/UserDashboard';
import DeviceDetails from './components/DeviceDetails';

function App() {
  return (
    <Router>
      <AppBar position="static" style = {{backgroundColor: "#FFBF00"}}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Energy Management System
          </Typography>
          <Button href = "/" color="inherit">Home</Button>
          <Button href = "/login-panel" color="inherit">Log In</Button>
        </Toolbar>
      </AppBar>
      <Routes>
        <Route exact path="/" element={<Home/>} />
        <Route path="/login-panel" element={<LogInHome/>} />
        <Route exact path="/admin-view" element={<AdminDashboard/>} />
        <Route exact path="/user-view" element={<UserDashboard/>} />
        <Route exact path="/device/:id" element={<DeviceDetails/>}/>
        <Route path="*" component={() => "404 NOT FOUND"} />
      </Routes>
    </Router>
  );
}

export default App;
