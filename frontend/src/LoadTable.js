import React, { useState, useEffect } from 'react';

function LoadTable() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/data')
            .then(response => response.json())
            .then(data => setData(data));
    }, []);

    return (
        <table>
            <tr>
                <th>Name</th>
                <th>Amount</th>
                <th>Selected</th>
            </tr>
            {data.map(item => (
            <tr>
                <td>{item.name}</td>
                <td>{item.amount}</td>
                <td>
                    <input type="checkbox" id={item.name + "-selected"} name={item.name} />
                </td>
            </tr>
            ))}
          </table>
    );
}

export default LoadTable;