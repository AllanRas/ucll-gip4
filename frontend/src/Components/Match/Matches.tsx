import user from "../Login";
import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {Container, Table} from "react-bootstrap";

const getSpelersURL = "http://localhost:8080/api/matches";

interface Match {
    id : number
    teamBlue : Team
    teamRed : Team
    scoreBlueTeam : number
    scoreRedTeam : number
}

interface Team {
    id : number
    naam : string
    actief : boolean
    managerDTO : {
        id: number
        userDTO: {
            achternaam: string
            email: string
            id: number
            role: string
            username: string
            voornaam: string
        }
    }
    spelerDTO : SpelerDTO[]
}

interface SpelerDTO{
    id : number
    reserve : boolean
    spelerid: number
}

const Matches = () => {
    const [matches, setMatches] = React.useState<Match[]>([]);

    // get spelers from api
    React.useEffect(() => {
        axios.get<Match[]>(getSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            console.log(response.data);
            setMatches(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }, []);

    if(!matches) return null;

    return(
        <>

            <Container className="bg-dark text-white-50">
                <h1>Matches</h1>
                <br/>
                <Link to={"/AddMatch"}>Match aanmaken</Link>
                <br/>
                <br/>
                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>Team Blue</th>
                        <th>Team Red</th>
                        <th>score blue</th>
                        <th>score red</th>
                        <th> </th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        matches.map(match => (
                            <tr key={match.id}>
                                <td>{match.id}</td>
                                <td>{match.teamBlue.naam}</td>
                                <td>{match.teamRed.naam}</td>
                                <td>{match.scoreBlueTeam}</td>
                                <td>{match.scoreRedTeam}</td>
                                <td><Link to={"/Matches/" + match.id}>Details</Link></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </Table>
            </Container>
        </>
    )
}

export default Matches