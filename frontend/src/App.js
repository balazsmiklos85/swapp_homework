import './App.css';
// import 'antd/dist/antd.css'; Doesn't work. No idea why. // TODO make this work, so the table looks nice
import React, { useState } from 'react';
import LoadTable from './LoadTable';

function App() {
    const backendUrl = 'http://localhost:8080'

    const [response, setResponse] = useState('');

    const handleClick = async (event) => {
        event.preventDefault()
        setResponse(null);

        const form = event.target
        var data = Array.from(form)
                          .filter(child => child.tagName === 'INPUT')
                          .filter(input => input.checked)
                          .map(input => {
                              var result = {}
                              result['name'] = input.name;
                              return result;
                          });

        const options = {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        };

        const res = await fetch(backendUrl + "/invoice", options);
        const json = await res.json();
        setResponse( backendUrl + "/invoices/" + json.id);
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
