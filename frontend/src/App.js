import './App.css';
// import 'antd/dist/antd.css'; Doesn't work. No idea why. // TODO make this look nice
import React, { useState } from 'react';
import LoadTable from './LoadTable';

function App() {
    const [response, setResponse] = useState('');

    const handleClick = async (event) => {
        event.preventDefault()
        setResponse(null);
        const formData = new FormData(event.target);

        const options = {
            method: 'POST',
            body: formData
        };

        const res = await fetch('http://localhost:8080/invoice', options); // TODO use a constant for the URL
        const json = await res.json();
        setResponse("http://localhost:8080/invoices/" + json);
    }
  
  return (
    <form onSubmit={handleClick}>
        <LoadTable />
        <button type="submit">Generate invoice</button>
        { response ? 
        <div>
            <a href={response}>Download generated invoice</a>
        </div>
        : null }
    </form>
  );
}

export default App;
