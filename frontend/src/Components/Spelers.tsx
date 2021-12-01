import {Table} from "react-bootstrap";
import axios from 'axios';
import React from "react";

const getSpelersURL = "http://localhost:8080/api/spelers";

interface Speler {
    id: number;
    actief: boolean;
    adresDTO: {
        id: number;
        gemeente: string;
        straat: string;
        huisnummer: string;
        postcode:string;
    }
    geboortedatum: string;
    userDTO: {
        id: number;
        achternaam: string;
        email: string;
        role: string;
        username: string;
        voornaam: string;
    }
}

const Spelers = () => {
    const [spelers, setSpelers] = React.useState<Speler[]>([]);

    React.useEffect(() => {
        axios.get<Speler[]>(getSpelersURL, {
            auth: {
                username: 'manager',
                password: 'manager'
            }
        }).then((response) =>{
            console.log(response.data);
            setSpelers(response.data);
        });
    }, []);

    if(!spelers) return null;

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
                    {
                        spelers.map(speler => (
                            <tr>
                                <td>{speler.id}</td>
                                <td>{speler.userDTO.username}</td>
                                <td>{speler.userDTO.voornaam}</td>
                                <td>{speler.userDTO.achternaam}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </Table>
        </>
    )
}

export default Spelers