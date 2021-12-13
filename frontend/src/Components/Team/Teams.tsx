import user from "../Login";
import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {Table} from "react-bootstrap";

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

const Teams = () => {

    const [teams, setTeams] = React.useState<Team[]>([]);

    React.useEffect(() => {
        axios.get<Team[]>(getTeamsURL, {
            withCredentials: true
        }).then((response) =>{
            console.log(response.data);
            setTeams(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }, []);

    if(!teams) return null;

    return(
        <>
            <h1>Teams</h1>
            <br/>
            <Link to={"/AddTeam"}>Team toevoegen</Link>
            <br/>
            <br/>
            <Table striped bordered hover variant={'dark'}>
                <thead>
                <tr>
                    <th>id</th>
                    <th>naam</th>
                    <th>manager</th>
                    <th>actief</th>
                    <th> </th>
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
                            <td><Link to={"/Teams/" + team.id}>Details</Link></td>
                        </tr>
                    ))
                }
                </tbody>
            </Table>
        </>
    )
}

export default Teams