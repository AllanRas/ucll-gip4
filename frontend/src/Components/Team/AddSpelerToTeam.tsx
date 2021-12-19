import { useNavigate, useParams} from "react-router-dom";
import React, { useEffect, useState} from "react";
import axios from "axios";
import {Button, Container, Table} from "react-bootstrap";

interface Team {
    id : number
    naam : string
    actief : boolean
    spelerDTO : SpelerDTO[]
    managerDTO : {
        id: number
        userDTO: {
            username: string
        }
    }
}

interface SpelerDTO{
    id : number
    reserve : boolean
    spelerid: number
}

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

const AddSpelerToTeam = () => {

    let params = useParams();
    let navigate = useNavigate();

    // get Team
    const getTeamURL = "http://localhost:8080/api/teams/" + params.id + "/getOne";
    const [team, setTeam] = useState<Team>({
        managerDTO: {id: 0, userDTO: {username: ""}},
        spelerDTO: [],
        actief: false,
        naam: "",
        id: 0
        }
    );

    //get spelers
    const getSpelersURL = "http://localhost:8080/api/spelers";
    const [spelers, setSpelers] = React.useState<Speler[]>([]);


    useEffect(() => {
        getTeam();
        getSpelers();
    },[]);

    const getTeam = () => {
        axios.get<Team>(getTeamURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            setTeam(response.data);
        }).catch(err => {
            console.log(err);
        });
    }

    const getSpelers = () => {
        axios.get<Speler[]>(getSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            setSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }


    //add speler to team
    const teamsURL = "http://localhost:8080/api/teams";
    const PostAddSpeler = async (spelerId : number , reserve: boolean) => {
        await axios.post<Team>(teamsURL + "/" + params.id + "/AddSpeler/"+  + spelerId + "/" + reserve,{}, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            getTeam();
        }).catch(err => {
            console.log(err);
        });
    };

    const DeleteSpeler = (spelerid : number, teamid : number) => {
        const deleteSpelerURL =  "http://localhost:8080/api/teams/" + teamid +"/Delete/" + spelerid
        axios.delete(deleteSpelerURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            getTeam();
        }).catch(err => {
            console.log(err);
        });
    }

    const ReservePromoveren = (spelerid : number, teamid : number) => {
        const reservePromoverenURL =  "http://localhost:8080/api/teams/" + teamid +"/ReservePromoveren/" + spelerid
        axios.put(reservePromoverenURL, {},{
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            getTeam();
        }).catch(err => {
            console.log(err);
        });
    }

    return (
        <>
            <Container className="col-md-11 bg-dark text-white-50">



                <h1>team : {team.naam}</h1>
                <br/>
                <Button onClick={() => navigate(-1)}>Terug</Button>
                <h2>manager: {team.managerDTO.userDTO.username} </h2>
                <h2>Spelers : </h2>

                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                        <th className="col-md-2"></th>
                        <th className="col-md-2"></th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        spelers.map(speler => (
                                team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id && !spelerDTO.reserve) ?
                                    <tr key={speler.id}>
                                        <td>{speler.id}</td>
                                        <td>{speler.userDTO.username}</td>
                                        <td>{speler.userDTO.voornaam}</td>
                                        <td>{speler.userDTO.achternaam}</td>
                                        <td><Button onClick={() => ReservePromoveren(speler.id, team.id)}> degraderen </Button></td>
                                        <td><Button onClick={() => DeleteSpeler(speler.id, team.id)}> Verwijderen </Button></td>
                                    </tr>
                            : ""
                            )
                        )
                    }

                    </tbody>
                </Table>

                <h2>Reserve spelers :</h2>

                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                        <th className="col-md-2"></th>
                        <th className="col-md-2"></th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        spelers.map(speler => (
                                team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id && spelerDTO.reserve) ?
                                    <tr key={speler.id}>
                                        <td>{speler.id}</td>
                                        <td>{speler.userDTO.username}</td>
                                        <td>{speler.userDTO.voornaam}</td>
                                        <td>{speler.userDTO.achternaam}</td>
                                        <td><Button onClick={() => ReservePromoveren(speler.id, team.id)}> Promoveren </Button></td>
                                        <td><Button onClick={() => DeleteSpeler(speler.id, team.id)}> Verwijderen </Button></td>
                                    </tr>
                                    : ""
                            )
                        )
                    }

                    </tbody>
                </Table>


                <h2>Spelers Toevoegen</h2>

                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                        <th className="col-md-1"> speler </th>
                        <th className="col-md-1"> reserve </th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        spelers.map(speler => (
                            speler.actief?
                            team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) ? "" :
                                <tr key={speler.id}>
                                    <td>{speler.id}</td>
                                    <td>{speler.userDTO.username}</td>
                                    <td>{speler.userDTO.voornaam}</td>
                                    <td>{speler.userDTO.achternaam}</td>
                                    <td><Button className="btn-success" onClick={() => PostAddSpeler(speler.id, false)}> + </Button></td>
                                    <td><Button className="btn-warning" onClick={() => PostAddSpeler(speler.id, true)}> + </Button></td>
                                </tr>
                                : ""
                            )
                        )
                    }

                    </tbody>
                </Table>


            </Container>
        </>
    )
}

export default AddSpelerToTeam