import React from 'react';

export default function Home() {

  localStorage.clear();

  return (
    <div style={{ textAlign: "center" }}>
      <h1>Welcome to the Energy Management App</h1>
      <p>Please register or log in if you already have an account!</p>
    </div>
  );
}