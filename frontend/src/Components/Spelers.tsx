import {Table} from "react-bootstrap";
import axios from 'axios';
import React from "react";

const getSpelersURL = "http://localhost:8080/spelers";

const Spelers = () => {
    const [spelers, setSpelers] = React.useState(null);

    React.useEffect(() => {
        axios.get(getSpelersURL, {
            headers: {

            }
        }).then((response) =>{
           setSpelers(response.data);
        });
    }, []);

    if (!spelers) return null;

    return(
        <>
            <h1>Spelers</h1>

            <Table striped bordered hover variant={'dark'}>
                <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>speler.id</td>
                        <td>speler.username</td>
                        <td>speler.voornaam</td>
                        <td>speler.achternaam</td>
                    </tr>
                </tbody>
            </Table>
        </>
    )
}

export default Spelers