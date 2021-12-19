import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {Container, Table} from "react-bootstrap";

const getTeamsURL = "http://localhost:8080/api/teams";

interface Team {
    id: number
    naam : string,
    managerDTO : {
        userDTO: {
            username: string,
        }
    },
    actief: boolean,
}

const SpelerTeams = () => {

    const [teams, setTeams] = React.useState<Team[]>([]);

    React.useEffect(() => {
        axios.get<Team[]>(getTeamsURL, {
            withCredentials: true
        }).then((response) =>{
            setTeams(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }, []);

    if(!teams) return null;

    return(
        <>
            <Container className="bg-dark text-white-50">
                <h1>Teams</h1>
                <br/>
                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>naam</th>
                        <th>manager</th>
                        <th>actief</th>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        teams.map(team => (
                            <tr key={team.id}>
                                <td>{team.id}</td>
                                <td>{team.naam}</td>
                                <td>{team.managerDTO.userDTO.username}</td>
                                <td>{team.actief ? "Ja" : "Nee" }</td>
                                <td><Link to={"/SpelerTeams/" + team.id}>Details</Link></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </Table>
            </Container>

        </>
    )
}

export default SpelerTeams