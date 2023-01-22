import './App.css';
// import 'antd/dist/antd.css'; Doesn't work. No idea why.
import React from 'react';
import { Button } from 'antd';
import LoadTable from './LoadTable';

function App() {
  return (
    <div>
      <LoadTable />
      <Button>Click me</Button>
    </div>
  );
}

export default App;
