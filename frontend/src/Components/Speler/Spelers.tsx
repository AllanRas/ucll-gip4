import {Container, Table} from "react-bootstrap";
import axios from 'axios';
import React from "react";
import {Link} from "react-router-dom";
import user from "../Login";


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
    Object.freeze(user);

    const [spelers, setSpelers] = React.useState<Speler[]>([]);

    // get spelers from api
    React.useEffect(() => {
        axios.get<Speler[]>(getSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            console.log(response.data);
            setSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }, []);

    if(!spelers) return null;

    return(
        <>
            <Container className="bg-dark text-white-50">
                <h1>Spelers</h1>
                <br/>
                <Link to={"/AddSpeler"}>Speler toevoegen</Link>
                <br/>
                <br/>
                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                        <th> </th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        spelers.map(speler => (
                            <tr key={speler.id}>
                                <td>{speler.id}</td>
                                <td>{speler.userDTO.username}</td>
                                <td>{speler.userDTO.voornaam}</td>
                                <td>{speler.userDTO.achternaam}</td>
                                <td><Link to={"/Spelers/" + speler.id}>Details</Link></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </Table>
            </Container>
        </>
    )
}

export default Spelers