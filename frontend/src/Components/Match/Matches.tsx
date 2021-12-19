import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {Container, Table} from "react-bootstrap";
import moment from "moment";

const getSpelersURL = "http://localhost:8080/api/matches";

interface Match {
    id : number
    teamBlue : Team
    teamRed : Team
    scoreBlueTeam : number
    scoreRedTeam : number
    datumtijd: Date
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
                        <th className="col-md-0">id</th>
                        <th className="col-md-3">Team Blue</th>
                        <th className="col-md-3">Team Red</th>
                        <th>won</th>
                        <th className="col-md-0">blue - red</th>
                        <th className="col-md-2">datum en tijd</th>
                        <th className="col-md-2"> </th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        matches.map(match => (
                            <tr key={match.id}>
                                <td>{match.id}</td>
                                <td>{match.teamBlue.naam}</td>
                                <td>{match.teamRed.naam}</td>
                                <td>{
                                    match.scoreRedTeam === 0 && match.scoreBlueTeam === 0 ? "---":
                                    match.scoreRedTeam < match.scoreBlueTeam ? match.teamBlue.naam :  match.teamRed.naam
                                }
                                </td>
                                <td>{match.scoreBlueTeam} - {match.scoreRedTeam}</td>
                                <td>{moment(match.datumtijd).format('DD-MM-YYYY hh:mm')}</td>
                                <td><Link to={"/Matches/" + match.id}>Details & Score</Link></td>
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