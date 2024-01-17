import React, { useState, useEffect, useRef } from 'react';
import { Box } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import Chart from 'chart.js';

const DevicesDetails = (props) => {
    const [measurements, setMeasurements] = useState([]);
    const [date, setDate] = useState(null);
    const [hourlyDifferences, setHourlyDifferences] = useState([]);
    const deviceUuid = window.location.href.split('/')[window.location.href.split('/').length - 1];
    const chartRef = useRef(null); // Reference to the chart canvas
    useEffect(() => {
        if (date) {
            getNewMeasurements();
        }
    }, [date]);

    useEffect(() => {
        updateHourlyDifferences(); // Call updateHourlyDifferences when measurements change
      }, [measurements]);
    

    useEffect(() => {
        console.log("measurements")
        console.log(measurements)
    }, [measurements]);

    const getNewMeasurements = () => {
        console.log("getNewMeasurements")
        console.log(deviceUuid)
        console.log(date)

        const dateObj = new Date(date);
        const formattedDate = `${dateObj.getFullYear()}-${String(dateObj.getMonth() + 1).padStart(2, '0')}-${String(dateObj.getDate()).padStart(2, '0')}`;

        console.log(formattedDate)
        fetch(`http://localhost:8090/measurements-by-day/${deviceUuid}/${formattedDate}`, {
            method: 'GET',
            headers: {
                'Authorization': localStorage.getItem('jwtToken')
            }
        })
        .then(res => res.json())
        .then(data => setMeasurements(data))
        .catch(err => console.log(err));updateHourlyDifferences();
    };
  
    const updateHourlyDifferences = () => {
        const hourlyDiff = {};
    
        // Initialize hourly differences for each hour of the day
        for (let i = 0; i < 24; i++) {
          hourlyDiff[i] = [];
        }
    
        // Calculate differences based on timestamp intervals for each hour
        measurements.forEach((measurement) => {
          const timestamp = new Date(measurement.timestamp);
          const hour = timestamp.getHours();
    
          hourlyDiff[hour].push(measurement.value);
        });
    
        const hourlyDifferencesData = [];
    
        // Calculate hourly differences
        for (let i = 0; i < 24; i++) {
          const values = hourlyDiff[i];
          if (values.length >= 2) {
            const hourlyDiffValue = values[values.length - 1] - values[0]; // Calculate difference
            hourlyDifferencesData.push(hourlyDiffValue);
          } else {
            hourlyDifferencesData.push(0); // If no measurements in the hour, set difference as 0
          }
        }
    
        const chartData = {
          labels: Array.from({ length: 24 }, (_, i) => `${i}:00`), // Generate labels for all 24 hours
          datasets: [
            {
              label: 'Hourly Differences',
              data: hourlyDifferencesData,
              backgroundColor: 'rgba(75, 192, 192, 0.6)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1,
            },
          ],
        };
    
        setHourlyDifferences(chartData);
      };
  
    useEffect(() => {
      // Draw the chart after setting the hourly differences data
      if (chartRef.current && hourlyDifferences.labels && hourlyDifferences.datasets) {
        new Chart(chartRef.current, {
          type: 'bar',
          data: hourlyDifferences,
          options: {
            responsive: true,
            scales: {
              x: {
                title: {
                  display: true,
                  text: 'Hour',
                },
              },
              y: {
                title: {
                  display: true,
                  text: 'Hourly Differences',
                },
              },
            },
          },
        });
      }
    }, [hourlyDifferences]);
  
    return (
        <Box sx={{ p: 3 }}>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker label="Pick a date" value={date} onChange={(newValue) => setDate(newValue)} />
          </LocalizationProvider>
    
          {/* Render the chart */}
          <canvas ref={chartRef} width="400" height="200"></canvas>
        </Box>
      );
}

export default DevicesDetails;