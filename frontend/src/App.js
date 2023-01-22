import './App.css';
// import 'antd/dist/antd.css'; Doesn't work. No idea why. // TODO make this work, so the table looks nice
import React, { useState } from 'react';
import LoadTable from './LoadTable';

function App() {
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
                              result['selected'] = true;
                              return result;
                          });

        const options = {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        };

        const res = await fetch('http://localhost:8080/invoice', options); // TODO use a constant for the URL
        const json = await res.text(); //TODO the backend should return .json();
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
